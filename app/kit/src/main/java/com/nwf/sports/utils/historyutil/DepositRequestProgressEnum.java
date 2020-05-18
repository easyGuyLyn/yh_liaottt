package com.nwf.sports.utils.historyutil;

/**
 * 存款
 */

public enum DepositRequestProgressEnum
{
    PENDING(0),
    ONGOING(1),
    APPROVED(2),
    CANCELLED(-1),
    CANCEL_CSO(-2),
    REJECTED(-3);
    private int flag;

    private DepositRequestProgressEnum(int flag)
    {
        this.flag = flag;
    }

    public int getFlag()
    {
        return flag;
    }
}
