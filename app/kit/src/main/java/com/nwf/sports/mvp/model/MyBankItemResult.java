package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by Nereus on 2017/6/30.
 */
public class MyBankItemResult implements Parcelable {

    /**
     * id : 1000264607
     * bankName : 中国农业银行
     * name : 哈哈哈哈
     * cardNo : 6565646646767679
     * type : 借记卡
     * city : 东城区
     * province : 北京市
     * branch : 经济技术
     * def : 1
     * currency : CNY
     * bankInfo : {"code":"abc","name":"中国农业银行","icon":"http://10.91.37.42:11000/static/banks/bank_ny.png","icon_bg":"http://10.91.37.42:11000/static/banks/banklist/bank_ny_tm.png","bg":"http://10.91.37.42:11000/static/banks/banklist/bank_bg_gn.png"}
     */

    private String id;
    private String bankName;
    private String name;
    private String cardNo;
    private String type;
    private String city;
    private String province;
    private String branch;
    private String def;
    private String currency;
    private BankInfoBean bankInfo;

    public boolean isEmpty() {
        return TextUtils.isEmpty(cardNo);
    }

    public MyBankItemResult(){

    }

    protected MyBankItemResult(Parcel in) {
        id = in.readString();
        bankName = in.readString();
        name = in.readString();
        cardNo = in.readString();
        type = in.readString();
        city = in.readString();
        province = in.readString();
        branch = in.readString();
        def = in.readString();
        currency = in.readString();
    }

    public static final Creator<MyBankItemResult> CREATOR = new Creator<MyBankItemResult>() {
        @Override
        public MyBankItemResult createFromParcel(Parcel in) {
            return new MyBankItemResult(in);
        }

        @Override
        public MyBankItemResult[] newArray(int size) {
            return new MyBankItemResult[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BankInfoBean getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfoBean bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public String toString() {
        return "MyBankItemResult{" +
                "id='" + id + '\'' +
                ", bankName='" + bankName + '\'' +
                ", name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", branch='" + branch + '\'' +
                ", def='" + def + '\'' +
                ", currency='" + currency + '\'' +
                ", bankInfo=" + bankInfo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(bankName);
        dest.writeString(name);
        dest.writeString(cardNo);
        dest.writeString(type);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(branch);
        dest.writeString(def);
        dest.writeString(currency);
    }

    public static class BankInfoBean implements Parcelable{
        /**
         * code : abc
         * name : 中国农业银行
         * icon : http://10.91.37.42:11000/static/banks/bank_ny.png
         * icon_bg : http://10.91.37.42:11000/static/banks/banklist/bank_ny_tm.png
         * bg : http://10.91.37.42:11000/static/banks/banklist/bank_bg_gn.png
         */

        private String code;
        private String name;
        private String icon;
        private String icon_bg;
        private String bg;

        protected BankInfoBean(Parcel in) {
            code = in.readString();
            name = in.readString();
            icon = in.readString();
            icon_bg = in.readString();
            bg = in.readString();
        }

        public static final Creator<BankInfoBean> CREATOR = new Creator<BankInfoBean>() {
            @Override
            public BankInfoBean createFromParcel(Parcel in) {
                return new BankInfoBean(in);
            }

            @Override
            public BankInfoBean[] newArray(int size) {
                return new BankInfoBean[size];
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

        @Override
        public String toString() {
            return "BankInfoBean{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", icon_bg='" + icon_bg + '\'' +
                    ", bg='" + bg + '\'' +
                    '}';
        }
    }
}
