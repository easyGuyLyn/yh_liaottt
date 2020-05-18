package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-27
 * <p>修改人：Simon
 * <p>修改时间：2020-01-27
 * <p>修改备注：
 **/
public class RedPacketResult implements Parcelable {

    /**
     * packetId : d3b89c5f1c2c4ab98abdd0553246d374
     * productId : E03
     * userName : vsimon9090
     * sendTitle : 恭喜发财，大吉大利
     * sendAmount : 100
     * sendNumber : 1
     * sendType : 0
     * groupId : PpPCPCll
     * flag : null
     * grabPacketKey : null
     * pageNo : null
     * pageSize : null
     */

    public String packetId;
    public String productId;
    public String userName;
    public String sendTitle;
    public String sendAmount;
    public int sendNumber;
    public int sendType;
    public String groupId;
    public String flag;
    public String grabPacketKey;
    public String pageNo;
    public String pageSize;
    public String specialNumber;

    public RedPacketResult() {

    }


    protected RedPacketResult(Parcel in) {
        packetId = in.readString();
        productId = in.readString();
        userName = in.readString();
        sendTitle = in.readString();
        sendAmount = in.readString();
        sendNumber = in.readInt();
        sendType = in.readInt();
        groupId = in.readString();
        flag = in.readString();
        grabPacketKey = in.readString();
        pageNo = in.readString();
        pageSize = in.readString();
        specialNumber = in.readString();
    }

    public static final Creator<RedPacketResult> CREATOR = new Creator<RedPacketResult>() {
        @Override
        public RedPacketResult createFromParcel(Parcel in) {
            return new RedPacketResult(in);
        }

        @Override
        public RedPacketResult[] newArray(int size) {
            return new RedPacketResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packetId);
        parcel.writeString(productId);
        parcel.writeString(userName);
        parcel.writeString(sendTitle);
        parcel.writeString(sendAmount);
        parcel.writeInt(sendNumber);
        parcel.writeInt(sendType);
        parcel.writeString(groupId);
        parcel.writeString(flag);
        parcel.writeString(grabPacketKey);
        parcel.writeString(pageNo);
        parcel.writeString(pageSize);
        parcel.writeString(specialNumber);
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSendTitle() {
        return sendTitle;
    }

    public void setSendTitle(String sendTitle) {
        this.sendTitle = sendTitle;
    }

    public String getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(String sendAmount) {
        this.sendAmount = sendAmount;
    }

    public int getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGrabPacketKey() {
        return grabPacketKey;
    }

    public void setGrabPacketKey(String grabPacketKey) {
        this.grabPacketKey = grabPacketKey;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSpecialNumber() {
        return specialNumber;
    }

    public void setSpecialNumber(String specialNumber) {
        this.specialNumber = specialNumber;
    }
}
