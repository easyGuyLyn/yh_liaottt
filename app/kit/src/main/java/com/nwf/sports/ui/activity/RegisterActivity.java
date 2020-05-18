package com.nwf.sports.ui.activity;

import android.view.View;

import com.dawoo.coretool.util.activity.KeyboardUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.ui.fragment.RegisterAccountFragment;
import com.nwf.sports.ui.fragment.RegisterAccountSecondFragment;
import com.nwf.sports.ui.fragment.RegisterPhoneFragment;
import com.nwf.sports.ui.fragment.RegisterPhoneSecondFragment;
import com.nwf.sports.ui.views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-09
 * <p>修改人：Simon
 * <p>修改时间：2020-01-09
 * <p>修改备注：
 **/
public class RegisterActivity extends BaseActivity implements SwitchoverListener {

    @BindView(R.id.view_pager)
    public CustomViewPager mViewPager;

    int mViewPagerNum = 0;
    String type = "";

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
        }
        mViewPager.setOffscreenPageLimit(4);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(RegisterPhoneFragment.newInstance(this));
        fragmentList.add(RegisterPhoneSecondFragment.newInstance(this));
        fragmentList.add(RegisterAccountFragment.newInstance(this));
        fragmentList.add(RegisterAccountSecondFragment.newInstance(this));
        AdapterFragment adpter = new AdapterFragment(fragmentManager, fragmentList);
        mViewPager.setAdapter(adpter);
        OnSwitchoverListener(type);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void OnSwitchoverListener(String type) {
        KeyboardUtil.toggleKeyBoard(this);
        if (type.equals("RegisterAccountSecondFragment")) {//账号注册二级页面
            mViewPager.setCurrentItem(3, false);
            mViewPagerNum = 3;
        } else if (type.equals("RegisterAccountFragment")) {//账号注册
            mViewPager.setCurrentItem(2, false);
            mViewPagerNum = 2;
        } else if (type.equals("RegisterPhoneSecondFragment")) {//手机号二级页面
            mViewPagerNum = 1;
            mViewPager.setCurrentItem(1, false);
        } else {
            mViewPagerNum = 0;
            mViewPager.setCurrentItem(0, false);
        }
    }

    @OnClick({R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                switch (mViewPagerNum) {
                    case 1:
                        OnSwitchoverListener("RegisterPhoneFragment");
                        break;
                    case 3:
                        OnSwitchoverListener("RegisterAccountFragment");
                        break;
                    case 0:
                    case 2:
                        finish();
                        break;
                }
                break;
        }
    }

}
