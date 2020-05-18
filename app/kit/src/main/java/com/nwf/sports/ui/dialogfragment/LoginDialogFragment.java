package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.ui.fragment.LoginAccouutFragment;
import com.nwf.sports.ui.fragment.LoginPhoneFragment;
import com.nwf.sports.ui.views.CustomViewPager;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 登录界面
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LoginDialogFragment extends BaseDialogFragment implements SwitchoverListener {

    @BindView(R.id.view_pager)
    public CustomViewPager mViewPager;
    @BindView(R.id.tv_register)
    public TextView mTvRegister;

    String phone = "";
    String type = "";

    public static LoginDialogFragment getInstance(String phone, String type) {
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValue.ARG_PARAM1, phone);
        bundle.putString(ConstantValue.ARG_PARAM2, type);
        loginDialogFragment.setArguments(bundle);
        return loginDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone = getArguments().getString(ConstantValue.ARG_PARAM1);
            type = getArguments().getString(ConstantValue.ARG_PARAM2);
        }
    }

    @Override
    protected int getViewId() {
        intScreenWProportion = 1;
        intScreenHProportion = 1;
        AnimationsStyle = -1;
        return R.layout.dialogfragment_login;
    }

    @Override
    protected void initViews(View view) {

        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();

        LoginPhoneFragment loginPhoneFragment = LoginPhoneFragment.newInstance(phone);
        loginPhoneFragment.setSwitchoverListener(this);
        fragmentList.add(loginPhoneFragment);

        LoginAccouutFragment loginAccouutFragment = LoginAccouutFragment.newInstance();
        loginAccouutFragment.setSwitchoverListener(this);
        fragmentList.add(loginAccouutFragment);

        AdapterFragment adpter = new AdapterFragment(fragmentManager, fragmentList);
        mViewPager.setAdapter(adpter);

        TextViewLinkUtil.textLinkOnClick(getContext(), "还没有账号？", "立即注册",
                "", "#100000", false, mTvRegister,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
                        if (mViewPager.getCurrentItem() == 1) {
                            RxBus.get().post(ConstantValue.SKIP_REGISTER, "RegisterAccountFragment");
                        } else {
                            RxBus.get().post(ConstantValue.SKIP_REGISTER, "RegisterPhoneFragment");
                        }
                    }
                });
//        if (!TextUtils.isEmpty(type)) {
        OnSwitchoverListener(type);
//        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.transparency, R.id.im_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.transparency:
            case R.id.im_close:
                dismissAllowingStateLoss();
                break;
        }
    }

    @Override
    public void OnSwitchoverListener(String type) {
        if (type.equals("LoginAccouutFragment")) {
            mViewPager.setCurrentItem(1);
        } else {
            mViewPager.setCurrentItem(0);
        }
    }
}
