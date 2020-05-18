package com.nwf.sports.ui.activity;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.AdapterFragment;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.ui.fragment.LoginAccouutFragment;
import com.nwf.sports.ui.fragment.LoginPhoneFragment;
import com.nwf.sports.ui.views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-09
 * <p>修改人：Simon
 * <p>修改时间：2020-01-09
 * <p>修改备注：
 **/
public class LoginActivity extends BaseActivity implements SwitchoverListener {

    @BindView(R.id.view_pager)
    public CustomViewPager mViewPager;
//    @BindView(R.id.tv_register)
//    public TextView mTvRegister;

    String phone = "";
    String type = "";

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        RxBus.get().register(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();

        LoginPhoneFragment loginPhoneFragment = LoginPhoneFragment.newInstance(phone);
        loginPhoneFragment.setSwitchoverListener(this);
        fragmentList.add(loginPhoneFragment);

        LoginAccouutFragment loginAccouutFragment = LoginAccouutFragment.newInstance();
        loginAccouutFragment.setSwitchoverListener(this);
        fragmentList.add(loginAccouutFragment);


        AdapterFragment adpter = new AdapterFragment(fragmentManager, fragmentList);
//        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adpter);
//        OnSwitchoverListener(type);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void OnSwitchoverListener(String type) {
        if (type.equals("LoginAccouutFragment")) {
            mViewPager.setCurrentItem(1);
        } else {
            mViewPager.setCurrentItem(0);
        }
    }

    /**
     * 登录成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGIN)})
    public void loginSucceed(String string) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
