package cn.wildfire.chat.kit.net.base;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

/**
 * Created by imndx on 2017/12/15.
 */

@Keep
public class ResultWrapper<T> extends StatusResult {

    @SerializedName("body")
    T body;


    public T getResult() {
        return body;
    }

    public void setResult(T result) {
        this.body = result;
    }



}
