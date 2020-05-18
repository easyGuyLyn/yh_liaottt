package com.nwf.sports.mvp.model;


import java.math.BigDecimal;

/**
 * <p>类描述： 登录注册返回的用户信息
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public class LoginResult {

    /**
     * pid : E03
     * loginType : 1
     * userName : vandrew007
     * refCode : 1001791
     * ipAddress : null
     * domainName : http://10.91.37.42:11000/
     * websiteType : 6
     * deviceType : 2
     * version : null
     * levelNum : 0
     * localBalance : 0.0
     * totalBalance : 0.0
     * gameBalance : 0.0
     * depositLevel : 0
     * realName : 胖哥
     * phone : 13667788999
     */
    private String pid;
    private String token;
    private int loginType;
    private String userName;
    private String refCode;
    private Object ipAddress;
    private String domainName;
    private String websiteType;
    private String deviceType;
    private String version;
    private int levelNum;
    private BigDecimal localBalance;
    private BigDecimal totalBalance;
    private BigDecimal gameBalance;
    private int depositLevel;
    private String realName;
    private String phone;
    private String password;
    private String sportUserId;
    private String sportToken;
    public boolean isRealLogin; //是否已经真实登录

    public LoginResult() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getWebsiteType() {
        return websiteType;
    }

    public void setWebsiteType(String websiteType) {
        this.websiteType = websiteType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginResult{" +
                "pid='" + pid + '\'' +
                ", token='" + token + '\'' +
                ", loginType=" + loginType +
                ", userName='" + userName + '\'' +
                ", refCode='" + refCode + '\'' +
                ", ipAddress=" + ipAddress +
                ", domainName='" + domainName + '\'' +
                ", websiteType='" + websiteType + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", version='" + version + '\'' +
                ", levelNum=" + levelNum +
                ", localBalance=" + localBalance +
                ", totalBalance=" + totalBalance +
                ", gameBalance=" + gameBalance +
                ", depositLevel=" + depositLevel +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
