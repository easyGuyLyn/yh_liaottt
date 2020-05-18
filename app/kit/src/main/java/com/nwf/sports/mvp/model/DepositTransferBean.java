package com.nwf.sports.mvp.model;

import android.os.Parcel;

import java.io.Serializable;

/**
 * <p>类描述：  BQ存款 返回数据
 * <p>创建人：Simon
 * <p>创建时间：2019-04-11
 * <p>修改人：Simon
 * <p>修改时间：2019-04-11
 * <p>修改备注：
 **/
public class DepositTransferBean implements Serializable {
    String accountName;
    String bankAccountCode;
    String bqpaytypeCode;

    public DepositTransferBean(String accountName, String bankAccountCode, String bqpaytypeCode) {
        this.accountName = accountName;
        this.bankAccountCode = bankAccountCode;
        this.bqpaytypeCode = bqpaytypeCode;
    }

    protected DepositTransferBean(Parcel in) {
        accountName = in.readString();
        bankAccountCode = in.readString();
        bqpaytypeCode = in.readString();
    }


    @Override
    public String toString() {
        return "DepNameEvent{" +
                "accountName='" + accountName + '\'' +
                ", bankAccountCode='" + bankAccountCode + '\'' +
                ", bqpaytypeCode='" + bqpaytypeCode + '\'' +
                '}';
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getBqpaytypeCode() {
        return bqpaytypeCode;
    }

    public void setBqpaytypeCode(String bqpaytypeCode) {
        this.bqpaytypeCode = bqpaytypeCode;
    }

}
