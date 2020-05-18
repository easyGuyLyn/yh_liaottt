package cn.wildfirechat.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-15
 * <p>修改人：Simon
 * <p>修改时间：2020-01-15
 * <p>修改备注：
 **/
public class RedPack implements Parcelable {

    public String redPacketName;    //标题
    public String redPacketNumber;  //数量
    public String redPacketTotal;  //金额
    public String redPacketType;  //类型 红包类型（0手气红包，1普通红包，2单人红包，3埋雷红包，4牛牛红包）
    public String redPacketWhich; //发送id
    public String target; // 聊天id
    public String redPacketId; // 红包id
    public String statues = "0"; // 红包状态（0：未领取（默认状态），1：已领取，2：已过期）
    public String specialNumber; // 埋雷数字

    public RedPack() {

    }

    protected RedPack(Parcel in) {
        redPacketName = in.readString();
        redPacketNumber = in.readString();
        redPacketTotal = in.readString();
        redPacketType = in.readString();
        redPacketWhich = in.readString();
        target = in.readString();
        redPacketId = in.readString();
        statues = in.readString();
        specialNumber = in.readString();
    }

    public static final Creator<RedPack> CREATOR = new Creator<RedPack>() {
        @Override
        public RedPack createFromParcel(Parcel in) {
            return new RedPack(in);
        }

        @Override
        public RedPack[] newArray(int size) {
            return new RedPack[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(redPacketName);
        parcel.writeString(redPacketNumber);
        parcel.writeString(redPacketTotal);
        parcel.writeString(redPacketType);
        parcel.writeString(redPacketWhich);
        parcel.writeString(target);
        parcel.writeString(redPacketId);
        parcel.writeString(statues);
        parcel.writeString(specialNumber);
    }
}
