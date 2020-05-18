package cn.wildfire.chat.kit.conversation.ext.core;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.ServiceActivity;

import cn.wildfire.chat.kit.annotation.ExtContextMenuItem;
import cn.wildfirechat.model.Conversation;

public class ServiceExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "客服")
    public void pickImage(View containerView, Conversation conversation) {
        activity.startActivity(new Intent(activity, ServiceActivity.class));
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
        return R.mipmap.bottom_icon_custom_service;
    }

    @Override
    public String title(Context context) {
        return "客服";
    }
}
