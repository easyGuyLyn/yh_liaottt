package com.nwf.sports.net;

import com.nwf.sports.net.util.RequestBuilder;
import com.nwf.sports.utils.data.IMDataCenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;

/**
 * Created by Nereus on 2017/5/12.
 * 这是一个OkHttpClient(callfactory)代理，它修改了请求信息，用于如下场景
 * 请求参数需要加密
 * 请求体需要加密
 * <p>
 * 这样的场景下，代理可以统一将明文加密成密文，然后交给OkHttpClient执行
 */

public class ProxyCallFactory implements Call.Factory
{
    private OkHttpClient client;
    private ClientConfig clientConfig;
    private IMDataCenter dataCenter = IMDataCenter.getInstance();

    public ProxyCallFactory(OkHttpClient client, ClientConfig clientConfig)
    {
        this.clientConfig = clientConfig;
        this.client = client;
    }

    @Override
    public Call newCall(Request request)
    {
        try
        {
            String token = dataCenter.getUserInfoBean().getToken();
            RequestBuilder builder = new RequestBuilder(clientConfig, token);
            return client.newCall(builder.newRequest(request));
        }
        catch (IOException e)
        {
            Timber.e(e, "有毛病，不能CallFactory中转换请求");
        }
        return client.newCall(request);
    }
}
