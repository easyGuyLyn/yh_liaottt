package com.nwf.sports.mvp.model;

/**
 * Created by NWF_AK on 2017/7/15.
 */

public class QueryDepositProgressResult
{

    /**
     * endtime : 2017-07-25 16:34:48
     * starttime : 2017-07-25 16:34:37
     * status : 2  [2 到账]为返回首页，[0 提交]为刷新进度，
     */

    private String endtime;
    private String starttime;
    private String status;

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public String getStarttime()
    {
        return starttime;
    }

    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
