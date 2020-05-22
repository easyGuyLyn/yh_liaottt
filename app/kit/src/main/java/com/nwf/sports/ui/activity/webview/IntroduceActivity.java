package com.nwf.sports.ui.activity.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.ui.activity.AddBankActivity;
import com.nwf.sports.ui.activity.BaseActivity;
import com.nwf.sports.ui.activity.BindPhoneActivity;
import com.nwf.sports.ui.activity.DiscountsActivity;
import com.nwf.sports.ui.activity.MainActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.BindBankFlowEnum;
import com.nwf.sports.utils.BindPhoneFlowEnum;
import com.nwf.sports.utils.data.IMDataCenter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * <p>类描述： 具体的活动页或者公司介绍页/历程
 * <p>创建人：Simon
 * <p>创建时间：2019-05-16
 * <p>修改人：Simon
 * <p>修改时间：2019-05-16
 * <p>修改备注：
 **/
public class IntroduceActivity extends BaseActivity {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @BindView(R.id.flayout_record)
    FrameLayout frameLayoutRecord;

    @BindView(R.id.com_homepage_introduce_title)
    PNTitleBar introduceTitle;

    @BindView(R.id.wv_homepage_introduce_content)
    WebView wvHomepageIntroduceContent;

    // TODO: Rename and change types of parameters
    private String title;
    private String url;

    public IntroduceActivity() {
        // Required empty public constructor
    }

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.fragment_recordslist);
    }

    @Override
    protected void initViews() {
        RxBus.get().register(this);
        if (getIntent() != null) {
            title = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            url = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
        }
        LogUtils.e("url: " + url + ", title: " + title);
        introduceTitle.setTitle(title);
        introduceTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settingWebview();
        wvHomepageIntroduceContent.loadUrl("" + url);

    }

    @Override
    protected void initData() {

    }


    /**
     * 设置viewview属性
     */
    public void settingWebview() {
        wvHomepageIntroduceContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wvHomepageIntroduceContent.getSettings().setJavaScriptEnabled(true);
        wvHomepageIntroduceContent.addJavascriptInterface(new RecordsInterface(this), "AndroidWebView");
        wvHomepageIntroduceContent.setWebChromeClient(new CommonWebChromeClient());
        wvHomepageIntroduceContent.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                //
                LogUtils.e("提前加载JS文件");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url); //结束
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
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

        @JavascriptInterface
        public void goGamesList(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        /**
         * 跳转到优惠记录列表
         */
        @JavascriptInterface
        public void goSpecialOffer() {
            LogUtils.e("javascriptToJava( )");
            startActivity(new Intent(IntroduceActivity.this, DiscountsActivity.class));
            finish();
        }

        /**
         * 在线客服
         */
        @JavascriptInterface
        public void goOnlineService() {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    ActivityUtil.skipToService(IntroduceActivity.this);
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void goRegister() {
            LogUtils.e("javascriptToJava( )");
            goRegister("", "");
        }

        @JavascriptInterface
        public void goRegister(String actionName, String param) {
            LogUtils.e("javascriptToJava( )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
                    intent.putExtra(ConstantValue.SKIP_MIAN_TYPE, MainActivity.TAB_INDEX_HOME);
                    intent.putExtra(ConstantValue.MAIN_OPEN_TYPE, ConstantValue.SKIP_REGISTER);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        @JavascriptInterface
        public void goLogin() {
            LogUtils.e("javascriptToJava( )");
            goLogin("", "");
        }

        @JavascriptInterface
        public void goLogin(String actionName, String param) {
            LogUtils.e("javascriptToJava( " + actionName + ", " + param + " )");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
                    intent.putExtra(ConstantValue.SKIP_MIAN_TYPE, MainActivity.TAB_INDEX_HOME);
                    intent.putExtra(ConstantValue.MAIN_OPEN_TYPE, ConstantValue.SKIP_LOGIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        /**
         * 给H5 端 用户Token
         */
        @JavascriptInterface
        public void getLoginInfo() {
            LogUtils.e("javascriptToJava( )");
            //必须开启线程进行JS调用
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    if (!Check.isNull(wvHomepageIntroduceContent)) {
                        wvHomepageIntroduceContent.loadUrl("javascript:getLoginInfo('" + IMDataCenter.getInstance().getUserInfoBean().getToken() + "')");
                    }
                }
            });

        }

        /**
         * 跳转到存款界面
         */
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
            Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
            intent.putExtra(ConstantValue.SKIP_MIAN_TYPE, MainActivity.TAB_INDEX_DEPOSIT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
            Bundle mbundle = new Bundle();
            mbundle.putString(ConstantValue.ARG_PARAM1, param);
            mbundle.putString(ConstantValue.ARG_PARAM2, actionName);
            startActivity(new Intent(IntroduceActivity.this, IntroduceActivity.class).putExtras(mbundle));
            finish();
        }

        /**
         * 关闭窗体, 关闭当前界面
         */
        @JavascriptInterface
        public void onCloseFromJS() {
            LogUtils.e("-----------onCloseFromJS()------------");
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
                    RxBus.get().post(ConstantValue.INTRODUCE_RELOAD, "reload");
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
            LogUtils.e("javascriptToJava (" + actionName + ", " + url + ")");
            Bundle mbundle = new Bundle();
            mbundle.putString(ConstantValue.ARG_PARAM1, param);
            mbundle.putString(ConstantValue.ARG_PARAM2, actionName);
            startActivity(new Intent(IntroduceActivity.this, IntroduceActivity.class).putExtras(mbundle));

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
            String phone = IMDataCenter.getInstance().getUserInfoBean().getPhone();
            LogUtils.e("javascriptToJava (" + actionName + ", " + param + ", bindPhone " + phone + ")");
            wvHomepageIntroduceContent.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                    if (!TextUtils.isEmpty(phone)) {
                        Bundle mbundle = new Bundle();
                        mbundle.putString(ConstantValue.BIND_BANK_FLOW, BindBankFlowEnum.TOINTRODUCE.getServicename());
                        mbundle.putString(ConstantValue.ARG_PARAM2, actionName);
                        mbundle.putString(ConstantValue.ARG_PARAM3, param);
                        startActivity(new Intent(IntroduceActivity.this, AddBankActivity.class).putExtras(mbundle));
                    } else {
                        Bundle mbundle = new Bundle();
                        mbundle.putString(ConstantValue.BIND_PHONE_FLOW, BindPhoneFlowEnum.TOINTRODUCE.getServicename());
                        mbundle.putBoolean(ConstantValue.ARG_PARAM1, false);
                        mbundle.putString(ConstantValue.ARG_PARAM2, actionName);
                        mbundle.putString(ConstantValue.ARG_PARAM3, param);
                        startActivity(new Intent(IntroduceActivity.this, BindPhoneActivity.class).putExtras(mbundle));
                    }
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
                    HomeDiscountsResult homePage = IMDataCenter.getInstance().getMyLocalCenter().getHomePage();
                    if (homePage == null || homePage.getPromotionsList() == null) {
                        ToastUtil.showToastLong("优惠活动为空");
                        return;
                    }
                    ArrayList list = new ArrayList();
                    list.addAll(homePage.getPromotionsList());
                    Bundle mbundle = new Bundle();
                    Intent intent = new Intent(IntroduceActivity.this, DiscountsActivity.class);
                    intent.putExtra(ConstantValue.SKIP_MIAN_TYPE, MainActivity.TAB_INDEX_DEPOSIT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mbundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, list);
                    startActivity(intent);
                }
            });

        }

    }

    /**
     * 刷新webview
     */
    @Subscribe(tags = {@Tag(ConstantValue.INTRODUCE_RELOAD)})
    public void introduceReload(String type) {
        LogUtils.e("event: " + type);
        wvHomepageIntroduceContent.reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }


    /**
     * 调用相册
     */
    /**
     * Android 5.0以下版本的文件选择回调
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected String mUploadableFileTypes = "image/*";

    protected static final int REQUEST_CODE_FILE_PICKER = 51426;

    private class CommonWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // setProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //  setWebViewTitleName(title);
        }

        //  Android 2.2 (API level 8)到Android 2.3 (API level 10)版本选择文件时会触发该隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // Android 3.0 (API level 11)到 Android 4.0 (API level 15))版本选择文件时会触发，该方法为隐藏方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Android 4.1 (API level 16) -- Android 4.3 (API level 18)版本选择文件时会触发，该方法为隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        // Android 5.0 (API level 21)以上版本会触发该方法，该方法为公开方法
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;//是否支持多选
                openFileInput(null, filePathCallback, allowMultiple);
                return true;
            } else {
                return false;
            }
        }

        @SuppressLint("NewApi")
        protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
            //Android 5.0以下版本
            if (mFileUploadCallbackFirst != null) {
                mFileUploadCallbackFirst.onReceiveValue(null);
            }
            mFileUploadCallbackFirst = fileUploadCallbackFirst;

            //Android 5.0及以上版本
            if (mFileUploadCallbackSecond != null) {
                mFileUploadCallbackSecond.onReceiveValue(null);
            }
            mFileUploadCallbackSecond = fileUploadCallbackSecond;

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);

            if (allowMultiple) {
                if (Build.VERSION.SDK_INT >= 18) {
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
            }

            i.setType(mUploadableFileTypes);
            startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_FILE_PICKER);
        }

        IX5WebChromeClient.CustomViewCallback mCallBack;

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            fullScreen();
            wvHomepageIntroduceContent.setVisibility(View.GONE);
            frameLayoutRecord.setVisibility(View.VISIBLE);
            frameLayoutRecord.addView(view);
            mCallBack = customViewCallback;
            super.onShowCustomView(view, customViewCallback);
        }

        @Override
        public void onShowCustomView(View view, int i, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            fullScreen();
            wvHomepageIntroduceContent.setVisibility(View.GONE);
            frameLayoutRecord.setVisibility(View.VISIBLE);
            frameLayoutRecord.addView(view);
            mCallBack = customViewCallback;
            super.onShowCustomView(view, i, customViewCallback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack != null) {
                mCallBack.onCustomViewHidden();
            }
            wvHomepageIntroduceContent.setVisibility(View.VISIBLE);
            frameLayoutRecord.removeAllViews();
            frameLayoutRecord.setVisibility(View.GONE);
            super.onHideCustomView();
        }

//        @Override
//        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
//            return super.onJsAlert(webView, s, s1, jsResult);
//        }
//
//        @Override
//        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
//            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
//        }
//
//        @Override
//        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
//            return super.onJsConfirm(webView, s, s1, jsResult);
//        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    //Android 5.0以下版本
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }
}
