package cn.wildfire.chat.kit.conversation.message.viewholder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.mvp.model.RedPacketStateResult;
import com.nwf.sports.ui.activity.NiuniuRedPacketParticularsActivity;
import com.nwf.sports.ui.activity.RedPacketParticularsActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.RobRedPacketDialogFragment;
import com.nwf.sports.ui.dialogfragment.RobRedPacketOverDialogFragment;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.annotation.EnableContextMenu;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.annotation.MessageContextMenuItem;
import cn.wildfire.chat.kit.annotation.ReceiveLayoutRes;
import cn.wildfire.chat.kit.annotation.SendLayoutRes;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.net.IMErrorCodeEnum;
import cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfirechat.message.RedPack;
import cn.wildfirechat.message.RedPacketMessageContent;
import cn.wildfirechat.message.TextMessageContent;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.remote.ChatManager;


@MessageContentType(value = {
        RedPacketMessageContent.class
})
@SendLayoutRes(resId = R.layout.conversation_item_red_packet_send)
@ReceiveLayoutRes(resId = R.layout.conversation_item_red_packet_receive)
@EnableContextMenu
public class RedPacketMessageContentViewHolder extends NormalMessageContentViewHolder {

    @BindView(R.id.img_red)
    ImageView imgRed;
    @BindView(R.id.tv_red_packet_name)
    TextView tvRedPacketName;
    @BindView(R.id.tv_red_packet_state)
    TextView tvRedPacketState;
    @BindView(R.id.tv_red_packet_type)
    TextView tvRedPacketType;
    @BindView(R.id.layout_red_packet)
    LinearLayout layoutRedPacket;

    public RedPacketMessageContentViewHolder(ConversationFragment fragment, RecyclerView.Adapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        layoutRedPacket.getLayoutParams().width = UIUtils.dip2Px(240);
        RedPacketMessageContent redPacketMessageContent = (RedPacketMessageContent) message.message.content;
        tvRedPacketName.setText(redPacketMessageContent.redPacketName);
        String userId = ChatManager.Instance().getUserId();
        if (redPacketMessageContent.statues.equals("0")) {
            tvRedPacketState.setText("未领取");
            if (message.message.sender.equals(userId)) {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_send_normal);
            } else {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_receive_normal);
            }
            imgRed.setImageResource(R.mipmap.rb_icon_normal);
        } else if (redPacketMessageContent.statues.equals("1")) {
            tvRedPacketState.setText("已领取");
            if (message.message.sender.equals(userId)) {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_send_already);
            } else {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_receive_already);
            }
            imgRed.setImageResource(R.mipmap.rb_icon_already);
        } else if (redPacketMessageContent.statues.equals("2")) {
            tvRedPacketState.setText("已过期");
            if (message.message.sender.equals(userId)) {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_send_already);
            } else {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_receive_already);
            }
            imgRed.setImageResource(R.mipmap.rb_icon_already);
        } else if (redPacketMessageContent.statues.equals("3")) {
            tvRedPacketState.setText("已抢完");
            if (message.message.sender.equals(userId)) {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_send_already);
            } else {
                layoutRedPacket.setBackgroundResource(R.drawable.im_hbbg_receive_already);
            }
            imgRed.setImageResource(R.mipmap.rb_icon_already);
        }

        if (redPacketMessageContent.redPacketType.equals("3")) {
            tvRedPacketType.setText("扫雷红包");
        } else if (redPacketMessageContent.redPacketType.equals("4")) {
            tvRedPacketType.setText("牛牛红包");
        } else {
            tvRedPacketType.setText("手气红包");
        }
    }

    @OnClick(R.id.layout_red_packet)
    public void onClickTest(View view) {
//        Toast.makeText(fragment.getContext(), "onTextMessage click: 点击了", Toast.LENGTH_SHORT).show();
//        messageViewModel.sendRedPacketHintMsg(message.message.conversation);
        RedPacketMessageContent redPacketMessageContent = (RedPacketMessageContent) message.message.content;

        AppService.Instance().queryPacketState(redPacketMessageContent.redPacketId, new AppService.QueryPacketStateCallback() {
            @Override
            public void onSuccess(RedPacketStateResult redPacketStateResult) {
                if (redPacketStateResult.getFlag() == 0) {
                    if (redPacketMessageContent.statues.equals("0")) {
                        unclaimed(redPacketMessageContent);
                    } else if (redPacketMessageContent.statues.equals("1")) {
                        alreadyReceived(redPacketMessageContent);
                    }
                } else if (redPacketStateResult.getFlag() == 1) {
                    redPacketMessageContent.statues = "2";
                    ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                    pastDue(redPacketMessageContent);
                } else if (redPacketStateResult.getFlag() == 2) {
                    redPacketMessageContent.statues = "3";
                    ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                    pastDue(redPacketMessageContent);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                switch (code) {
                    case -1:
                        ToastUtil.showToastLong("查询红包状态异常");
                        break;
                }
             //   ToastUtil.showToastLong("查询红包状态异常");
//                dismissAllowingStateLoss();
            }
        });
    }

    /**
     * 红包未领取
     */
    public void unclaimed(RedPacketMessageContent redPacketMessageContent) {
        RedPack redPack = new RedPack();
        redPack.redPacketName = redPacketMessageContent.redPacketName;
        redPack.redPacketNumber = redPacketMessageContent.redPacketNumber;
        redPack.redPacketTotal = redPacketMessageContent.redPacketTotal;
        redPack.redPacketType = redPacketMessageContent.redPacketType;
        redPack.redPacketWhich = redPacketMessageContent.redPacketWhich;
        redPack.redPacketId = redPacketMessageContent.redPacketId;
        redPack.statues = redPacketMessageContent.statues;

        UserInfo userInfo = ChatManager.Instance().getUserInfo(message.message.sender, false);
        FragmentActivity activity = fragment.getActivity();
        RobRedPacketDialogFragment robRedPacketDialogFragment = RobRedPacketDialogFragment.getInstance(redPack, userInfo.displayName, userInfo.portrait);
        robRedPacketDialogFragment.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppService.Instance().robRedPacket(redPacketMessageContent.redPacketId, message.message.conversation.target, new AppService.RobRedPacketCallback() {
                    @Override
                    public void onSuccess() {
                        tvRedPacketState.setText("已领取");
                        redPacketMessageContent.statues = "1";
                        ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
//                        RedPackNotification redPackNotification = new RedPackNotification();
//                        redPackNotification.redPacketName = redPacketMessageContent.redPacketName;
//                        redPackNotification.redBagCreatorId = message.message.sender;
//                        UserInfoBean userInfoBean = IMDataCenter.getInstance().getUserInfoBean();
//                        redPackNotification.recipientsId = userInfoBean.getSportUserId();
//                        redPackNotification.loginName = userInfoBean.getUsername();
//                        redPackNotification.redPacketId = redPacketMessageContent.redPacketId;
//                        redPackNotification.grabMoney = "";
//                        messageViewModel.sendRedPacketHintMsg(message.message.conversation, redPackNotification);
                        robRedPacketDialogFragment.dismissAllowingStateLoss();

                        Intent intent = new Intent(fragment.getActivity(), RedPacketParticularsActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, redPacketMessageContent.redPacketId);
                        intent.putExtra(ConstantValue.ARG_PARAM2, true);
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if (code == IMErrorCodeEnum.ERROR_CODE_PACKET_RECEIVED.getCode()
                                || code == IMErrorCodeEnum.ERROR_CODE_PACKET_RECEIVED.getCode()) { //已领取过红包
                            redPacketMessageContent.statues = "1";
                            ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                            redPacketMessageContent.statues = "1";
                            ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                            Intent intent = null;
                            if (redPacketMessageContent.redPacketType.equals("4")) {
                                intent = new Intent(fragment.getActivity(), NiuniuRedPacketParticularsActivity.class);
                            } else {
                                intent = new Intent(fragment.getActivity(), RedPacketParticularsActivity.class);
                            }
                            intent.putExtra(ConstantValue.ARG_PARAM1, redPack.redPacketId);
                            intent.putExtra(ConstantValue.ARG_PARAM2, true);
                            activity.startActivity(intent);
                            robRedPacketDialogFragment.dismissAllowingStateLoss();
                        } else if (code == IMErrorCodeEnum.ERROR_CODE_PACKET_FINSH_.getCode()
                                || code == IMErrorCodeEnum.ERROR_CODE_PACKET_GRAB_ISOVER.getCode()) {
                            redPacketMessageContent.statues = "3";
                            ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                            DialogFramentManager.getInstance().showDialog(activity.getSupportFragmentManager(), RobRedPacketOverDialogFragment.getInstance(redPack, userInfo.displayName));
                            redPacketMessageContent.statues = "1";
                            ChatManager.Instance().updateMessage(message.message.messageId, redPacketMessageContent);
                            Intent intent = null;
                            if (redPacketMessageContent.redPacketType.equals("4")) {
                                intent = new Intent(fragment.getActivity(), NiuniuRedPacketParticularsActivity.class);
                            } else {
                                intent = new Intent(fragment.getActivity(), RedPacketParticularsActivity.class);
                            }
                            intent.putExtra(ConstantValue.ARG_PARAM1, redPack.redPacketId);
                            intent.putExtra(ConstantValue.ARG_PARAM2, true);
                            activity.startActivity(intent);
                        }
                     //   ToastUtil.showToastLong(msg);
                        robRedPacketDialogFragment.dismissAllowingStateLoss();
                    }
                });
            }
        });
        DialogFramentManager.getInstance().showDialog(activity.getSupportFragmentManager(), robRedPacketDialogFragment);
    }

    public void show() {

    }

    /**
     * 已领取
     */
    public void alreadyReceived(RedPacketMessageContent redPacketMessageContent) {
        FragmentActivity activity = fragment.getActivity();
        Intent intent = new Intent(fragment.getActivity(), RedPacketParticularsActivity.class);
        intent.putExtra(ConstantValue.ARG_PARAM2, true);
        intent.putExtra(ConstantValue.ARG_PARAM1, redPacketMessageContent.redPacketId);
        activity.startActivity(intent);
    }

    /**
     * 已过期
     */
    public void pastDue(RedPacketMessageContent redPacketMessageContent) {
        alreadyReceived(redPacketMessageContent);
    }


    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_CLIP, title = "复制", confirm = false, priority = 12)
    public void clip(View itemView, UiMessage message) {
        ClipboardManager clipboardManager = (ClipboardManager) fragment.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            return;
        }
        TextMessageContent content = (TextMessageContent) message.message.content;
        ClipData clipData = ClipData.newPlainText("messageContent", content.getContent());
        clipboardManager.setPrimaryClip(clipData);
    }
}
