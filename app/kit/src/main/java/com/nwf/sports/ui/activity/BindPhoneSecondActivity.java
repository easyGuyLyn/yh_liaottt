package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dawoo.coretool.util.ResHelper;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.BindPhoneView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.ui.views.vcedittext.VerificationCodeEditText;
import com.nwf.sports.utils.BindPhoneFlowEnum;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.HideDataUtil;
import com.nwf.sports.utils.InputMethodUtils;
import com.nwf.sports.utils.Ticker;
import com.nwf.sports.utils.data.IMDataCenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 绑定手机号 输入验证码
 * <p>创建人：Simon
 * <p>创建时间：2019-04-16
 * <p>修改人：Simon
 * <p>修改时间：2019-04-16
 * <p>修改备注：
 **/
public class BindPhoneSecondActivity extends BaseActivity implements BindPhoneView, SendSmsCodeView {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.pb_circle)
    ProgressBar pbCircle;
    @BindView(R.id.tv_countdown_sendsms)
    TextView tvCountDown;
    @BindView(R.id.ll_resend)
    LinearLayout linearLayout;
    @BindView(R.id.flayout_pbar)
    FrameLayout frameLayout;

    @BindView(R.id.ed_verfifcation_code)
    VerificationCodeEditText edVerfifcationCode;

    private String phone = "";
    private String verify = "";

    private PhonePresenter mPhonePresenter = null; //绑定手机号
    private SendSmsCodePresenter mSendSmsCodePresenter = null;
    private Ticker ticker = new Ticker(); //倒计时

    //进度条最大值
    private final int MAX_PROGRESS = Constant.ACTION_SEND_AUTH_CODE;

    boolean isModification = false; //是否是修改手机号码

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_bind_phone_second);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            phone = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            isModification = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM2, false);
        }
        mPhonePresenter = new PhonePresenter(this, this);
        mSendSmsCodePresenter = new SendSmsCodePresenter(this, this);

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
        String s = HideDataUtil.hideCardNo(phone, 3, 2);
        tvPhone.setText(s.replaceAll("(.{4})", "$1\t\t"));
        edVerfifcationCode.addTextChangedListener(authWatcher);
    }

    @Override
    protected void initData() {
        //点击事件
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_BINDPHONE);
        map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
        mSendSmsCodePresenter.sendSmsCodePhone(map);
    }

    /**
     * 检查验证码
     */
    TextWatcher authWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            onVeify();
        }
    };

    /**
     * 校验验证码
     */
    private void onVeify() {
        String trim = edVerfifcationCode.getText().toString().trim();
        if (trim.length() == Constant.CODE_LENGTH) {
            btnOk.setEnabled(true);
        } else {
            btnOk.setEnabled(false);
        }
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
    }

    @Override
    public void setSendSmsCodeComplete() {
        linearLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        pbCircle.setMax(MAX_PROGRESS);
        pbCircle.setProgress(0);
        tvCountDown.setText("60s");

        showMessage(getString(R.string.str_send_sms_ok));
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                String template = getString(R.string.str_wait);
                tvCountDown.setText(String.format(template, String.valueOf(left)));
                pbCircle.setProgress(MAX_PROGRESS - left);
            }

            @Override
            public void onEnd() {
                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
        InputMethodUtils.showSoftInput(edVerfifcationCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticker.stop();
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
    }


    @OnClick({R.id.ll_resend, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_resend:
                //点击事件
                Map<String, String> map = new HashMap<>();
                map.put("mobile", phone);
                map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_BINDPHONE);
                map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                mSendSmsCodePresenter.sendSmsCodePhone(map);
                break;

            case R.id.btn_ok:
                verify = edVerfifcationCode.getText().toString().trim();
                if (isModification) {
                    String oldphone = IMDataCenter.getInstance().getUserInfoBean().getPhone();
                    mPhonePresenter.modifyPhone(oldphone, phone, verify);
                } else {
                    mPhonePresenter.bindPhone(phone, verify);
                }
                break;
        }
    }

    @Override
    public void bindPhone() {
        showMessage("绑定成功");
        RxBus.get().post(ConstantValue.REFRESH_USERINFO, "REFRESH_USERINFO");
        if (isModification) {
            toMain();
            return;
        }
        if (getIntent() != null) {
            String stringExtra = getIntent().getStringExtra(ConstantValue.BIND_PHONE_FLOW);
            if (!TextUtils.isEmpty(stringExtra)) {
                if (BindPhoneFlowEnum.TOBINDBANK.getServicename().equals(stringExtra)) {
                    toMain();
                    Bundle mbundle = new Bundle();
                    startActivity(new Intent(this, AddBankActivity.class).putExtras(mbundle));
                } else if (BindPhoneFlowEnum.TOINTRODUCE.getServicename().equals(stringExtra)) {
                    String title = getIntent().getStringExtra(ConstantValue.ARG_PARAM3);
                    String url = getIntent().getStringExtra(ConstantValue.ARG_PARAM4);
                    Bundle mbundle = new Bundle();
                    mbundle.putString(ConstantValue.ARG_PARAM1, title);
                    mbundle.putString(ConstantValue.ARG_PARAM2, url);
                    startActivity(new Intent(BindPhoneSecondActivity.this, IntroduceActivity.class).putExtras(mbundle));
                } else {
                    toMain();
                }
            } else {
                toMain();
            }
        } else {
            toMain();
        }

    }

    public void toMain() {
        InputMethodUtils.hideSoftInput(this);
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
    }

    @Override
    public void checkResultSucceed(boolean checkResult, String phone) {

    }
}
