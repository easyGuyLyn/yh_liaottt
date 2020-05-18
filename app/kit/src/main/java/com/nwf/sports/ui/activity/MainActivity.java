package com.nwf.sports.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.NetworkUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.google.android.material.tabs.TabLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.king.zxing.Intents;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.chat.ChatFriendActivity;
import com.nwf.sports.chat.main.PCLoginActivity;
import com.nwf.sports.mvp.model.CheckupgradeResult;
import com.nwf.sports.mvp.model.SkipLoginBean;
import com.nwf.sports.mvp.presenter.LimitTransferPresenter;
import com.nwf.sports.mvp.presenter.UpdatePresenter;
import com.nwf.sports.mvp.view.LimitTransferView;
import com.nwf.sports.mvp.view.UpdateView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LineHelpErreDialogFragment;
import com.nwf.sports.ui.dialogfragment.LoadingDialogFragment;
import com.nwf.sports.ui.dialogfragment.LoginDialogFragment;
import com.nwf.sports.ui.dialogfragment.ModificationNameDialogFragment;
import com.nwf.sports.ui.dialogfragment.RegisterDialogFragment;
import com.nwf.sports.ui.dialogfragment.RemindCommonDialogFragment;
import com.nwf.sports.ui.dialogfragment.UpdateDialogFragment;
import com.nwf.sports.ui.fragment.DepositFragment;
import com.nwf.sports.ui.fragment.MineFragment;
import com.nwf.sports.ui.fragment.NewHomeFragment;
import com.nwf.sports.ui.views.CustomViewPager;
import com.nwf.sports.utils.data.DataCenter;
import com.nwf.sports.utils.line.LineErrorDialogBean;
import com.nwf.sports.utils.line.LineHelperService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;
import cn.wildfire.chat.kit.IMConnectionStatusViewModel;
import cn.wildfire.chat.kit.IMServiceStatusViewModel;
import cn.wildfire.chat.kit.WfcScheme;
import cn.wildfire.chat.kit.channel.ChannelInfoActivity;
import cn.wildfire.chat.kit.contact.ContactViewModel;
import cn.wildfire.chat.kit.contact.newfriend.SearchUserActivity;
import cn.wildfire.chat.kit.conversation.CreateConversationActivity;
import cn.wildfire.chat.kit.conversationlist.ConversationListFragment;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModel;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModelFactory;
import cn.wildfire.chat.kit.group.GroupInfoActivity;
import cn.wildfire.chat.kit.qrcode.ScanQRCodeActivity;
import cn.wildfire.chat.kit.search.SearchPortalActivity;
import cn.wildfire.chat.kit.user.UserInfoActivity;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.BuildConfig;
import cn.wildfirechat.client.ConnectionStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.remote.ChatManager;
import q.rorbin.badgeview.QBadgeView;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-03-27
 * <p>修改人：Simon
 * <p>修改时间：2019-03-27
 * <p>修改备注：
 **/
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, UpdateView, LimitTransferView {

    public static final int TAB_INDEX_HOME = 0;
    public static final int TAB_INDEX_DEPOSIT = 1;
    public static final int TAB_INDEX_CHAT = 2;
    //    public static final int TAB_INDEX_WITHDRAWAL = 3;
    public static final int TAB_INDEX_MINE = 3;
    @BindView(R.id.layout_home)
    LinearLayout layoutHome;
    @BindView(R.id.layout_deposit)
    LinearLayout layoutDeposit;
    @BindView(R.id.layout_withdrawal)
    LinearLayout layoutWithdrawal;
    @BindView(R.id.layout_mine)
    LinearLayout layoutMine;
    @BindView(R.id.layout_chat)
    LinearLayout layoutChat;
    @BindView(R.id.icon_chat)
    ImageView iconChat;
    @BindView(R.id.toolbar_layout)
    View toolbarlayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.friend)
    ImageView friend;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;
    UpdatePresenter mUpdatePresenter = null;
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    private LoadingDialogFragment mLoadingDialogFragment;
    private LineHelpErreDialogFragment mLineHelpErreDialogFragment;
    private QBadgeView unreadMessageUnreadBadgeView;
    private QBadgeView unreadFriendRequestBadgeView;
    private LimitTransferPresenter mLimitTransferPresenter = null;

    public static final int REQUEST_CODE_SCAN_QR_CODE = 100;
    public static final int REQUEST_IGNORE_BATTERY_CODE = 101;

    private boolean isInitialized = false;
    private Observer<Boolean> imStatusLiveDataObserver = status -> {
        if (status && !isInitialized) {
//            if (mLoadingDialogFragment != null) {
//                mLoadingDialogFragment.dismissAllowingStateLoss();
//                mLoadingDialogFragment = null;
//            }
            isInitialized = true;
        }
    };
    View searchView = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        mLimitTransferPresenter = new LimitTransferPresenter(this, this);
        RxBus.get().register(this);
        mUpdatePresenter = new UpdatePresenter(this, this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(NewHomeFragment.newInstance());
        fragmentList.add(DepositFragment.newInstance());
        fragmentList.add(new ConversationListFragment());
//        fragmentList.add(WithdrawalFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        AdapterFragment adpter = new AdapterFragment(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adpter);
        // 预加载数量
        mViewPager.setOffscreenPageLimit(4);

        int skipType = getIntent().getIntExtra(ConstantValue.SKIP_MIAN_TYPE, -1);
        if (skipType == -1) {
            // 初始化选中home页
            switchTab(TAB_INDEX_HOME);
        } else {
            switchTab(skipType);
            String stringExtra = getIntent().getStringExtra(ConstantValue.MAIN_OPEN_TYPE);
            switch (stringExtra) {
                case ConstantValue.SKIP_LOGIN:
                    skipRegister("");
                    break;
                case ConstantValue.SKIP_REGISTER:
                    skipLogin(new SkipLoginBean("", ""));
                    break;
            }
        }

        if (!IMApplication.isGain) {  //如果初始化没走完 就弹出dialog
            mLoadingDialogFragment = new LoadingDialogFragment();
            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLoadingDialogFragment);
        }
        if (IMApplication.mLineErrorDialogBean != null) {//如果出现异常情况 就弹出dialog
            onLineErreEvent(IMApplication.mLineErrorDialogBean);
        }

        if (IMApplication.isGain) {
            mUpdatePresenter.checkupgrade();
        }

        setSupportActionBar(toolbar);
        init();
    }

    @Override
    protected void initData() {
//        if (mLoadingDialogFragment == null) {
//            mLoadingDialogFragment = new LoadingDialogFragment();
//        }
//        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLoadingDialogFragment);
        IMServiceStatusViewModel imServiceStatusViewModel = ViewModelProviders.of(this).get(IMServiceStatusViewModel.class);
        imServiceStatusViewModel.imServiceStatusLiveData().observe(this, imStatusLiveDataObserver);
        IMConnectionStatusViewModel connectionStatusViewModel = ViewModelProviders.of(this).get(IMConnectionStatusViewModel.class);
        connectionStatusViewModel.connectionStatusLiveData().observe(this, status -> {
            if (status == ConnectionStatus.ConnectionStatusTokenIncorrect || status == ConnectionStatus.ConnectionStatusSecretKeyMismatch || status == ConnectionStatus.ConnectionStatusRejected || status == ConnectionStatus.ConnectionStatusLogout) {
                Log.e("TT", "聊天错误码===" + status);
                ChatManager.Instance().disconnect(true);
                RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
            }
        });

        refreshRedOacketLimit("");
        queryChangenameFlag();
    }

    private ContactViewModel contactViewModel;
    private ConversationListViewModel conversationListViewModel;

    private void init() {
        conversationListViewModel = ViewModelProviders
                .of(this, new ConversationListViewModelFactory(Arrays.asList(Conversation.ConversationType.Single, Conversation.ConversationType.Group, Conversation.ConversationType.Channel), Arrays.asList(0)))
                .get(ConversationListViewModel.class);
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        conversationListViewModel.unreadCountLiveData().observe(this, unreadCount -> {
            if (unreadCount != null && unreadCount.unread > 0) {
                showUnreadMessageBadgeView(unreadCount.unread);  //添加聊天数量
            } else {
                hideUnreadMessageBadgeView();
            }
        });

        contactViewModel.friendRequestUpdatedLiveData().observe(this, count -> {
            if (count == null || count == 0) {
                hideUnreadFriendRequestBadgeView();
            } else {
                showUnreadFriendRequestBadgeView(count);
            }
        });

//        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
//        contactViewModel.friendRequestUpdatedLiveData().observe(this, count -> {
//            if (count == null || count == 0) {
//                hideUnreadFriendRequestBadgeView();
//            } else {
//                showUnreadFriendRequestBadgeView(count);
//            }
//        });
//
//        if (checkDisplayName()) {
//            ignoreBatteryOption();
//        }

    }

    private void showUnreadFriendRequestBadgeView(int count) {
        if (friend == null) {
            return;
        }
        if (unreadFriendRequestBadgeView == null) {
            unreadFriendRequestBadgeView = new QBadgeView(MainActivity.this);
            unreadFriendRequestBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            unreadFriendRequestBadgeView.bindTarget(framelayout);
        }
        unreadFriendRequestBadgeView.setBadgeNumber(count);
    }

    public void hideUnreadFriendRequestBadgeView() {
        if (unreadFriendRequestBadgeView != null) {
            unreadFriendRequestBadgeView.hide(true);
            unreadFriendRequestBadgeView = null;
        }
    }


    /**
     * 切换页面
     * 连带改变tab选中状态
     *
     * @param index tab下标
     */
    public void switchTab(int index) {
        toolbarlayout.setVisibility(View.GONE);
        switch (index) {
            case TAB_INDEX_HOME:
                refreshRedOacketLimit("");
                mViewPager.setCurrentItem(TAB_INDEX_HOME, false);
                changeTabState(index);
                break;
            case TAB_INDEX_DEPOSIT:
                if (!DataCenter.getInstance().getUserInfoBean().isRealLogin) {
                    skipLogin(new SkipLoginBean("", ""));
                    return;
                }
                mViewPager.setCurrentItem(TAB_INDEX_DEPOSIT, false);
                changeTabState(index);
                break;
            case TAB_INDEX_CHAT:
                refreshRedOacketLimit("");
                if (!DataCenter.getInstance().getUserInfoBean().isRealLogin) {
                    skipLogin(new SkipLoginBean("", ""));
                    return;
                }
                toolbarlayout.setVisibility(View.VISIBLE);
                setTitle("消息");
                mViewPager.setCurrentItem(TAB_INDEX_CHAT, false);
                changeTabState(index);
                break;
//            case TAB_INDEX_WITHDRAWAL:
//                if (!DataCenter.getInstance().getUserInfoBean().isRealLogin) {
//                    skipLogin(new SkipLoginBean("", ""));
//                    return;
//                }
//                mViewPager.setCurrentItem(TAB_INDEX_WITHDRAWAL, false);
//                changeTabState(index);
//                break;
            case TAB_INDEX_MINE:
                if (!DataCenter.getInstance().getUserInfoBean().isRealLogin) {
                    skipLogin(new SkipLoginBean("", ""));
                    return;
                }
                mViewPager.setCurrentItem(TAB_INDEX_MINE, false);
                changeTabState(index);
                break;
            default:
        }
    }


    /**
     * 改变tab的选中和未选中状态
     *
     * @param index
     */
    private void changeTabState(int index) {
        switch (index) {
            case TAB_INDEX_HOME:
                layoutHome.setSelected(true);
                layoutDeposit.setSelected(false);
                layoutChat.setSelected(false);
                layoutWithdrawal.setSelected(false);
                layoutMine.setSelected(false);
                break;
            case TAB_INDEX_DEPOSIT:
                layoutHome.setSelected(false);
                layoutDeposit.setSelected(true);
                layoutChat.setSelected(false);
                layoutWithdrawal.setSelected(false);
                layoutMine.setSelected(false);
                break;
            case TAB_INDEX_CHAT:
                layoutHome.setSelected(false);
                layoutDeposit.setSelected(false);
                layoutChat.setSelected(true);
                layoutWithdrawal.setSelected(false);
                layoutMine.setSelected(false);
                break;
//            case TAB_INDEX_WITHDRAWAL:
//                layoutHome.setSelected(false);
//                layoutDeposit.setSelected(false);
//                layoutChat.setSelected(false);
//                layoutWithdrawal.setSelected(true);
//                layoutMine.setSelected(false);
//                break;
            case TAB_INDEX_MINE:
                layoutHome.setSelected(false);
                layoutDeposit.setSelected(false);
                layoutChat.setSelected(false);
                layoutWithdrawal.setSelected(false);
                layoutMine.setSelected(true);
                break;
            default:
        }
    }


    @OnClick({R.id.layout_home, R.id.layout_deposit, R.id.layout_withdrawal, R.id.layout_mine, R.id.layout_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_home:
                switchTab(TAB_INDEX_HOME);
                break;
            case R.id.layout_deposit:
                switchTab(TAB_INDEX_DEPOSIT);
                break;
            case R.id.layout_chat:
                switchTab(TAB_INDEX_CHAT);
                break;
//            case R.id.layout_withdrawal:
//                switchTab(TAB_INDEX_WITHDRAWAL);
//                break;
            case R.id.layout_mine:
                switchTab(TAB_INDEX_MINE);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUpdatePresenter != null) {
            mUpdatePresenter.onDestory();
        }
        RxBus.get().unregister(this);
    }


    /**
     * 弹出注册框
     */
    @Subscribe(tags = {@Tag(ConstantValue.SKIP_REGISTER)})
    public void skipRegister(String s) {
        DialogFramentManager.getInstance().clearDialog();
        RegisterDialogFragment registerDialogFragment = RegisterDialogFragment.getInstance(s);
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), registerDialogFragment);
    }

    /**
     * 弹出登录框
     */
    @Subscribe(tags = {@Tag(ConstantValue.SKIP_LOGIN)})
    public void skipLogin(SkipLoginBean skipLoginBean) {
        DialogFramentManager.getInstance().clearDialog();
        LoginDialogFragment loginDialogFragment = LoginDialogFragment.getInstance(skipLoginBean.phone, skipLoginBean.type);
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), loginDialogFragment);
//        RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

    /**
     * 切换选择
     */
    @Subscribe(tags = {@Tag(ConstantValue.MAINACTIVITY_SWITCHOVER)})
    public void switchoverPage(Integer type) {
        switchTab(type);
    }

    /**
     * 地区限制
     */
    @Subscribe(tags = {@Tag(ConstantValue.REGION_ASTRICT)})
    public void regionAstrict(String type) {
        RemindCommonDialogFragment remindCommonDialogFragment = new RemindCommonDialogFragment()
                .setClose(false)
                .setTvContent("尊敬的用户: 当前地区访问受限")
                .setOnRightButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityStackManager.getInstance().finishAllActivity();
                    }
                }, "知道了");
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), remindCommonDialogFragment);
    }

    /**
     * token 失效
     */
    @Subscribe(tags = {@Tag(ConstantValue.TOKEN_LOSE_EFFICACY)})
    public void tokenLoseEfficacy(String type) {
        Toast.makeText(this, "登录失效，请重新登录", Toast.LENGTH_SHORT).show();
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
        RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
    }

    /**
     * 登录成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGIN)})
    public void loginSucceed(String string) {
        refreshRedOacketLimit("");
        queryChangenameFlag();
    }

    public void queryChangenameFlag() {
        if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            boolean type = (boolean) SPTool.get(DataCenter.getInstance().getUserInfoBean().username+ConstantValue.MODIFICATION_NAME, false);
            if (type) {
                return;
            }
            AppService.Instance().queryChangenameFlag(new AppService.QueryChangenameFlagCallback() {
                @Override
                public void onSuccess(boolean isModification) {
                    if (!isModification) {
                        ModificationNameDialogFragment modificationNameDialogFragment = new ModificationNameDialogFragment();
                        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), modificationNameDialogFragment);
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    showMessage(msg);
                }
            });
        }
    }

    /**
     * 刷新紅包余额
     */
    @Subscribe(tags = {@Tag(ConstantValue.REFRESH_RED_OACKET_LIMIT)})
    public void refreshRedOacketLimit(String type) {
        if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            Map<String, String> map = new HashMap<>();
            map.put("actype", "1");
            map.put("currency ", "CNY");
            map.put("gameId", "");
            map.put("gmid", "E03085");
            map.put("language", "zh");
            map.put("loginName", DataCenter.getInstance().getUserInfoBean().getUsername());
            mLimitTransferPresenter.transferTargetGame(map);
        }
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }

    private void closeApp() {
        // 判断时间间隔
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            // 退出
            ActivityStackManager.getInstance().finishAllActivity();
        }
    }

    @Override
    public void checkupgrade(CheckupgradeResult checkupgradeResult) {
        if (checkupgradeResult == null) {
            return;
        }
        if (checkupgradeResult.getVersionCode() > BuildConfig.VERSION_CODE) {
            UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();
            updateDialogFragment.setCheckupgradeResult(checkupgradeResult);
            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), updateDialogFragment);
        }
    }


    /**
     * 线路处理或者登录出现异常
     *
     * @param event
     */
    @Subscribe(tags = {@Tag(ConstantValue.PING_DOMAIN_ERRE)})
    public void onLineErreEvent(LineErrorDialogBean event) {
        if (mLoadingDialogFragment != null) {
            mLoadingDialogFragment.dismissAllowingStateLoss();
            mLoadingDialogFragment = null;
        }
        if (!NetworkUtils.isConnected()) {
            mLineHelpErreDialogFragment = new LineHelpErreDialogFragment()
                    .setTvTitle("提示")
                    .setTvContent("尊敬的用户: " + getString(R.string.n_homepage_game_no_network) + "\n错误码：-5")
                    .setOnLeftButtonListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityStackManager.getInstance().finishAllActivity();
                        }
                    }, "关闭");
            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLineHelpErreDialogFragment);
            return;
        }
        mLineHelpErreDialogFragment = new LineHelpErreDialogFragment()
                .setTvTitle("提示")
                .setTvContent("尊敬的用户: " + event.getMsg() + "\n错误码：" + event.getCode())
                .setOnLeftButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFramentManager.getInstance().clearDialog();
                    }
                }, "关闭")
                .setOnRightButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!NetworkUtils.isConnected()) {
                            showMessage(getString(R.string.n_homepage_game_no_network));
                            return;
                        }
                        DialogFramentManager.getInstance().clearDialog();
                        mLoadingDialogFragment = new LoadingDialogFragment();
                        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLoadingDialogFragment);
                        LineHelperService.startService(MainActivity.this);
                    }
                }, "重试");
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLineHelpErreDialogFragment);
        IMApplication.mLineErrorDialogBean = null;
    }


    /**
     * 线路chcek完成 刷新网络层
     */
    @Subscribe(tags = {@Tag(ConstantValue.START_REQUEST)})
    public void startRequest(String type) {
        if (mLoadingDialogFragment != null) {
            mLoadingDialogFragment.dismissAllowingStateLoss();
            mLoadingDialogFragment = null;
        }
        if (mUpdatePresenter != null) {
            mUpdatePresenter.onDestory();
        }
        LogUtils.e("这个-===" + RetrofitHelper.baseUrl());
        mUpdatePresenter = new UpdatePresenter(this, this);
        mUpdatePresenter.checkupgrade();
    }


    /**
     * 退出登录
     */
    @Subscribe(tags = {@Tag(ConstantValue.LOG_OUT)})
    public void logOut(String string) {
        DataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();

        ChatManagerHolder.gChatManager.disconnect(true);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().clear().apply();

        switchTab(MainActivity.TAB_INDEX_HOME);
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }


    private void showUnreadMessageBadgeView(int count) {
        if (unreadMessageUnreadBadgeView == null) {
            unreadMessageUnreadBadgeView = new QBadgeView(MainActivity.this);
            unreadMessageUnreadBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            unreadMessageUnreadBadgeView.bindTarget(layoutChat);
        }
        unreadMessageUnreadBadgeView.setBadgeNumber(count);
    }

    private void hideUnreadMessageBadgeView() {
        if (unreadMessageUnreadBadgeView != null) {
            unreadMessageUnreadBadgeView.hide(true);
            unreadMessageUnreadBadgeView = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SCAN_QR_CODE:
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    onScanPcQrCode(result);
                }
                break;
            case REQUEST_IGNORE_BATTERY_CODE:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "允许野火IM后台运行，更能保证消息的实时性", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    private void onScanPcQrCode(String qrcode) {
        String prefix = qrcode.substring(0, qrcode.lastIndexOf('/') + 1);
        String value = qrcode.substring(qrcode.lastIndexOf("/") + 1);
        switch (prefix) {
            case WfcScheme.QR_CODE_PREFIX_PC_SESSION:
                pcLogin(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_USER:
                showUser(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_GROUP:
                joinGroup(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_CHANNEL:
                subscribeChannel(value);
                break;
            default:
                Toast.makeText(this, "qrcode: " + qrcode, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void pcLogin(String token) {
        Intent intent = new Intent(this, PCLoginActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    private void showUser(String uid) {

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        UserInfo userInfo = userViewModel.getUserInfo(uid, true);
        if (userInfo == null) {
            return;
        }
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }

    private void joinGroup(String groupId) {
        Intent intent = new Intent(this, GroupInfoActivity.class);
        intent.putExtra("groupId", groupId);
        startActivity(intent);
    }

    private void subscribeChannel(String channelId) {
        Intent intent = new Intent(this, ChannelInfoActivity.class);
        intent.putExtra("channelId", channelId);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showSearchPortal();
                break;
            case R.id.chat:
                createConversation();
                break;
            case R.id.add_contact:
                searchUser();
                break;
            case R.id.scan_qrcode:
                String[] permissions = new String[]{Manifest.permission.CAMERA};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!checkPermission(permissions)) {
                        requestPermissions(permissions, 100);
                        return true;
                    }
                }
                startActivityForResult(new Intent(this, ScanQRCodeActivity.class), REQUEST_CODE_SCAN_QR_CODE);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChatFriend() {
        Intent intent = new Intent(this, ChatFriendActivity.class);
        startActivity(intent);
    }

    private void showSearchPortal() {
        Intent intent = new Intent(this, SearchPortalActivity.class);
        startActivity(intent);
    }

    private void createConversation() {
        Intent intent = new Intent(this, CreateConversationActivity.class);
        startActivity(intent);
    }

    private void searchUser() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    public boolean checkPermission(String[] permissions) {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                granted = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    break;
                }
            }
        }
        return granted;
    }

    @OnClick(R.id.friend)
    public void onViewClicked() {
        showChatFriend();
    }
}
