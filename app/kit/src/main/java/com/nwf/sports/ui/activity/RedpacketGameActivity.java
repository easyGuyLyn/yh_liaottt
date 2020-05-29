package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.ivi.imsdk.BuildConfig;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.IMApplication;
import com.nwf.sports.RedPacketIMServicesManger;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.chat.login.model.GameTokenBean;
import com.nwf.sports.chat.login.model.InGameResult;
import com.nwf.sports.mvp.model.HomeGameBean;
import com.nwf.sports.mvp.model.RedPacketGameListDetailsResult;
import com.nwf.sports.mvp.model.RedPacketGameListResult;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.SingleToast;
import com.nwf.sports.utils.data.IMDataCenter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.wildfire.chat.kit.IMConnectionStatusViewModel;
import cn.wildfire.chat.kit.IMServiceStatusViewModel;
import cn.wildfire.chat.kit.conversation.ConversationActivity;
import cn.wildfire.chat.kit.group.GroupViewModel;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.client.ConnectionStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.GroupInfo;
import cn.wildfirechat.remote.ChatManager;
import ivi.net.base.netlibrary.callback.RequestCallBack;
import ivi.net.base.netlibrary.model.ResponseModel;
import ivi.net.base.netlibrary.request.Request;


/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-02-04
 * <p>修改人：Simon
 * <p>修改时间：2020-02-04
 * <p>修改备注：
 **/
public class RedpacketGameActivity extends BaseActivity {

    @BindView(R.id.v_dep_name_title)
    PNTitleBar vDepNameTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_game_list)
    RecyclerView rvGameList;
    private GroupViewModel groupViewModel;

    List<HomeGameBean> mHomeGameBeans = new ArrayList<HomeGameBean>();

    List<RedPacketGameListResult.RedPacketGroupVoListBean> mRedPacketList = new ArrayList<RedPacketGameListResult.RedPacketGroupVoListBean>();
    private CommonAdapter<RedPacketGameListResult.RedPacketGroupVoListBean> mRedPacketAdapter = null;

    private int mViewPagerNum = 0;


    private boolean isInitialized = false;
    private Observer<Boolean> imStatusLiveDataObserver = status -> {
        if (status && !isInitialized) {
            isInitialized = true;
        }
    };


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_game_red_packet);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            mViewPagerNum = getIntent().getIntExtra(ConstantValue.ARG_PARAM1, -1);
        }

        IMServiceStatusViewModel imServiceStatusViewModel = ViewModelProviders.of(this).get(IMServiceStatusViewModel.class);
        imServiceStatusViewModel.imServiceStatusLiveData().observe(this, imStatusLiveDataObserver);
        IMConnectionStatusViewModel connectionStatusViewModel = ViewModelProviders.of(this).get(IMConnectionStatusViewModel.class);
        connectionStatusViewModel.connectionStatusLiveData().observe(this, status -> {
            if (status == ConnectionStatus.ConnectionStatusTokenIncorrect
                    || status == ConnectionStatus.ConnectionStatusSecretKeyMismatch
                    || status == ConnectionStatus.ConnectionStatusRejected
                    || status == ConnectionStatus.ConnectionStatusLogout) {

                Log.e("TT", "聊天错误码===" + status);
                SingleToast.showLongMsg("聊天错误码=== " + status);
                ChatManager.Instance().disconnect(true);

            } else if (status == ConnectionStatus.ConnectionStatusConnected) {
                if (BuildConfig.DEBUG) {
                    SingleToast.showLongMsg("聊天已连接 ");
                }

            }
        });


        vDepNameTitle.setMoreListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedpacketGameActivity.this, RedPacketRegulationActivity.class);
                startActivity(intent);
            }
        });
        vDepNameTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);

        mHomeGameBeans.clear();
        mHomeGameBeans.add(new HomeGameBean("聊天福利群", R.drawable.icon_redpacket_game_welfare, R.drawable.bg_red_packet_game_welfare));
        mHomeGameBeans.add(new HomeGameBean("扫雷红包群", R.drawable.icon_redpacket_game_saolei, R.drawable.bg_red_packet_game_saolei));
        mHomeGameBeans.add(new HomeGameBean("牛牛红包群", R.drawable.icon_redpacket_game_niuniu, R.drawable.bg_red_packet_game_niuniu));

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.im_white));
        for (int i = 0; i < mHomeGameBeans.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(R.layout.activity_redpacket_game_tab_item);
            TextView tabName = tab.getCustomView().findViewById(R.id.tab_name);
            ImageView imgBg = tab.getCustomView().findViewById(R.id.img_bg);
            ImageView imgIcon = tab.getCustomView().findViewById(R.id.img_icon);
            tabName.setText(mHomeGameBeans.get(i).name);
            imgBg.setImageResource(mHomeGameBeans.get(i).bg);
            imgIcon.setImageResource(mHomeGameBeans.get(i).icon);
            if (mViewPagerNum == i) {
                mTabLayout.addTab(tab, true);
            } else {
                mTabLayout.addTab(tab);
            }
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerNum = tab.getPosition();
                getRedPacket();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rvGameList.setLayoutManager(new LinearLayoutManager(this));
        mRedPacketAdapter = new CommonAdapter<RedPacketGameListResult.RedPacketGroupVoListBean>(this, R.layout.activity_redpacket_game_type_item, mRedPacketList) {
            @Override
            public void convert(ViewHolder holder, RedPacketGameListResult.RedPacketGroupVoListBean item, int position) {
                ImageView imageView = holder.getView(R.id.img_head_portrait);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvRule = holder.getView(R.id.tv_rule);
                tvName.setText(item.getGroupName());
                tvRule.setText(item.getGroupRemark());
                //加载图片
                Glide.with(RedpacketGameActivity.this)
                        .load(item.getPortrait())
                        .into(imageView);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (!IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
//                            DialogFramentManager.getInstance().clearDialog();
//                            LoginDialogFragment loginDialogFragment = LoginDialogFragment.getInstance("", "");
//                            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), loginDialogFragment);
//                            return;
//                        }

                        if (TextUtils.isEmpty(IMDataCenter.getInstance().getGame_token())
                                || TextUtils.isEmpty(IMDataCenter.getInstance().getGame_u2token())) {
                            if (!RedPacketIMServicesManger.isLocalEnvironment()) {
                                SingleToast.showLongMsg("红包签名参数异常，请稍后再试");
                                inGame();
                                return;
                            }
                        }


                        if (item.isInGroup()) {
                            Intent intent = ConversationActivity.buildConversationIntent(RedpacketGameActivity.this, Conversation.ConversationType.Group, item.getGroupId(), 0);
                            startActivity(intent);
                            return;
                        }

                        AppService.Instance().QueryGroupJoinInfo(item.getGroupId(), new AppService.QueryGroupJoinInfoCallback() {
                            @Override
                            public void onSuccess(RedPacketGameListDetailsResult statusResult) {
                                if (statusResult.isInGroup()) {
                                    Intent intent = ConversationActivity.buildConversationIntent(RedpacketGameActivity.this, Conversation.ConversationType.Group, item.getGroupId(), 0);
                                    startActivity(intent);
                                    return;
                                }
                                if (statusResult.isCanInGroup()) {
                                    UserViewModel userViewModel = ViewModelProviders.of(RedpacketGameActivity.this).get(UserViewModel.class);
                                    String userId = userViewModel.getUserId();
                                    Log.e("ok ", "groupViewModel.addGroupMember userId:" + userId);
                                    GroupInfo groupInfo = groupViewModel.getGroupInfo(item.getGroupId(), true);

                                    groupViewModel.addGroupMember(groupInfo, Collections.singletonList(userId)).observe(RedpacketGameActivity.this, new Observer<Boolean>() {
                                        @Override
                                        public void onChanged(Boolean aBoolean) {
                                            if (aBoolean) {
                                                Intent intent = ConversationActivity.buildConversationIntent(RedpacketGameActivity.this, Conversation.ConversationType.Group, item.getGroupId(), 0);
                                                startActivity(intent);
//                                                finish();
                                            } else {
                                                Toast.makeText(RedpacketGameActivity.this, R.string.add_member_fail, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    SingleToast.showLongMsg("暂时无法加入该群");
                                }

                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                //showMessage(msg);
                            }
                        });

                    }
                });
            }
        };
        rvGameList.setAdapter(mRedPacketAdapter);

        if (mViewPagerNum == -1) {
            mViewPagerNum = 0;
        }
        getRedPacket();
    }


    public static void inGame() {

        Map<String, Object> params = new HashMap<>();
        params.put("gameCode", "A01095");
        params.put("gameKind", 13);
        params.put("incILog", 1);
        params.put("isWithTransfer", 1);
        params.put("incILog", 1);
        params.put("productId", IMDataCenter.getInstance().getProductId());
        params.put("verticalApp", 1);
        params.put("loginName", IMDataCenter.getInstance().getLoginName());

        Request.with(IMApplication.getContext()).post("/game/inGame", params, new RequestCallBack<InGameResult>() {
            @Override
            public void onGatewaySuccess(@Nullable InGameResult loginResult, ResponseModel.Head head) {

                if (loginResult != null && !TextUtils.isEmpty(loginResult.getUrl())) {

                    String data = decodeToString(loginResult.getUrl());

                    GameTokenBean gameTokenBean = new Gson().fromJson(data, GameTokenBean.class);

                    IMDataCenter.getInstance().setGame_u2token(gameTokenBean.getGame_u2token());
                    IMDataCenter.getInstance().setGame_token(gameTokenBean.getGame_token());


                }


            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });


    }


    public static String decodeToString(String str) {
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void getRedPacket() {
        AppService.Instance().QueryRedpacketGroup(mViewPagerNum, new AppService.QueryRedpacketGroupCallback() {
            @Override
            public void onSuccess(List<RedPacketGameListResult.RedPacketGroupVoListBean> statusResult) {
                List<RedPacketGameListResult.RedPacketGroupVoListBean> redPacketGroupVoList = statusResult;
                mRedPacketList.clear();
                mRedPacketList.addAll(redPacketGroupVoList);
                mRedPacketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String msg) {
                //  ToastUtil.showToastLong(msg);
            }
        });

    }

    @Override
    protected void initData() {
//        mRedPacketList.clear();
//        mRedPacketList.add("");
//        mRedPacketList.add("");
//        mRedPacketList.add("");
//        mRedPacketAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
