package com.nwf.sports.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;
import com.nwf.sports.mvp.presenter.HomePresenter;
import com.nwf.sports.mvp.view.HomeView;
import com.nwf.sports.utils.GameWebSetting;
import com.nwf.sports.utils.data.DataCenter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-08
 * <p>修改人：Simon
 * <p>修改时间：2020-01-08
 * <p>修改备注：
 **/
public class GameFragment extends BaseFragment implements HomeView {

    @BindView(R.id.flayout_xpay)
    FrameLayout flayoutXpay;
    @BindView(R.id.wv_pay_x5_game)
    WebView wvPayGame;
    private HomePresenter mHomePresenter = null;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main_game;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        GameWebSetting.init(wvPayGame);
        mHomePresenter = new HomePresenter(getActivity(), this);
        wvPayGame.addJavascriptInterface(new JsInterface(getActivity()), "AndroidWebView");
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
//        wvPayGame.loadUrl("https://www.json.cn/");
        loginGame();
    }

    @Override
    protected void loadData() {

    }

    public void loginGame() {
        if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            Map<String, String> map = new HashMap<>();
            map.put("actype", "1");
            map.put("currency ", "CNY");
            map.put("gameId", "");
            map.put("gmid", "E03083");
            map.put("language", "zh");
            map.put("loginName", DataCenter.getInstance().getUserInfoBean().getUsername());
            mHomePresenter.loginGame(map, "体育");
        }
    }

    /**
     * 登录成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGIN)})
    public void loginSucceed(String string) {
        ToastUtil.showToastLong("登录成功");
        loginGame();
    }


    @Override
    public void gameListSuccess(List<HomeGameResult> gameResults) {

    }

    @Override
    public void homePageSuccess(HomeDiscountsResult homeDiscountsResult) {

    }

    @Override
    public void downloadApps(DownloadAppResult downloadAppResult) {

    }

    @Override
    public void loginGame(String url, String title) {
        wvPayGame.loadUrl(url);
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
            getActivity().finish();
            LogUtils.e("-----------onCloseFromJS()------------aggjoppo");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    }
}
