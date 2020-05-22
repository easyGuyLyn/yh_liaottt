package com.nwf.sports.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.presenter.LoginPresenter;
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.HintCommonDialogFragment;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.data.IMDataCenter;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;

/**
 * <p>类描述： 账号密码登录
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LoginAccouutFragment extends BaseFragment implements CommonView {

    SwitchoverListener mSwitchoverListener;
    @BindView(R.id.ed_account)
    EditText edAccount;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.iv_login_pwd_show)
    ImageView ivLoginPwdShow;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_n_login_account_password_error)
    TextView tvNLoginAccountPasswordError;
    @BindView(R.id.tv_go_register)
    public TextView mTvGoRegister;

    LoginPresenter mLoginPresenter;                   //登录
    HintCommonDialogFragment hintCommonDialogFragment = null;

    public static LoginAccouutFragment newInstance() {
        LoginAccouutFragment fragment = new LoginAccouutFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setSwitchoverListener(SwitchoverListener switchoverListener) {
        this.mSwitchoverListener = switchoverListener;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_login_account;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter(getActivity(), this);
        edAccount.addTextChangedListener(accountWatcher);
        edPassword.addTextChangedListener(pwdWatcher);

        TextViewLinkUtil.textLinkOnClick(getActivity(), "还没有账号？", "立即注册",
                "", "#100000", false, mTvGoRegister,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
//                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
//                        intent.putExtra(ConstantValue.ARG_PARAM1, "RegisterAccountFragment");
//                        startActivity(intent);
                        RxBus.get().post(ConstantValue.SKIP_REGISTER, "RegisterAccountFragment");
                    }
                });

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.iv_login_pwd_show, R.id.tv_question, R.id.tv_switchover_account, R.id.tv_login})
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
            case R.id.tv_question:
                break;
            case R.id.tv_switchover_account:
                if (mSwitchoverListener != null) {
                    mSwitchoverListener.OnSwitchoverListener("LoginPhoneFragment");
                }
                break;
            case R.id.tv_login:
                if (tvLogin.isClickable() && DoubleClickHelper.getNewInstance().isDoubleClick()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("loginType", "1"); //登录类型 1 为account 2 为moblie
                    map.put("password", edPassword.getText().toString());
                    map.put("userName", Constant.PRODUCT_INITIAL + edAccount.getText().toString());

                    map.put("mobile", Constant.PRODUCT_INITIAL + edAccount.getText().toString());
                    map.put("code", edPassword.getText().toString());
                    map.put("clientId", ChatManagerHolder.gChatManager.getClientId());
                    map.put("platform", "2");
                    mLoginPresenter.loginByUsername(map);
                }
                break;
        }
    }

    /**
     * 账号监听
     */
    TextWatcher accountWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = s.toString().length();
            onVerifyUnameAndPwd();

            if (tvNLoginAccountPasswordError.isShown()) {
                tvNLoginAccountPasswordError.setVisibility(View.INVISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 密码监听
     */
    TextWatcher pwdWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onVerifyUnameAndPwd();
            if (tvNLoginAccountPasswordError.isShown()) {
                tvNLoginAccountPasswordError.setVisibility(View.INVISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 校验账号和密码
     *
     * @return
     */
    private void onVerifyUnameAndPwd() {
        boolean uname = onVeifyCanLogin();
        boolean pwd = onVeifyPwd();
        if (uname & pwd) {
            tvLogin.setEnabled(true);
        } else {
            tvLogin.setEnabled(false);
        }
    }

    /**
     * 校验账号
     *
     * @return
     */
    private boolean onVeifyCanLogin() {
        if (Check.isNull(edAccount) || Check.isNull(edAccount.getText())) {
            return false;
        }
        String uname = edAccount.getText().toString().trim();
        if (uname.length() >= 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验密码
     *
     * @return
     */
    private boolean onVeifyPwd() {
        if (Check.isNull(edPassword) || Check.isNull(edPassword.getText())) {
            return false;
        }
        String pwd = edPassword.getText().toString().trim();
        if (pwd.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setData(Object data) {
        IMDataCenter.getInstance().getUserInfoCenter().clearUserInfoBean();

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
            IMDataCenter.getInstance().setUserInfoBean(localUserInfo);

            //需要注意token跟clientId是强依赖的，一定要调用getClientId获取到clientId，然后用这个clientId获取token，这样connect才能成功，如果随便使用一个clientId获取到的token将无法链接成功。
            boolean connect = ChatManagerHolder.gChatManager.connect(loginResult.getSportUserId(), loginResult.getSportToken());
            Log.e("TTAATTTAA","/////"+connect+"///");
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
    public void showMessage(String message) {
        super.showMessage(message);
        tvNLoginAccountPasswordError.setVisibility(View.VISIBLE);
        //输入错多次之后提示对话框
        if (message.contentEquals(Constant.PRODUCT_PWD_MUL_ERROR)) {
            hintCommonDialogFragment = new HintCommonDialogFragment()
                    .setTvTitle(getString(R.string.str_hint))
                    .setTvContent(getString(R.string.n_login_account_pwd_mul_error))
                    .setOnLeftButtonListener(null, getString(R.string.n_login_i_know))
                    .setOnRightButtonListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hintCommonDialogFragment.dismissAllowingStateLoss();
                        }
                    }, getString(R.string.n_contact_service));
            DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), hintCommonDialogFragment);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mLoginPresenter != null) {
            mLoginPresenter.onDestory();
        }
    }
}
