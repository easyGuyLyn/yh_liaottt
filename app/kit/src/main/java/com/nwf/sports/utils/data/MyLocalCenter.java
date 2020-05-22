package com.nwf.sports.utils.data;

import android.text.TextUtils;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-04-01
 * <p>修改人：Simon
 * <p>修改时间：2019-04-01
 * <p>修改备注：
 **/
public class MyLocalCenter {

    public static final long INTERVAL_BOUND_PHONE = 10 * 60 * 1000L;

    /**
     * 手机号验证时间
     */
    public void recordBoundPhoneEvent() {
        SPTool.put(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.KEY_TIME_BOUND_PHONE, System.currentTimeMillis());
    }

    /**
     * 检验  检验时间是否大于 10分钟
     *
     * @return
     */
    public boolean boundPhoneRecently() {
        long curTime = System.currentTimeMillis();
        long lasttime = (long) SPTool.get(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.KEY_TIME_BOUND_PHONE, 0l);
        boolean recently = ((curTime - lasttime) <= INTERVAL_BOUND_PHONE);
        Timber.d("boundPhoneRecently %s cur:%d - last:%d = %d", String.valueOf(recently), curTime, lasttime, curTime - lasttime);
        return recently;
    }

    /**
     * 保存用户余额
     *
     * @param balance
     */
    public void saveBalance(String balance) {
        Timber.d("保存余额:%s", balance);
        SPTool.put(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue._BALANCE, balance);
    }

    /**
     * 获取用户余额
     *
     * @return
     */
    public String getBalance() {
        return (String) SPTool.get(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue._BALANCE, "0");
    }

    /**
     * 保存用户bq存款选择的银行
     * type 区分银行
     *
     * @param bank
     */
    public void saveDepositTransferBank(String bank, String type) {
        SPTool.put(type + ConstantValue.DEPOSIT_TRANSFER_BANK, bank);
    }

    /**
     * 获取用户bq存款选择的银行
     * type 区分银行
     *
     * @return
     */
    public String getDepositTransferBank(String type) {
        return (String) SPTool.get(type + ConstantValue.DEPOSIT_TRANSFER_BANK, "");
    }

    /**
     * 保存用户bq存款输入的名字
     *
     * @param name
     */
    public void saveDepositTransferName(String name) {
        SPTool.put(ConstantValue.DEPOSIT_TRANSFER_NAME, name);
    }

    /**
     * 获取用户bq存款输入的名字
     *
     * @return
     */
    public String getDepositTransferName() {
        return (String) SPTool.get(ConstantValue.DEPOSIT_TRANSFER_NAME, "");
    }

    /**
     * 保存用户bq存款选择的支付类型
     *
     * @param name
     */
    public void saveDepositTransferType(String name) {
        SPTool.put(ConstantValue.DEPOSIT_TRANSFER_TYPE, name);
    }

    /**
     * 获取用户bq存款选择的支付类型
     *
     * @return
     */
    public String getDepositTransferType() {
        return (String) SPTool.get(ConstantValue.DEPOSIT_TRANSFER_TYPE, "");
    }

    /**
     * 保存客服用户回拨电话
     *
     * @param name
     */
    public void saveServiceCallback(String name) {
        SPTool.put(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.SERVICE_CALLBACK, name);
    }

    /**
     * 获取客服用户回拨电话
     *
     * @return
     */
    public String getServiceCallback() {
        return (String) SPTool.get(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.SERVICE_CALLBACK, "");
    }


    /**
     * 保存首页bnner 和优惠
     */
    public void saveHomePage(HomeDiscountsResult homeDiscountsResult) {
        Gson gson = new Gson();
        String homeDiscountsString = "";
        try {
            homeDiscountsString = gson.toJson(homeDiscountsResult);
        } catch (Exception e) {
            homeDiscountsString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.HOMEPAGE, homeDiscountsString);
        LogUtils.e("saveHomePage ==" + SPTool.get(ConstantValue.HOMEPAGE, ""));
    }

    /**
     * 获取首页bnner 和优惠
     *
     * @return
     */
    public HomeDiscountsResult getHomePage() {
        HomeDiscountsResult items = new HomeDiscountsResult();
        String HomeDiscountsString = (String) SPTool.get(ConstantValue.HOMEPAGE, "");
        if (!TextUtils.isEmpty(HomeDiscountsString)) {
            try {
                items = new Gson().fromJson(HomeDiscountsString, HomeDiscountsResult.class);
                LogUtils.e("getHomePage == " + items.toString());
            } catch (Exception e) {
                items = null;
                LogUtils.e("解析json异常");
                return items;
            }
        }
        return items;
    }


    /**
     * 保存存款列表数据
     */
    public void saveDeposit(DepositMannersVo depositMannersVo) {
        Gson gson = new Gson();
        String depositMannersVoString = "";
        try {
            depositMannersVoString = gson.toJson(depositMannersVo);
        } catch (Exception e) {
            depositMannersVoString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.DEPOSIT, depositMannersVoString);
        LogUtils.e("saveDeposit ==" + SPTool.get(ConstantValue.BANKNUMBER_LIST, ""));
    }

    /**
     * 获取存款列表数据
     *
     * @return
     */
    public DepositMannersVo getDepositManners() {
        DepositMannersVo items = null;
        String DepositString = (String) SPTool.get(ConstantValue.DEPOSIT, "");
        if (!TextUtils.isEmpty(DepositString)) {
            try {
                items = new Gson().fromJson(DepositString, DepositMannersVo.class);
                LogUtils.e("getDepositManners == " + items.toString());
            } catch (Exception e) {
                items = null;
                LogUtils.e("解析json异常");
                return items;
            }
        }
        return items;
    }


    /**
     * 保存用户存款输入的金额
     *
     * @param mount
     */
    public void saveDepositMount(String mount) {
        SPTool.put(ConstantValue.DEPOSITLASTAMOUNT, mount);
    }

    /**
     * 获取用户存款输入的金额
     *
     * @return
     */
    public String getDepositMount() {
        return (String) SPTool.get(ConstantValue.DEPOSITLASTAMOUNT, "");
    }


    /**
     * 保存 下载app过程的 数据
     * 用于下载app时记录数据
     */
    public void saveDownloadApp(List<DownloadAppResult.AppsBean> appsBeans) {
        Gson gson = new Gson();
        String downloadString = "";
        try {
            downloadString = gson.toJson(appsBeans);
        } catch (Exception e) {
            downloadString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.DOWNLOAD_APPS, downloadString);
        LogUtils.e("saveDownloadApp ==" + SPTool.get(ConstantValue.DOWNLOAD_APPS, ""));
    }

    /**
     * 获取下载app过程的 数据
     * 用于下载app时记录数据
     *
     * @return
     */
    public List<DownloadAppResult.AppsBean> getDownloadApp() {
        List<DownloadAppResult.AppsBean> items = new ArrayList<>();
        String downloadString = (String) SPTool.get(ConstantValue.DOWNLOAD_APPS, "");
        if (!TextUtils.isEmpty(downloadString)) {
            try {
                items = new Gson().fromJson(downloadString, new TypeToken<List<DownloadAppResult.AppsBean>>() {
                }.getType());
            } catch (Exception e) {
                LogUtils.e("解析json异常");
                return items;
            }
        }
        LogUtils.e("getDownloadApp == " + items.toString());
        return items;
    }

    /**
     * 保存 下载app 数据
     * 用与首页缓存
     */
    public void saveDownload(DownloadAppResult downloadAppResult) {
        Gson gson = new Gson();
        String downloadString = "";
        try {
            downloadString = gson.toJson(downloadAppResult);
        } catch (Exception e) {
            downloadString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.DOWNLOAD_APPS_DATA, downloadString);
        LogUtils.e("saveDownload ==" + SPTool.get(ConstantValue.DOWNLOAD_APPS_DATA, ""));
    }

    /**
     * 获取下载app 数据
     * 用与首页缓存
     *
     * @return
     */
    public DownloadAppResult getDownloadData() {
        DownloadAppResult items = new DownloadAppResult();
        String downloadString = (String) SPTool.get(ConstantValue.DOWNLOAD_APPS_DATA, "");
        if (!TextUtils.isEmpty(downloadString)) {
            try {
                items = new Gson().fromJson(downloadString, DownloadAppResult.class);
            } catch (Exception e) {
                items = null;
                LogUtils.e("解析json异常");
                return items;
            }
        }
        LogUtils.e("getDownloadData == " + items.toString());
        return items;
    }

    /**
     * 保存 游戏列表数据
     */
    public void saveHomeGameResult(List<HomeGameResult> gameResults) {
        Gson gson = new Gson();
        String gameResultsString = "";
        try {
            gameResultsString = gson.toJson(gameResults);
        } catch (Exception e) {
            gameResultsString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.saveGameList(ConstantValue.GAME_LIST, gameResultsString);
        LogUtils.e("saveHomeGameResult ==" + SPTool.get(ConstantValue.GAME_LIST, ""));
    }

    /**
     * 获取游戏列表数据
     *
     * @return
     */
    public List<HomeGameResult> getHomeGameResult() {
        List<HomeGameResult> items = new ArrayList<>();
        String downloadString = SPTool.readGameList(ConstantValue.GAME_LIST, "");
        if (!TextUtils.isEmpty(downloadString)) {
            try {
                items = new Gson().fromJson(downloadString, new TypeToken<List<HomeGameResult>>() {
                }.getType());
            } catch (Exception e) {
                LogUtils.e("解析json异常");
                return items;
            }
        }
        LogUtils.e("getHomeGameResult == " + items.toString());
        return items;
    }


}
