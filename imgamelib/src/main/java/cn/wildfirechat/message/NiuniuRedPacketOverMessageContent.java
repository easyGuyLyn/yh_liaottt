package cn.wildfirechat.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_niuniu_red_packet_over;

/**
 * 牛牛红包结算的通知消息
 */

@ContentTag(type = ContentType_niuniu_red_packet_over, flag = PersistFlag.Persist_And_Count)
public class NiuniuRedPacketOverMessageContent extends MessageContent {
    public String bankerWinTotal;    //庄家赢总数
    public String playerWinTotal;  //闲家赢总数
    public String bankerDisplayName;  //庄家昵称
    public String bankerNnDesc;  //庄家牛数描述
    public String bankerPortrait;  //庄家头像
    public String redPacketId;  //红包ID

    public NiuniuRedPacketOverMessageContent() {
    }


    protected NiuniuRedPacketOverMessageContent(Parcel in) {
        super(in);
        bankerWinTotal = in.readString();
        playerWinTotal = in.readString();
        bankerDisplayName = in.readString();
        bankerNnDesc = in.readString();
        bankerPortrait = in.readString();
        redPacketId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(bankerWinTotal);
        dest.writeString(playerWinTotal);
        dest.writeString(bankerDisplayName);
        dest.writeString(bankerNnDesc);
        dest.writeString(bankerPortrait);
        dest.writeString(redPacketId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NiuniuRedPacketOverMessageContent> CREATOR = new Creator<NiuniuRedPacketOverMessageContent>() {
        @Override
        public NiuniuRedPacketOverMessageContent createFromParcel(Parcel in) {
            return new NiuniuRedPacketOverMessageContent(in);
        }

        @Override
        public NiuniuRedPacketOverMessageContent[] newArray(int size) {
            return new NiuniuRedPacketOverMessageContent[size];
        }
    };

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        payload.searchableContent = "牛牛红包结算";
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("bankerWinTotal", bankerWinTotal);
            objWrite.put("playerWinTotal", playerWinTotal);
            objWrite.put("bankerDisplayName", bankerDisplayName);
            objWrite.put("bankerNnDesc", bankerNnDesc);
            objWrite.put("bankerPortrait", bankerPortrait);
            objWrite.put("redPacketId", redPacketId);
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
        if (payload.content != null && !payload.content.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(payload.content);
                bankerWinTotal = jsonObject.optString("bankerWinTotal");
                playerWinTotal = jsonObject.optString("playerWinTotal");
                bankerDisplayName = jsonObject.optString("bankerDisplayName");
                bankerNnDesc = jsonObject.optString("bankerNnDesc");
                bankerPortrait = jsonObject.optString("bankerPortrait");
                redPacketId = jsonObject.optString("redPacketId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mentionedType = payload.mentionedType;
        mentionedTargets = payload.mentionedTargets;
    }

    @Override
    public String digest(Message message) {
        return "牛牛红包结算";
    }


}
