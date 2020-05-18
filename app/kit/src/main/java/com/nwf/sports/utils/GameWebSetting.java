package com.nwf.sports.utils;

import android.os.Build;
import android.view.View;

import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Created by ak on 2017/10/22.
 */


public class GameWebSetting
{
/**
     * 设置webSetting
     * @param webView
     */

    public static void init(WebView webView){
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebContentsDebuggingEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        //获取触摸焦点
        webView.requestFocusFromTouch();
        webView.requestFocus();
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        IX5WebViewExtension ix5 = webView.getX5WebViewExtension();
        if(ix5!=null){
            ix5.setScrollBarFadingEnabled(false);
        }
        WebSettings webSettings = webView.getSettings();
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        //多窗口
        webSettings.supportMultipleWindows();
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        // 启用缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCursiveFontFamily("cursive");
        webSettings.setDisplayZoomControls(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setFantasyFontFamily("fantasy");
        webSettings.setFixedFontFamily("monospace");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLightTouchEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        //图片先不加载最后再加载
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        webSettings.setMediaPlaybackRequiresUserGesture(true);
        webSettings.setNavDump(false);
        //是否支持插件
        webSettings.setPluginsEnabled(false);
        webSettings.setSansSerifFontFamily("sans-serif");
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(false);
        webSettings.setSerifFontFamily("serif");
        webSettings.setStandardFontFamily("sans-serif");
        webSettings.setUseWebViewBackgroundForOverscrollBackground(false);
        webSettings.setSupportMultipleWindows(false);
        //支持缩放，默认为true。
        webSettings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }

        webSettings.setEnableSmoothTransition(false);

        webSettings.setGeolocationEnabled(true);

        webSettings.setSaveFormData(true);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
            /**
             * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
             * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
             * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
             **/
            webSettings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}
