package cn.wildfire.chat.kit.conversation.ext.core;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dawoo.coretool.util.ToastUtil;
import com.ivi.imsdk.R;

import cn.wildfire.chat.kit.annotation.ExtContextMenuItem;
import cn.wildfirechat.model.Conversation;

public class RechargeExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "充值")
    public void pickImage(View containerView, Conversation conversation) {
        ToastUtil.showToastLong("充值");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.bottom_icon_pay;
    }

    @Override
    public String title(Context context) {
        return "充值";
    }
}
