package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 注册界面
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class RegisterDialogFragment extends BaseDialogFragment implements SwitchoverListener {

    @BindView(R.id.view_pager)
    public CustomViewPager mViewPager;

    @BindView(R.id.im_back)
    public ImageView mImBack;

    @BindView(R.id.im_close)
    public ImageView mImClose;

    int mViewPagerNum = 0;
    String type = "";

    public static RegisterDialogFragment getInstance(String type) {
        RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValue.ARG_PARAM1, type);
        registerDialogFragment.setArguments(bundle);
        return registerDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ConstantValue.ARG_PARAM1);
        }
    }

    @Override
    protected int getViewId() {
        intScreenWProportion = 1;
        intScreenHProportion = 1;
        AnimationsStyle = -1;
        return R.layout.dialogfragment_register;
    }

    @Override
    protected void initViews(View view) {
        mViewPager.setOffscreenPageLimit(4);
        FragmentManager fragmentManager = getChildFragmentManager();
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.im_close, R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                dismissAllowingStateLoss();
                break;
            case R.id.im_back:
                switch (mViewPagerNum) {
                    case 1:
                        OnSwitchoverListener("RegisterPhoneFragment");
                        break;
                    case 3:
                        OnSwitchoverListener("RegisterAccountFragment");
                        break;
                }
                break;
        }
    }

    public void SwitchoverImage(boolean isSwitchover) {
        if (isSwitchover) {
            mImBack.setVisibility(View.VISIBLE);
            mImClose.setVisibility(View.GONE);
        } else {
            mImBack.setVisibility(View.GONE);
            mImClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnSwitchoverListener(String type) {
        KeyboardUtil.toggleKeyBoard(getActivity());
        if (type.equals("RegisterAccountSecondFragment")) {//账号注册二级页面
            mViewPager.setCurrentItem(3);
            mViewPagerNum = 3;
            SwitchoverImage(true);
        } else if (type.equals("RegisterAccountFragment")) {//账号注册
            mViewPager.setCurrentItem(2);
            mViewPagerNum = 2;
            SwitchoverImage(false);
        } else if (type.equals("RegisterPhoneSecondFragment")) {//手机号二级页面
            mViewPagerNum = 1;
            mViewPager.setCurrentItem(1);
            SwitchoverImage(true);
        } else {
            mViewPagerNum = 0;
            mViewPager.setCurrentItem(0);
            SwitchoverImage(false);
        }
    }
}
