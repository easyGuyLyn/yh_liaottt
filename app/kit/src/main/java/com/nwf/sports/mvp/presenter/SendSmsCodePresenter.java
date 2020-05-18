package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.NetworkUtils;
import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.api.IPhoneApi;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.SafetyVerificationView;
import com.nwf.sports.mvp.view.SendSmsCodeView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.AppStatus;
import com.nwf.sports.utils.data.DataCenter;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 验证码处理
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class SendSmsCodePresenter<T extends IBaseView> extends BasePresenter {
    private T mView;
    private IPhoneApi api = null;

    public SendSmsCodePresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IPhoneApi.class);
    }


    /**
     * 发送验证码
     * sendType (string, optional): 发送类型 (10:登录) (6:注册) (1:手机绑定) (8 银行卡修改/删除) (3 手机修改) (11 常规验证码(身份验证)) ,
     * smsType (string, optional): 验证码通道 60025 代表 注册/登录 /常规验证码
     */
    public void sendSmsCodePhone(Map<String, String> map) {
        if (!NetworkUtils.isConnected()) {
            mView.showMessage("发送短信验证码失败，请检查网络");
            return;
        }
        UserInfoBean userInfoBean = DataCenter.getInstance().getUserInfoBean();
        if (userInfoBean.isRealLogin()) {
            map.put("loginName", userInfoBean.getUsername());
        }
        SendSmsCodeView view = (SendSmsCodeView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.sendSmsCodePhone(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse>() {
                    @Override
                    public void onNext(AppTextMessageResponse response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.setSendSmsCodeComplete();
                            mView.showMessage(ResHelper.getString(R.string.str_send_sms_ok));
                        } else {
                            if (!Check.isEmpty(response.getMsg())) {
                                mView.showMessage(response.getMsg());
                            } else {
                                mView.showMessage(ResHelper.getString(R.string.str_send_sms_fail));
                            }
                            view.setSendSmsCodeError("");
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mView.showMessage(ResHelper.getString(R.string.str_send_sms_fail));
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 安全验证 验证验证码
     */
    public void verifyBoundPhone(String smscode) {
        SafetyVerificationView view = (SafetyVerificationView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.verifysms(smscode),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse>() {
                    @Override
                    public void onNext(AppTextMessageResponse response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
//                            DataCenter.getInstance().getMyLocalCenter().recordBoundPhoneEvent();
                            view.verifyBoundPhoneSucceed();
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage(ResHelper.getString(R.string.str_fail_verify_smscode));
                            } else {
                                if (AppStatus.ERROR_SMSCODE_INVALID.equals(response.getCode())) {
                                    view.verifyBoundPhoneDefeated(true);
                                } else {
                                    view.verifyBoundPhoneDefeated(false);
                                    mView.showMessage(response.getMsg());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_send_sms_fail));
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }


    @Override
    public void onDestory() {
        super.onDestory();
        api = null;
    }
}
