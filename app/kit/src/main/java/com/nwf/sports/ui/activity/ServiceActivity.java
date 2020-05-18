package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.RegexUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.ServiceCallbackResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.view.GetbindphoneView;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.RemindCommonDialogFragment;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.MD5Util;
import com.nwf.sports.utils.Ticker;
import com.nwf.sports.utils.data.DataCenter;
import com.nwf.sports.utils.data.MyLocalCenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：客服界面
 * <p>创建人：Simon
 * <p>创建时间：2019-04-30
 * <p>修改人：Simon
 * <p>修改时间：2019-04-30
 * <p>修改备注：
 **/
public class ServiceActivity extends BaseActivity implements GetbindphoneView {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.tv_homepage_introduce_title)
    TextView tvHomepageIntroduceTitle;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.iv_service_phone_clear)
    ImageView ivServicePhoneClear;
    @BindView(R.id.btn_callback)
    TextView btnCallback;
    @BindView(R.id.btn_immediately_relation)
    TextView btnImmediatelyRelation;
    @BindView(R.id.tv_service_phone_error_auth)
    TextView tvServicePhoneErrorAuth;
    @BindView(R.id.ivw_about)
    TextView ivwAbout;

    private String serviceCallback = ""; //回拨的电话
    private Ticker ticker = new Ticker(); //倒计时
    private boolean hasFocusn = false;

    PhonePresenter mPhonePresenter = null;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_service);
    }

    @Override
    protected void initViews() {
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhonePresenter = new PhonePresenter(this, this);
        UserInfoBean userInfoBean = DataCenter.getInstance().getUserInfoBean();
        if (userInfoBean.isRealLogin) {
            MyLocalCenter myLocalCenterCenter = DataCenter.getInstance().getMyLocalCenter();
            serviceCallback = myLocalCenterCenter.getServiceCallback();
            if (!TextUtils.isEmpty(serviceCallback)) {
                vTitle.setFocusable(true);
                vTitle.setFocusableInTouchMode(true);
                vTitle.requestFocus();
                edPhone.setText(serviceCallback.substring(0, 3) + "******" + serviceCallback.substring(9));
                ivServicePhoneClear.setVisibility(View.VISIBLE);
                btnCallback.setEnabled(true);
            } else {
                String phone = DataCenter.getInstance().getUserInfoBean().getPhone();
                if (TextUtils.isEmpty(phone)) {
                    serviceCallback = "";
                    edPhone.setText(serviceCallback);
                    ivServicePhoneClear.setVisibility(View.INVISIBLE);
                    btnCallback.setEnabled(false);
                    return;
                }
                if (!RegexUtils.isMobilePCExact(phone)) {
                    serviceCallback = "";
                    edPhone.setText(serviceCallback);
                    ivServicePhoneClear.setVisibility(View.INVISIBLE);
                    btnCallback.setEnabled(false);
                    return;
                }
                serviceCallback = phone;
                edPhone.setText(serviceCallback.substring(0, 3) + "******" + serviceCallback.substring(9));
                ivServicePhoneClear.setVisibility(View.VISIBLE);
                tvServicePhoneErrorAuth.setVisibility(View.INVISIBLE);
                btnCallback.setEnabled(true);
                DataCenter.getInstance().getMyLocalCenter().saveServiceCallback(serviceCallback);

                vTitle.setFocusable(true);
                vTitle.setFocusableInTouchMode(true);
                vTitle.requestFocus();
                hasFocusn = false;
            }
        } else {
            serviceCallback = "";
            btnCallback.setEnabled(false);
        }
        edPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !hasFocusn) {
                    hasFocusn = true;
                    edPhone.setText("");
                }
            }
        });
        edPhone.addTextChangedListener(phoneWatcher);
    }

    @Override
    protected void initData() {
//        mPhonePresenter.getCheckBindPhone();
    }

    TextWatcher phoneWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //用户输入的字符串
            String input = s.toString().trim();
            int length = input.length();
            //校验手机号码的有效性
            if (length >= 11) {
                if (onCheckPhone()) {
                    if (ticker.isStop()) {
                        btnCallback.setEnabled(true);
                    }
                }
            } else {
                btnCallback.setEnabled(false);
            }
            if (length > 0) {
                ivServicePhoneClear.setVisibility(View.VISIBLE);
            } else {
                tvServicePhoneErrorAuth.setVisibility(View.INVISIBLE);
                ivServicePhoneClear.setVisibility(View.INVISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            //onVeifyCanLogin(s.toString());
        }
    };

    //校验手机号码
    private boolean onCheckPhone() {
        if (!hasFocusn) {
            serviceCallback = DataCenter.getInstance().getMyLocalCenter().getServiceCallback();
        } else {
            serviceCallback = edPhone.getText().toString().trim().replace(" ", "");
        }
        if (!RegexUtils.isMobilePCExact(serviceCallback)) {
            tvServicePhoneErrorAuth.setVisibility(View.VISIBLE);
        } else {
            tvServicePhoneErrorAuth.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }


    @OnClick({R.id.btn_callback, R.id.btn_immediately_relation, R.id.iv_service_phone_clear, R.id.ivw_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_callback:
                DataCenter.getInstance().getMyLocalCenter().saveServiceCallback(serviceCallback);
                serviceCallback = Base64.encodeToString(serviceCallback.getBytes(), Base64.NO_WRAP);//base 64
                StringBuilder host = new StringBuilder();
                host.append("pid=").append(Constant.PRODUCT_ID.toLowerCase()).append("&phone=").append(serviceCallback);
                String token = MD5Util.MD5Encode(host.toString());//md5
                mPhonePresenter.onQueryServiceCallBack(serviceCallback, token);
                break;
            case R.id.btn_immediately_relation:
//                LIVUserInfo info = new LIVUserInfo();
//                UserInfoBean userInfoBean = DataCenter.getInstance().getUserInfoBean();
//                //设置用户唯一id
//                info.setUserId(userInfoBean.username);
//                //设置用户姓名
//                info.setName(userInfoBean.username);
//                LIVManager.getInstance().startService(this, info);

                String userId = DataCenter.getInstance().getUserInfoBean().username;
                String userName = "永乐体育会员";
                String loginName = userName;
                String name = userName;
                long timestamp = Calendar.getInstance().getTimeInMillis();
                String infoValue = "userId=" + userId + "loginName=" + loginName + "name=" + name + "timestamp=" + timestamp;
                String url = "https://www.f66-kefu.com/chat/chatClient/chatbox.jsp?companyID=8996&configID=3&info=" + infoValue;

                Bundle mbundle = new Bundle();
                mbundle.putString(ConstantValue.ARG_PARAM1, "客服");
                mbundle.putString(ConstantValue.ARG_PARAM2, url);
                startActivity(new Intent(ServiceActivity.this, IntroduceActivity.class).putExtras(mbundle));

                break;
            case R.id.iv_service_phone_clear:
                edPhone.setText("");
                break;
            case R.id.ivw_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

    /**
     * 倒计时
     */
    public void onSendAuthCode() {
        btnCallback.setEnabled(false);
        ticker.stop();
        ticker.setOnTickerListener(new Ticker.OnTickerListener() {
            @Override
            public void onTick(int total, int left) {
                String template = getString(R.string.str_wait);
                btnCallback.setText(String.format(template, String.valueOf(left)));
            }

            @Override
            public void onEnd() {
                btnCallback.setEnabled(true);
                btnCallback.setText("确认回拨");
            }
        });
        ticker.setTotalTicker(60);
        ticker.begin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ticker != null) {
            ticker.stop();
        }
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
    }

    @Override
    public void getBindPhoneViewSucceed(String phone) {
        if (Check.isNull(phone)) {
            return;
        }
        if (!RegexUtils.isMobilePCExact(phone)) {
            return;
        }
        serviceCallback = phone;
        edPhone.setText(serviceCallback.substring(0, 3) + "******" + serviceCallback.substring(9));
        ivServicePhoneClear.setVisibility(View.VISIBLE);
        tvServicePhoneErrorAuth.setVisibility(View.INVISIBLE);
        btnCallback.setEnabled(true);
        DataCenter.getInstance().getMyLocalCenter().saveServiceCallback(serviceCallback);

        vTitle.setFocusable(true);
        vTitle.setFocusableInTouchMode(true);
        vTitle.requestFocus();
        hasFocusn = false;
    }

    @Override
    public void callbackResultSucceed(ServiceCallbackResult serviceCallbackResult) {
        RemindCommonDialogFragment remindCommonDialogFragment = new RemindCommonDialogFragment()
                .setTvContent("电话回拨中，我们将在1分钟内为您致电，请您保持电话畅通。")
                .setOnRightButtonListener(null, "确定");
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), remindCommonDialogFragment);
        onSendAuthCode();
    }
}
