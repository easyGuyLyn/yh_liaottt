package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * <p>类描述：
 * <p>创建人：simon
 * <p>创建时间：2019-04-03
 * <p>修改人：simon
 * <p>修改时间：2019-04-03
 * <p>修改备注：
 **/
public class PointCardVo implements Serializable, Parcelable {

    /**
     * "id":"626",
     * "flag":true,
     * "values":"5,10,15,25,30,50,100,200",
     * "productid":"E03",
     * "description":"※该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。;※在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。	",
     * "name":"骏网一卡通",
     * "value":"10",
     * "code":"JUNNET",
     * "iconUrl":null,
     * "cardValues":[
     * "5",
     * "10",
     * "15",
     * "25",
     * "30",
     * "50",
     * "100",
     * "200"
     * ],
     * "descriptions":[
     * "※该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。",
     * "※在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。	"
     * ]
     */
    private String id;
    private String code;
    private String name;
    // ※该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。;
    // ※在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。
//    private  List<String> descriptions;
    private List<String> cardValues; // 金额数组
    private String value; // 费率 需要除以 100
    private String flag;  // true/false
    private String iconUrl; // url
    public int position;

    public PointCardVo() {
    }

    protected PointCardVo(Parcel in) {
        id = in.readString();
        code = in.readString();
        name = in.readString();
        cardValues = in.createStringArrayList();
        value = in.readString();
        flag = in.readString();
        iconUrl = in.readString();
        position = in.readInt();
    }

    public static final Creator<PointCardVo> CREATOR = new Creator<PointCardVo>() {
        @Override
        public PointCardVo createFromParcel(Parcel in) {
            return new PointCardVo(in);
        }

        @Override
        public PointCardVo[] newArray(int size) {
            return new PointCardVo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    //    public String getDescription()
    //    {
    //        return description;
    //    }
    //
    //    public void setDescription(String description)
    //    {
    //        this.description = description;
    //    }

    public List<String> getCardValues() {
        return cardValues;
    }

    public void setCardValues(List<String> cardValues) {
        this.cardValues = cardValues;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "PointCardVo{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", cardValues=" + cardValues.toString() +
                ", value='" + value + '\'' +
                ", flag='" + flag + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeStringList(cardValues);
        dest.writeString(value);
        dest.writeString(flag);
        dest.writeString(iconUrl);
        dest.writeInt(position);
    }
}
