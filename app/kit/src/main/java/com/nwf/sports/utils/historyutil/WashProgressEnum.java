package com.nwf.sports.utils.historyutil;

/**
 * 取款
 */

public enum WashProgressEnum
{

    PENDING(0),
    ONGOING(1),
    PENDING_ONGOING(9),
    APPROVED(2),
    CANCELLED(-1),
    CANCEL_CSO(-2),
    REJECTED(-3);

    private int flag;

    private WashProgressEnum(int flag)
    {
        this.flag = flag;
    }

    public int getFlag()
    {
        return flag;
    }
}
