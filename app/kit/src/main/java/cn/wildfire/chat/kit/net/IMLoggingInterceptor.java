package cn.wildfire.chat.kit.net;

import android.text.TextUtils;

import com.nwf.sports.utils.data.IMDataCenter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 **/
public class IMLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");


    private static String stringSort(String src) {

        char[] charAraay = src.toCharArray();
        Arrays.sort(charAraay);
        return new String(charAraay);

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Headers dheaders = request.headers();



        String qid = UUID.randomUUID().toString();
        String sign = "";
        String timestamp = System.currentTimeMillis() + "";
        String token = IMDataCenter.getInstance().getGame_token();

        RequestBody requestBody = request.body();

        boolean hasBody = requestBody != null;

        if (hasBody) {

            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            sign = md5(stringSort(buffer.readUtf8()) + timestamp + IMDataCenter.getInstance().getGame_u2token());

        }


        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        headers.put("timestamp", timestamp);
        headers.put("sign", sign);
        headers.put("qid", qid);


        Request.Builder builder = request.newBuilder()
                .headers(dheaders)
                .headers(Headers.of(headers));


        request = builder.build();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }

        return processResponse(response, chain);
    }

    //访问网络之后，处理Response(这里没有做特别处理)
    private Response processResponse(Response response, Chain chain) {

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


}
