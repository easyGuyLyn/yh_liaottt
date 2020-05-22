package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dawoo.coretool.util.ResHelper;
import com.dawoo.coretool.util.SpannableStringUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.SafetyVerificationView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
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
 * <p>类描述： 手机号管理手机号
 * <p>创建人：Simon
 * <p>创建时间：2019-04-16
 * <p>修改人：Simon
 * <p>修改时间：2019-04-16
 * <p>修改备注：
 **/
public class ManagementPhoneActivity extends BaseActivity implements SafetyVerificationView, SendSmsCodeView {

    private static final int CHANGE = 0;
    private static final int PROGRESS = 1;
    private static final int RESEND = 2;

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.change_phone)
    TextView changePhone;
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
    private SendSmsCodePresenter mSendSmsCodePresenter;

    private String phone = "";
    private String verify = "";

    private Ticker ticker = new Ticker(); //倒计时

    //进度条最大值
    private final int MAX_PROGRESS = Constant.ACTION_SEND_AUTH_CODE;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_management_phone);
    }

    @Override
    protected void initViews() {
        mSendSmsCodePresenter = new SendSmsCodePresenter(this, this);
        phone = IMDataCenter.getInstance().getUserInfoBean().getPhone();
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String s = HideDataUtil.hideCardNo(phone, 3, 3);
        tvPhone.setText(s.replaceAll("(.{4})", "$1\t\t"));
        edVerfifcationCode.addTextChangedListener(authWatcher);
        switchStatus(CHANGE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //点击事件
                Map<String, String> map = new HashMap<>();
                map.put("mobile", IMDataCenter.getInstance().getUserInfoBean().getPhone());
                map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_PHONE);
                map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                mSendSmsCodePresenter.sendSmsCodePhone(map);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //变化的颜色值
                ds.setColor(getResources().getColor(R.color.color_100000));
                //是否有下划线
                ds.setUnderlineText(true);
            }
        };

        CharSequence charSequence = new SpannableStringUtils.Builder()
                .append("更换请点击").setForegroundColor(getResources().getColor(R.color.color_4A4A4A))
                .append("获取验证码").setForegroundColor(getResources().getColor(R.color.color_100000)).setClickSpan(clickableSpan)
                .setBackgroundColor(getResources().getColor(R.color.color_white))
                .create();
        changePhone.setMovementMethod(LinkMovementMethod.getInstance());
        changePhone.setText(charSequence);
        changePhone.setVisibility(View.VISIBLE);
    }

    public void switchStatus(int type) {
        switch (type) {
            case CHANGE:
                changePhone.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                break;

            case PROGRESS:
                changePhone.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                break;

            case RESEND:
                changePhone.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void initData() {
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
    protected void onDestroy() {
        super.onDestroy();
        ticker.stop();
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
    }

    @OnClick({R.id.ll_resend, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_resend:
                Map<String, String> map = new HashMap<>();
                map.put("mobile", IMDataCenter.getInstance().getUserInfoBean().getPhone());
                map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_PHONE);
                map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                mSendSmsCodePresenter.sendSmsCodePhone(map);
                break;

            case R.id.btn_ok:
                verify = edVerfifcationCode.getText().toString().trim();
                mSendSmsCodePresenter.verifyBoundPhone(verify);
                break;
        }
    }

    @Override
    public void verifyBoundPhoneSucceed() {
        showMessage("验证成功");
        Bundle mbundle = new Bundle();
        mbundle.putString(ConstantValue.BIND_PHONE_FLOW, BindPhoneFlowEnum.TOMAIN.getServicename());
        mbundle.putBoolean(ConstantValue.ARG_PARAM1, true);
        startActivity(new Intent(this, BindPhoneActivity.class).putExtras(mbundle));
        finish();
    }

    @Override
    public void verifyBoundPhoneDefeated(boolean isInvalid) {
        if (isInvalid) {
            showMessage(ResHelper.getString(R.string.str_fail_verify_smscode));
        }
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
        showMessage(errmsg);
    }

    @Override
    public void setSendSmsCodeComplete() {
        switchStatus(PROGRESS);
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
                switchStatus(RESEND);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
        InputMethodUtils.showSoftInput(edVerfifcationCode);
    }

}