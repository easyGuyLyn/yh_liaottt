package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWF_AK on 2017/7/14.
 */

public class Depositmanners implements Parcelable
{

    private List<PaymentListBean> paymentList;

    public List<PaymentListBean> getPaymentList()
    {
        return paymentList;
    }

    public void setPaymentList(List<PaymentListBean> paymentList)
    {
        this.paymentList = paymentList;
    }

    public static class PaymentListBean implements Parcelable
    {
        /**
         * extra : [{"code":"gddb","name":"广东发展银行"},{"code":"hxb","name":"华夏银行"},{"code":"boco","name":"交通银行"},{"code":"hzbank","name":"杭州银行"},{"code":"ebb",
         * "name":"光大银行"},{"code":"citic","name":"中信银行"},{"code":"ccb","name":"建设银行"},{"code":"inb","name":"兴业银行"},{"code":"abc","name":"中国农业银行"},
         * {"code":"msb","name":"民生银行"},{"code":"bos","name":"上海银行"},{"code":"cmb","name":"招商银行"},{"code":"pab","name":"平安银行"},{"code":"shpdb",
         * "name":"上海浦东发展银行"},{"code":"icbc","name":"工商银行"},{"code":"nbcb","name":"宁波银行"},{"code":"bofc","name":"中国银行"},{"code":"psb","name":"邮政储蓄"},
         * {"code":"bjyh","name":"北京银行"}]
         * highestvalue : 150000
         * iconUrl :
         * lowestvalue : 100
         * manners :
         * paymannerid : 1
         * paymannername : 银联在线支付
         * serviceavailable : true
         */

        private String highestvalue;
        private String iconUrl;
        private String lowestvalue;
        private String manners;
        private String payid;
        private String paymannerid;
        private String paymannername;
        private boolean serviceavailable;
        private float handleFee;   // 3 ===> 3% 0.03   2018-08-21
        private String isBigAmount; // Y,N 是否大额支付 2018-08-21
        private List<ExtraBean> extra;
        private List<TransferTypeVo> transferTypeList;
        private String[] amountList;

        @Override
        public String toString()
        {
            return "PaymentListBean{" +
                    "highestvalue='" + highestvalue + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", lowestvalue='" + lowestvalue + '\'' +
                    ", manners='" + manners + '\'' +
                    ", payid='" + payid + '\'' +
                    ", paymannerid='" + paymannerid + '\'' +
                    ", paymannername='" + paymannername + '\'' +
                    ", serviceavailable=" + serviceavailable +
                    ", handleFee=" + handleFee +
                    ", isBigAmount='" + isBigAmount + '\'' +
                    ", extra=" + new Gson().toJson(extra) +
                    ", transferTypeList=" + new Gson().toJson(transferTypeList) +
                    '}';
        }

        public PaymentListBean()
        {
        }

        @Override
        public Object clone()
        {
            // throws CloneNotSupportedException
            //try
            //{
            //    return super.clone();
            //} catch (CloneNotSupportedException e)
            //{
            //    e.printStackTrace();
            //}

            PaymentListBean vo = new PaymentListBean();
            vo.payid = this.payid;
            vo.paymannerid = this.paymannerid;
            vo.paymannername = this.paymannername;
            vo.lowestvalue = this.lowestvalue;
            vo.highestvalue = this.highestvalue;
            vo.iconUrl = this.iconUrl;
            vo.manners = this.manners;
            vo.extra = this.extra;
            vo.serviceavailable = this.serviceavailable;
            vo.handleFee = this.handleFee;
            vo.isBigAmount = this.isBigAmount;
            vo.transferTypeList = this.transferTypeList;
            vo.amountList = this.amountList;

            return vo;
        }

        //public PaymentListBean(String payid, String paymannerid, String paymannername, String lowestvalue, String
        //        highestvalue, String iconUrl, String manners, List<ExtraBean> extra, boolean serviceavailable, float
        //                               handleFee, String isBigAmount)
        //{
        //    this.payid = payid;
        //    this.paymannerid = paymannerid;
        //    this.paymannername = paymannername;
        //    this.lowestvalue = lowestvalue;
        //    this.highestvalue = highestvalue;
        //    this.iconUrl = iconUrl;
        //    this.manners = manners;
        //    this.extra = extra;
        //    this.serviceavailable = serviceavailable;
        //    this.handleFee = handleFee;
        //    this.isBigAmount = isBigAmount;
        //}

        public String getHighestvalue()
        {
            return highestvalue;
        }

        public void setHighestvalue(String highestvalue)
        {
            this.highestvalue = highestvalue;
        }

        public String getIconUrl()
        {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl)
        {
            this.iconUrl = iconUrl;
        }

        public String getLowestvalue()
        {
            return lowestvalue;
        }

        public void setLowestvalue(String lowestvalue)
        {
            this.lowestvalue = lowestvalue;
        }

        public String getManners()
        {
            return manners;
        }

        public void setManners(String manners)
        {
            this.manners = manners;
        }

        public String getPayid()
        {
            return payid;
        }

        public void setPayid(String payid)
        {
            this.payid = payid;
        }

        public String getPaymannerid()
        {
            return paymannerid;
        }

        public void setPaymannerid(String paymannerid)
        {
            this.paymannerid = paymannerid;
        }

        public String getPaymannername()
        {
            return paymannername;
        }

        public void setPaymannername(String paymannername)
        {
            this.paymannername = paymannername;
        }

        public boolean isServiceavailable()
        {
            return serviceavailable;
        }

        public void setServiceavailable(boolean serviceavailable)
        {
            this.serviceavailable = serviceavailable;
        }

        public List<ExtraBean> getExtra()
        {
            if (null == extra)
            {
                return new ArrayList<>(); // 2018-08-09 解决崩溃问题
            }
            return extra;
        }

        public float getHandleFee()
        {
            return handleFee;
        }

        public void setHandleFee(float handleFee)
        {
            this.handleFee = handleFee;
        }

        public String getIsBigAmount()
        {
            return isBigAmount;
        }

        public void setIsBigAmount(String isBigAmount)
        {
            this.isBigAmount = isBigAmount;
        }

        public void setExtra(List<ExtraBean> extra)
        {
            this.extra = extra;
        }

        public List<TransferTypeVo> getTransferTypeList()
        {
            return transferTypeList;
        }

        public void setTransferTypeList(List<TransferTypeVo> transferTypeList)
        {
            this.transferTypeList = transferTypeList;
        }

        public String[] getAmountList()
        {
            return amountList;
        }

        public void setAmountList(String[] amountList)
        {
            this.amountList = amountList;
        }

        public static class TransferTypeVo implements Parcelable
        {
            private String code;
            private String desc;

            @Override
            public String toString()
            {
                return "TransferTypeVo{" +
                        "code='" + code + '\'' +
                        ", desc='" + desc + '\'' +
                        '}';
            }

            public String getCode()
            {
                return code;
            }

            public void setCode(String code)
            {
                this.code = code;
            }

            public String getDesc()
            {
                return desc;
            }

            public void setDesc(String desc)
            {
                this.desc = desc;
            }

            protected TransferTypeVo(Parcel in)
            {
                code = in.readString();
                desc = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags)
            {
                dest.writeString(code);
                dest.writeString(desc);
            }

            @Override
            public int describeContents()
            {
                return 0;
            }

            public static final Creator<TransferTypeVo> CREATOR = new Creator<TransferTypeVo>()
            {
                @Override
                public TransferTypeVo createFromParcel(Parcel in)
                {
                    return new TransferTypeVo(in);
                }

                @Override
                public TransferTypeVo[] newArray(int size)
                {
                    return new TransferTypeVo[size];
                }
            };
        }

        public static class ExtraBean implements Parcelable
        {

            /**
             * code : gddb
             * name : 广东发展银行
             */

            private String code;
            private String name;
            public boolean isCheck;
            /**
             * id : 206107
             * bankAccountCode : BCCB
             * bankAccountName : FK(付款)
             * bankAccountNo : 45471140212121
             * bankName : 北京银行
             * bankCity : 北京市
             * branchName : dfasdfasdf
             * trustLevel : 00000011100000000000
             * cusLevel : 0000001110000000000000000
             * limitAmount : 90000000
             * province : 北京
             * provinces : null
             * lastDepostFlag : 2
             * remarks : null
             * depositAmount : 0
             * isShow : 0
             * currency : CNY
             * specialMember : null
             * wslotterys : []
             */

            private String id;
            private String bankAccountCode;
            private String bankAccountName;
            private String bankAccountNo;
            private String bankName;
            private String bankCity;
            private String branchName;
            //            private String trustLevel;
            //            private String cusLevel;
            private String limitAmount;
            //            private String province;
            //            private String provinces;
            //            private String lastDepostFlag;
            //            private String remarks;
            //            private String depositAmount;
            private String isShow;
            private String currency;
            //            private String specialMember;

            @Override
            public Object clone() throws CloneNotSupportedException
            {
                return super.clone();
            }

            public ExtraBean(String id, String bankAccountCode, String bankAccountName, String bankAccountNo, String bankName, String bankCity, String
                    branchName, String currency)
            {

                this.id = id;
                this.bankAccountCode = bankAccountCode;
                this.bankAccountName = bankAccountName;
                this.bankAccountNo = bankAccountNo;
                this.bankName = bankName;
                this.bankCity = bankCity;
                this.branchName = branchName;
                this.currency = currency;
            }

            public String getCode()
            {
                return code;
            }

            public void setCode(String code)
            {
                this.code = code;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getBankAccountCode()
            {
                return bankAccountCode;
            }

            public void setBankAccountCode(String bankAccountCode)
            {
                this.bankAccountCode = bankAccountCode;
            }

            public String getBankAccountName()
            {
                return bankAccountName;
            }

            public void setBankAccountName(String bankAccountName)
            {
                this.bankAccountName = bankAccountName;
            }

            public String getBankAccountNo()
            {
                return bankAccountNo;
            }

            public void setBankAccountNo(String bankAccountNo)
            {
                this.bankAccountNo = bankAccountNo;
            }

            public String getBankName()
            {
                return bankName;
            }

            public void setBankName(String bankName)
            {
                this.bankName = bankName;
            }

            public String getBankCity()
            {
                return bankCity;
            }

            public void setBankCity(String bankCity)
            {
                this.bankCity = bankCity;
            }

            public String getBranchName()
            {
                return branchName;
            }

            public void setBranchName(String branchName)
            {
                this.branchName = branchName;
            }

/*            public String getTrustLevel() {
                return trustLevel;
            }

            public void setTrustLevel(String trustLevel) {
                this.trustLevel = trustLevel;
            }

            public String getCusLevel() {
                return cusLevel;
            }

            public void setCusLevel(String cusLevel) {
                this.cusLevel = cusLevel;
            }

            public String getLimitAmount() {
                return limitAmount;
            }

            public void setLimitAmount(String limitAmount) {
                this.limitAmount = limitAmount;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public Object getProvinces() {
                return provinces;
            }

            public void setProvinces(String provinces) {
                this.provinces = provinces;
            }

            public String getLastDepostFlag() {
                return lastDepostFlag;
            }

            public void setLastDepostFlag(String lastDepostFlag) {
                this.lastDepostFlag = lastDepostFlag;
            }*/

/*             public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

           public String getDepositAmount() {
                return depositAmount;
            }

            public void setDepositAmount(String depositAmount) {
                this.depositAmount = depositAmount;
            }*/

            public String getIsShow()
            {
                return isShow;
            }

            public void setIsShow(String isShow)
            {
                this.isShow = isShow;
            }

            public String getCurrency()
            {
                return currency;
            }

            public void setCurrency(String currency)
            {
                this.currency = currency;
            }

/*            public Object getSpecialMember() {
                return specialMember;
            }

            public void setSpecialMember(String specialMember) {
                this.specialMember = specialMember;
            }*/

            @Override
            public int describeContents()
            {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags)
            {
                dest.writeString(this.code);
                dest.writeString(this.name);
                dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
                dest.writeString(this.id);
                dest.writeString(this.bankAccountCode);
                dest.writeString(this.bankAccountName);
                dest.writeString(this.bankAccountNo);
                dest.writeString(this.bankName);
                dest.writeString(this.limitAmount);
                dest.writeString(this.isShow);
                dest.writeString(this.bankCity);
                dest.writeString(this.branchName);
                dest.writeString(this.currency);
                /*dest.writeString(this.remarks);
                dest.writeString(this.trustLevel);
                dest.writeString(this.cusLevel);
                dest.writeString(this.province);
                dest.writeString(this.provinces);
                dest.writeString(this.lastDepostFlag);
                dest.writeString(this.depositAmount);
                dest.writeString(this.specialMember);*/
            }

            protected ExtraBean(Parcel in)
            {
                this.code = in.readString();
                this.name = in.readString();
                this.isCheck = in.readByte() != 0;
                this.id = in.readString();
                this.bankAccountCode = in.readString();
                this.bankAccountName = in.readString();
                this.bankAccountNo = in.readString();
                this.bankName = in.readString();
                this.limitAmount = in.readString();
                this.isShow = in.readString();
                this.bankCity = in.readString();
                this.branchName = in.readString();
                this.currency = in.readString();
                /*this.remarks = in.readString();
                this.trustLevel = in.readString();
                this.cusLevel = in.readString();
                this.province = in.readString();
                this.provinces = in.readString();
                this.lastDepostFlag = in.readString();
                this.depositAmount = in.readString();
                this.specialMember = in.readString();*/
            }

            public static final Creator<ExtraBean> CREATOR = new Creator<ExtraBean>()
            {
                @Override
                public ExtraBean createFromParcel(Parcel source)
                {
                    return new ExtraBean(source);
                }

                @Override
                public ExtraBean[] newArray(int size)
                {
                    return new ExtraBean[size];
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
            dest.writeString(this.highestvalue);
            dest.writeString(this.iconUrl);
            dest.writeString(this.lowestvalue);
            dest.writeString(this.manners);
            dest.writeString(this.payid);
            dest.writeString(this.paymannerid);
            dest.writeString(this.paymannername);
            dest.writeString(this.isBigAmount);
            dest.writeDouble(this.handleFee);
            dest.writeByte(this.serviceavailable ? (byte) 1 : (byte) 0);
            dest.writeTypedList(this.extra);
            dest.writeTypedList(this.transferTypeList);
            dest.writeStringArray(this.amountList);
        }

        protected PaymentListBean(Parcel in)
        {
            this.highestvalue = in.readString();
            this.iconUrl = in.readString();
            this.lowestvalue = in.readString();
            this.manners = in.readString();
            this.payid = in.readString();
            this.paymannerid = in.readString();
            this.paymannername = in.readString();
            this.isBigAmount = in.readString();
            this.handleFee = in.readFloat();
            this.serviceavailable = in.readByte() != 0;
            this.extra = in.createTypedArrayList(ExtraBean.CREATOR);
            this.transferTypeList = in.createTypedArrayList(TransferTypeVo.CREATOR);
            this.amountList = in.createStringArray();
        }

        public static final Creator<PaymentListBean> CREATOR = new Creator<PaymentListBean>()
        {
            @Override
            public PaymentListBean createFromParcel(Parcel source)
            {
                return new PaymentListBean(source);
            }

            @Override
            public PaymentListBean[] newArray(int size)
            {
                return new PaymentListBean[size];
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
        dest.writeList(this.paymentList);
    }

    public Depositmanners()
    {
    }

    protected Depositmanners(Parcel in)
    {
        this.paymentList = new ArrayList<PaymentListBean>();
        in.readList(this.paymentList, PaymentListBean.class.getClassLoader());
    }

    public static final Creator<Depositmanners> CREATOR = new Creator<Depositmanners>()
    {
        @Override
        public Depositmanners createFromParcel(Parcel source)
        {
            return new Depositmanners(source);
        }

        @Override
        public Depositmanners[] newArray(int size)
        {
            return new Depositmanners[size];
        }
    };
}
