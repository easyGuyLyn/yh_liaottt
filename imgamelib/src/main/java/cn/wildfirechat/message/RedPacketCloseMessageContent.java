package cn.wildfirechat.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_red_packet_remind_close;

/**
 * 红包结算的通知消息
 */

@ContentTag(type = ContentType_red_packet_remind_close, flag = PersistFlag.No_Persist)
public class RedPacketCloseMessageContent extends MessageContent {
    public String redPacketId;    //红包ID
    public String redPacketMessageId;  //红包的消息ID

    public RedPacketCloseMessageContent() {
    }


    protected RedPacketCloseMessageContent(Parcel in) {
        super(in);
        redPacketId = in.readString();
        redPacketMessageId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(redPacketId);
        dest.writeString(redPacketMessageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedPacketCloseMessageContent> CREATOR = new Creator<RedPacketCloseMessageContent>() {
        @Override
        public RedPacketCloseMessageContent createFromParcel(Parcel in) {
            return new RedPacketCloseMessageContent(in);
        }

        @Override
        public RedPacketCloseMessageContent[] newArray(int size) {
            return new RedPacketCloseMessageContent[size];
        }
    };

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        payload.searchableContent = "";
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("redPacketId", redPacketId);
            objWrite.put("redPacketMessageId", redPacketMessageId);
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
//        ChatManager.Instance().getMessageByUid(redPacketMessageId);
//        ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
        if (payload.content != null && !payload.content.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(payload.content);
                redPacketId = jsonObject.optString("redPacketId");
                redPacketMessageId = jsonObject.optString("redPacketMessageId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mentionedType = payload.mentionedType;
        mentionedTargets = payload.mentionedTargets;
        Log.e("RedPacketCloseMessa1111", "收到通知1==" + redPacketMessageId + "红包id==" + redPacketId);
    }


    @Override
    public String digest(Message message) {
        return "";
    }


}
