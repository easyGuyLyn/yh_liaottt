package com.nwf.sports.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.LimitInputTextWatcher;
import com.nwf.sports.utils.data.DataCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;

/**
 * <p>类描述： 账号注册二级界面
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class RegisterAccountSecondFragment extends BaseFragment implements CommonView {

    SwitchoverListener mSwitchoverListener;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.iv_login_pwd_show)
    ImageView ivLoginPwdShow;
    @BindView(R.id.tv_account_auth_code_error)
    TextView tvAccountAuthCodeError;
    @BindView(R.id.ed_referral)
    EditText edReferral;
    @BindView(R.id.skip_second)
    TextView skipSecond;

    private RegisterPresenter mRegisterPresenter;  //注册
    String account = "";

    public static RegisterAccountSecondFragment newInstance(SwitchoverListener switchoverListener) {
        RegisterAccountSecondFragment fragment = new RegisterAccountSecondFragment();
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
        return R.layout.fragment_register_account_second;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mRegisterPresenter = new RegisterPresenter(getActivity(), this);
        edPassword.addTextChangedListener(accountWatcher);
        edReferral.addTextChangedListener(new LimitInputTextWatcher(edReferral, LimitInputTextWatcher.LETTER_FIGURE));
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        RxBus.get().unregister(this);
        if (mRegisterPresenter != null) {
            mRegisterPresenter.onDestory();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            skipSecond.setEnabled(false);
            edPassword.setText("");
            edReferral.setText("");
            tvAccountAuthCodeError.setText(R.string.n_register_account_set_pwd_rule);
            tvAccountAuthCodeError.setTextColor(getResources().getColor(R.color.color_4A4A4A));
            ivLoginPwdShow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_password_oppen));
            edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            account = "";
        }
    }


    /**
     * 接收账号
     */
    @Subscribe(tags = {@Tag(ConstantValue.REGISTER_ACCOUNT)})
    public void receptionAccount(String s) {
        account = s;
        if (TextUtils.isEmpty(account)) {
            showMessage("系统异常，请重试");
            mSwitchoverListener.OnSwitchoverListener("RegisterAccountFragment");
        }
    }

    @OnClick({R.id.iv_login_pwd_show, R.id.skip_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_pwd_show:
                if (edPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivLoginPwdShow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_password_close));
                } else {
                    ivLoginPwdShow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_password_oppen));
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                edPassword.setSelection(edPassword.getText().length());
                break;
            case R.id.skip_second:
                String trim = edReferral.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    showMessage("请输入4-8位推荐码");
                    return;
                }
                if (onVeifyCanRegister(edPassword.getText().toString().trim()) && DoubleClickHelper.getNewInstance().isDoubleClick()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("registerType", Constant.REGISTERTYPE);
                    map.put("userName", Constant.PRODUCT_INITIAL + account);
                    map.put("password", edPassword.getText().toString().trim());

                    map.put("clientId", ChatManagerHolder.gChatManager.getClientId());
                    map.put("platform", "2");
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
                    mRegisterPresenter.registerAccountPwd(map);
                } else {
                    showMessage("请输入正确的密码");
                }
                break;
        }
    }

    @Override
    public void setData(Object data) {
        if (data instanceof LoginResult) {
            if (Check.isNull(data)) {
                return;
            }
            DataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();
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

    TextWatcher accountWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onVeifyCanRegister(s.toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 校验注册按钮是否可用
     *
     * @param inputPwd
     */
    private boolean onVeifyCanRegister(String inputPwd) {
        if (inputPwd.length() >= 6) {
            Pattern pattern = Pattern.compile("[a-z0-9]{6,16}");
            boolean isok = pattern.matcher(inputPwd).matches();
            if (isok) {
                tvAccountAuthCodeError.setText(R.string.n_register_account_set_pwd_rule);
                tvAccountAuthCodeError.setTextColor(getResources().getColor(R.color.color_4A4A4A));
                skipSecond.setEnabled(true);
                return true;
            } else {
                tvAccountAuthCodeError.setText(R.string.str_err_password);
                tvAccountAuthCodeError.setTextColor(getResources().getColor(R.color.color_FF3300));
                skipSecond.setEnabled(false);
                return false;
            }

        } else {
            skipSecond.setEnabled(false);
            return false;
        }
    }


    /**
     * 检验推荐码是否合规
     *
     * @return
     */
    private boolean referralreferral() {
        if (edReferral.getText().toString().length() >= 4 && edReferral.getText().toString().length() < 9) {
            return true;
        } else {
            return false;
        }
    }
}
