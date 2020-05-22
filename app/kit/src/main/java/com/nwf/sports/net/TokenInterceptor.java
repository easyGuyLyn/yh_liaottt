package com.nwf.sports.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nwf.sports.net.request.AppTextMessageRequest;
import com.nwf.sports.net.util.MacUtil;
import com.nwf.sports.utils.data.IMDataCenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Nereus on 2017/5/12.
 * 当某个请求-响应的响应码为401即要求重新认证的时候，此类的方法被调用，以取得新的token，放入request中，重新请求。
 */

public class TokenInterceptor implements Interceptor {
    private IMDataCenter dataCenter = IMDataCenter.getInstance();
    private ClientConfig config;
    public TokenInterceptor(ClientConfig config)
    {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain)throws IOException{
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Response response = chain.proceed(request);
        //开始重试 有且仅重试一次
        if(response.code()==401 && null == request.header("DoneTryToken"))
        {  //在这里同步刷新Token
            String newToken = "8090";
            Request completeRequest = request.newBuilder()
                    .post(withNewToken(request.body(),newToken))
                    .addHeader("DoneTryToken","yes")
                    .build();
            return chain.proceed(completeRequest);
        }
        return response;
    }



    private RequestBody withNewToken(RequestBody requestBody,String newToken)
    {
        SerializerFeature[] serializerFeature = {
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero
        };

        String body = fromRequestBody(requestBody);
        if(TextUtils.isEmpty(body))
        {
            return null;
        }
        AppTextMessageRequest messageRequest = JSON.parseObject(body, AppTextMessageRequest.class);
        messageRequest.setToken(newToken);
        messageRequest.setMac(MacUtil.generateMac(messageRequest));
        String newjsonstring = JSON.toJSONString(messageRequest,serializerFeature);
        MediaType contentType = MediaType.parse("application/json");
        return RequestBody.create(contentType,newjsonstring);
    }

    private String fromRequestBody(RequestBody requestBody)
    {
        ByteArrayOutputStream outputStream = null;
        Buffer buffer=null;
        try {
            buffer = new Buffer();
            requestBody.writeTo(buffer);
            outputStream = new ByteArrayOutputStream(4096);
            buffer.copyTo(outputStream);
            String body = outputStream.toString("UTF-8");
            return body;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(null != buffer)
            {
                buffer.close();
            }
            if(null != outputStream)
            {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
