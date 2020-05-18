package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.RegexUtils;
import com.dawoo.coretool.util.SpannableStringUtils;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.BindPhoneView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.Ticker;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 绑定手机号
 * <p>创建人：Simon
 * <p>创建时间：2019-03-05
 * <p>修改人：Simon
 * <p>修改时间：2019-03-05
 * <p>修改备注：
 **/
public class PhoneBindDingDialogFragment extends BaseDialogFragment implements BindPhoneView, SendSmsCodeView {

    @BindView(R.id.tv_title_secureverify)
    TextView mTvTitleSecureverify;
    @BindView(R.id.tvPhone_secureverify)
    EditText mTvPhoneSecureverify;
    @BindView(R.id.tvPhone_erre)
    TextView mTvPhoneErre;
    @BindView(R.id.etSmscode_secureverify)
    EditText mEtSmscodeSecureverify;
    @BindView(R.id.btn_send_secureverify)
    TextView mBtnSendSecureverify;
    @BindView(R.id.tv_err_secureverify)
    TextView mTvErrSecureverify;
    @BindView(R.id.btnCancel_secureverify)
    Button mBtnCancelSecureverify;
    @BindView(R.id.btnConfirm_secureverify)
    Button mBtnConfirmSecureverify;

    private String phone;
    private String verify;

    private PhonePresenter mPhoneBinDialogPresenter = null; //绑定手机号
    private SendSmsCodePresenter mSendSmsCodePresenter = null;
    private Ticker ticker = new Ticker(); //倒计时

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_phone_binding_verify;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsOutCanback = true;
        mIsKeyCanback = true;
        AnimationsStyle = -1;
        intScreenWProportion = 1;
        super.onCreate(savedInstanceState);
        mPhoneBinDialogPresenter = new PhonePresenter(getActivity(), this);
        mSendSmsCodePresenter = new SendSmsCodePresenter(getActivity(), this);
    }

    @Override
    protected void initViews(View view) {
        mTvPhoneSecureverify.addTextChangedListener(phoneWatcher);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
//        ticker.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 检测电话号码是否正确
     */
    TextWatcher phoneWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //用户输入的字符串
            String input = s.toString().trim();
            int length = input.length();

            //校验手机号码的有效性
            if (length >= 11) {
                if (onCheckPhone()) {

                }
            } else {
                mTvPhoneErre.setVisibility(View.INVISIBLE);
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 校验手机号码
     */
    private boolean onCheckPhone() {
        String formatPhone = mTvPhoneSecureverify.getText().toString().trim();
        String phone = formatPhone.replace(" ", "");
        if (!RegexUtils.isMobilePCExact(phone)) {
            mTvPhoneErre.setText("请输入11~12位正确的手机号码");
            mTvPhoneErre.setVisibility(View.VISIBLE);
        } else {
            mTvPhoneErre.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }


    @OnClick({R.id.btn_send_secureverify, R.id.btnCancel_secureverify, R.id.btnConfirm_secureverify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_secureverify:
                String trim = mTvPhoneSecureverify.getText().toString().trim();
                if (trim.length() < 11) {
                    mTvPhoneErre.setText("请输入11~12位正确的手机号码");
                    mTvPhoneErre.setVisibility(View.VISIBLE);
                    return;
                }
                if (onCheckPhone()) {
                    mPhoneBinDialogPresenter.checkPhone(trim);
                } else {
                    mTvPhoneErre.setText("请输入11~12位正确的手机号码");
                    mTvPhoneErre.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.btnCancel_secureverify:
                dismissAllowingStateLoss();
                break;
            case R.id.btnConfirm_secureverify:
                phone = mTvPhoneSecureverify.getText().toString().trim();
                verify = mEtSmscodeSecureverify.getText().toString().trim();
                if (TextUtils.isEmpty(verify)) {
                    showMessage("请输入验证码");
                    return;
                }
                mPhoneBinDialogPresenter.bindPhone(phone, verify);
                break;
        }
    }


    @Override
    public void checkResultSucceed(boolean checkResult, String phone) {
        if (!checkResult) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //点击事件
                    dismissAllowingStateLoss();
                    ActivityUtil.skipToService(getContext());
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    //变化的颜色值
                    ds.setColor(getResources().getColor(R.color.color_FF3300));
                    //是否有下划线
                    ds.setUnderlineText(true);
                }
            };

            CharSequence charSequence = new SpannableStringUtils.Builder()
                    .append(getString(R.string.str_phone_inuse)).setForegroundColor(getResources().getColor(R.color.color_4A4A4A))
                    .append(" 联系客服>>").setForegroundColor(getResources().getColor(R.color.color_FF3300)).setClickSpan(clickableSpan)
                    .setBackgroundColor(getResources().getColor(R.color.color_white))
                    .create();
            mTvPhoneErre.setMovementMethod(LinkMovementMethod.getInstance());
            mTvPhoneErre.setText(charSequence);
            mTvPhoneErre.setVisibility(View.VISIBLE);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone);
            map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_BINDPHONE);
            map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
            mSendSmsCodePresenter.sendSmsCodePhone(map);
        }
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
        showMessage(errmsg);
    }

    @Override
    public void setSendSmsCodeComplete() {
        showMessage(getString(R.string.str_send_sms_ok));
        mBtnSendSecureverify.setEnabled(false);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //点击事件
                dismissAllowingStateLoss();
                ActivityUtil.skipToService(getActivity());
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //变化的颜色值
                ds.setColor(getResources().getColor(R.color.color_FF3300));
                //是否有下划线
                ds.setUnderlineText(true);
            }
        };

        CharSequence charSequence = new SpannableStringUtils.Builder()
                .append("获取不到验证码？").setForegroundColor(getResources().getColor(R.color.color_4A4A4A))
                .append(" 联系客服>>").setForegroundColor(getResources().getColor(R.color.color_FF3300)).setClickSpan(clickableSpan)
                .setBackgroundColor(getResources().getColor(R.color.color_white))
                .create();

        mTvErrSecureverify.setMovementMethod(LinkMovementMethod.getInstance());
        mTvErrSecureverify.setText(charSequence);
        mTvErrSecureverify.setVisibility(View.VISIBLE);

        ticker.stop();
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                String template = getString(R.string.str_wait);
                mBtnSendSecureverify.setText(String.format(template, String.valueOf(left)));
                mBtnSendSecureverify.setTextColor(getResources().getColor(R.color.color_9B9B9B));
            }

            @Override
            public void onEnd() {
                mBtnSendSecureverify.setEnabled(true);
                mBtnSendSecureverify.setTextColor(getResources().getColor(R.color.color_1ECE94));
                mBtnSendSecureverify.setText(R.string.str_send_smscode);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
    }


    @Override
    public void bindPhone() {
        showMessage("绑定成功");
        RxBus.get().post(ConstantValue.REFRESH_USERINFO, "REFRESH_USERINFO");
        dismissAllowingStateLoss();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ticker.stop();
        if (mPhoneBinDialogPresenter != null) {
            mPhoneBinDialogPresenter.onDestory();
        }
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
    }
}
