package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ak on 2018/2/13.
 */

public class PreviousWithdrawResult implements Parcelable {

    /**
     * bankAccountNo : 4=ZPgKpiuasKPRSFIZEyC0yb+2eEKs4zcWT4OdL
     * amount : 100000
     * flag : -1    0等待  1已处理  2审批通过 -1 前台取消  -2 后台取消  9处理中  -3已拒绝
     * requestId : E031906151216C1B001
     * bankAccountType : null
     * bankName : 中国农业银行
     * flagZH : 取消
     * remark : null
     * createDate : 2019-06-15 12:16:42
     */

    private String bankAccountNo;
    private String amount;
    private int flag;
    private String requestId;
    private String bankAccountType;
    private String bankName;
    private String flagZH;
    private String remark;
    private String createDate;

    public PreviousWithdrawResult() {

    }

    protected PreviousWithdrawResult(Parcel in) {
        bankAccountNo = in.readString();
        amount = in.readString();
        flag = in.readInt();
        requestId = in.readString();
        bankAccountType = in.readString();
        bankName = in.readString();
        flagZH = in.readString();
        remark = in.readString();
        createDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bankAccountNo);
        dest.writeString(amount);
        dest.writeInt(flag);
        dest.writeString(requestId);
        dest.writeString(bankAccountType);
        dest.writeString(bankName);
        dest.writeString(flagZH);
        dest.writeString(remark);
        dest.writeString(createDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreviousWithdrawResult> CREATOR = new Creator<PreviousWithdrawResult>() {
        @Override
        public PreviousWithdrawResult createFromParcel(Parcel in) {
            return new PreviousWithdrawResult(in);
        }

        @Override
        public PreviousWithdrawResult[] newArray(int size) {
            return new PreviousWithdrawResult[size];
        }
    };

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFlagZH() {
        return flagZH;
    }

    public void setFlagZH(String flagZH) {
        this.flagZH = flagZH;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "PreviousWithdrawResult{" +
                "bankAccountNo='" + bankAccountNo + '\'' +
                ", amount=" + amount +
                ", flag=" + flag +
                ", requestId='" + requestId + '\'' +
                ", bankAccountType=" + bankAccountType +
                ", bankName='" + bankName + '\'' +
                ", flagZH='" + flagZH + '\'' +
                ", remark=" + remark +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
