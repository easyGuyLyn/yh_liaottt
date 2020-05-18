package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ak on 2017/7/21.
 */

public class OnlinePay implements Parcelable {

    /**
     * amount : 1000
     * billdate : 20170721
     * billno : B7917072114011411
     * memo :
     * message :
     * status : 0
     * url : http://uatpay.zjzsh003.xyz/OnlinePaymentDispatch.do
     */

    private String amount;
    private String billdate;
    private String billno;
    private String keycode;
    private String memo;
    private String message;
    private int status;
    private String url;

    @Override
    public String toString() {
        return "OnlinePay{" +
                "amount='" + amount + '\'' +
                ", billdate='" + billdate + '\'' +
                ", billno='" + billno + '\'' +
                ", keycode='" + keycode + '\'' +
                ", memo='" + memo + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.billdate);
        dest.writeString(this.billno);
        dest.writeString(this.keycode);
        dest.writeString(this.memo);
        dest.writeString(this.message);
        dest.writeInt(this.status);
        dest.writeString(this.url);
    }

    public OnlinePay() {
    }

    protected OnlinePay(Parcel in) {
        this.amount = in.readString();
        this.billdate = in.readString();
        this.billno = in.readString();
        this.keycode = in.readString();
        this.memo = in.readString();
        this.message = in.readString();
        this.status = in.readInt();
        this.url = in.readString();
    }

    public static final Creator<OnlinePay> CREATOR = new Creator<OnlinePay>() {
        @Override
        public OnlinePay createFromParcel(Parcel source) {
            return new OnlinePay(source);
        }

        @Override
        public OnlinePay[] newArray(int size) {
            return new OnlinePay[size];
        }
    };
}
