package com.nwf.sports.mvp.model;

import java.math.BigDecimal;

/**
 * Created by Nereus on 2017/6/20.
 */
public class RefreshBalanceResult
{
    public BigDecimal balance;

    @Override
    public String toString()
    {
        return "GetBalanceResult{" +
                "balance=" + balance +
                '}';
    }

    public RefreshBalanceResult()
    {
    }

    public RefreshBalanceResult(BigDecimal balance)
    {
        this.balance = balance;
    }
}
