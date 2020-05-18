package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.RegexUtils;
import com.dawoo.coretool.util.ResHelper;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.view.CheckPhoneView;
import com.nwf.sports.ui.views.PNTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 绑定手机号
 * <p>创建人：Simon
 * <p>创建时间：2019-04-16
 * <p>修改人：Simon
 * <p>修改时间：2019-04-16
 * <p>修改备注：
 **/
public class BindPhoneActivity extends BaseActivity implements CheckPhoneView {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.tv_phone_error_auth)
    TextView tvPhoneErrorAuth;

    String phone = "";
    PhonePresenter mPhonePresenter = null; //获取手机号
    boolean isModification = false; //是否是修改手机号码

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_bind_phone);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            isModification = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM1, false);
        }
        mPhonePresenter = new PhonePresenter(this, this);
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (isModification) {
            vTitle.setTitle(ResHelper.getString(R.string.str_title_modifyphone_setting));
        } else {
            vTitle.setTitle(ResHelper.getString(R.string.str_title_bind_phone));
        }
        edPhone.addTextChangedListener(phoneWatcher);
    }

    @Override
    protected void initData() {

    }

    /**
     * 检查手机号
     */
    TextWatcher phoneWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            if (s.length() >= 11) {
                onVeifyCanLogin();
            } else {
                tvPhoneErrorAuth.setVisibility(View.GONE);
                btnOk.setEnabled(false);
            }
        }
    };

    /**
     * 校验手机号码
     *
     * @return
     */
    private boolean onCheckPhone() {
        phone = edPhone.getText().toString().trim();
        if (!RegexUtils.isMobilePCExact(phone)) {
//            showMessage("手机号格式错误");
        } else {
            return true;
        }
        return false;
    }

    /**
     * 校验是否可以下一步
     */
    private void onVeifyCanLogin() {
        if (!onCheckPhone()) {
            tvPhoneErrorAuth.setVisibility(View.VISIBLE);
            btnOk.setEnabled(false);
        } else {
            tvPhoneErrorAuth.setVisibility(View.GONE);
            btnOk.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        mPhonePresenter.checkPhone(phone);
    }

    @Override
    public void checkResultSucceed(boolean checkResult, String phone) {
        if (!checkResult) {
            showMessage("该号码已被其他账号绑定，请联系客服");
        } else {
            Bundle mbundle = new Bundle();
            mbundle.putString(ConstantValue.ARG_PARAM1, phone);
            if (getIntent() != null) {
                String stringExtra = getIntent().getStringExtra(ConstantValue.BIND_PHONE_FLOW);
                if (!TextUtils.isEmpty(stringExtra)) { //处理web完善信息在返回webview
                    mbundle.putString(ConstantValue.BIND_PHONE_FLOW, stringExtra);
                    mbundle.putString(ConstantValue.ARG_PARAM3, getIntent().getStringExtra(ConstantValue.ARG_PARAM2));
                    mbundle.putString(ConstantValue.ARG_PARAM4, getIntent().getStringExtra(ConstantValue.ARG_PARAM3));
                }
            }
            mbundle.putBoolean(ConstantValue.ARG_PARAM2, isModification);
            startActivity(new Intent(mContext, BindPhoneSecondActivity.class).putExtras(mbundle));
        }
    }
}
