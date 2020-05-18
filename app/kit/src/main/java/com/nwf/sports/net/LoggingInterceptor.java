package com.nwf.sports.net;

import android.content.Context;

import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.utils.FlurryAgentUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-02-15
 * <p>修改人：Simon
 * <p>修改时间：2019-02-15
 * <p>修改备注：
 **/
public class LoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context = IMApplication.getContext();
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .headers(Headers.of(NetUtil.setHeaders()));
        request = builder.build();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            Map<String, String> comment = new HashMap<>();
            comment.put("url", request.url().toString());
            comment.put("error", e.toString());
            FlurryAgentUtils.backTrackError(ConstantValue.REQUEST_ERROR, comment);
            throw e;
        }

        return processResponse(response, chain);
    }

    //访问网络之后，处理Response(这里没有做特别处理)
    private Response processResponse(Response response, Chain chain) {
        Map<String, String> comment = new HashMap<>();
        String url = response.request().url().toString();
        if (response.code() == 200) {
            String data = response.body().source().buffer().clone().readString(UTF8);
            try {
                AppTextMessageResponse appTextMessageResponse = new Gson().fromJson(data, AppTextMessageResponse.class);
                if (!appTextMessageResponse.isSuccess()) {
                    if (appTextMessageResponse.getCode().equals("401")) {
                        RxBus.get().post(ConstantValue.TOKEN_LOSE_EFFICACY, "401");
                        return response;
                    }
                    comment.put("url", url);
                    comment.put("data", data);
                    FlurryAgentUtils.backTrackError(ConstantValue.REQUEST_ERROR, comment);
                }
            } catch (Exception e) {
                comment.put("url", url);
                comment.put("data", data);
                comment.put("error", e.toString());
                FlurryAgentUtils.backTrackError(ConstantValue.REQUEST_ERROR, comment);
            }
        } else {
            String s = response.networkResponse().toString();
            comment.put("url", url);
            comment.put("messageError", s);
            FlurryAgentUtils.backTrackError(ConstantValue.REQUEST_ERROR, comment);
        }
        return response;
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
