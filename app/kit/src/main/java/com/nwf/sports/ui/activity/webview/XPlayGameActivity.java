package com.nwf.sports.ui.activity.webview;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.BaseActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.GameWebSetting;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * 嵌套游戏 X5内核适配
 */

public class XPlayGameActivity extends BaseActivity {
    @BindView(R.id.pay_x5_game_title)
    PNTitleBar payGameTitle;
    @BindView(R.id.flayout_xpay)
    FrameLayout flayoutXpay;
    @BindView(R.id.wv_pay_x5_game)
    WebView wvPayGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setTheme(R.style.nwf_SplashTheme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.fragment_pay_x5_game);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        GameWebSetting.init(wvPayGame);

        String gameurl = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
        String gameCnName = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
        boolean hidetitlebar = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM3, false);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtils.e("SDK_INT upper ORIENTATION_LANDSCAPE");
            payGameTitle.setVisibility(View.VISIBLE);
            LogUtils.e("SDK_INT upper ......");
            payGameTitle.setTitle(gameCnName);
            payGameTitle.setBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else if (hidetitlebar) {
            payGameTitle.setVisibility(View.GONE);
        } else {
            payGameTitle.setVisibility(View.VISIBLE);
            LogUtils.e("SDK_INT upper ......");
            payGameTitle.setTitle(gameCnName);
            payGameTitle.setBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        wvPayGame.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        wvPayGame.setWebChromeClient(new WebChromeClient() {

            IX5WebChromeClient.CustomViewCallback customViewCallback;

            @Override
            public void onHideCustomView() {
                if (!Check.isNull(customViewCallback)) {
                    customViewCallback.onCustomViewHidden();
                }
                wvPayGame.setVisibility(View.VISIBLE);
                flayoutXpay.removeAllViews();
                flayoutXpay.setVisibility(View.GONE);
                super.onHideCustomView();
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                wvPayGame.setVisibility(View.GONE);
                this.customViewCallback = customViewCallback;
                flayoutXpay.removeAllViews();
                flayoutXpay.setVisibility(View.VISIBLE);
                flayoutXpay.addView(view);
                super.onShowCustomView(view, customViewCallback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

        });
        wvPayGame.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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

//        if (!Check.isEmpty(gameType)) {
//            if ("OPUS".equals(gameType)) {//PT的用bobo的html格式加载
//                synCookieToWebviewopus(gameurl, "S=" + uuid);
//            } else if ("shaba".equals(gameType)) {
//                synCookieToWebviewshaba(gameurl, "st=" + uuid);
//            }
//        }
//        if ("post".equals(type)) {
//            wvPayGame.postUrl(getIntent().getStringExtra("url"), null);
//        } else {
//            wvPayGame.loadUrl(getIntent().getStringExtra("url"));
//        }

        wvPayGame.loadUrl(gameurl);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String gameCnName = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
        boolean hidetitlebar = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM3, false);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtils.e("SDK_INT upper ORIENTATION_LANDSCAPE");
            payGameTitle.setVisibility(View.GONE);
        } else if (hidetitlebar) {
            payGameTitle.setVisibility(View.GONE);
        } else {
            LogUtils.e("SDK_INT upper ......");
            payGameTitle.setVisibility(View.VISIBLE);
            payGameTitle.setTitle(gameCnName);
            payGameTitle.setBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public class JsInterface {
        private Context mContext;

        public JsInterface(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public void showInforFroms(String name) {
            ToastUtil.showToastLong(name);
            LogUtils.e("javascriptTOjava(" + name + "," + name + ")");
        }

        @JavascriptInterface
        public void showInfoFromJs(String name) {
            //Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
            ToastUtil.showToastLong(name);
            LogUtils.e("showInfoFromJs(" + name + ")");
        }

        @JavascriptInterface
        public void onCloseFromJS() {
            finish();
            LogUtils.e("-----------onCloseFromJS()------------aggjoppo");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!Check.isNull(wvPayGame) && wvPayGame.canGoBack()) {
                wvPayGame.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (!Check.isNull(wvPayGame)) {
                ViewParent parent = wvPayGame.getParent();
                if (!Check.isNull(parent)) {
                    ((ViewGroup) parent).removeAllViews();
                }
                wvPayGame.stopLoading();
                wvPayGame.getSettings().setJavaScriptEnabled(false);
                wvPayGame.clearHistory();
                wvPayGame.clearView();
                wvPayGame.removeAllViews();
                wvPayGame.destroy();
                wvPayGame = null;
                System.gc();
                LogUtils.e("XplayGameActivity:--------onDestroy()--------");
            }
        } catch (Exception value) {
            LogUtils.e("XplayGameActivity异常:" + value);
        }
        if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }
}
