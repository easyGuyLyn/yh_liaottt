package cn.wildfire.chat.kit.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ivi.imsdk.R;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.wildfire.chat.kit.annotation.LayoutRes;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfirechat.message.notification.RedPacketNotificationContent;

@MessageContentType(value = {
        RedPacketNotificationContent.class
})
@LayoutRes(resId = R.layout.conversation_item_redpacket_notification)
/**
 * 小灰条消息, 居中显示，且不显示发送者，用于简单的红包通知
 *
 */
public class RedPacketNotificationMessageContentViewHolder extends NotificationMessageContentViewHolder {

    @BindView(R.id.notificationTextView)
    TextView notificationTextView;

    @BindView(R.id.notificationTextView_name)
    TextView notificationTextViewName;

    public RedPacketNotificationMessageContentViewHolder(ConversationFragment fragment, RecyclerView.Adapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message, int position) {
        super.onBind(message, position);
        onBind(message);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        return true;
    }

    protected void onBind(UiMessage message) {
        try {
            RedPacketNotificationContent redPacketNotificationContent = ((RedPacketNotificationContent) message.message.content);
//            UserInfo userInfo = ChatManager.Instance().getUserInfo(redPacketNotificationContent.recipientsId, false);
//            UserInfo userInfo1 = ChatManager.Instance().getUserInfo(redPacketNotificationContent.redBagCreatorId, false);
            notificationTextViewName.setText(redPacketNotificationContent.recipientsName);
            notificationTextView.setText("领取了" + redPacketNotificationContent.redBagCreatorName + "的红包");
        } catch (Exception e) {
            e.printStackTrace();
            notificationTextView.setText("message is invalid");
        }
    }
}
