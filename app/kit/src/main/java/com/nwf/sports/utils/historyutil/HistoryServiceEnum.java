package com.nwf.sports.utils.historyutil;

/**
 * Created by Nereus on 2017/6/20.
 */
public enum HistoryServiceEnum
{

    DEPOSIT("depositHistoryService"), WITHDRAW("withdrawHistoryService"), PROMOTION("promotionHistoryService"), REBATE("rebateHistoryService");

    private String servicename;

    private HistoryServiceEnum(String name)
    {
        this.servicename = name;
    }

    public final String getServicename()
    {
        return servicename;
    }
}
