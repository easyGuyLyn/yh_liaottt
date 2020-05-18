package com.nwf.sports.mvp.model;

import com.google.gson.GsonBuilder;

import java.math.BigDecimal;

/**
 * Created by Nereus on 2017/6/20.
 */
public class HistoryItemResult
{
    public String requestId;
    public BigDecimal amount;
    public long date;
    public String description;
    public int progress;

    public HistoryItemResult()
    {
    }

    @Override
    public String toString()
    {
        /*return "HistoryItemResult{" +
                "requestId='" + requestId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';*/
        return new GsonBuilder().create().toJson(this);
    }

}
