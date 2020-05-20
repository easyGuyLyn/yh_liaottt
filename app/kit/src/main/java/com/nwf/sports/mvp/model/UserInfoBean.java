package com.nwf.sports.mvp.model;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-02-15
 * <p>修改人：Simon
 * <p>修改时间：2019-02-15
 * <p>修改备注：
 **/
public class UserInfoBean {
    public boolean isRealLogin = true; //是否已经真实登录
    public String username; //账号
    public String password;//密码
    public int levelNum;
    public String token = "1234"; //token
    private BigDecimal localBalance;
    private BigDecimal totalBalance;
    private BigDecimal gameBalance;
    private int depositLevel;
    private String realName;
    private String phone;
    private String sportUserId;
    private String sportToken;

    public boolean isRealLogin() {
        return isRealLogin;
    }

    public void setRealLogin(boolean realLogin) {
        isRealLogin = realLogin;
    }

    public String getUsername() {
        if (TextUtils.isEmpty(username)) {
            return "";
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsRealLogin() {
        return this.isRealLogin;
    }

    public void setIsRealLogin(boolean isRealLogin) {
        this.isRealLogin = isRealLogin;
    }

    public BigDecimal getLocalBalance() {
        return localBalance;
    }

    public void setLocalBalance(BigDecimal localBalance) {
        this.localBalance = localBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getGameBalance() {
        return gameBalance;
    }

    public void setGameBalance(BigDecimal gameBalance) {
        this.gameBalance = gameBalance;
    }

    public int getDepositLevel() {
        return depositLevel;
    }

    public void setDepositLevel(int depositLevel) {
        this.depositLevel = depositLevel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSportUserId() {
        return sportUserId;
    }

    public void setSportUserId(String sportUserId) {
        this.sportUserId = sportUserId;
    }

    public String getSportToken() {
        return sportToken;
    }

    public void setSportToken(String sportToken) {
        this.sportToken = sportToken;
    }

    public boolean isTryLogin() {
        if (!isRealLogin) {
            return !TextUtils.isEmpty(token)
                    && !TextUtils.isEmpty(username)
                    && !TextUtils.isEmpty(password);
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "UserInfoBean{" +
                "isRealLogin=" + isRealLogin +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", levelNum=" + levelNum +
                ", token='" + token + '\'' +
                ", localBalance=" + localBalance +
                ", totalBalance=" + totalBalance +
                ", gameBalance=" + gameBalance +
                ", depositLevel=" + depositLevel +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
