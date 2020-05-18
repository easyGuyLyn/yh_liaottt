package com.nwf.sports.utils.historyutil;

/**
 * 优惠
 */

public enum DiscountsProgressEnum
{

    PENDING(0),
    ONGOING(1),
    PENDING_ONGOING(9),
    APPROVED(2),
    CANCELLED(-1),
    CANCEL_CSO(-2),
    REJECTED(-3);

    private int flag;

    private DiscountsProgressEnum(int i)
    {
        this.flag = i;
    }

    public int getFlag()
    {
        return this.flag;
    }

    public String getCode()
    {
        return String.valueOf(this.getFlag());
    }
}