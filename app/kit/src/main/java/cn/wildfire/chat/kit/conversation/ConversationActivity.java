package cn.wildfire.chat.kit.conversation;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.ExtraBean;
import com.nwf.sports.ui.activity.SendNiuNiuRedPacketActivity;
import com.nwf.sports.ui.activity.SendRedPacketActivity;
import com.nwf.sports.ui.activity.SendSweepRedPacketActivity;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.IMServiceStatusViewModel;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.ConversationInfo;
import cn.wildfirechat.model.GroupInfo;
import cn.wildfirechat.remote.ChatManager;

public class ConversationActivity extends WfcBaseActivity {
    @BindView(R.id.tv_com_title_name)
    TextView tvComTitleName;
    @BindView(R.id.tv_com_title_red_packet)
    ImageView tvComTitleRedPacket;

    private boolean isInitialized = false;
    private ConversationFragment conversationFragment;
    private Conversation conversation;

    @Override
    protected int contentLayout() {
        return R.layout.fragment_chat_container_activity;
    }

    private void setConversationBackground() {
        // you can setup your conversation background here
//        getWindow().setBackgroundDrawableResource(R.mipmap.splash);
    }

    @Override
    protected void afterViews() {
        IMServiceStatusViewModel imServiceStatusViewModel = ViewModelProviders.of(this).get(IMServiceStatusViewModel.class);
        imServiceStatusViewModel.imServiceStatusLiveData().observe(this, aBoolean -> {
            if (!isInitialized && aBoolean) {
                init();
                isInitialized = true;
            }
        });
        conversationFragment = new ConversationFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFrameLayout, conversationFragment, "content")
                .commit();

        setConversationBackground();
    }

    public void setTitleIcon() {
        tvComTitleRedPacket.setVisibility(View.VISIBLE);
        ConversationInfo conversationInfo = ChatManager.Instance().getConversation(conversation);
        if (conversationInfo == null) {
            return;
        }
        GroupInfo groupInfo = ChatManager.Instance().getGroupInfo(conversation.target, false);
        if (groupInfo == null) {
            return;
        }
        String extra = groupInfo.extra;
//        if (conversation.type.getValue() == Conversation.ConversationType.Group.getValue()) {
//            if (!TextUtils.isEmpty(extra)) {
//                ExtraBean extraBean = new Gson().fromJson(extra, ExtraBean.class);
//                switch (extraBean.nwfGroupType) {
//                    case 1:
//                    case 2:
//                        tvComTitleRedPacket.setVisibility(View.VISIBLE);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
    }

    @Override
    protected int menu() {
        return R.menu.im_conversation;
//        return 0;
    }

    @Override
    protected void afterMenus(Menu menu) {
        super.afterMenus(menu);
//        menu.findItem(R.id.menu_red_packet).setVisible(false);
//        ConversationInfo conversationInfo = ChatManager.Instance().getConversation(conversation);
//        if (conversationInfo == null) {
//            return;
//        }
//        GroupInfo groupInfo = ChatManager.Instance().getGroupInfo(conversation.target, true);
//        if (groupInfo == null) {
//            return;
//        }
//        String extra = groupInfo.extra;
//        if (conversation.type.getValue() == Conversation.ConversationType.Group.getValue()) {
//            if (!TextUtils.isEmpty(extra)) {
//                ExtraBean extraBean = new Gson().fromJson(extra, ExtraBean.class);
//                switch (extraBean.nwfGroupType) {
//                    case 1:
//                    case 2:
//                        menu.findItem(R.id.menu_red_packet).setVisible(true);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
    }

    public ConversationFragment getConversationFragment() {
        return conversationFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_conversation_info) {
            showConversationInfo();
            return true;
        }
        if (item.getItemId() == R.id.menu_red_packet) {
            try {
                showSendRedPacket();
            } catch (Exception e) {
                Log.e("MenuItem", "发红包异常" + e.toString());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!conversationFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void showConversationInfo() {
        Intent intent = new Intent(this, ConversationInfoActivity.class);
        ConversationInfo conversationInfo = ChatManager.Instance().getConversation(conversation);
        if (conversationInfo == null) {
            Toast.makeText(this, "获取会话信息失败", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("conversationInfo", conversationInfo);
        startActivity(intent);
    }

    private void showSendRedPacket() {
        Intent intent = null;
        ExtraBean extraBean = null;
        ConversationInfo conversationInfo = ChatManager.Instance().getConversation(conversation);
        if (conversationInfo == null) {
            Toast.makeText(this, "获取会话信息失败", Toast.LENGTH_SHORT).show();
            return;
        }
        GroupInfo groupInfo = ChatManager.Instance().getGroupInfo(conversation.target, true);
        if (groupInfo == null) {
            Toast.makeText(this, "获取会话信息失败", Toast.LENGTH_SHORT).show();
            return;
        }
        String extra = groupInfo.extra;
        extraBean = new Gson().fromJson(extra, ExtraBean.class);
        switch (conversationInfo.conversation.type) {
            case Single:
                intent = new Intent(this, SendRedPacketActivity.class);
                intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                if (extraBean != null) {
                    intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                }
                startActivity(intent);
                break;
            case Group:
                switch (extraBean.nwfGroupType) {
                    case 1:
                        intent = new Intent(this, SendSweepRedPacketActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                        if (extraBean != null) {
                            intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                        }
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(this, SendNiuNiuRedPacketActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                        if (extraBean != null) {
                            intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                        }
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(this, SendRedPacketActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1, conversation.target);
                        intent.putExtra(ConstantValue.ARG_PARAM2, conversation.type);
                        if (extraBean != null) {
                            intent.putExtra(ConstantValue.ARG_PARAM3, extraBean);
                        }
                        startActivity(intent);
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        conversation = intent.getParcelableExtra("conversation");
        if (conversation == null) {
            finish();
        }
        long initialFocusedMessageId = intent.getLongExtra("toFocusMessageId", -1);
        String channelPrivateChatUser = intent.getStringExtra("channelPrivateChatUser");
        conversationFragment.setupConversation(conversation, null, initialFocusedMessageId, channelPrivateChatUser);
    }


    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (titleId != 0) {
            setTitle(getText(titleId));
        }
    }

    @Override
    public void setTitle(CharSequence title) {
//        super.setTitle(title);
        if (!TextUtils.isEmpty(title)) {
            tvComTitleName.setText(title);
        }
        if (!title.equals("系统管理员")) {
            setTitleIcon();
        }
    }

    private void init() {
        Intent intent = getIntent();
        conversation = intent.getParcelableExtra("conversation");
        String conversationTitle = intent.getStringExtra("conversationTitle");
        long initialFocusedMessageId = intent.getLongExtra("toFocusMessageId", -1);
        if (conversation == null) {
            finish();
        }
        conversationFragment.setupConversation(conversation, conversationTitle, initialFocusedMessageId, null);

    }

    public static Intent buildConversationIntent(Context context, Conversation.ConversationType type, String target, int line) {
        return buildConversationIntent(context, type, target, line, -1);
    }

    public static Intent buildConversationIntent(Context context, Conversation.ConversationType type, String target, int line, long toFocusMessageId) {
        Conversation conversation = new Conversation(type, target, line);
        return buildConversationIntent(context, conversation, null, toFocusMessageId);
    }

    public static Intent buildConversationIntent(Context context, Conversation.ConversationType type, String target, int line, String channelPrivateChatUser) {
        Conversation conversation = new Conversation(type, target, line);
        return buildConversationIntent(context, conversation, null, -1);
    }

    public static Intent buildConversationIntent(Context context, Conversation conversation, String channelPrivateChatUser, long toFocusMessageId) {
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra("conversation", conversation);
        intent.putExtra("toFocusMessageId", toFocusMessageId);
        intent.putExtra("channelPrivateChatUser", channelPrivateChatUser);
        return intent;
    }

    @OnClick({R.id.tv_com_title_more, R.id.tv_com_title_red_packet, R.id.iv_com_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_com_title_more:
                showConversationInfo();
                break;
            case R.id.tv_com_title_red_packet:
                showSendRedPacket();
                break;
            case R.id.iv_com_title_back:
                finish();
                break;
        }
    }
}
