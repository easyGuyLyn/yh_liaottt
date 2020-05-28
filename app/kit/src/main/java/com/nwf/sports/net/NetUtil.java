package com.nwf.sports.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;

import com.dawoo.coretool.util.LogUtils;
import com.nwf.sports.IMApplication;
import com.nwf.sports.utils.data.IMDataCenter;
import com.nwf.sports.utils.ssl.SSLUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.nwf.sports.net.RetrofitHelper.DEFAULT_READ_TIMEOUT_SECONDS;
import static com.nwf.sports.net.RetrofitHelper.DEFAULT_TIMEOUT_SECONDS;
import static com.nwf.sports.net.RetrofitHelper.DEFAULT_WRITE_TIMEOUT_SECONDS;


/**
 * Created by benson on 18-1-3.
 */

public class NetUtil {
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        if (userAgent == null || "".equals(userAgent)) {
            userAgent = android.webkit.WebSettings.getDefaultUserAgent(context);
        }


        String ua = userAgent.replace("Android", "app_android");
        userAgent = ua + "; app_version=v3.0";

        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @NonNull
    public static Map<String, String> setHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", IMDataCenter.getInstance().getUserInfoBean().token);
        return headers;
    }

    public static Map<String, String> setHeaders(String jsonData) {

        String qid = UUID.randomUUID().toString();
        String sign = "";
        String timestamp = System.currentTimeMillis() + "";
        String token = IMDataCenter.getInstance().getGame_token();


        sign = md5(stringSort(jsonData) + timestamp + IMDataCenter.getInstance().getGame_u2token());


        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        headers.put("timestamp", timestamp);
        headers.put("sign", sign);
        headers.put("qid", qid);

        return headers;
    }

    private static String stringSort(String src) {

        char[] charAraay = src.toCharArray();
        Arrays.sort(charAraay);
        return new String(charAraay);

    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 登录后设置cookie
     */
    public static void setCookie(Response response) {
        List<String> cookies = response.headers().values("Set-Cookie");
        for (String cookie : cookies) {
            if (cookie.contains("SID=") && cookie.length() > 80) {
                LogUtils.e("登录后Cookie ==> " + cookie);
               // IMDataCenter.getInstance().setCookie(cookie);
                CookieManager.getInstance().setCookie(RetrofitHelper.baseUrl(), cookie);
            }
        }
    }

    /**
     * 这个两个在 API level 21 被抛弃
     * CookieManager.getInstance().removeSessionCookie();
     * CookieManager.getInstance().removeAllCookie();
     * <p>
     * 推荐使用这两个， level 21 新加的
     * CookieManager.getInstance().removeSessionCookies();
     * CookieManager.getInstance().removeAllCookies();
     **/
    public static void removeCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(IMApplication.getContext());
            CookieSyncManager.getInstance().sync();
        }
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     */
    public static boolean syncCookie(String url, String cookie) {
        String domain = RetrofitHelper.baseUrl();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.CookieSyncManager cookieSyncManager = android.webkit.CookieSyncManager.createInstance(IMApplication.getContext());
            cookieSyncManager.sync();
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
        String newCookie = cookieManager.getCookie(domain);
        return !TextUtils.isEmpty(newCookie);
    }

    /**
     * 创建 OkHttpClient.Builder
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClientBuilderForHttps() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//失败重连
                .sslSocketFactory(new TlsSniSocketFactory(), new SSLUtil.TrustAllManager())
                .hostnameVerifier(new TrueHostnameVerifier());
        return builder;
    }


    /**
     * 统一设置
     *
     * @return
     */
    public static Request getOkhttpRequest(String url) {
        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .get()
                .build();

        return request;
    }

}
