package cn.wildfirechattest;


import com.ivi.imsdk.BuildConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ivi.net.base.netlibrary.config.GatewaysModel;
import ivi.net.base.netlibrary.config.NetConfig;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;


public class INetConfig extends NetConfig {
    @Override
    public boolean debug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public boolean isLocalEnvironment() {
        return  true;
    }

    @Override
    public List<GatewaysModel> gatewayUrls() {
        //配置网关接口，支持多网关配置，请求失败会自动切换到下一个网关
        List<GatewaysModel> list = new ArrayList<>();
        //配置产品网关地址
        list.add(new GatewaysModel("A01", "http://www.pt-gateway.com/_glaxy_a01_"));

        return list;

    }

    @Override
    public String appid() {
        return "A01VERTICAL02";
    }

    @Override
    public String loginName() {
        return "gmob382";
    }

    @Override
    public String productId() {
        return "A01";
    }

    @Override
    public String getUserToken() {
        return "d2149c41025c4f2a97af4e125b2e65cb";
    }

    @Override
    public String wmsProductCode() {
        return null;
    }


    @Override
    public Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
