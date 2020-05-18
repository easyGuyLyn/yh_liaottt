package com.nwf.sports.mvp.model;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class LimitTransferResult {


    /**
     * flag : true
     * gameBalance : 0
     */

    private boolean flag;
    private int gameBalance;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getGameBalance() {
        return gameBalance;
    }

    public void setGameBalance(int gameBalance) {
        this.gameBalance = gameBalance;
    }
}
