package com.nwf.sports.mvp.model;

/**
 * Created by simon on 20179/4/4.
 */
public class CheckIfBindPhoneResult
{
    public boolean bound;
    public String phone;

    @Override
    public String toString()
    {
        return "CheckIfBindPhoneResult{" +
                "bound=" + bound +
                ", phone='" + phone + '\'' +
                '}';
    }

    public CheckIfBindPhoneResult(boolean bound, String phone)
    {
        this.bound = bound;
        this.phone = phone;
    }

    public CheckIfBindPhoneResult()
    {
    }
}
