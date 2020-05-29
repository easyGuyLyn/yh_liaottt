package cn.wildfirechattest;


import com.ivi.imsdk.BuildConfig;

import java.util.ArrayList;
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

    //    RedPacketIMServicesManger.getInstance().setLocalEnvironment(true);

        return true;
    }

    @Override
    public List<GatewaysModel> gatewayUrls() {
        //配置网关接口，支持多网关配置，请求失败会自动切换到下一个网关
        List<GatewaysModel> list = new ArrayList<>();

        List<String> java_gateway = new ArrayList<>();
        java_gateway.add("http://www.pt-gateway.com/_glaxy_a01_");

        //配置产品网关地址
        list.add(new GatewaysModel(java_gateway));


        //RedPacketIMServicesManger.getInstance().setAppServerHost("http://www.pt-gateway.com");

        return list;

    }

    @Override
    public String appid() {
        return "A01VERTICAL02";
    }

    @Override
    public String loginName() {
        return "gmob386";
    }

    @Override
    public String productId() {
        return "A01";
    }

    @Override
    public String getUserToken() {
        return "2a7a3f7e54354e16aa335717530667f6";
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
