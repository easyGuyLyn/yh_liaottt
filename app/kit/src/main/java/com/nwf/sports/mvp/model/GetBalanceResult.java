package com.nwf.sports.mvp.model;

import java.math.BigDecimal;

/**
 * Created by Nereus on 2017/6/20.
 */
public class GetBalanceResult {


    /**
     * pid : E03
     * userName : null
     * levelNum : 0
     * localBalance : 9.999901455E7
     * totalBalance : 9.999901455E7
     * gameBalance : 0.0
     */

    private String pid;
    private String userName;
    private int levelNum;
    private BigDecimal localBalance;
    private BigDecimal totalBalance;
    private BigDecimal gameBalance;


    public GetBalanceResult() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "GetBalanceResult{" +
                "pid='" + pid + '\'' +
                ", userName='" + userName + '\'' +
                ", levelNum=" + levelNum +
                ", localBalance=" + localBalance +
                ", totalBalance=" + totalBalance +
                ", gameBalance=" + gameBalance +
                '}';
    }
}
