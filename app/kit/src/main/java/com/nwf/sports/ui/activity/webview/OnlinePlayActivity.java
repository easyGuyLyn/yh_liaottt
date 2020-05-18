package com.nwf.sports.ui.activity.webview;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.dawoo.coretool.util.LogUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.ui.activity.BaseActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.GameWebSetting;
import com.nwf.sports.utils.data.DataCenter;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * <p>类描述：在线支付的界面(包括银联在线支付、微信/微信扫码、支付宝/支付宝扫码、财付通扫码)
 * <p>创建人：Simon
 * <p>创建时间：2019-04-10
 * <p>修改人：Simon
 * <p>修改时间：2019-04-10
 * <p>修改备注：
 **/
public class OnlinePlayActivity extends BaseActivity {

    @BindView(R.id.com_online_play_title)
    PNTitleBar comOnlinePlayTitle;
    @BindView(R.id.wv_online_play)
    WebView wvOnlinePlay;
    private OnlinePay onlinePay;
    private String title;
    private boolean isNeedSysBrowser;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_deposit_online_play);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            onlinePay = getIntent().getParcelableExtra(ConstantValue.ARG_PARAM1);
            title = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
            isNeedSysBrowser = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM3, false);
        }
        comOnlinePlayTitle.setTitle(title);
        comOnlinePlayTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GameWebSetting.init(wvOnlinePlay);
        wvOnlinePlay.setWebChromeClient(new WebChromeClient() {

        });
        wvOnlinePlay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
        });
    }

    @Override
    protected void initData() {
        StringBuilder payString = new StringBuilder();
        payString.append("product=").append(Constant.PRODUCT_ID).
                append("&billno=").append(onlinePay.getBillno()).
                append("&amount=").append(onlinePay.getAmount()).
                append("&loginname=").append(DataCenter.getInstance().getUserInfoBean().username).
                append("&currency=").append(Constant.PRODUCT_CURRENCY).
                append("&language=").append(Constant.PRODUCT_LANGUAGE).
                append("&customerType=").append(1).
                append("&keycode=").append(onlinePay.getKeycode());

        if (onlinePay.getUrl().contains("?")) {
            payString = new StringBuilder();
        }
        LogUtils.e(onlinePay.getUrl() + "?" + payString.toString());

        if (isNeedSysBrowser) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(onlinePay.getUrl() + "?" + payString.toString());
            intent.setData(uri);
            startActivity(intent);
            finish();
        } else {
            wvOnlinePlay.postUrl(onlinePay.getUrl(), payString.toString().getBytes());
        }

    }
}
