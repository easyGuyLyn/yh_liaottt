package cn.wildfire.chat.kit.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nwf.sports.net.MyHttpLoggingInterceptor;
import com.nwf.sports.net.NetUtil;
import com.nwf.sports.utils.SingleToast;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.wildfire.chat.kit.net.base.ResultWrapper;
import cn.wildfire.chat.kit.net.base.StatusResult;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by imndx on 2017/12/15.
 */

public class OKHttpHelper {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new MyHttpLoggingInterceptor())
            //    .addInterceptor(new IMLoggingInterceptor())
            .build();

    private static Gson gson = new Gson();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static <T> void get(final String url, Map<String, String> params, final Callback<T> callback) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (params != null) {
            HttpUrl.Builder builder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            httpUrl = builder.build();
        }

        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(-1, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(url, call, response, callback);
            }
        });

    }

    public static <T> void post(final String url, Map<String, Object> param, final Callback<T> callback) {


        if (!url.startsWith("http") && !url.startsWith("https")) {
            Log.e("okh", "url 不合法");
            return;
        }


        String jsonData = gson.toJson(param);

        RequestBody body = RequestBody.create(JSON, jsonData);

        final Request request = new Request.Builder()
                .headers(Headers.of(NetUtil.setHeaders(jsonData)))
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(-1, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(url, call, response, callback);

            }
        });
    }

    public static <T> void put(final String url, Map<String, String> param, final Callback<T> callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(param));
        final Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(-1, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(url, call, response, callback);

            }
        });
    }

    private static <T> void handleResponse(String url, Call call, okhttp3.Response response, Callback<T> callback) {


        if (callback != null) {
            if (!response.isSuccessful()) {

                SingleToast.showLongMsg(response.message() + "");

                callback.onFailure(response.code(), response.message());
                return;
            }

            Type type;
            if (callback instanceof SimpleCallback) {
                Type types = callback.getClass().getGenericSuperclass();
                type = ((ParameterizedType) types).getActualTypeArguments()[0];
            } else {
                Type[] types = callback.getClass().getGenericInterfaces();
                type = ((ParameterizedType) types[0]).getActualTypeArguments()[0];
            }

            if (type.equals(String.class)) {
                try {
                    callback.onSuccess((T) response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }


            try {
                StatusResult statusResult;
                if (type instanceof Class && type.equals(StatusResult.class)) {
                    statusResult = gson.fromJson(response.body().string(), StatusResult.class);
                    if (statusResult.getHead().getErrCode().equals("0000")) {
                        callback.onSuccess((T) statusResult);
                    } else {

                        String errMsg = statusResult.getHead().getErrMsg();

//                        if (!TextUtils.isEmpty(IMErrorCodeEnum.getCodeNameByCode(Integer.parseInt(statusResult.getHead().getErrCode())))) {
//                            errMsg = IMErrorCodeEnum.getCodeNameByCode(Integer.parseInt(statusResult.getHead().getErrCode()));
//                        }

                        SingleToast.showLongMsg(errMsg);
                        callback.onFailure(Integer.parseInt(statusResult.getHead().getErrCode()), errMsg);
                    }
                } else {
                    ResultWrapper<T> wrapper = gson.fromJson(response.body().string(), new ResultType(type));
                    if (wrapper == null) {
                        callback.onFailure(-1, "response is null , 接口返回数据异常");
                        return;
                    }
                    if (wrapper.getHead().getErrCode().equals("0000") && wrapper.getResult() != null) {
                        callback.onSuccess(wrapper.getResult());
                    } else {

                        String errMsg = wrapper.getHead().getErrMsg();

//                        if (!TextUtils.isEmpty(IMErrorCodeEnum.getCodeNameByCode(Integer.parseInt(wrapper.getHead().getErrCode())))) {
//                            errMsg = IMErrorCodeEnum.getCodeNameByCode(Integer.parseInt(wrapper.getHead().getErrCode()));
//                        }

                        SingleToast.showLongMsg(errMsg);
                        callback.onFailure(Integer.parseInt(wrapper.getHead().getErrCode()), errMsg);
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                callback.onFailure(-1, e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(-1, e.getMessage());
            }
        }
    }

    private static class ResultType implements ParameterizedType {
        private final Type type;

        public ResultType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

        @Override
        public Type getRawType() {
            return ResultWrapper.class;
        }
    }
}
