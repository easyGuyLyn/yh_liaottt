package com.nwf.sports.net.util;

import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.NetworkUtils;
import com.google.gson.Gson;
import com.nwf.sports.net.ClientConfig;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.request.AppTextRequest;
import com.nwf.sports.utils.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by Nereus on 2017/8/30.
 */

public class RequestBuilder {
    private ClientConfig clientConfig;
    private String token;

    public RequestBuilder(ClientConfig config, String token) {
        this.clientConfig = config;
        this.token = token;
    }

    public Request newRequest(Request originalRequest) throws IOException {
        Timber.tag(getClass().getName());
        if (!"POST".equals(originalRequest.method())) {
//            Timber.e("请求必须是POST请求");
//            throw new IllegalArgumentException("请求方法必须是POST");
            return originalRequest;
        }
        //好啦，开始修改请求
        RequestBody requestBody = originalRequest.body();
        FormBody formBody = null;
        if (null != requestBody && (requestBody instanceof FormBody) && (requestBody.contentLength() != 0)) {
            formBody = (FormBody) requestBody;
        }
        MediaType contentType = MediaType.parse("application/json");
        RequestBody newRequestBody = RequestBody.create(contentType, convert(formBody));
        Request newRequest = originalRequest.newBuilder().post(newRequestBody).build();
        return newRequest;
    }

    /**
     * @return
     */
    private String convert(FormBody formBody) {
        Timber.d("打印请求参数");
        Map<String, Object> map = new HashMap<>();
        if (null != formBody) {
            final int size = formBody.size();
            for (int index = 0; index < size; index++) {
                String name = formBody.name(index);
                Object value = formBody.value(index);
                Timber.d("%s ---> %s", name, value);
                map.put(name, value);
            }
        }

        return getRequestBody(map);
    }

    private String getRequestBody(Map<String, Object> data) {
        SerializerFeature[] serializerFeature = {
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero
        };
        AppTextRequest appTextRequest = new AppTextRequest();
        data.put("deviceType", Constant.DEVICETYPE);
        data.put("domainName", RetrofitHelper.baseUrl());
        data.put("ipAddress", NetworkUtils.getIPAddress(true));
        data.put("pid", Constant.PRODUCT_ID);
        data.put("version", Build.VERSION.RELEASE);
        data.put("websiteType", Constant.WEBSITETYPE);
        data.put("appType", Constant.APPTYPE);
        data.put("channelID", clientConfig.channelID);

        Gson gson = new Gson();
        if (null != data && !data.isEmpty()) {
            LogUtils.e(data.toString());
            String jsonstr = gson.toJson(data);
//            String jsonstr = " {\"deviceType\":\"1\",\"domainName\":\"localhost\",\"ipAddress\":\"10.91.6.23\",\"loginType\":1\n" +
//                    "                ,\"password\":\"qwe123\",\"pid\":\"E03\",\"userName\":\"vandrew007\",\"version\":\"1.0\",\"websiteType\":\"6\"}  ";
            LogUtils.e("-------OkHttp:okjsonstr-------" + jsonstr);
//            String encryptText = Des3Util.encrypt(jsonstr, "1a0dcc06af4585e83a1c4967");
            String encryptText = "";
            try {
                encryptText = DESHelper.encrypt(jsonstr);
            } catch (Exception e) {
//                throw new RuntimeException("3DES加密过程异常", e);
            }

            appTextRequest.setRequestData(encryptText);
        }
        return JSON.toJSONString(appTextRequest, serializerFeature);
    }
}
