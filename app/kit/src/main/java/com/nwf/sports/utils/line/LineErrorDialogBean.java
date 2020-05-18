package com.nwf.sports.utils.line;

/**
 * 当线路出现异常 需要返回的错误信息
 **/
public class LineErrorDialogBean {
    private int code;//协议好的code码
    private String msg;//具体的补充信息

    public LineErrorDialogBean() {
    }
    public LineErrorDialogBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
