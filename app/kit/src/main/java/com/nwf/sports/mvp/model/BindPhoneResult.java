package com.nwf.sports.mvp.model;

/**
 * Created by Nereus on 2017/8/12.
 */

public class BindPhoneResult
{

    public boolean alreadyBoundPhone;
    public String phone;

    public BindPhoneResult()
    {
    }

    public BindPhoneResult(boolean alreadyBoundPhone, String phone)
    {
        this.alreadyBoundPhone = alreadyBoundPhone;
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "BindPhoneResult{" +
                "alreadyBoundPhone=" + alreadyBoundPhone +
                ", phone='" + phone + '\'' +
                '}';
    }
}
