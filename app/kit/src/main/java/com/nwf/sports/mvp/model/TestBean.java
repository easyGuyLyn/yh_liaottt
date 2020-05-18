package com.nwf.sports.mvp.model;

import java.util.List;

/**
 * Created by jack on 18-3-22.
 */

public class TestBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : []
     */

    private int code;
    private String msg;
    private List<Data> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

    }
}
