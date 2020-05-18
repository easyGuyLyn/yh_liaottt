package com.nwf.sports;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.file.FileIOUtils;
import com.dawoo.coretool.util.file.FileUtils;
import com.dawoo.coretool.util.packageref.DeviceUtils;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.coretool.util.packageref.Utils;
import com.ivi.imsdk.BuildConfig;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.chat.BaseApp;
import com.nwf.sports.chat.Config;
import com.nwf.sports.chat.viewholder.LocationMessageContentViewHolder;
import com.nwf.sports.net.ClientConfig;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.utils.CommentUtils;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.line.LineErrorDialogBean;
import com.nwf.sports.utils.ssl.SSLSocketFactoryCompat;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.multidex.MultiDex;
import cn.wildfire.chat.kit.WfcUIKit;
import cn.wildfire.chat.kit.conversation.message.viewholder.MessageViewHolderManager;
import cn.wildfire.chat.kit.conversation.message.viewholder.NiuniuRedPacketMessageContentViewHolder;
import cn.wildfire.chat.kit.conversation.message.viewholder.NiuniuRedPacketOverMessageContentViewHolder;
import cn.wildfire.chat.kit.conversation.message.viewholder.RedPacketMessageContentViewHolder;
import cn.wildfire.chat.kit.conversation.message.viewholder.RedPacketNotificationMessageContentViewHolder;
import cn.wildfire.chat.kit.conversation.message.viewholder.SweepRedPacketMessageContentViewHolder;
import cn.wildfirechat.message.NiuniuRedPacketMessageContent;
import cn.wildfirechat.message.NiuniuRedPacketOverMessageContent;
import cn.wildfirechat.message.RedPacketCloseMessageContent;
import cn.wildfirechat.message.RedPacketMessageContent;
import cn.wildfirechat.message.SweepRedPacketMessageContent;
import cn.wildfirechat.message.notification.RedPacketNotificationContent;
import cn.wildfirechat.push.PushService;
import cn.wildfirechat.remote.ChatManager;
import okhttp3.OkHttpClient;

import static com.nwf.sports.net.RetrofitHelper.DEFAULT_READ_TIMEOUT_SECONDS;
import static com.nwf.sports.net.RetrofitHelper.DEFAULT_TIMEOUT_SECONDS;
import static com.nwf.sports.net.RetrofitHelper.DEFAULT_WRITE_TIMEOUT_SECONDS;


public class IMApplication extends BaseApp {
    private static Context context;
    public static Handler handler = new Handler();
    private ClientConfig clientConfig;
    public static boolean isGain = false;  //APP是否 线路登录以及刷新 是否完成
    public static LineErrorDialogBean mLineErrorDialogBean = null;  //线路异常

    private static IMApplication mInstance;
    public static boolean isDownload = false;
    public static boolean isChat = false; //是否是纯聊天不让点击其他页面

    public static IMApplication getInstance() {
        return mInstance;
    }

    //兼容 4.5版本以下 添加MultiDex分包，但未初始化的问题
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        Utils.init(context);
        configClient();
        initOkHttpUtils();
        loadX5();
        initServe();
        initFlurry();
        chatInit();
    }


    public void chatInit() {
        String imHost = (String) SPTool.get(ConstantValue.SAVA_IM_URL, "");
        if (!TextUtils.isEmpty(imHost)) {
            RetrofitHelper.setImDomain(imHost);
        }

//        // bugly，务必替换为你自己的!!!
//        if ("wildfirechat.cn".equals(RetrofitHelper.imUrl())) {
//            CrashReport.initCrashReport(getApplicationContext(), Config.BUGLY_ID, false);
//        }

        // 只在主进程初始化
        if (getCurProcessName(this).equals(BuildConfig.APPLICATION_ID)) {
            WfcUIKit wfcUIKit = WfcUIKit.getWfcUIKit();
            wfcUIKit.init(this);
            wfcUIKit.setAppServiceProvider(AppService.Instance());
            PushService.init(this, BuildConfig.APPLICATION_ID);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(LocationMessageContentViewHolder.class);
            //红包消息
            MessageViewHolderManager.getInstance().registerMessageViewHolder(RedPacketMessageContentViewHolder.class);
            ChatManager.Instance().registerMessageContent(RedPacketMessageContent.class);
            //扫雷红包消息
            MessageViewHolderManager.getInstance().registerMessageViewHolder(SweepRedPacketMessageContentViewHolder.class);
            ChatManager.Instance().registerMessageContent(SweepRedPacketMessageContent.class);
            //扫雷红包消息
            MessageViewHolderManager.getInstance().registerMessageViewHolder(NiuniuRedPacketMessageContentViewHolder.class);
            ChatManager.Instance().registerMessageContent(NiuniuRedPacketMessageContent.class);
            //抢红包消息提示
            ChatManager.Instance().registerMessageContent(RedPacketNotificationContent.class);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(RedPacketNotificationMessageContentViewHolder.class);
            //红包结算的通知消息
            ChatManager.Instance().registerMessageContent(RedPacketCloseMessageContent.class);
            //红包结算的通知消息
            ChatManager.Instance().registerMessageContent(NiuniuRedPacketOverMessageContent.class);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(NiuniuRedPacketOverMessageContentViewHolder.class);
            setupWFCDirs();
        }
    }

    private void setupWFCDirs() {
        File file = new File(Config.VIDEO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.AUDIO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.FILE_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.PHOTO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 加载x5
     */
    void loadX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("loadX5", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 配置OkhttpUtils
     */
    public static void initOkHttpUtils() {
        //设置https
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//失败重连
                .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert)
                .build();

        OkHttpUtils.initClient(client);
    }

    /**
     * 文件加密
     */
    private void configClient() {
        String versionName = PackageInfoUtil.getPackageInfo(getApplicationContext()).versionName;

        String locale = DeviceUtils.getLocaleLanguage(getApplicationContext());
        if (TextUtils.isEmpty(locale)) {
            locale = Locale.SIMPLIFIED_CHINESE.getLanguage();
        }
        String deviceId = DeviceUtils.getAndroidID();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Build.BRAND + Build.SERIAL + Build.DEVICE;
        }
        String filePath = FileUtils.getFilePath(getApplicationContext(), "") + "/markets.txt";
        //先读本地文件，没有的话，再读comments，然后在保存到本地
        String comment = FileIOUtils.readFile2String(filePath);
        if (TextUtils.isEmpty(comment)) {
            comment = CommentUtils.readAPK(new File(getApplicationContext().getPackageCodePath()));
            if (TextUtils.isEmpty(comment)) {
                comment = Constant.CHANNEL_ID;
            }
            FileIOUtils.writeFileFromString(filePath, comment);
        }
        clientConfig = new ClientConfig(Constant.PRODUCT_ID, comment, Constant.PRODUCT_PLATFORM, versionName, locale, deviceId);
        //Client.config(new ClientConfig("e04","android",versionName,locale,deviceId));
        RetrofitHelper.config(clientConfig);
        //Client.setToken("eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNqqVkosTVGyUvIILKmINHL1Ci3xjTBLdQ019k
        // -qMim3tVXSUSouTQIqSExPLi5JLS4xMAAKZRYXA4UMDQwNDAzMDI3MDAyBglklmUDBkqLSVCAntaJAycrQxNLC0tzIwMhcRykvKQ0iYGpiABSoBQAAAP__.9AefImiFGDw73R802b_XKDM
        // -MlGnrPcfVsdal08_lyo");
    }

    /**
     * 在线客服配置
     */
    public void initServe() {
//        try {
//            LIVManager.getInstance().init(getApplicationContext());
//            LIVManager.getInstance().setServerUrl("https://robots800.com/sdk");
//            LIVManager.getInstance().setAppKey("live800_bsliz7w8ymsc");
//            LIVManager.getInstance().setAuthorities(getResources().getString(R.string.provider_auth)); // 非必设
//        } catch (Exception e) {
//            LogUtils.e("************永乐在线客服连接异常*************");
//        }
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }


    /**
     * 雅虎统计
     */
    private void initFlurry() {
//        new FlurryAgent.Builder().
//                withLogEnabled(true).
//                withContinueSessionMillis(10).
//                withCaptureUncaughtExceptions(true).
//                build(this, Constant.FLURRY_KEY);
    }
}
