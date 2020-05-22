package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.BannerPaddingViewHolder;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;
import com.nwf.sports.mvp.presenter.HomePresenter;
import com.nwf.sports.mvp.view.HomeView;
import com.nwf.sports.ui.activity.DiscountsActivity;
import com.nwf.sports.ui.activity.DownloadAppsActivity;
import com.nwf.sports.ui.activity.MainActivity;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.activity.webview.XPlayGameActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LoginDialogFragment;
import com.nwf.sports.ui.dialogfragment.RegisterDialogFragment;
import com.nwf.sports.ui.views.BannerIndicatorView;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.data.IMDataCenter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 首页
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.banner_normal)
    MZBannerView mNormalBanner;
    @BindView(R.id.banner_indicator)
    BannerIndicatorView mBannerIndicator;
    @BindView(R.id.rv_game_list)
    RecyclerView rvGameList;
    @BindView(R.id.rv_mygame_list)
    RecyclerView rvMyGameList;
    @BindView(R.id.rv_discounts_list)
    RecyclerView rvDiscountsList;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.im_appdownload)
    ImageView mImAppDownload;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    List<HomeGameResult> mHomeGameResults = new ArrayList<>();

    private CommonAdapter<HomeGameResult.GameItemBean> mHomeGameAdapter = null; //游戏adapter
    private List<HomeGameResult.GameItemBean> mHomeGames = new ArrayList<>();

    private CommonAdapter<HomeGameResult.GameItemBean> mHomeMyGameAdapter = null; //推荐游戏adapter
    private List<HomeGameResult.GameItemBean> mHomeMyGames = new ArrayList<>();

    private CommonAdapter<HomeDiscountsResult.PromotionsListBean> mHomeDiscountsAdapter = null; //优惠adapter
    private ArrayList<HomeDiscountsResult.PromotionsListBean> mDiscounts = new ArrayList<>();

    private List<HomeDiscountsResult.BannerListBean> mBannerLists = new ArrayList<>();  //Banner

    private HomePresenter mHomePresenter = null;
    private DownloadAppResult mDownloadAppResult = null;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        mHomePresenter = new HomePresenter(getActivity(), this);
        setDiscounts();
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
            loginLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
        }

        HomeDiscountsResult homePage = IMDataCenter.getInstance().getMyLocalCenter().getHomePage();
        List<HomeGameResult> homeGameResult = IMDataCenter.getInstance().getMyLocalCenter().getHomeGameResult();
        DownloadAppResult downloadData = IMDataCenter.getInstance().getMyLocalCenter().getDownloadData();

        if (homeGameResult.size() > 0) {
            gameListSuccess(homeGameResult);
        }

        if (homePage != null) {
            homePageSuccess(homePage);
        }

        if (downloadData != null) {
            downloadApps(downloadData);
        }


    }


    @Override
    protected void loadData() {
        isUIVisible = true;
        isViewCreated = true;
        if (IMApplication.isGain) {
            mHomePresenter.gameList();
            mHomePresenter.homePage();
            mHomePresenter.downloadApps();
            if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
                loginLayout.setVisibility(View.GONE);
            } else {
                loginLayout.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * 设置游戏
     */
    public void setGame() {
        mTabLayout.removeAllTabs();
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.color_1ECE94));

        for (int i = 0; i < mHomeGameResults.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();//获得每一个tab
            tab.setCustomView(R.layout.activity_history_tab_item);//给每一个tab设置view
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            textView.setText(mHomeGameResults.get(i).getGameProvider());//设置tab上的文字
            mTabLayout.addTab(tab);
        }

        Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mHomeGames.clear();
                mHomeGames.addAll(mHomeGameResults.get(tab.getPosition()).getGameItem());
                mHomeGameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (mHomeGameResults.size() > 0) {
            mHomeGames.clear();
            mHomeGames.addAll(mHomeGameResults.get(0).getGameItem());
        }
        rvGameList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        mHomeGameAdapter = new CommonAdapter<HomeGameResult.GameItemBean>(getActivity(), R.layout.fragment_home_game_item, mHomeGames) {

            @Override
            public void convert(ViewHolder holder, HomeGameResult.GameItemBean item, int position) {
                TextView gameName = holder.getView(R.id.tv_game_name);
                TextView tvStartGame = holder.getView(R.id.tv_start_game);
                ImageView imageView = holder.getConvertView().findViewById(R.id.item_game_image);
                ImageView maintenance = holder.getConvertView().findViewById(R.id.item_game_maintenance);
                gameName.setText(item.getChineseName());

                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(45);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.game_placeholder);
                //加载图片
                Glide.with(mContext)
                        .load(item.getFilePath())
                        .apply(options)
                        .into(imageView);

                if (mHomeGameResults.get(mTabLayout.getSelectedTabPosition()).getFlag() == 0) {
                    maintenance.setVisibility(View.VISIBLE);
                } else {
                    maintenance.setVisibility(View.GONE);
                }

                tvStartGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mHomeGameResults.get(mTabLayout.getSelectedTabPosition()).getFlag() != 0) {
                            startGame(item);
                        } else {
                            showMessage("该游戏正在维护中,请稍后再试");
                        }

                    }
                });
            }
        };
        rvGameList.setAdapter(mHomeGameAdapter);

        rvMyGameList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        mHomeMyGameAdapter = new CommonAdapter<HomeGameResult.GameItemBean>(getActivity(), R.layout.fragment_home_mygame_item, mHomeMyGames) {
            @Override
            public void convert(ViewHolder holder, HomeGameResult.GameItemBean item, int position) {
                ImageView imageView = holder.getConvertView().findViewById(R.id.item_game_image);
                ImageView maintenance = holder.getConvertView().findViewById(R.id.item_game_maintenance);
                holder.setText(R.id.tv_game_name, item.getChineseName());
                TextView tvStartGame = holder.getView(R.id.tv_start_game);
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(45);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.game_placeholder);
                //加载图片
                Glide.with(mContext)
                        .load(item.getFilePath())
                        .apply(options)
                        .into(imageView);

                if (item.getFlag() == 0) {
                    maintenance.setVisibility(View.VISIBLE);
                } else {
                    maintenance.setVisibility(View.GONE);
                }
                tvStartGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getFlag() != 0) {
                            startGame(item);
                        } else {
                            showMessage("该游戏正在维护中,请稍后再试");
                        }
                    }
                });
            }
        };
        rvMyGameList.setAdapter(mHomeMyGameAdapter);
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
            mHomePresenter.loginGame(map, item.getChineseName());
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("actype", "0");
            map.put("currency ", "CNY");
            map.put("gameId", item.getGameId());
            map.put("gmid", item.getGmid());
            map.put("language", "zh");
            map.put("loginName", IMDataCenter.getInstance().getUserInfoBean().getUsername());
            mHomePresenter.trialGame(map, item.getChineseName());
        }
    }

    /**
     * 设置Banner
     */
    public void setBanner() {
        mBannerIndicator.setCellCount(mBannerLists.size());
        mNormalBanner.getViewPager().removeAllViews();
        mNormalBanner.setDelayedTime(5000);
        mNormalBanner.setIndicatorVisible(false);
        mNormalBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Bundle mbundle = new Bundle();
                mbundle.putString(ConstantValue.ARG_PARAM1, mBannerLists.get(position).getContent());
                mbundle.putString(ConstantValue.ARG_PARAM2, mBannerLists.get(position).getActionUrl());
                startActivity(new Intent(getActivity(), IntroduceActivity.class).putExtras(mbundle));
            }
        });

        mNormalBanner.setPages(mBannerLists, new MZHolderCreator<BannerPaddingViewHolder>() {
            @Override
            public BannerPaddingViewHolder createViewHolder() {
                return new BannerPaddingViewHolder();
            }
        });
        mBannerIndicator.bindWithViewPager(mNormalBanner.getViewPager(), mBannerLists.size());
        mNormalBanner.start();
    }

    /**
     * 设置优惠
     */
    public void setDiscounts() {
        rvDiscountsList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        mHomeDiscountsAdapter = new CommonAdapter<HomeDiscountsResult.PromotionsListBean>(getActivity(), R.layout.fragment_home_discounts_item, mDiscounts) {
            @Override
            public void convert(ViewHolder holder, HomeDiscountsResult.PromotionsListBean item, int position) {
                ImageView imageView = holder.getConvertView().findViewById(R.id.image);
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(27);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.nwf_banner_placeholder);
                //加载图片
                Glide.with(mContext)
                        .load(item.getMinImgUrl())
                        .apply(options)
                        .into(imageView);

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle mbundle = new Bundle();
                        mbundle.putString(ConstantValue.ARG_PARAM1, item.getTitle());
                        mbundle.putString(ConstantValue.ARG_PARAM2, item.getActionUrl());
                        startActivity(new Intent(getActivity(), IntroduceActivity.class).putExtras(mbundle));
                    }
                });
            }
        };
        rvDiscountsList.setAdapter(mHomeDiscountsAdapter);

    }

    @Override
    public void onPause() {
        super.onPause();
        mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNormalBanner.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_homepage_contract_service, R.id.im_appdownload, R.id.tv_all_appdownloads, R.id.tv_all_discounts})
    public void onViewClicked(View view) {
        Bundle mbundle = null;
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
                ActivityUtil.skipToService(getActivity());
                break;
            case R.id.tv_all_appdownloads:
            case R.id.im_appdownload:
                mbundle = new Bundle();
                if (null != mDownloadAppResult && null != mDownloadAppResult.apps && mDownloadAppResult.apps.size() > 0) {
                    mbundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, mDownloadAppResult.apps);
                    startActivity(new Intent(mContext, DownloadAppsActivity.class).putExtras(mbundle));
                } else {
                    showMessage("网络异常");
                }
                break;
            case R.id.tv_all_discounts:
                mbundle = new Bundle();
                mbundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, mDiscounts);
                startActivity(new Intent(mContext, DiscountsActivity.class).putExtras(mbundle));
                break;
        }
    }


    /**
     * 登录成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGIN)})
    public void loginSucceed(String string) {
        ToastUtil.showToastLong("登录成功");
        loginLayout.setVisibility(View.GONE);
    }

    /**
     * 退出登录
     */
    @Subscribe(tags = {@Tag(ConstantValue.LOG_OUT)})
    public void logOut(String string) {
        IMDataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();
        loginLayout.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).switchTab(MainActivity.TAB_INDEX_HOME);
    }

    @Override
    public void gameListSuccess(List<HomeGameResult> gameResults) {
        IMDataCenter.getInstance().getMyLocalCenter().saveHomeGameResult(gameResults);
        mHomeMyGames.clear();
        for (HomeGameResult homeGameResult : gameResults) {
            for (HomeGameResult.GameItemBean gameItemBean : homeGameResult.getGameItem()) {
                gameItemBean.setGmid(homeGameResult.getGmid());
                if (gameItemBean.getIsRecommend().equals("1")) {
                    gameItemBean.setFlag(homeGameResult.getFlag());
                    mHomeMyGames.add(gameItemBean);
                }
            }
        }
        mHomeGameResults.clear();
        mHomeGameResults.addAll(gameResults);
        setGame();
    }

    @Override
    public void homePageSuccess(HomeDiscountsResult homeDiscountsResult) {
        IMDataCenter.getInstance().getMyLocalCenter().saveHomePage(homeDiscountsResult);
        mBannerLists.clear();
        mBannerLists.addAll(homeDiscountsResult.getBannerList());
        mDiscounts.clear();
        mDiscounts.addAll(homeDiscountsResult.getPromotionsList());
        mHomeDiscountsAdapter.notifyDataSetChanged();
        setBanner();
    }

    @Override
    public void downloadApps(DownloadAppResult downloadAppResult) {
        mDownloadAppResult = downloadAppResult;
        IMDataCenter.getInstance().getMyLocalCenter().saveDownload(downloadAppResult);
        //设置图片圆角角度
//        RoundedCorners roundedCorners = new RoundedCorners(27);
        RequestOptions options = RequestOptions.placeholderOf(R.mipmap.nwf_banner_placeholder);
        //加载图片
        Glide.with(mContext)
                .load(downloadAppResult.imgUrl)
                .apply(options)
                .into(mImAppDownload);
    }

    @Override
    public void loginGame(String url, String title) {
        Bundle mbundle = new Bundle();
        mbundle.putString(ConstantValue.ARG_PARAM1, url);
        mbundle.putString(ConstantValue.ARG_PARAM2, title);
        mbundle.putBoolean(ConstantValue.ARG_PARAM3, false);
        startActivity(new Intent(mContext, XPlayGameActivity.class).putExtras(mbundle));
    }

    @Override
    public void onDetach() {
        RxBus.get().unregister(this);
        super.onDetach();
        if (mHomePresenter != null) {
            mHomePresenter.onDestory();
        }
    }

    /**
     * 线路chcek完成 刷新网络层
     */
    @Subscribe(tags = {@Tag(ConstantValue.START_REQUEST)})
    public void startRequest(String type) {
        if (mHomePresenter != null) {
            mHomePresenter.onDestory();
        }
        mHomePresenter = new HomePresenter(getActivity(), this);
        mHomePresenter.gameList();
        mHomePresenter.homePage();
        mHomePresenter.downloadApps();

        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
            loginLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
        }

    }
}
