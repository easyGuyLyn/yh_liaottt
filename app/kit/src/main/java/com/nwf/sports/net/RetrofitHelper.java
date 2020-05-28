package com.nwf.sports.net;

import com.dawoo.coretool.util.LogUtils;
import com.nwf.sports.IMServicesManger;
import com.nwf.sports.utils.NullOnEmptyConverterFactory;
import com.nwf.sports.utils.ssl.SSLSocketFactoryCompat;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络
 */

public class RetrofitHelper {
    public static final int DEFAULT_TIMEOUT_SECONDS = 6;
    public static final int DEFAULT_READ_TIMEOUT_SECONDS = 30;
    public static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 30;
    private static Retrofit mRetrofit;
    private static OkHttpClient client;
    private static ClientConfig clientConfig;
    private static ProxyCallFactory proxyCallFactory;
    private static String domainUrl = "https://gwapi.czsjnp.com";
    private static String IM_SERVER_HOST = "10.66.72.55";
    public static String APP_SERVER_ADDRESS;




    /**
     * 应该在Application onCreate中实例
     *
     * @param config
     */
    public static void config(ClientConfig config) {
        clientConfig = config;
        proxyCallFactory = new ProxyCallFactory(getClient(), clientConfig);
    }

    /**
     * 基本域名（+path）
     * 1
     *
     * @return
     */
    public static String baseUrl() {
//        domainUrl = "https://gwapi.czsjnp.com";            // 新的 线上/运营环境 2018-12-17
//        domainUrl = "http://uatnwfgwapi.agg013.com/";             // 新的 UAT
//        domainUrl = "http://10.91.37.42:11000/";         // 本地
//        domainUrl = "http://10.91.6.23:8080/";         // Andrew本地
//        domainUrl = "http://10.91.6.22:7070/  ";         // Jonathan
//        domainUrl = "http://10.91.6.11:8099/  ";         // Aaron

        //GameLog.log("get domainUrl:"+domainUrl);
        return domainUrl;
    }

    /**
     * IM域名
     * 1
     *
     * @return
     */
    public static String imHost() {
//        IM_SERVER_HOST = "110.173.49.42";//正式环境
//        IM_SERVER_HOST = "110.173.53.28";//UAT环境
//        IM_SERVER_HOST = "10.91.37.3";//测试环境
//        IM_SERVER_HOST = "10.91.6.19";//Bake本地环境
        return IM_SERVER_HOST;
    }

    /**
     * IM域名
     * 1
     *
     * @return
     */
    public static String imUrl() {
        // APP_SERVER_ADDRESS = "http://www.pt-gateway.com/redenvelope" ;
        return APP_SERVER_ADDRESS;
    }

    /**
     * 获取 OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getClient() {
        if (null == client) {
            MyHttpLoggingInterceptor interceptor = new MyHttpLoggingInterceptor();
            client = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .addInterceptor(interceptor)
//                    .addInterceptor(new TokenInterceptor(clientConfig))
//                    .retryOnConnectionFailure(true)//失败重连
                    .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert)
                    .build();
        }
        return client;
    }

    /**
     * 获取 Retrofit 实例
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        if (null == mRetrofit) {
            if (null == proxyCallFactory) {
                throw new NullPointerException("client config has to  be configed in the Application onCreate");
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .callFactory(proxyCallFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return mRetrofit;
    }

    /**
     * 改变URL
     *
     * @param url
     */
    public static void setClientDomain(String url) {
        domainUrl = url;
        LogUtils.e("set domainUrl:" + domainUrl);
        clearRetrofit();
    }

    /**
     * 改变ImURL
     */
    public static void setImDomain(String host) {
        IM_SERVER_HOST = host;
        APP_SERVER_ADDRESS = "http://" + IM_SERVER_HOST + ":8888";
        LogUtils.e("set ImDomain:" + APP_SERVER_ADDRESS);
    }

    /**
     * 获取服务对象   Rxjava+Retrofit建立在接口对象的基础上的
     * 泛型避免强制转换
     */
    public static <T> T getService(Class<T> classz) {
        return getRetrofit().create(classz);
    }

    /**
     * 切换环境而使用的 retrofit赋值为null
     */
    public static void clearRetrofit() {
        mRetrofit = null;
    }

    /**
     * 切换环境而使用的 还原domainUrl
     */
    public static void clearDomainUrl() {
        setClientDomain("https://gwapi.czsjnp.com");
    }

}
