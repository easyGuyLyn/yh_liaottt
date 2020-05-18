package com.nwf.sports.mvp.model;

/**
 * Created by simon on 2019/3/14.
 */

public class TotalAmountResult
{
    /**
     * ratio : 0.03
     * totalAmount : 0.00
     * userReportUrl : 123
     */

    private double ratio;
    private String totalAmount;
    private String userReportUrl;

    public double getRatio()
    {
        return ratio;
    }

    public void setRatio(double ratio)
    {
        this.ratio = ratio;
    }

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getUserReportUrl()
    {
        return userReportUrl;
    }

    public void setUserReportUrl(String userReportUrl)
    {
        this.userReportUrl = userReportUrl;
    }
}
