package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>类描述：IM的群信息自定义
 * <p>创建人：Simon
 * <p>创建时间：2020-02-06
 * <p>修改人：Simon
 * <p>修改时间：2020-02-06
 * <p>修改备注：
 **/
public class ExtraBean implements Parcelable {

    /**
     * nwfGroupType
     *
     * 红包群类型（0：聊天福利群，1：扫雷红包群，2：牛牛红包群，3：直播红包群，-1：自定义群，4：不翻倍牛牛群）
     *
     * lowestValue
     * BigDecimal	可发红包最低金额（0表示无限制，其它表示有限制）
     * 	hightestValue	BigDecimal	可发红包最高金额（0表示无限制，其它表示有限制）
     * 	odds	BigDecimal	赔率
     * 	minRedpacketNumber	int	最小发包个数
     * 	maxRedpacketNumber	int	最大发包个数
     */

    /**
     * nwfGroupType : 2
     * lowestValue : 600.0
     * hightestValue : 8000.0
     * odds : 1.8
     * minRedpacketNumber : 4
     * maxRedpacketNumber : 15
     */

    public int nwfGroupType;
    public String lowestValue;
    public String hightestValue;
    public String odds;
    public int minRedpacketNumber;
    public int maxRedpacketNumber;


    protected ExtraBean(Parcel in) {
        nwfGroupType = in.readInt();
        lowestValue = in.readString();
        hightestValue = in.readString();
        odds = in.readString();
        minRedpacketNumber = in.readInt();
        maxRedpacketNumber = in.readInt();
    }

    public static final Creator<ExtraBean> CREATOR = new Creator<ExtraBean>() {
        @Override
        public ExtraBean createFromParcel(Parcel in) {
            return new ExtraBean(in);
        }

        @Override
        public ExtraBean[] newArray(int size) {
            return new ExtraBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(nwfGroupType);
        parcel.writeString(lowestValue);
        parcel.writeString(hightestValue);
        parcel.writeString(odds);
        parcel.writeInt(minRedpacketNumber);
        parcel.writeInt(maxRedpacketNumber);
    }
}
