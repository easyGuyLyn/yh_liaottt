package cn.wildfirechat.message;

import android.os.Parcel;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_red_packet;

/**
 * 红包消息
 */

@ContentTag(type = ContentType_red_packet, flag = PersistFlag.Persist_And_Count)
public class RedPacketMessageContent extends MessageContent {
    public String redPacketName;    //标题
    public String redPacketNumber;  //数量
    public String redPacketTotal;  //金额
    public String redPacketType;  //类型
    public String redPacketWhich; //发送id
    public String redPacketId; // 红包id
    public String statues; // 红包状态（0：未领取（默认状态），1：已领取，2：已过期,3已抢完）

    public RedPacketMessageContent() {
    }

    public RedPacketMessageContent(String redPacketName, String redPacketNumber, String redPacketTotal, String redPacketType, String redPacketWhich) {
        this.redPacketName = redPacketName;
        this.redPacketNumber = redPacketNumber;
        this.redPacketTotal = redPacketTotal;
        this.redPacketType = redPacketType;
        this.redPacketWhich = redPacketWhich;
    }

    public RedPacketMessageContent(RedPack redPack) {
        this.redPacketName = redPack.redPacketName;
        this.redPacketNumber = redPack.redPacketNumber;
        this.redPacketTotal = redPack.redPacketTotal;
        this.redPacketType = redPack.redPacketType;
        this.redPacketWhich = redPack.redPacketWhich;
        this.redPacketId = redPack.redPacketId;
        this.statues = redPack.statues;
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        payload.searchableContent = redPacketName;
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("redPacketName", redPacketName);
            objWrite.put("redPacketNumber", redPacketNumber);
            objWrite.put("redPacketTotal", redPacketTotal);
            objWrite.put("redPacketType", redPacketType);
            objWrite.put("redPacketWhich", redPacketWhich);
            objWrite.put("redPacketId", redPacketId);
            objWrite.put("statues", statues);
            payload.content = objWrite.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        payload.mentionedType = mentionedType;
        payload.mentionedTargets = mentionedTargets;
        return payload;
    }

    @Override
    public void decode(MessagePayload payload) {
        redPacketName = payload.searchableContent;
        RedPack redPack = null;
        try {
            redPack = new Gson().fromJson(payload.content, RedPack.class);
        } catch (Exception e) {
            redPack = null;
        }
        if (redPack != null) {
            this.redPacketName = redPack.redPacketName;
            this.redPacketNumber = redPack.redPacketNumber;
            this.redPacketTotal = redPack.redPacketTotal;
            this.redPacketType = redPack.redPacketType;
            this.redPacketWhich = redPack.redPacketWhich;
            this.redPacketId = redPack.redPacketId;
            this.statues = redPack.statues;
        }
        mentionedType = payload.mentionedType;
        mentionedTargets = payload.mentionedTargets;
    }


    @Override
    public String digest(Message message) {
//        return "[红包]";
        return "[红包]" + redPacketName;
    }


    protected RedPacketMessageContent(Parcel in) {
        super(in);
        redPacketName = in.readString();
        redPacketNumber = in.readString();
        redPacketTotal = in.readString();
        redPacketType = in.readString();
        redPacketWhich = in.readString();
        redPacketId = in.readString();
        statues = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(redPacketName);
        dest.writeString(redPacketNumber);
        dest.writeString(redPacketTotal);
        dest.writeString(redPacketType);
        dest.writeString(redPacketWhich);
        dest.writeString(redPacketId);
        dest.writeString(statues);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedPacketMessageContent> CREATOR = new Creator<RedPacketMessageContent>() {
        @Override
        public RedPacketMessageContent createFromParcel(Parcel in) {
            return new RedPacketMessageContent(in);
        }

        @Override
        public RedPacketMessageContent[] newArray(int size) {
            return new RedPacketMessageContent[size];
        }
    };

}
