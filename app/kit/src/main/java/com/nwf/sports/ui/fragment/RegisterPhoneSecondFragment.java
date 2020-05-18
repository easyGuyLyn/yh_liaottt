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
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.presenter.RegisterPresenter;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.LimitInputTextWatcher;
import com.nwf.sports.utils.Ticker;
import com.nwf.sports.utils.data.DataCenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;

/**
 * <p>类描述： 手机号注册二级界面
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class RegisterPhoneSecondFragment extends BaseFragment implements SendSmsCodeView, CommonView {

    SwitchoverListener mSwitchoverListener;

    String phone = "";
    @BindView(R.id.ed_verification_code)
    EditText edVerificationCode;
    @BindView(R.id.bt_verification_code)
    TextView btVerificationCode;
    @BindView(R.id.tv_n_login_phone_auth_code_error)
    TextView tvNLoginPhoneAuthCodeError;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ed_referral)
    EditText edReferral;
    private SendSmsCodePresenter mSendSmsCodePresenter;       //验证码
    private RegisterPresenter mRegisterPresenter;  //注册
    private Ticker ticker = new Ticker(); //倒计时

    public static RegisterPhoneSecondFragment newInstance(SwitchoverListener switchoverListener) {
        RegisterPhoneSecondFragment fragment = new RegisterPhoneSecondFragment();
        fragment.setSwitchoverListener(switchoverListener);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    public void setSwitchoverListener(SwitchoverListener switchoverListener) {
        this.mSwitchoverListener = switchoverListener;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_register_phone_second;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mSendSmsCodePresenter = new SendSmsCodePresenter(getActivity(), this);
        mRegisterPresenter = new RegisterPresenter(getActivity(), this);
        edVerificationCode.addTextChangedListener(authWatcher);
        edReferral.addTextChangedListener(new LimitInputTextWatcher(edReferral, LimitInputTextWatcher.LETTER_FIGURE));
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    /**
     * 接收手机号
     */
    @Subscribe(tags = {@Tag(ConstantValue.REGISTER_PHONE)})
    public void receptionPhone(String s) {
        phone = s;
        if (!TextUtils.isEmpty(phone)) {
            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone);
            map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_REGISTER);
            map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
            mSendSmsCodePresenter.sendSmsCodePhone(map);
        } else {
            showMessage("系统异常，请重试");
            mSwitchoverListener.OnSwitchoverListener("RegisterPhoneFragment");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            edVerificationCode.setText("");
            edReferral.setText("");
            tvRegister.setEnabled(false);
            btVerificationCode.setEnabled(false);
        } else {
            phone = "";
        }
    }

    @OnClick({R.id.bt_verification_code, R.id.tv_not_code, R.id.tv_register})
    public void onViewClicked(View view) {
        Map<String, String> map = null;
        switch (view.getId()) {
            case R.id.bt_verification_code:
                map = new HashMap<>();
                map.put("mobile", phone);
                map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_REGISTER);
                map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                mSendSmsCodePresenter.sendSmsCodePhone(map);
                break;
            case R.id.tv_not_code:
                ActivityUtil.skipToService(getActivity());
                break;
            case R.id.tv_register:
                String trim = edVerificationCode.getText().toString().trim();
                map = new HashMap<>();
                if (referralreferral()) {
                    map.put("refcode", edReferral.getText().toString());
                }
                String edReferralString = edReferral.getText().toString().trim();
                if (!TextUtils.isEmpty(edReferralString)) {
                    if (referralreferral()) {
                        map.put("refcode", edReferral.getText().toString());
                    } else {
                        showMessage("请输入4-8位推荐码");
                        return;
                    }
                } else {
                    map.put("refcode", "");
                }
                map.put("mobile", phone);
                map.put("smsCode", trim);
                map.put("registerType", "3"); //0 office注册, 1 PC ,2 H5 ,3 android ,4 ios

                map.put("clientId", ChatManagerHolder.gChatManager.getClientId());
                map.put("platform", "2");
                mRegisterPresenter.fastRegister(map);
                break;
        }
    }

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

    /**
     * 校验是否可以注册
     */
    private void onVeifyCanLogin() {
        String trim = edVerificationCode.getText().toString().trim();
        if (trim.length() == Constant.CODE_LENGTH) {
            tvRegister.setEnabled(true);
        } else {
            tvRegister.setEnabled(false);
        }
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
        btVerificationCode.setEnabled(true);
        showMessage(errmsg);
    }

    @Override
    public void setSendSmsCodeComplete() {
        onSendAuthCode();
    }

    /**
     * 倒计时
     */
    public void onSendAuthCode() {
        ticker.stop();
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                String template = getString(R.string.str_wait);
                btVerificationCode.setText(String.format(template, String.valueOf(left)));
            }

            @Override
            public void onEnd() {
                btVerificationCode.setEnabled(true);
                btVerificationCode.setText(R.string.str_resend_smscode);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
        btVerificationCode.setEnabled(false);
    }


    @Override
    public void setData(Object data) {
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
            localUserInfo.setSportUserId(loginResult.getSportUserId());
            localUserInfo.setSportToken(loginResult.getSportToken());
            DataCenter.getInstance().setUserInfoBean(localUserInfo);
            showMessage("注册成功");

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

    /**
     * 检验推荐码是否合规
     *
     * @return
     */
    private boolean referralreferral() {
        if (edReferral.getText().toString().length() >= 4) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (ticker != null) {
            ticker.stop();
        }
        if (mRegisterPresenter != null) {
            mRegisterPresenter.onDestory();
        }
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
    }
}
