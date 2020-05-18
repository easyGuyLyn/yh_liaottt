package com.nwf.sports.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.RegexUtils;
import com.dawoo.coretool.util.activity.KeyboardUtil;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.mvp.model.SkipLoginBean;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.view.CheckPhoneView;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 手机号注册
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class RegisterPhoneFragment extends BaseFragment implements CheckPhoneView {
    SwitchoverListener mSwitchoverListener;

    @BindView(R.id.tv_login)
    public TextView mTvLogin;
    @BindView(R.id.ed_phone)
    EditText mEdPhone;
    @BindView(R.id.tv_register_phone_error_auth)
    TextView tvRegisterPhoneAuthError;
    @BindView(R.id.skip_second)
    TextView skipSecond;

    private String phone;
    private String formatPhone;


    private PhonePresenter mPhonePresenter;

    public static RegisterPhoneFragment newInstance(SwitchoverListener switchoverListener) {
        RegisterPhoneFragment fragment = new RegisterPhoneFragment();
        fragment.setSwitchoverListener(switchoverListener);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setSwitchoverListener(SwitchoverListener switchoverListener) {
        this.mSwitchoverListener = switchoverListener;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_register_phone;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mPhonePresenter = new PhonePresenter(getActivity(), this);
        TextViewLinkUtil.textLinkOnClick(getContext(), "已有账号？", "立即登录",
                "", "#100000", false, mTvLogin,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
//                        ((RegisterActivity)getActivity()).finish();
                        SkipLoginBean skipLoginBean = new SkipLoginBean("LoginPhoneFragment", "");
                        RxBus.get().post(ConstantValue.SKIP_LOGIN, skipLoginBean);
                    }
                });
        mEdPhone.addTextChangedListener(phoneWatcher);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.skip_account, R.id.skip_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.skip_account:
                if (mSwitchoverListener != null) {
                    mSwitchoverListener.OnSwitchoverListener("RegisterAccountFragment");
                }
                break;
            case R.id.skip_second:
                mPhonePresenter.checkPhone(phone);
                break;
        }
    }


    /**
     * 检查手机号
     */
    TextWatcher phoneWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //用户输入的字符串
            String input = s.toString();
            int length = input.length();

            //实现电话号码数字分组 344
            if (length == 4) {
                if (input.substring(3).equals(new String(" "))) {
                    input = input.substring(0, 3);
                    mEdPhone.setText(input);
                    mEdPhone.setSelection(input.length());
                } else { // +  130 2
                    input = input.substring(0, 3) + " " + input.substring(3);
                    mEdPhone.setText(input);
                    mEdPhone.setSelection(input.length());
                }
            } else if (length == 9) {
                if (input.substring(8).equals(new String(" "))) {
                    input = input.substring(0, 8);
                    mEdPhone.setText(input);
                    mEdPhone.setSelection(input.length());
                } else {// +
                    input = input.substring(0, 8) + " " + input.substring(8);
                    mEdPhone.setText(input);
                    mEdPhone.setSelection(input.length());
                }
            }
            //校验手机号码的有效性
            if (length >= 13) {
                if (onCheckPhone()) {
                    skipSecond.setEnabled(true);
                } else {
                    skipSecond.setEnabled(false);
                }
            } else {
                tvRegisterPhoneAuthError.setVisibility(View.INVISIBLE);
                skipSecond.setEnabled(false);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
//            onVeifyCanLogin();
        }
    };

    //校验手机号码
    private boolean onCheckPhone() {
        formatPhone = mEdPhone.getText().toString().trim();
        if (formatPhone.length() < 13) {
            return false;
        }
        phone = formatPhone.replace(" ", "");
        if (!RegexUtils.isMobilePCExact(phone)) {
            tvRegisterPhoneAuthError.setText(R.string.n_register_phone_err);
            tvRegisterPhoneAuthError.setVisibility(View.VISIBLE);
        } else {
            tvRegisterPhoneAuthError.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    private void onRegisterPhoneListener() {
        tvRegisterPhoneAuthError.setVisibility(View.VISIBLE);
        TextViewLinkUtil.textLinkOnClick(getContext(), getResources().getString(R.string.n_register_account_inuse) + " ", "去登录>>", "", "#4A4A4A", false,
                tvRegisterPhoneAuthError, new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
                        SkipLoginBean skipLoginBean = new SkipLoginBean("LoginPhoneFragment", phone);
                        RxBus.get().post(ConstantValue.SKIP_LOGIN, skipLoginBean);
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
    }

    @Override
    public void checkResultSucceed(boolean checkResult, String phone) {
        //检查是否可用，如果已经使用了，显示此账号已经注册了，跳转到登录界面
        if (!checkResult) {
            onRegisterPhoneListener();
            skipSecond.setEnabled(true);
        } else {
            if (mSwitchoverListener != null) {
                KeyboardUtil.toggleKeyBoard(getActivity());
                mSwitchoverListener.OnSwitchoverListener("RegisterPhoneSecondFragment");
                RxBus.get().post(ConstantValue.REGISTER_PHONE, phone);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        tvRegisterPhoneAuthError.setText(R.string.n_register_phone_err);
        tvRegisterPhoneAuthError.setVisibility(View.VISIBLE);
    }
}
