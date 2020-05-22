package com.nwf.sports.ui.activity.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.packageref.DeviceUtils;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.MyBankItemResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.model.UserInformJS;
import com.nwf.sports.ui.activity.BaseActivity;
import com.nwf.sports.ui.activity.MainActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LoginDialogFragment;
import com.nwf.sports.ui.dialogfragment.RegisterDialogFragment;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.data.IMDataCenter;
import com.tencent.smtt.sdk.WebView;

import java.util.List;

import butterknife.BindView;

/**
 * <p>类描述： 优惠详情
 * <p>创建人：Simon
 * <p>创建时间：2019-05-16
 * <p>修改人：Simon
 * <p>修改时间：2019-05-16
 * <p>修改备注：
 **/
public class AiscountsDetailsActivity extends BaseActivity {

    //回调
    public static final int STANDARD = 0;
    public static final int SINGLETOP = 1;
    public static final int SINGLETASK = 2;  //注册回调

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.flayout_record)
    FrameLayout flayoutRecord;
    @BindView(R.id.wv_homepage_introduce_content)
    WebView wvHomepageIntroduceContent;

    private boolean isBindPhone = false;

    String title = "";
    String url = "";

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_aiscounts_details);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            title = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            url = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
        }
        vTitle.setMoreListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vTitle.setTitle(title);
        if (TextUtils.isEmpty(IMDataCenter.getInstance().getUserInfoBean().getPhone())){
            isBindPhone = false;
        }else {
            isBindPhone = true;
        }
    }


    public void webViewSetting() {
        wvHomepageIntroduceContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wvHomepageIntroduceContent.getSettings().setJavaScriptEnabled(true);
        wvHomepageIntroduceContent.addJavascriptInterface(new RecordsInterface(this), "AndroidWebView");
    }

    @Override
    protected void initData() {

    }

    public class RecordsInterface {
        private Context mContext;

        public RecordsInterface(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public void goGamesList() {
            LogUtils.e("javascriptToJava( )");
            goGamesList("", "");
        }

        /**
         * 进入电子游戏
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goGamesList(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {  //进入电子游戏
                    finish();
//                    EventBus.getDefault().post(new NRequestEnterGameEvent("MG", "E03026"));
                }
            });
        }

        @JavascriptInterface
        public void goFishing() {
            LogUtils.e("javascriptToJava( )");
            goFishing("", "");
        }

        /**
         * 进入捕鱼王
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goFishing(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
//                    EventBus.getDefault().post(new NRequestEnterGameEvent("Fishing", "E03026"));
                }
            });
        }

        @JavascriptInterface
        public void goOnlineSports() {
            LogUtils.e("javascriptToJava( )");
            goOnlineSports("", "");
        }

        /**
         * 进入沙巴体育
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goOnlineSports(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {  //进入沙巴体育
                    finish();
//                    EventBus.getDefault().post(new NRequestEnterGameEvent("OnlineSports", "E03031"));
                }
            });
        }

        /**
         * 跳转到优惠记录列表
         */
        @JavascriptInterface
        public void goSpecialOffer() {//String actionName,String param
            LogUtils.e("javascriptToJava( )");
            finish(); // hide();

//            EventBus.getDefault().post(new StartBrotherEvent(HistoryFragment.newInstance(new HistoryDepositIndex())));
        }

        @JavascriptInterface
        public void goAGIN() {
            LogUtils.e("javascriptToJava( )");
            goAGIN("", "");
        }

        /**
         * 进入AG国际
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goAGIN(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
//                    EventBus.getDefault().post(new NRequestEnterGameEvent("AGIN", "E03026"));
                }
            });
        }

        @JavascriptInterface
        public void goAG() {
            LogUtils.e("javascriptToJava( )");
            goAG("", "");
        }

        /**
         * 进入AG旗舰
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goAG(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
//                    EventBus.getDefault().post(new NRequestEnterGameEvent("AG", "E03003"));
                }
            });
        }

        /**
         * 在线客服 2018-8-8
         */
        @JavascriptInterface
        public void goOnlineService() {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                    ActivityUtil.skipToService(mContext);
                }
            });
        }

        @JavascriptInterface
        public void goRegister() {
            LogUtils.e("javascriptToJava( )");
            goRegister("", "");
        }

        /**
         * 注册
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goRegister(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                    RegisterDialogFragment registerDialogFragment = RegisterDialogFragment.getInstance("");
                    DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), registerDialogFragment);  //回调
//                    EventBus.getDefault().post(new StartBrotherEvent(NRegisterFragment.newInstance("", ""), SINGLETASK));
                }
            });
        }

        @JavascriptInterface
        public void goLogin() {
            LogUtils.e("javascriptToJava( )");
            goLogin("", "");
        }

        /**
         * 登录
         *
         * @param actionName
         * @param param
         */
        @JavascriptInterface
        public void goLogin(String actionName, String param) {
            LogUtils.e("javascriptToJava( " + actionName + ", " + param + " )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("IntroduceActivity javascriptTOjava pop()");
                    finish();
                    LoginDialogFragment loginDialogFragment = LoginDialogFragment.getInstance("", "");
                    DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), loginDialogFragment);
//                    EventBus.getDefault().post(new StartBrotherEvent(NLoginFragment.newInstance("", ""), SINGLETASK));
                }
            });
        }

        @JavascriptInterface
        public void getLoginInfo() {
            LogUtils.e("javascriptToJava( )");
            //必须开启线程进行JS调用
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    UserInfoBean userInfoBean = IMDataCenter.getInstance().getUserInfoBean();
                    UserInformJS userInformJS = new UserInformJS();
                    userInformJS.isLogin = userInfoBean.isRealLogin() ? "true" : "false";
                    userInformJS.account = userInfoBean.isRealLogin() ? userInfoBean.getUsername() : ""; // 试玩账号不传值
                    userInformJS.star = userInfoBean.getLevelNum();
                    userInformJS.token = userInfoBean.getToken();
                    List<MyBankItemResult> myBanks = IMDataCenter.getInstance().getMyBankRepositoryCenter().getMyBanks().bankList;
                    if (myBanks.size() > 0) {
                        userInformJS.hasBankCard = "" + true;
                    } else {
                        userInformJS.hasBankCard = "" + false;
                    }
                    userInformJS.ip = DeviceUtils.getLocalInetAddress().getHostAddress();
                    userInformJS.mac = DeviceUtils.getMacAddress();
                    String userInformJson = new Gson().toJson(userInformJS);
                    //wvAd.loadData("","text/html","UTF-8");
                    if (!Check.isNull(wvHomepageIntroduceContent)) {
                        wvHomepageIntroduceContent.loadUrl("javascript:getLoginInfo('" + userInformJson + "')");
                    }
                    //wvAd.loadUrl("javascript:showInfoFromJava('"+userInformJson+ "')");
                }
            });

        }

        @JavascriptInterface
        public void goDeposit() {
            LogUtils.e("javascriptToJava( )");
            goDeposit("", "");
        }

        /**
         * 跳转到存款界面
         */
        @JavascriptInterface
        public void goDeposit(String actionName, String param) {
            LogUtils.e("javascriptToJava( " + actionName + "," + param + " )");
//            EventBus.getDefault().post(new StartBrotherEvent(MainFragment.newInstance("registerSuccessByPhone", ""), SINGLETASK));
            RxBus.get().post(ConstantValue.MAINACTIVITY_SWITCHOVER, MainActivity.TAB_INDEX_DEPOSIT);
            finish();

        }

        /**
         * 跳转到活动详情
         *
         * @param actionName URL
         * @param param      title
         */
        @JavascriptInterface
        public void actDetail(String actionName, String param) {
            LogUtils.e("javascriptToJava(" + actionName + ", " + param + ")");
//            EventBus.getDefault().post(new StartBrotherEvent(IntroduceActivity.newInstance(param, actionName)));
            finish();
        }

        /**
         * 关闭窗体, 关闭当前界面 2018-08-10
         */
        @JavascriptInterface
        public void onCloseFromJS() {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void onCloseFromJS2() {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        /**
         * 关闭窗体, 关闭当前界面, 并通知上个页面刷新
         */
        @JavascriptInterface
        public void closeAndReloadLastPage() {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
//                    EventBus.getDefault().post(EVENT_RELOAD);
                }
            });

        }

        @JavascriptInterface
        public void openNewWebView() {
            LogUtils.e("javascriptToJava( )");
            openNewWebView("", "");
        }

        /**
         * 跳转新的活动页
         *
         * @param actionName URL
         * @param param      title
         */
        @JavascriptInterface
        public void openNewWebView(String actionName, String param) {
            LogUtils.e("javascriptToJava (" + actionName + ", " + param + ")");
//            EventBus.getDefault().post(new StartBrotherEvent(IntroduceActivity.newInstance(param, actionName)));
        }

        @JavascriptInterface
        public void toFillInfomation() {
            LogUtils.e("javascriptToJava( )");
            toFillInfomation("", "");
        }

        /**
         * 完善资料
         *
         * @param actionName URL
         * @param param      title
         */
        @JavascriptInterface
        public void toFillInfomation(final String actionName, final String param) {
            LogUtils.e("javascriptToJava (" + actionName + ", " + param + ", isBindPhone " + isBindPhone + ")");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
//                    if (isBindPhone) {
//                        int bankNumber = new Local().getBankNumber();
//                        boolean firstBank = (bankNumber == 0);
//                        PNBaseFragment fragment = AddBankFragment.newInstanceForAddBank(firstBank, mParam1, mParam2);
//                        EventBus.getDefault().post(new StartBrotherEvent(fragment));
//                    } else {
//                        EventBus.getDefault().post(new StartBrotherEvent(ModifyPhoneFragment.newInstanceAD(true,mParam1, mParam2)));
//                    }
                }
            });

        }

        @JavascriptInterface
        public void goSysBrowser(String url) {
            LogUtils.e("javascriptToJava: " + url);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        }

        /**
         * 跳转到优惠活动列表
         */
        @JavascriptInterface
        public void goPromotionList() {
            LogUtils.e("javascriptToJava( )");

            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
//                    List<HomeActivityItemResult> list = LocalHomeActivityDao.queryList();
//                    HomeActivity vo = new HomeActivity();
//                    vo.setResult(list);
//                    EventBus.getDefault().post(new StartBrotherEvent(UserActivityListFragment.newInstance(vo, null)));
                }
            });

        }

    }
}
