package com.nwf.sports.chat.login.model;


import java.io.Serializable;

/**
 * 真-model，code啊，message之类的，放到了status里面去了
 */
public class LoginResult implements Serializable {
    private String userId;
    private String token;
    private boolean register;//是否新注册
    private boolean changeNameFlag;//是否已经修改昵称

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isChangeNameFlag() {
        return changeNameFlag;
    }

    public void setChangeNameFlag(boolean changeNameFlag) {
        this.changeNameFlag = changeNameFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
