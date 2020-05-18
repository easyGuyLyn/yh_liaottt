package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ak on 2017/7/25.
 */

public class FasterPay implements Parcelable
{

    /**
     * message :
     * order : {"accountname":"建设银行","accountnumber":"546456551355345","amount":1000,"bankaddress":"北京分行",
     * "bankcity":"北京市","bankcode":"CCBC","bankname":"中国建设银行","bankprovince":"北京","billno":"E04EP17072514260322",
     * "postscript":"7611"}
     * success : 0
     */

    private String message;
    private OrderBean order;
    private int success;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public OrderBean getOrder()
    {
        return order;
    }

    public void setOrder(OrderBean order)
    {
        this.order = order;
    }

    public int getSuccess()
    {
        return success;
    }

    public void setSuccess(int success)
    {
        this.success = success;
    }

    public static class OrderBean implements Parcelable
    {
        /**
         * accountname : 建设银行
         * accountnumber : 546456551355345
         * amount : 1000
         * bankaddress : 北京分行
         * bankcity : 北京市
         * bankcode : CCBC
         * bankname : 中国建设银行
         * bankprovince : 北京
         * billno : E04EP17072514260322
         * postscript : 7611
         */

        private String accountname;
        private String accountnumber;
        private String amount;
        private String bankaddress;
        private String bankcity;
        private String bankcode;
        private String bankname;
        private String bankprovince;
        private String billno;
        private String postscript;
        private String qrcode;
        private ArrayList<GuideVo> bqCourses;
        private String changeBQBankDisplay;

        public String getChangeBQBankDisplay()
        {
            return changeBQBankDisplay;
        }

        public void setChangeBQBankDisplay(String changeBQBankDisplay)
        {
            this.changeBQBankDisplay = changeBQBankDisplay;
        }

        public String getAccountname()
        {
            return accountname;
        }

        public void setAccountname(String accountname)
        {
            this.accountname = accountname;
        }

        public String getAccountnumber()
        {
            return accountnumber;
        }

        public void setAccountnumber(String accountnumber)
        {
            this.accountnumber = accountnumber;
        }

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public String getBankaddress()
        {
            return bankaddress;
        }

        public void setBankaddress(String bankaddress)
        {
            this.bankaddress = bankaddress;
        }

        public String getBankcity()
        {
            return bankcity;
        }

        public void setBankcity(String bankcity)
        {
            this.bankcity = bankcity;
        }

        public String getBankcode()
        {
            return bankcode;
        }

        public void setBankcode(String bankcode)
        {
            this.bankcode = bankcode;
        }

        public String getBankname()
        {
            return bankname;
        }

        public void setBankname(String bankname)
        {
            this.bankname = bankname;
        }

        public String getBankprovince()
        {
            return bankprovince;
        }

        public void setBankprovince(String bankprovince)
        {
            this.bankprovince = bankprovince;
        }

        public String getBillno()
        {
            return billno;
        }

        public void setBillno(String billno)
        {
            this.billno = billno;
        }

        public String getPostscript()
        {
            return postscript;
        }

        public void setPostscript(String postscript)
        {
            this.postscript = postscript;
        }

        public String getQrcode()
        {
            return qrcode;
        }

        public void setQrcode(String qrcode)
        {
            this.qrcode = qrcode;
        }

        public ArrayList<GuideVo> getBqCourses()
        {
            return bqCourses;
        }

        public void setBqCourses(ArrayList<GuideVo> bqCourses)
        {
            this.bqCourses = bqCourses;
        }

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(this.accountname);
            dest.writeString(this.accountnumber);
            dest.writeString(this.amount);
            dest.writeString(this.bankaddress);
            dest.writeString(this.bankcity);
            dest.writeString(this.bankcode);
            dest.writeString(this.bankname);
            dest.writeString(this.bankprovince);
            dest.writeString(this.billno);
            dest.writeString(this.postscript);
            dest.writeString(this.qrcode);
            dest.writeList(this.bqCourses);
        }

        public OrderBean()
        {
        }

        protected OrderBean(Parcel in)
        {
            this.accountname = in.readString();
            this.accountnumber = in.readString();
            this.amount = in.readString();
            this.bankaddress = in.readString();
            this.bankcity = in.readString();
            this.bankcode = in.readString();
            this.bankname = in.readString();
            this.bankprovince = in.readString();
            this.billno = in.readString();
            this.postscript = in.readString();
            this.qrcode = in.readString();
            this.bqCourses = in.readArrayList(GuideVo.class.getClassLoader());
        }

        public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>()
        {
            @Override
            public OrderBean createFromParcel(Parcel source)
            {
                return new OrderBean(source);
            }

            @Override
            public OrderBean[] newArray(int size)
            {
                return new OrderBean[size];
            }
        };
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.message);
        dest.writeParcelable((Parcelable) this.order, flags);
        dest.writeInt(this.success);
    }

    public FasterPay()
    {
    }

    protected FasterPay(Parcel in)
    {
        this.message = in.readString();
        this.order = in.readParcelable(OrderBean.class.getClassLoader());
        this.success = in.readInt();
    }

    public static final Creator<FasterPay> CREATOR = new Creator<FasterPay>()
    {
        @Override
        public FasterPay createFromParcel(Parcel source)
        {
            return new FasterPay(source);
        }

        @Override
        public FasterPay[] newArray(int size)
        {
            return new FasterPay[size];
        }
    };

    public static class GuideVo implements Parcelable
    {

        private String title;
        private ArrayList<String> iconList;

        protected GuideVo(Parcel in)
        {
            title = in.readString();
            iconList = in.createStringArrayList();
        }

        public static final Creator<GuideVo> CREATOR = new Creator<GuideVo>()
        {
            @Override
            public GuideVo createFromParcel(Parcel in)
            {
                return new GuideVo(in);
            }

            @Override
            public GuideVo[] newArray(int size)
            {
                return new GuideVo[size];
            }
        };

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public ArrayList<String> getIconList()
        {
            return iconList;
        }

        public void setIconList(ArrayList<String> iconList)
        {
            this.iconList = iconList;
        }

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(title);
            dest.writeStringList(iconList);
        }
    }

}
