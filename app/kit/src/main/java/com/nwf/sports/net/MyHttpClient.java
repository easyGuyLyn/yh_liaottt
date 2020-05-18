package com.nwf.sports.net;

import com.dawoo.coretool.util.LogUtils;
import com.google.gson.Gson;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.utils.ssl.SSLSocketFactoryCompat;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by AK on 2017/8/10.
 */
public class MyHttpClient {
    public static final int DEFAULT_TIMEOUT_SECONDS = 7;
    public static final int DEFAULT_READ_TIMEOUT_SECONDS = 20;
    public static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 20;
    private static MyHttpClient instance = null;
    public MyHttpLoggingInterceptor interceptor = new MyHttpLoggingInterceptor();

    public static MyHttpClient getInstance() {
        if (instance == null) {
            instance = new MyHttpClient();
        }
        return instance;
    }

    private String getRequestBody(Object data) {
        Gson gson = new Gson();
        String jsonstr = gson.toJson(data);
        LogUtils.e("Game request data:" + jsonstr);
        return gson.toJson(jsonstr);
    }

    /**
     * 通用 post请求
     *
     * @param url
     * @param callback
     * @return
     */
    public AppTextMessageResponse execute(String url, Object data, Callback callback) {
        LogUtils.e("===== execute url:" + url);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), getRequestBody(data));
        Request request = new Request.Builder().post(requestBody).url(url).build();
        client.newCall(request).enqueue(callback);

        return new AppTextMessageResponse();
    }

    /**
     * 通用 get请求
     *
     * @param url
     * @param callback
     * @return
     */
    public AppTextMessageResponse executeGet(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert)
                .build();
        Request request = new Request.Builder().get().url(url).build();
        client.newCall(request).enqueue(callback);

        return new AppTextMessageResponse();
    }

    /**
     * 获取 选择线路 OkHttpClient
     *
     * @return
     */
    public OkHttpClient getCheckClient() {
        OkHttpClient client = null;
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
//                    .addInterceptor(new TokenInterceptor(TestApplication.getInstance().getClientConfig()))
//                    .sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager())
//                    .hostnameVerifier(new SSLUtil.TrustAllHostnameVerifier())
                    .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert)
                    .build();
        }
        return client;
    }

}
