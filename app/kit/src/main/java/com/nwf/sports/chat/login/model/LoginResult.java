package com.nwf.sports.chat.login.model;


import java.io.Serializable;

/**
 * 真-model，code啊，message之类的，放到了status里面去了
 */
public class LoginResult implements Serializable {
    private String userId;
    private String token;

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
