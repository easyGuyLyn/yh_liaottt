package com.nwf.sports.utils.line;

import android.content.Context;
import android.util.Log;

import com.dawoo.coretool.util.SPTool;
import com.google.gson.Gson;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.mvp.model.DomainUrl;
import com.nwf.sports.net.MyHttpClient;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.util.RequestBuilder;
import com.nwf.sports.utils.FlurryAgentUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Simon on 2018/11/7 0007.
 * 线路选择工具类
 */

public class LineHelperUtil {

    private Context mContext;
    public static final String TAG = "LineHelperUtil";
    public static final String LOCAL_DOMAIN = "LOCAL_DOMAIN";  //本地保存的域名
    public static final String LOCAL_DOMAIN_TIME_DIY = "LOCAL_DOMAIN_TIME_DIY";  //本地保存的域名的时间
    private long interval = 60 * 1000 * 60 * 24; //保存超时时间
    private List<Call> mDoCheckLineList = Collections.synchronizedList(new ArrayList<>());  //请求队列
    //-----------------------------------------------------------------------------------------
    public static final int progress_erre_CheckDomain = 50;//检测域名 全部没有通过
    public static final int progress_finish = 100;//操作完成
    //错误信息
    public static final int PING_DOMAIN_ERRE = 404; //Ping 线路没有一个通过
    //------------------------------------------------------------------------------------------
    private LineTaskProgressListener mLineTaskProgressListener;  //回调接口
    private LineErrorDialogBean mLineErrorDialogBean = new LineErrorDialogBean(); //错误信息Bean
    private List<DomainUrl.ListBean> domains = new ArrayList<>(); //线路集合
    private List<String> localityDomains = new ArrayList<>(); //本地线路集合
    private boolean ifStop = false; //是否Ping 通一个域名
    private int domainsNum = 0; //对于判断  线路ping过 数量

    public static List<String> domainError = new ArrayList(); //记录错误日志

    public LineHelperUtil(Context context) {
        this.mContext = context;
    }

    public void setLineTaskProgressListener(LineTaskProgressListener lineTaskProgressListener) {
        mLineTaskProgressListener = lineTaskProgressListener;
    }

    /**
     * 检测线路
     */
    public void pingDomain() {
        // 取出本地域名线路
        String spDomain = (String) SPTool.get(LOCAL_DOMAIN, "");
        long time = (long) SPTool.get(LOCAL_DOMAIN_TIME_DIY, -1l);
        long currentTime = System.currentTimeMillis();
        //        本地缓存暂时不使用  不确定稳定性
        //        if (TextUtils.isEmpty(spDomain)) { //本地没有缓存数据 走正常请求
        //            onGetAvailableDomain();
        //        } else if (currentTime - time > interval) { //本地缓存数据超过时间 将重新拉取线路
        //            onGetAvailableDomain();
        //        } else {
        //            Log.e(TAG, "上次保存在本地的线路: domain: " + spDomain);
        //            callBackProgress(progress_finish_CheckDomain);
        //            boolean isReachable = doPingDomain(spDomain);
        //            if (isReachable) {
        //                Client.setClientDomain(spDomain + "/");
        //            } else {
        //                onGetAvailableDomain();
        //            }
        //
        //        }
        onGetAvailableDomain();

    }

    /**
     * 获取可用域名 并且请求
     */
    public void onGetAvailableDomain() {
        Log.e(TAG, "********************************");
        MyHttpClient myHttpClient = MyHttpClient.getInstance();
        String domainUrl = "https://gwapi.czsjnp.com/static/mobile/E03/qipaiapp.json";
        myHttpClient.executeGet(domainUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, "onFailure:\n" + e.toString());
                Log.e(TAG, "当前高峰期，正在搜索最佳线路...");
                onGetFailureDomain();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.e(TAG, "onResponse body:" + responseText);
                if (response.isSuccessful()) {
                    try {
                        Log.e(TAG, "获取线路成功");
                        DomainUrl domainUrl = new Gson().fromJson(responseText, DomainUrl.class);
                        domains = domainUrl.getList();
                        getLinesFromBossServer();
                    } catch (Exception e) {
                        onGetFailureDomain();
                        Log.e(TAG, "****************获取线路解析失败****************" + e.toString());
                        Map<String, String> comment = new HashMap<>();
                        comment.put("domainJsonError", e.toString());
                        FlurryAgentUtils.backTrackError(ConstantValue.NET_DOMAIN_JSON, comment);
                    }
                } else {
                    Map<String, String> comment = new HashMap<>();
                    comment.put("domainJsonError", "请求失败" + responseText);
                    FlurryAgentUtils.backTrackError(ConstantValue.NET_DOMAIN_JSON, comment);
                    onGetFailureDomain();
                    Log.e(TAG, "****************获取线路失败****************" + response.message());
                }
                call.cancel();
            }
        });
    }

    /**
     * 获取线路后确定线路
     */
    public void getLinesFromBossServer() {
        domainsNum = 0;
        ifStop = false;
        if (domains == null || domains.size() == 0) {
            onGetFailureDomain();
            Log.e(TAG, "****获取线路列表为空***");
            return;
        }
        doCheckDomain(domains.get(domainsNum).getUrl());
    }

    /**
     * 对单个域名进行请求查看是否连通
     *
     * @param domain
     * @return
     */
    public void doCheckDomain(String domain) {
        domainsNum++;
        Log.e(TAG, "********** 开始Check域名" + domain);
        try {
            MyHttpClient myHttpClient = MyHttpClient.getInstance();
            OkHttpClient checkClient = myHttpClient.getCheckClient();
            RequestBody body = new FormBody.Builder().build();
            Request request = new Request.Builder().post(body).url(domain + "/api/check/server").build();
            RequestBuilder requestBuilder = new RequestBuilder(IMApplication.getInstance().getClientConfig(), "");
            Call call = checkClient.newCall(requestBuilder.newRequest(request));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "**********Check域名异常");
                    Log.e(TAG, "**********" + e.toString());
                    disposeDefeatedDomain(domain, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    try {
                    if (response.isSuccessful()) {
                        disposeSucceedDomain(domain);
//                            String responseText = response.body().string();
//                            AppTextMessageResponse appTextMessageResponse = new Gson().fromJson(responseText, AppTextMessageResponse.class);
//                            if (appTextMessageResponse.isSuccess()) {
//                                Log.e(TAG, "**********Check域名 请求接口成功");
//                                disposeSucceedDomain(domain);
//                            } else {
//                                Log.e(TAG, "**********Check域名通过 但是appTextMessageResponse.isSuccess() 不是true 失败：" + appTextMessageResponse.getMsg());
//                                disposeDefeatedDomain();
//                            }
                    } else {
                        Log.e(TAG, "**********Check域名  请求接口未通过");
                        disposeDefeatedDomain(domain, "错误" + response.code());
                    }
//                    } catch (Exception e) {
//                        Log.e(TAG, "**********Check域名 请求成功 异常");
//                        Log.e(TAG, "**********" + e.toString());
//                        disposeDefeatedDomain();
//                    }
                }
            });
            mDoCheckLineList.add(call);
        } catch (Exception e) {
            Log.e(TAG, "**********Check域名 请求异常");
            Log.e(TAG, "**********" + e.toString());
            disposeDefeatedDomain(domain, e.toString());
        }
    }

    /**
     * //如果网页版的域名不可访问,还可以访问到本地的URL地址 防止万一~~ 如果本地都不可以访问，那就升级或者重新修改配置文件吧。
     */
    private void onGetFailureDomain() {
        Log.e(TAG, "********************** 对本地线路 进行Check");
        domainsNum = 0;
        localityDomains.add("https://gwapi.czsjnp.com");
        onGetDomain(localityDomains.get(domainsNum));
    }

    /**
     * 对本地URL  进行请求
     *
     * @param domain
     */
    private void onGetDomain(String domain) {
        domainsNum++;
        Log.e(TAG, "********** 开始Check本地域名" + domain);
        try {
            MyHttpClient myHttpClient = MyHttpClient.getInstance();
            OkHttpClient checkClient = myHttpClient.getCheckClient();
            RequestBody body = new FormBody.Builder().build();
            Request request = new Request.Builder().post(body).url(domain + "/checkServerNet").build();
            RequestBuilder requestBuilder = new RequestBuilder(IMApplication.getInstance().getClientConfig(), "");
            Call call = checkClient.newCall(requestBuilder.newRequest(request));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "**********Check本地域名异常");
                    Log.e(TAG, "**********" + e.toString());
                    disposeDefeatedLocalityDomain(domain, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    try {
                    if (response.isSuccessful()) {
                        disposeSucceedDomain(domain);
//                            String responseText = response.body().string();
//                            AppTextMessageResponse appTextMessageResponse = new Gson().fromJson(responseText, AppTextMessageResponse.class);
//                            if (appTextMessageResponse.isSuccess()) {
//                                Log.e(TAG, "**********Check本地域名 请求接口成功");
//                                disposeSucceedDomain(domain);
//                            } else {
//                                Log.e(TAG, "**********Check本地域名通过 但是appTextMessageResponse.isSuccess() 不是true 失败：" + appTextMessageResponse.getMsg());
//                                disposeDefeatedLocalityDomain();
//                            }
                    } else {
                        Log.e(TAG, "**********Check本地域名  请求接口未通过");
                        disposeDefeatedLocalityDomain(domain, response.message());
                    }
//                    } catch (Exception e) {
//                        Log.e(TAG, "**********Check本地域名 请求成功 异常");
//                        Log.e(TAG, "**********" + e.toString());
//                        disposeDefeatedLocalityDomain();
//                    }
                }
            });
            mDoCheckLineList.add(call);
        } catch (Exception e) {
            Log.e(TAG, "**********Check本地域名 请求异常");
            Log.e(TAG, "**********" + e.toString());
            disposeDefeatedLocalityDomain(domain, e.toString());
        }

    }

    /**
     * 处理 访问成功的线路
     * ifStoop  判断是否是第一次 请求成功
     *
     * @param domain
     */
    public void disposeSucceedDomain(String domain) {
        if (!ifStop) {
            ifStop = true;
        } else {
            return;
        }
        RetrofitHelper.setClientDomain(domain + "/");
        Log.e(TAG, "最终的URL地址是：" + RetrofitHelper.baseUrl());
        Log.e(TAG, "**********成功的域名是===" + domain);
        SPTool.put(LOCAL_DOMAIN, domain);
        SPTool.put(LOCAL_DOMAIN_TIME_DIY, System.currentTimeMillis());
        callBackProgress(progress_finish);
    }

    /**
     * 处理 访问失败的线路
     */
    public void disposeDefeatedDomain(String url, String erre) {
        domainError.add(url + " 错误=" + erre);

        if (domainsNum >= domains.size()) {
            Log.e(TAG, "********** Check域名" + domains.size() + "次，均失败********  domainsNum==" + domainsNum);
            onGetFailureDomain();
            return;
        } else {
            doCheckDomain(domains.get(domainsNum).getUrl());
        }

    }

    /**
     * 处理 访问失败的线路 本地
     */
    public void disposeDefeatedLocalityDomain(String url, String erre) {
        domainError.add(url + " 错误=" + erre);

        if (domainsNum >= localityDomains.size()) {
            Log.e(TAG, "********** Check域名" + localityDomains.size() + "次，均失败********  domainsNum==" + domainsNum);
            callBackProgress(progress_erre_CheckDomain);

            Map<String, String> comment = new HashMap<>();

            Gson gson = new Gson();
            String domainErrorString = "";
            try {
                domainErrorString = gson.toJson(domainError);
                comment.put("domainError", domainErrorString);
            } catch (Exception e) {
                domainErrorString = "转json出错";
                comment.put("domainError", domainErrorString);
            }
//            if (domainError != null && domainError.size() > 0) {
//                comment.put("domainError", domainError.get(0));
//            }
            FlurryAgentUtils.backTrackError(ConstantValue.NET_DOMAIN, comment);
            return;
        } else {
            onGetDomain(localityDomains.get(domainsNum));
        }

    }


    /**
     * 向外回调进度
     *
     * @param progress
     */
    private void callBackProgress(int progress) {
        if (mLineTaskProgressListener != null) {
            mLineTaskProgressListener.onProgressBarChange(progress, 100);
        }
    }

    /**
     * 向外简单错误原因
     *
     * @param msg
     */
    private void callBackErrorSimpleReason(String msg) {
        if (mLineTaskProgressListener != null) {
            mLineTaskProgressListener.onErrorSimpleReason(msg);
        }
    }

    /**
     * 向外回调错误 bean
     *
     * @param code
     */
    private void callBackComplexReason(int code) {
        if (mLineTaskProgressListener != null) {
            mLineErrorDialogBean.setCode(code);
            mLineTaskProgressListener.onErrorComplexReason(mLineErrorDialogBean);
        }
    }

    /**
     * 回收使用过的资源
     */
    public void onDestroy() {
        for (Call call : mDoCheckLineList) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
    }
}
