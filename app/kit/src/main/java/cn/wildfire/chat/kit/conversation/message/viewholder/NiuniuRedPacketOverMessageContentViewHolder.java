package cn.wildfire.chat.kit.conversation.message.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.NiuniuRedPacketParticularsActivity;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.annotation.ReceiveLayoutRes;
import cn.wildfire.chat.kit.annotation.SendLayoutRes;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfirechat.message.NiuniuRedPacketOverMessageContent;


/**
 * 牛牛红包结算
 */
@MessageContentType(value = {
        NiuniuRedPacketOverMessageContent.class
})
@SendLayoutRes(resId = R.layout.conversation_item_niuniu_red_packet_over)
@ReceiveLayoutRes(resId = R.layout.conversation_item_niuniu_red_packet_over)
//@EnableContextMenu
public class NiuniuRedPacketOverMessageContentViewHolder extends MessageContentViewHolder {

    @BindView(R.id.layout_red_packet)
    LinearLayout layoutRedPacket;
    @BindView(R.id.portraitImageView)
    ImageView portraitImageView;

    @BindView(R.id.tv_banker_number)
    TextView tvBankerNumber;
    @BindView(R.id.tv_player_number)
    TextView tvPlayerNumber;
    @BindView(R.id.im_portrait)
    ImageView imPortrait;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_banker_niu)
    TextView tvBankerNiu;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.layout_red_packet_over)
    LinearLayout layoutRedPacketOver;

    public NiuniuRedPacketOverMessageContentViewHolder(ConversationFragment fragment, RecyclerView.Adapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        return false;
    }

    @Override
    public void onBind(UiMessage message, int position) {
        super.onBind(message, position);
        this.message = message;
        this.position = position;
        layoutRedPacket.getLayoutParams().width = UIUtils.dip2Px(280);
        portraitImageView.setVisibility(View.INVISIBLE);
        NiuniuRedPacketOverMessageContent niuniuRedPacketOverMessageContent = (NiuniuRedPacketOverMessageContent) message.message.content;
        tvBankerNumber.setText("庄赢" + niuniuRedPacketOverMessageContent.bankerWinTotal);
        tvPlayerNumber.setText("闲赢" + niuniuRedPacketOverMessageContent.playerWinTotal);
        tvUserName.setText(niuniuRedPacketOverMessageContent.bankerDisplayName);
        tvUserName.setText(niuniuRedPacketOverMessageContent.bankerDisplayName);
        tvBankerNiu.setText("庄家 (" + niuniuRedPacketOverMessageContent.bankerNnDesc + ")");

        Glide.with(fragment.getContext())
                .load(niuniuRedPacketOverMessageContent.bankerPortrait)
                .apply(new RequestOptions().placeholder(R.mipmap.im_avatar_def).centerCrop())
                .into(imPortrait);

    }


    @OnClick(R.id.layout_red_packet)
    public void onClickTest(View view) {
        NiuniuRedPacketOverMessageContent niuniuRedPacketOverMessageContent = (NiuniuRedPacketOverMessageContent) message.message.content;
        Intent intent = new Intent(fragment.getActivity(), NiuniuRedPacketParticularsActivity.class);
        intent.putExtra(ConstantValue.ARG_PARAM1, niuniuRedPacketOverMessageContent.redPacketId);
        intent.putExtra(ConstantValue.ARG_PARAM2, true);
        fragment.getActivity().startActivity(intent);
    }


//    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_CLIP, title = "复制", confirm = false, priority = 12)
//    public void clip(View itemView, UiMessage message) {
//        ClipboardManager clipboardManager = (ClipboardManager) fragment.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//        if (clipboardManager == null) {
//            return;
//        }
//        TextMessageContent content = (TextMessageContent) message.message.content;
//        ClipData clipData = ClipData.newPlainText("messageContent", content.getContent());
//        clipboardManager.setPrimaryClip(clipData);
//    }
}
