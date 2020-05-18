package com.nwf.sports.mvp.model;

import com.nwf.sports.utils.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nereus on 2017/6/30.
 */
public class MyBankResult {
    public List<MyBankItemResult> bankList =new ArrayList<>();
    public int maxbanknumber;

    @Override
    public String toString() {
        return "MyBankResult{" +
                "bankList=" + Strings.toString(bankList) +
                ", maxbanknumber=" + maxbanknumber +
                '}';
    }
}
