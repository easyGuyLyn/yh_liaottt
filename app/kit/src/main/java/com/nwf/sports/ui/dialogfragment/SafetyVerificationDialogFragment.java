package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.SendSmsCodePresenter;
import com.nwf.sports.mvp.view.SafetyVerificationView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.ui.views.vcedittext.VerificationCodeEditText;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.HideDataUtil;
import com.nwf.sports.utils.InputMethodUtils;
import com.nwf.sports.utils.PNCheck;
import com.nwf.sports.utils.Ticker;
import com.nwf.sports.utils.data.DataCenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 安全验证弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class SafetyVerificationDialogFragment extends BaseDialogFragment implements SafetyVerificationView, SendSmsCodeView {
    @BindView(R.id.btn_close)
    Button mBtnClose;
    @BindView(R.id.tv_relation)
    Button mTvRelation;
    @BindView(R.id.btn_send_secureverify)
    TextView mBtnSendSecureverify;
    @BindView(R.id.tv_err_secureverify)
    TextView tvErrSecureverify;
    @BindView(R.id.ed_verfifcation_code)
    VerificationCodeEditText edVerfifcationCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private View.OnClickListener mOnLeftButtonListener = null;
    private String mLeftButtonName = "";
    private View.OnClickListener mOnRightButtonListener = null;
    private String mRightButtonName = "";

    private SendSmsCodePresenter mSendSmsCodePresenter;
    private Ticker ticker = new Ticker(); //倒计时

    private BakeListener mBakeListener = null;

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_safety_verification;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsKeyCanback = false;
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        mSendSmsCodePresenter = new SendSmsCodePresenter(getActivity(), this);
        if (!TextUtils.isEmpty(mRightButtonName)) {
            mTvRelation.setText(mRightButtonName);
        } else {
            mTvRelation.setText("确定");
        }

        if (!TextUtils.isEmpty(mLeftButtonName)) {
            mBtnClose.setText(mLeftButtonName);
        } else {
            mBtnClose.setText("取消");
        }

        if (mOnLeftButtonListener != null) {
            mBtnClose.setOnClickListener(mOnLeftButtonListener);
        } else {
            mBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                }
            });
        }
        String phone = DataCenter.getInstance().getUserInfoBean().getPhone();
        if (!TextUtils.isEmpty(phone)){
            String s = HideDataUtil.hideCardNo(phone, 3, 3);
            tvPhone.setText(s.replaceAll("(.{4})", "$1\t\t"));
        }else {
            dismissAllowingStateLoss();
        }
    }


    @Override
    protected void initData() {

    }

    public SafetyVerificationDialogFragment setOnLeftButtonListener(View.OnClickListener onClickListener, String name) {
        mOnLeftButtonListener = onClickListener;
        mLeftButtonName = name;
        return this;
    }

    public SafetyVerificationDialogFragment setOnRightButtonListener(View.OnClickListener onClickListener, String name) {
        mOnRightButtonListener = onClickListener;
        mRightButtonName = name;
        return this;
    }

    public SafetyVerificationDialogFragment setOnBakeListener(BakeListener bakeListener) {
        mBakeListener = bakeListener;
        return this;
    }

    @Override
    public void verifyBoundPhoneSucceed() {
        showMessage("验证成功");
        if (mBakeListener != null) {
            mBakeListener.onAction();
        }
        dismissAllowingStateLoss();
    }

    @Override
    public void verifyBoundPhoneDefeated(boolean isInvalid) {
        if (isInvalid) {
            tvErrSecureverify.setVisibility(View.VISIBLE);
            tvErrSecureverify.setText("您输入的验证码不正确，请重新输入");
        } else {
            tvErrSecureverify.setVisibility(View.GONE);
            tvErrSecureverify.setText("");
        }
    }

    /**
     * 倒计时
     */
    public void onSendAuthCode() {
        mBtnSendSecureverify.setEnabled(false);
        ticker.stop();
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                String template = getString(R.string.str_wait);
                mBtnSendSecureverify.setText(String.format(template, String.valueOf(left)));
            }

            @Override
            public void onEnd() {
                mBtnSendSecureverify.setEnabled(true);
                mBtnSendSecureverify.setText(R.string.str_send_smscode);
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
    }

    @OnClick({R.id.btn_send_secureverify, R.id.tv_relation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_secureverify:
                Map<String, String> map = new HashMap<>();
                map.put("mobile", DataCenter.getInstance().getUserInfoBean().getPhone());
                map.put("sendType", Constant.PRODUCT_N_SEND_AUTH_CODE_PHONE);
                map.put("smsType", Constant.PRODUCT_N_SEND_AUTH_CODE_TYPE);
                mSendSmsCodePresenter.sendSmsCodePhone(map);
                break;
            case R.id.tv_relation:
                if (checkSmsCode()) {
                    String smscode = edVerfifcationCode.getText().toString();
                    mSendSmsCodePresenter.verifyBoundPhone(smscode);
                    InputMethodUtils.hideSoftInput(view);
                }
                break;
        }
    }

    /**
     * 检查验证码
     *
     * @return
     */
    private boolean checkSmsCode() {
        String smscode = edVerfifcationCode.getText().toString();
        PNCheck.CheckResult checkResult = PNCheck.checkSmsCode(smscode);
        if (!checkResult.isResultOk) {
            tvErrSecureverify.setVisibility(View.VISIBLE);
            tvErrSecureverify.setText(checkResult.msg);
        } else {
            tvErrSecureverify.setVisibility(View.INVISIBLE);
        }
        return checkResult.isResultOk;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ticker.stop();
        if (mSendSmsCodePresenter != null) {
            mSendSmsCodePresenter.onDestory();
        }
    }

    @Override
    public void setSendSmsCodeError(String errmsg) {
        showMessage(errmsg);
    }

    @Override
    public void setSendSmsCodeComplete() {
        onSendAuthCode();
    }

    public interface BakeListener {
        void onAction();
    }
}
