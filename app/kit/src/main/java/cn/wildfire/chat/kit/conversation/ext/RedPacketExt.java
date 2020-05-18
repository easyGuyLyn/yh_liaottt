package cn.wildfire.chat.kit.conversation.ext;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.ExtraBean;
import com.nwf.sports.ui.activity.SendNiuNiuRedPacketActivity;
import com.nwf.sports.ui.activity.SendRedPacketActivity;
import com.nwf.sports.ui.activity.SendSweepRedPacketActivity;

import cn.wildfire.chat.kit.annotation.ExtContextMenuItem;
import cn.wildfire.chat.kit.conversation.ext.core.ConversationExt;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.GroupInfo;
import cn.wildfirechat.remote.ChatManager;

public class RedPacketExt extends ConversationExt {

    public static final int RED_PACKET_BACK = 1;

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "红包")
    public void shoot(View containerView, Conversation conversation) {
        Intent intent = null;
        ExtraBean extraBean=null;
        GroupInfo groupInfo = ChatManager.Instance().getGroupInfo(conversation.target, true);
        String extra = groupInfo.extra;
        if (conversation.type.getValue() == Conversation.ConversationType.Group.getValue()) {
            if (!TextUtils.isEmpty(extra)) {
                extraBean = new Gson().fromJson(extra, ExtraBean.class);
                switch (extraBean.nwfGroupType) {
                    case 1:
                        intent = new Intent(activity, SendSweepRedPacketActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                        intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                        startActivityForResult(intent, 100);
                        return;
                    case 2:
                        intent = new Intent(activity, SendNiuNiuRedPacketActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                        intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                        startActivityForResult(intent, 100);
                        return;
                }
            }
        }
        intent = new Intent(activity, SendRedPacketActivity.class);
        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
        intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
        startActivityForResult(intent, 100);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                RedPacketResult result = data.getParcelableExtra("redPacketData");
//                if (result != null) {
//                    RedPack redPack = new RedPack();
//                    redPack.redPacketName = result.getSendTitle();
//                    redPack.redPacketNumber = result.getSendNumber() + "";
//                    redPack.redPacketTotal = result.getSendAmount();
//                    redPack.redPacketType = "" + result.getSendType();
//                    redPack.redPacketWhich = "" + conversation.target;
//                    redPack.redPacketId = "" + result.getPacketId();
//                    redPack.statues = "0";
//                    redPack.specialNumber = result.specialNumber;
//                    UIUtils.postTaskSafely(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (redPack.redPacketType.equals("3")) {
//                                messageViewModel.sendSweepRedPacketMsg(conversation, redPack);
//                            } else if (redPack.redPacketType.equals("4")) {
//                                messageViewModel.sendNiuniuRedPacketMsg(conversation, redPack);
//                            } else {
//                                messageViewModel.sendRedPacketMsg(conversation, redPack);
//                            }
//                        }
//                    });
//                }
//            }
//        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.bottom_icon_hb;
    }

    @Override
    public String title(Context context) {
        return "红包";
    }
}
