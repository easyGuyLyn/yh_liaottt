package com.nwf.sports.ui.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.LogUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.BaseActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

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


}
