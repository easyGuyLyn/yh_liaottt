package com.nwf.sports.mvp.model;

/**
 * Created by ak on 2018/4/3.
 */

public class GrowUpLevelResult
{
    /**
     * levelName : 见习小生
     * levelTime : 0年0个月10日
     * levelUpRatio : 0.03
     */

    private String levelName;
    private String levelTime;
    private double levelUpRatio;

    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    public String getLevelTime()
    {
        return levelTime;
    }

    public void setLevelTime(String levelTime)
    {
        this.levelTime = levelTime;
    }

    public double getLevelUpRatio()
    {
        return levelUpRatio;
    }

    public void setLevelUpRatio(double levelUpRatio)
    {
        this.levelUpRatio = levelUpRatio;
    }
}
