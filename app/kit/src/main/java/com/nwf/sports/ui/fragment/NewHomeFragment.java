package com.nwf.sports.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.adapter.BannerPaddingViewHolder;
import com.nwf.sports.chat.login.model.GameTokenBean;
import com.nwf.sports.chat.login.model.InGameResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameBean;
import com.nwf.sports.mvp.model.HomeGameResult;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LoginDialogFragment;
import com.nwf.sports.ui.dialogfragment.RegisterDialogFragment;
import com.nwf.sports.ui.views.BannerIndicatorView;
import com.nwf.sports.ui.views.CustomViewPager;
import com.nwf.sports.ui.views.NoticeView;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.data.IMDataCenter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechattest.TestActivity;
import ivi.net.base.netlibrary.callback.RequestCallBack;
import ivi.net.base.netlibrary.model.ResponseModel;
import ivi.net.base.netlibrary.request.Request;

/**
 * <p>类描述： 首页
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class NewHomeFragment extends BaseFragment {

    @BindView(R.id.banner_normal)
    MZBannerView mNormalBanner;
    @BindView(R.id.banner_indicator)
    BannerIndicatorView mBannerIndicator;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.notice)
    NoticeView vNotice;

    List<HomeGameBean> mHomeGameBeans = new ArrayList<HomeGameBean>();
    private int mViewPagerNum = 0;

    public static String[] notices = new String[]{
            "公共：新用户注册可以领取88元红包'",
            "公共：新用户注册可以领取188元红包",
            "公共：新用户注册可以领取288元红包",
            "公共：新用户注册可以领取388元红包",
            "公共：新用户注册可以领取888元红包",
    };


    public static NewHomeFragment newInstance() {
        NewHomeFragment fragment = new NewHomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_new;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
//        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
//            loginLayout.setVisibility(View.GONE);
//        } else {
//            loginLayout.setVisibility(View.VISIBLE);
//        }

        mHomeGameBeans.clear();
        mHomeGameBeans.add(new HomeGameBean("红包游戏", R.drawable.icon_home_game_red_packet, R.drawable.bg_home_game_red_packet));
//        mHomeGameBeans.add(new HomeGameBean("体育游戏", R.drawable.icon_home_game_sports, R.drawable.bg_home_game_sports));
//        mHomeGameBeans.add(new HomeGameBean("热门赛事直播", R.drawable.icon_home_game_sports_competition, R.drawable.bg_home_game_sports_competition));
//        mHomeGameBeans.add(new HomeGameBean("敬请期待", R.drawable.icon_home_game_expect, R.drawable.bg_home_game_expect));

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RedPackageGameFragment());
//        fragmentList.add(new SportsGameFragment());
//        fragmentList.add(new SportsGameMatchFragment());
//        fragmentList.add(new SportsGameWaitFragment());
        AdapterFragment adpter = new AdapterFragment(getChildFragmentManager(), fragmentList);
        mViewPager.setAdapter(adpter);
        // 预加载数量
        mViewPager.setOffscreenPageLimit(1);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.im_white));
        for (int i = 0; i < mHomeGameBeans.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(R.layout.activity_home_tab_game_item);
            TextView tabName = tab.getCustomView().findViewById(R.id.tab_name);
            ImageView imgBg = tab.getCustomView().findViewById(R.id.img_bg);
            ImageView imgIcon = tab.getCustomView().findViewById(R.id.img_icon);
            tabName.setText(mHomeGameBeans.get(i).name);
            imgBg.setImageResource(mHomeGameBeans.get(i).bg);
            imgIcon.setImageResource(mHomeGameBeans.get(i).icon);
            mTabLayout.addTab(tab);
        }
//        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mViewPagerNum = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPagerNum = 0;
//        setBanner();
//
//        vNotice.start(Arrays.asList(notices));
//        vNotice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getActivity(), notices[vNotice.getIndex()], Toast.LENGTH_SHORT).show();
//            }
//        });

        inGame();

    }


    @Override
    protected void loadData() {
        isUIVisible = true;
        isViewCreated = true;
//        if (IMApplication.isGain) {
//            if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
//                loginLayout.setVisibility(View.GONE);
//            } else {
//                loginLayout.setVisibility(View.VISIBLE);
//            }
//
//        }
    }


    /**
     * 开始游戏
     *
     * @param item
     */
    public void startGame(HomeGameResult.GameItemBean item) {
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
            Map<String, String> map = new HashMap<>();
            map.put("actype", "1");
            map.put("currency ", "CNY");
            map.put("gameId", item.getGameId());
            map.put("gmid", item.getGmid());
            map.put("language", "zh");
            map.put("loginName", IMDataCenter.getInstance().getUserInfoBean().getUsername());
//            mHomePresenter.loginGame(map, item.getChineseName());
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("actype", "0");
            map.put("currency ", "CNY");
            map.put("gameId", item.getGameId());
            map.put("gmid", item.getGmid());
            map.put("language", "zh");
            map.put("loginName", IMDataCenter.getInstance().getUserInfoBean().getUsername());
//            mHomePresenter.trialGame(map, item.getChineseName());
        }
    }

    private List<HomeDiscountsResult.BannerListBean> mBannerLists = new ArrayList<>();  //Banner

    /**
     * 设置Banner
     */
    public void setBanner() {
        mBannerLists.clear();
        HomeDiscountsResult.BannerListBean bannerListBean = new HomeDiscountsResult.BannerListBean();
        bannerListBean.setUrl("http://sportpic.f66-online.com/pic/20190905/280260fe312b456f9e9c94fc00397a59.jpg");
        mBannerLists.add(bannerListBean);
        bannerListBean = new HomeDiscountsResult.BannerListBean();
        bannerListBean.setUrl("http://sportpic.f66-online.com/pic/20190905/c8971aec3ef14d41b530d7e1b12417b0.jpg");
        mBannerLists.add(bannerListBean);
        bannerListBean = new HomeDiscountsResult.BannerListBean();
        bannerListBean.setUrl("http://sportpic.f66-online.com/pic/20190905/f2f3a40d1933456f87d19c28c3e11943.jpg");
        mBannerLists.add(bannerListBean);

        mBannerIndicator.setCellCount(mBannerLists.size());
        mNormalBanner.getViewPager().removeAllViews();
        mNormalBanner.setDelayedTime(5000);
        mNormalBanner.setIndicatorVisible(false);
        mNormalBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
            }
        });

        mNormalBanner.setPages(mBannerLists, new MZHolderCreator<BannerPaddingViewHolder>() {
            @Override
            public BannerPaddingViewHolder createViewHolder() {
                return new BannerPaddingViewHolder();
            }
        });
     //   mBannerIndicator.bindWithViewPager(mNormalBanner.getViewPager(), mBannerLists.size());
        //mNormalBanner.start();
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

                    GameTokenBean gameTokenBean = new Gson().fromJson(data,GameTokenBean.class);

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





    @Override
    public void onPause() {
        super.onPause();
      //  mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
      //  mNormalBanner.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_homepage_contract_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), loginDialogFragment);
                break;
            case R.id.bt_register:
                RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), registerDialogFragment);
                break;
            case R.id.iv_homepage_contract_service:
                if (IMApplication.isChat){
                    return;
                }
                ActivityUtil.skipToService(getActivity());
                break;
        }
    }


    /**
     * 登录成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGIN)})
    public void loginSucceed(String string) {
        ToastUtil.showToastLong("登录IM成功");
        loginLayout.setVisibility(View.GONE);
    }

    /**
     * 退出登录
     */
    @Subscribe(tags = {@Tag(ConstantValue.LOG_OUT)})
    public void logOut(String string) {
//        IMDataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();
//        loginLayout.setVisibility(View.VISIBLE);
//        ((MainActivity) getActivity()).switchTab(MainActivity.TAB_INDEX_HOME);
    }

    @Override
    public void onDetach() {
        RxBus.get().unregister(this);
        super.onDetach();
    }

    /**
     * 线路chcek完成 刷新网络层
     */
    @Subscribe(tags = {@Tag(ConstantValue.START_REQUEST)})
    public void startRequest(String type) {
//        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
//            loginLayout.setVisibility(View.GONE);
//        } else {
//            loginLayout.setVisibility(View.VISIBLE);
//        }
    }


}
