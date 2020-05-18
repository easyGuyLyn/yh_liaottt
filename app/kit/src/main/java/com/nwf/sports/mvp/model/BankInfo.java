package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Nereus on 2017/5/19.
 */
public class BankInfo implements Parcelable{

    /**
     * code : cmb
     * name : 招商银行
     * icon : http://10.91.6.23:8080/static/banks/bank_zs.png
     * icon_bg : http://10.91.6.23:8080/static/banks/banklist/bank_zs_tm.png
     * bg : http://10.91.6.23:8080/static/banks/banklist/bank_bg_red.png
     */

    private String code;
    private String name;
    private String icon;
    private String icon_bg;
    private String bg;

    public BankInfo(){

    }

    protected BankInfo(Parcel in) {
        code = in.readString();
        name = in.readString();
        icon = in.readString();
        icon_bg = in.readString();
        bg = in.readString();
    }

    public static final Creator<BankInfo> CREATOR = new Creator<BankInfo>() {
        @Override
        public BankInfo createFromParcel(Parcel in) {
            return new BankInfo(in);
        }

        @Override
        public BankInfo[] newArray(int size) {
            return new BankInfo[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_bg() {
        return icon_bg;
    }

    public void setIcon_bg(String icon_bg) {
        this.icon_bg = icon_bg;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(icon_bg);
        dest.writeString(bg);
    }
}
