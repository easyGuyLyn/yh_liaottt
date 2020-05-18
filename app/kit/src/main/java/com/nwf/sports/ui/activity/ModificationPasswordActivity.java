package com.nwf.sports.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.ModificationPasswordPresenter;
import com.nwf.sports.mvp.view.ModificationPasswordView;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.PNCheck;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 修改密码
 * <p>创建人：Simon
 * <p>创建时间：2019-04-29
 * <p>修改人：Simon
 * <p>修改时间：2019-04-29
 * <p>修改备注：
 **/
public class ModificationPasswordActivity extends BaseActivity implements ModificationPasswordView {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.ed_original_password)
    EditText edOriginalPassword;
    @BindView(R.id.ed_new_password)
    EditText edNewPassword;
    @BindView(R.id.ed_verify_new_password)
    EditText edVerifyNewPassword;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.tv_account_auth_code_error)
    TextView tvAccountAuthCodeError;
    @BindView(R.id.tv_err_password)
    TextView tvErrPassword;

    ModificationPasswordPresenter mModificationPasswordPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_modification_password);
    }

    @Override
    protected void initViews() {
        mModificationPasswordPresenter = new ModificationPasswordPresenter(this, this);
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edOriginalPassword.addTextChangedListener(mTextWatcher);
        edNewPassword.addTextChangedListener(mTextWatcher);
        edVerifyNewPassword.addTextChangedListener(mTextWatcher);

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            onVerifyUnameAndPwd();
        }
    };

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        String originalPassword = edOriginalPassword.getText().toString();
        String newPassword = edNewPassword.getText().toString();
        String verifyNewPassword = edVerifyNewPassword.getText().toString();
        if (originalPassword.equals(newPassword)) {
            showMessage("原密码与新密码不能相同");
            return;
        }
        if (!newPassword.equals(verifyNewPassword)) {
            showMessage("请确认新密码与确认密码是否相同");
            return;
        }
        mModificationPasswordPresenter.modifyPwd(originalPassword, newPassword);
    }


    /**
     * 原始密码
     *
     * @return
     */
    private boolean checkOriginalPassword() {
        String pwd = edOriginalPassword.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            tvAccountAuthCodeError.setVisibility(View.INVISIBLE);
            return false;
        }
        PNCheck.CheckResult checkResult = PNCheck.checkNWFPassword(pwd, R.string.str_verifyloginpwd_pwd_empty, R.string.str_err_password);
        if (checkResult.isResultOk) {
            tvAccountAuthCodeError.setVisibility(View.INVISIBLE);
        } else {
            tvAccountAuthCodeError.setText(checkResult.msg);
            tvAccountAuthCodeError.setVisibility(View.VISIBLE);
        }
        return checkResult.isResultOk;
    }

    /**
     * 新密码
     */
    public boolean checkNewPassword() {
        String pwd = edNewPassword.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            tvErrPassword.setText("6~16位字母或数字组合");
            tvErrPassword.setTextColor(getResources().getColor(R.color.color_4A4A4A));
            return false;
        }
        PNCheck.CheckResult checkResult = PNCheck.checkNWFPassword(pwd, R.string.str_pwd_empty, R.string.str_err_password);
        if (checkResult.isResultOk) {
            tvErrPassword.setText("6~16位字母或数字组合");
            tvErrPassword.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        } else {
            tvErrPassword.setText(checkResult.msg);
            tvErrPassword.setTextColor(getResources().getColor(R.color.color_FF3300));
        }
        return checkResult.isResultOk;
    }

    /**
     * 确认新密码
     */
//    public boolean checkVerifyNewPassword() {
//        String pwd = edVerifyNewPassword.getText().toString();
//        if (TextUtils.isEmpty(pwd)) {
//            return false;
//        }
//        PNCheck.CheckResult checkResult = PNCheck.checkNWFPassword(pwd, R.string.str_pwd_empty, R.string.str_err_password);
//        if (checkResult.isResultOk) {
//            tvErrPassword.setText("6~16位字母或数字组合");
//            tvErrPassword.setTextColor(getResources().getColor(R.color.color_4A4A4A));
//        } else {
//            tvErrPassword.setText(checkResult.msg);
//            tvErrPassword.setTextColor(getResources().getColor(R.color.color_FF3300));
//        }
//        return checkResult.isResultOk;
//    }


    /**
     * 校验确认按钮
     *
     * @return
     */
    private void onVerifyUnameAndPwd() {
        boolean checkOriginalPassword = checkOriginalPassword();
        boolean checkNewPassword = checkNewPassword();
        String pwd = edVerifyNewPassword.getText().toString();
//        boolean checkVerifyNewPassword = checkVerifyNewPassword();
        if (checkOriginalPassword && checkNewPassword && !TextUtils.isEmpty(pwd)) {
            btnOk.setEnabled(true);
        } else {
            btnOk.setEnabled(false);
        }
    }

    @Override
    public void modifyPwdSucceed() {
        showMessage("修改成功");
        finish();
    }

    @Override
    public void modifyPwdDefeated() {
        tvAccountAuthCodeError.setVisibility(View.VISIBLE);
    }
}
