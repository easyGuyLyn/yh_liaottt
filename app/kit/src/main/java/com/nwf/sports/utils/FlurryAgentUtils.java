package com.nwf.sports.utils;

import android.content.Context;
import android.os.Build;

import com.dawoo.coretool.util.NetworkUtils;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.google.gson.Gson;
import com.nwf.sports.IMApplication;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.utils.data.IMDataCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>类描述：FlurryAgent 错误记录
 * <p>创建人：Simon
 * <p>创建时间：2019-05-27
 * <p>修改人：Simon
 * <p>修改时间：2019-05-27
 * <p>修改备注：
 **/
public class FlurryAgentUtils {
    public static String IP = "0.0.0.0";

    /**
     * 固定返回
     *
     * @param type
     * @param typecomment
     */
    public static void backTrackError(final String type, final Map<String, String> typecomment) {
//        MyHttpClient myHttpClient = new MyHttpClient();
//        String domainUrl = "http://ifconfig.me/ip";
//        myHttpClient.executeGet(domainUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                FlurryAgent.logEvent(type, getComment(typecomment, context), true);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                ResponseBody body = response.body();
//                if (body != null) {
//                    String string = body.string();
//                    if (string.length() < 20) {
//                        IP = string;
//                    }
//                }
//                FlurryAgent.logEvent(type, getComment(typecomment, context), true);
//            }
//        });
       // FlurryAgent.logEvent(type, getComment(typecomment), true);
    }

    public static Map<String, String> getComment(Map<String, String> typecomment) {
        Context context = IMApplication.getContext();
        Map<String, String> comment = new HashMap<>();
        String UUID = "";
        try {
            UUID = context.getSharedPreferences("uuid", context.MODE_PRIVATE).getString("uuid", "");
        } catch (Exception e) {
            UUID = "";
        }
        String username = "";
        try {
            UserInfoBean localUserInfo = IMDataCenter.getInstance().getUserInfoBean();
            username = localUserInfo.getUsername();
        } catch (Exception e) {
            username = "";
        }
        String netType = "";
        try {
            netType = String.valueOf(NetworkUtils.getNetworkType());
        } catch (Exception e) {
            netType = "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        comment.put("APP_versions", PackageInfoUtil.getVersionName(context)); //app 版本号
        comment.put("mobile_IP",  NetworkUtils.getIPAddress(true)); //当前IP
        comment.put("UserName", username); //当前UserName
        comment.put("time", format); //当前时间
        for (String s : typecomment.keySet()) {
            try {
                comment.put(s, typecomment.get(s));
            } catch (Exception e) {
                comment.put(s, "");
            }
        }
        Map<String, String> mobile = new HashMap<>();
        mobile.put("mobile_facilityId", UUID); //设备ID
        mobile.put("mobile_type", Build.BRAND + "  " + Build.MODEL); //手机机型
        mobile.put("mobile_versions", Build.VERSION.RELEASE + "  (API:" + Build.VERSION.SDK_INT + ")"); //手机版本号
        mobile.put("mobile_network", netType); //当前网络环境
        Gson gson = new Gson();
        try {
            String mobileString = gson.toJson(mobile);
            comment.put("mobile", mobileString);
        } catch (Exception e) {
            comment.put("mobile", "");
        }
        return comment;
    }


}
