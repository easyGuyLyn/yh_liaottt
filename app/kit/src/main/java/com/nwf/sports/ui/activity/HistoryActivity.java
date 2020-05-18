package com.nwf.sports.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.ui.fragment.DepositHistoryFragment;
import com.nwf.sports.ui.fragment.WithdrawalHistoryFragment;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.historyutil.HistoryServiceEnum;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * <p>类描述： 账户记录
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public class HistoryActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private int mViewPagerNum = 0;

    private String[] title = {"存款", "取款"};

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void initViews() {
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DepositHistoryFragment.newInstance(HistoryServiceEnum.DEPOSIT));
        fragmentList.add(WithdrawalHistoryFragment.newInstance(HistoryServiceEnum.WITHDRAW));
        AdapterFragment adapterFragment = new AdapterFragment(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapterFragment);
        // 预加载数量
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(this);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.color_white), ContextCompat.getColor(this, R.color.color_1ECE94));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.color_1ECE94));

        for (int i = 0; i < adapterFragment.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.activity_history_tab_item);//给每一个tab设置view
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            textView.setText(title[i]);//设置tab上的文字
        }

        mViewPagerNum = 0;
    }

    @Override
    protected void initData() {

    }

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

}
