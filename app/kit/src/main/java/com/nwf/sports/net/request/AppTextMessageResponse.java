/**
 *
 */
package com.nwf.sports.net.request;

import java.io.Serializable;

/**
 * 定义移动端 应答报文中特有的属性
 *
 * @author simon
 * @see
 * @since 1.0
 */
public class AppTextMessageResponse<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4576543547832198559L;

    private T data;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public AppTextMessageResponse() {
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "0".equals(code);
    }

    public void setSuccess() {
        setCode("0");
    }
}
