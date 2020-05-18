package com.nwf.sports.mvp.model;

import java.math.BigDecimal;

/**
 * Created by Nereus on 2017/7/18.
 */
public class PersonalInfoResult {


    /**
     * userId : 1000566900
     * userName : vsimon999
     * mobile : 17439494994
     * createTime : 2018-10-17 17:31:39
     * pid : E03
     * levelNum : 2
     * depositLevel : 0
     * gameKey : null
     * lastGameCode : null
     * localBalance : 1.0000121516E8
     * realName : 哈哈哈哈
     * currency : CNY
     * refCode : 1001061
     * lastLoginDate : 2019-07-09 17:05:32
     * lastDepositDate : 2019-06-21T04:30:41.000+0000
     */

    private int userId;
    private String userName;
    private String mobile;
    private String createTime;
    private String pid;
    private int levelNum;
    private String depositLevel;
    private Object gameKey;
    private Object lastGameCode;
    private BigDecimal localBalance;
    private String realName;
    private String currency;
    private String refCode;
    private String lastLoginDate;
    private String lastDepositDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public String getDepositLevel() {
        return depositLevel;
    }

    public void setDepositLevel(String depositLevel) {
        this.depositLevel = depositLevel;
    }

    public Object getGameKey() {
        return gameKey;
    }

    public void setGameKey(Object gameKey) {
        this.gameKey = gameKey;
    }

    public Object getLastGameCode() {
        return lastGameCode;
    }

    public void setLastGameCode(Object lastGameCode) {
        this.lastGameCode = lastGameCode;
    }

    public BigDecimal getLocalBalance() {
        return localBalance;
    }

    public void setLocalBalance(BigDecimal localBalance) {
        this.localBalance = localBalance;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastDepositDate() {
        return lastDepositDate;
    }

    public void setLastDepositDate(String lastDepositDate) {
        this.lastDepositDate = lastDepositDate;
    }
}
