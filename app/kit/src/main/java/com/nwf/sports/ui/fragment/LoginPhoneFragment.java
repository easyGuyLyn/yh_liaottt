package com.nwf.sports.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.RegexUtils;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.presenter.LoginPresenter;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.CheckPhoneView;
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.Ticker;
import com.nwf.sports.utils.data.DataCenter;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;

/**
 * <p>类描述： 手机号码登录
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LoginPhoneFragment extends BaseFragment implements CheckPhoneView, SendSmsCodeView, CommonView {

    SwitchoverListener mSwitchoverListener;

    @BindView(R.id.bt_verification_code)
    TextView btVerificationCode;
    @BindView(R.id.ed_phone)
    EditText mEdPhone;
    @BindView(R.id.ed_verification_code)
    EditText mEdVerificationCode;
    @BindView(R.id.tv_login_phone_error_auth)
    TextView tvLoginPhoneAuthError;
    @BindView(R.id.tv_n_login_phone_auth_code_error)
    TextView tvNLoginPhoneAuthCodeError;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_go_register)
    public TextView mTvGoRegister;

    private String phone;
    private String formatPhone;

    //重发验证码是否可点击
    private boolean isResendCode = true;

    private LoginPresenter mLoginPresenter;                   //登录
    private SendSmsCodePresenter mSendSmsCodePresenter;       //验证码
    private PhonePresenter mPhonePresenter;                   //检查手机号
    private Ticker ticker = new Ticker(); //倒计时

    public static LoginPhoneFragment newInstance(String phone) {
        LoginPhoneFragment fragment = new LoginPhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValue.ARG_PARAM1, phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone = getArguments().getString(ConstantValue.ARG_PARAM1);
        }
    }

    public void setSwitchoverListener(SwitchoverListener switchoverListener) {
        this.mSwitchoverListener = switchoverListener;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_login_phone;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mPhonePresenter = new PhonePresenter(getActivity(), this);
        mLoginPresenter = new LoginPresenter(getActivity(), this);
        mSendSmsCodePresenter = new SendSmsCodePresenter(getActivity(), this);
        mEdPhone.addTextChangedListener(phoneWatcher);
        mEdVerificationCode.addTextChangedListener(authWatcher);
        if (!TextUtils.isEmpty(phone)) {
            LogUtils.e("未登录的手机号码，" + phone);
            String input = phone;
            input = input.substring(0, 3) + " " + input.substring(3);
            input = input.substring(0, 8) + " " + input.substring(8);
            mEdPhone.setText(input);
            mEdPhone.setSelection(input.length());
            if (onCheckPhone()) {
                onCheckCode(true);
            }
        }

        TextViewLinkUtil.textLinkOnClick(getActivity(), "还没有账号？", "立即注册",
                "", "#100000", false, mTvGoRegister,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
//                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
//                        intent.putExtra(ConstantValue.ARG_PARAM1, "RegisterPhoneFragment");
//                        startActivity(intent);
                        RxBus.get().post(ConstantValue.SKIP_REGISTER, "RegisterPhoneFragment");
                    }
                });
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.bt_verification_code, R.id.tv_not_code, R.id.tv_switchover_account, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_verification_code:
                if (onCheckPhone() && isResendCode) {
                    //校验手机号码是否已经注册过，如果已经注册过，就发送验证码，如果没有注册过，显示没有注册，跳转到注册页面
                    mPhonePresenter.checkPhone(phone);

                    Map<String, String> map = new HashMap<>();
                    map.put("mobile", phone);
                    map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_LOGIN);
                    map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                    mSendSmsCodePresenter.sendSmsCodePhone(map);

                    mEdVerificationCode.requestFocus();
                    onCheckCode(true);

                }
                break;
            case R.id.tv_not_code:
                break;
            case R.id.tv_switchover_account:
                if (mSwitchoverListener != null) {
                    mSwitchoverListener.OnSwitchoverListener("LoginAccouutFragment");
                }
                break;
            case R.id.tv_register:
                if (DoubleClickHelper.getNewInstance().isDoubleClick()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("loginType", "2"); //登录类型 1 为account 2 为moblie ,
                    map.put("mobile", phone);
                    map.put("smsCode", mEdVerificationCode.getText().toString().trim());

                    map.put("clientId", ChatManagerHolder.gChatManager.getClientId());
                    map.put("platform", "2");
                    mLoginPresenter.fastLogin(map);
                }
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
                    //ivLoginPhoneAuth.setVisibility(View.VISIBLE);
                    onCheckCode(true);
                } else {
                    onCheckCode(false);
                }
            } else {
                tvLoginPhoneAuthError.setVisibility(View.INVISIBLE);
                onCheckCode(false);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            onVeifyCanLogin();
        }
    };

    /**
     * 验证码
     */
    TextWatcher authWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            onVeifyCanLogin();
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
            tvLoginPhoneAuthError.setText(R.string.n_register_phone_err);
            tvLoginPhoneAuthError.setVisibility(View.VISIBLE);
        } else {
            tvLoginPhoneAuthError.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    //校验重发验证码是否可用
    private void onCheckCode(boolean normal) {
        if (normal) {
            btVerificationCode.setEnabled(true);
        } else {
            btVerificationCode.setEnabled(false);
        }
    }

    /**
     * 校验是否可以登录
     */
    private void onVeifyCanLogin() {
        String trim = mEdVerificationCode.getText().toString().trim();
        if (!onCheckPhone()) {
            return;
        }
        if (trim.length() == Constant.CODE_LENGTH) {
            tvRegister.setEnabled(true);
        } else {
            tvRegister.setEnabled(false);
        }
    }


    private void onRegisterPhoneListener() {
        TextViewLinkUtil.textLinkOnClick(getContext(), getResources().getString(R.string.n_register_account_no_inuse) + " ",
                getResources().getString(R.string.n_register_account_no_inuse_one), "",
                "#100000", false, tvLoginPhoneAuthError,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
                        if (TextUtils.isEmpty(phone)) {
                            RxBus.get().post(ConstantValue.SKIP_REGISTER, "RegisterPhoneFragment");
                        }
                    }
                });
    }

    /**
     * 倒计时
     */
    public void onSendAuthCode() {
        ticker.stop();
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                isResendCode = false;
                String template = getString(R.string.str_wait);
                btVerificationCode.setText(String.format(template, String.valueOf(left)));
            }

            @Override
            public void onEnd() {
                isResendCode = true;
                btVerificationCode.setEnabled(true);
                btVerificationCode.setText(R.string.str_resend_smscode);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
        showMessage(errmsg);
        isResendCode = true;
    }

    @Override
    public void setSendSmsCodeComplete() {
        onSendAuthCode();
    }

    @Override
    public void setData(Object data) {
        DataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();
        if (data instanceof LoginResult) {
            if (Check.isNull(data)) {
                return;
            }
            LoginResult loginResult = (LoginResult) data;
            UserInfoBean localUserInfo = new UserInfoBean();
            localUserInfo.setRealLogin(true);
            localUserInfo.setUsername(loginResult.getUserName());
            localUserInfo.setPassword(loginResult.getPassword());
            localUserInfo.setToken(loginResult.getToken());
            localUserInfo.setLevelNum(loginResult.getLevelNum());
            localUserInfo.setLocalBalance(loginResult.getLocalBalance());
            localUserInfo.setTotalBalance(loginResult.getTotalBalance());
            localUserInfo.setGameBalance(loginResult.getGameBalance());
            localUserInfo.setDepositLevel(loginResult.getDepositLevel());
            localUserInfo.setPhone(loginResult.getPhone());
            localUserInfo.setRealName(loginResult.getRealName());
            localUserInfo.setRealName(loginResult.getRealName());
            localUserInfo.setSportUserId(loginResult.getSportUserId());
            localUserInfo.setSportToken(loginResult.getSportToken());
            DataCenter.getInstance().setUserInfoBean(localUserInfo);


            //需要注意token跟clientId是强依赖的，一定要调用getClientId获取到clientId，然后用这个clientId获取token，这样connect才能成功，如果随便使用一个clientId获取到的token将无法链接成功。
            ChatManagerHolder.gChatManager.connect(loginResult.getSportUserId(), loginResult.getSportToken());
            SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
            sp.edit()
                    .putString("id", loginResult.getSportUserId())
                    .putString("token", loginResult.getSportToken())
                    .apply();

            RxBus.get().post(ConstantValue.EVENT_TYPE_LOGIN, "login");
            DialogFramentManager.getInstance().clearDialog();
        }
    }

    @Override
    public void onDetach() {
        if (ticker != null) {
            ticker.stop();
        }
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
        if (mLoginPresenter != null) {
            mLoginPresenter.onDestory();
        }
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
        super.onDetach();
    }

    @Override
    public void checkResultSucceed(boolean checkResult, String phone) {
        if (!checkResult) {
            tvLoginPhoneAuthError.setVisibility(View.INVISIBLE);

            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone);
            map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_LOGIN);
            map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
            mSendSmsCodePresenter.sendSmsCodePhone(map);

            mEdVerificationCode.requestFocus();
            onCheckCode(true);
        } else {
            tvLoginPhoneAuthError.setVisibility(View.VISIBLE);
            onRegisterPhoneListener();
        }
    }
}
