package cn.wildfirechat.message.notification;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_red_packet_remind;

/**
 * 抢紅包消息提示
 */

@ContentTag(type = ContentType_red_packet_remind, flag = PersistFlag.Persist)
public class RedPacketNotificationContent extends GroupNotificationMessageContent implements Parcelable {
    public String recipientsId;  //领取人的ID
    public String redBagCreatorId;  //红包创建人的ID
    public String grabMoney;  //抢到红包金额
    public String redPacketName;  //红包标题
    public String loginName;  //用户名称
    public String redPacketId;  //红包ID
    public String recipientsName;  //领取人显示名
    public String redBagCreatorName;  //创建人显示名

    public RedPacketNotificationContent() {
    }

    public RedPacketNotificationContent(RedPackNotification redPackNotification) {
        this.recipientsId = redPackNotification.recipientsId;
        this.redBagCreatorId = redPackNotification.redBagCreatorId;
        this.grabMoney = redPackNotification.grabMoney;
        this.redPacketName = redPackNotification.redPacketName;
        this.loginName = redPackNotification.loginName;
        this.redPacketId = redPackNotification.redPacketId;
    }


    protected RedPacketNotificationContent(Parcel in) {
        recipientsId = in.readString();
        redBagCreatorId = in.readString();
        grabMoney = in.readString();
        redPacketName = in.readString();
        loginName = in.readString();
        redPacketId = in.readString();
        recipientsName = in.readString();
        redBagCreatorName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipientsId);
        dest.writeString(redBagCreatorId);
        dest.writeString(grabMoney);
        dest.writeString(redPacketName);
        dest.writeString(loginName);
        dest.writeString(redPacketId);
        dest.writeString(recipientsName);
        dest.writeString(redBagCreatorName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedPacketNotificationContent> CREATOR = new Creator<RedPacketNotificationContent>() {
        @Override
        public RedPacketNotificationContent createFromParcel(Parcel in) {
            return new RedPacketNotificationContent(in);
        }

        @Override
        public RedPacketNotificationContent[] newArray(int size) {
            return new RedPacketNotificationContent[size];
        }
    };

    @Override
    public String formatNotification(Message message) {
//        UserInfo userInfo = ChatManager.Instance().getUserInfo(recipientsId, false);
//        UserInfo userInfo1 = ChatManager.Instance().getUserInfo(redBagCreatorId, false);
//        String name = userInfo.displayName;
//        String name1 = userInfo1.displayName;
        return recipientsName + "领取了" + redBagCreatorName + "的红包";
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
//        payload.searchableContent = redPacketName;
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("recipientsId", recipientsId);
            objWrite.put("redBagCreatorId", redBagCreatorId);
            objWrite.put("grabMoney", grabMoney);
            objWrite.put("redPacketName", redPacketName);
            objWrite.put("loginName", loginName);
            objWrite.put("redPacketId", redPacketId);
            objWrite.put("recipientsName", recipientsName);
            objWrite.put("redBagCreatorName", redBagCreatorName);
            payload.binaryContent = objWrite.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        payload.mentionedType = mentionedType;
        payload.mentionedTargets = mentionedTargets;
        return payload;
    }

    @Override
    public void decode(MessagePayload payload) {
//        redPacketName = payload.searchableContent;
        RedPackNotification redPackNotification = null;
        try {
            redPackNotification = new Gson().fromJson(new String(payload.binaryContent), RedPackNotification.class);
        } catch (Exception e) {
            redPackNotification = null;
        }
        if (redPackNotification != null) {
            this.redPacketName = redPackNotification.redPacketName;
            this.redBagCreatorId = redPackNotification.redBagCreatorId;
            this.grabMoney = redPackNotification.grabMoney;
            this.recipientsId = redPackNotification.recipientsId;
            this.loginName = redPackNotification.loginName;
            this.redPacketId = redPackNotification.redPacketId;
            this.recipientsName = redPackNotification.recipientsName;
            this.redBagCreatorName = redPackNotification.redBagCreatorName;
        }
        mentionedType = payload.mentionedType;
        mentionedTargets = payload.mentionedTargets;
    }


}
