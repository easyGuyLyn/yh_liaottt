package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.api.IPhoneApi;
import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.ServiceCallbackResult;
import com.nwf.sports.mvp.view.BindPhoneView;
import com.nwf.sports.mvp.view.CheckPhoneView;
import com.nwf.sports.mvp.view.GetbindphoneView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.VerifyPhoneView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.PNCheck;
import com.nwf.sports.utils.data.DataCenter;

import rx.Subscription;

/**
 * <p>类描述： 查询手机号
 * <p>创建人：Simon
 * <p>创建时间：2019-04-10
 * <p>修改人：Simon
 * <p>修改时间：2019-04-10
 * <p>修改备注：
 **/
public class PhonePresenter<T extends IBaseView> extends BasePresenter {
    private IPhoneApi api;
    private T mView;

    public PhonePresenter(Context context, T view) {
        super(context, view);
        this.mContext = context;
        this.mView = view;
        this.api = RetrofitHelper.getService(IPhoneApi.class);
    }


    /**
     * 绑定手机号
     */
    public void bindPhone(final String mobile, String smsCode) {
        final BindPhoneView view = (BindPhoneView) mView;
        PNCheck.CheckResult checkResult = PNCheck.collect(PNCheck.checkPhoneNumber(mobile), PNCheck.checkSmsCode(smsCode));
        if (!checkResult.isResultOk) {
            LogUtils.e("bindphone error:" + checkResult.msg);
            if (null != view) {
                view.showMessage(checkResult.msg);
            }
            return;
        }
        if (view == null) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.bindPhone(mobile, smsCode),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }

                        boolean bindPhoneResult = response.getData().flag;
                        if (response.isSuccess() && bindPhoneResult) {
                            DataCenter.getInstance().getMyLocalCenter().recordBoundPhoneEvent();
                            view.bindPhone();
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage(ResHelper.getString(R.string.str_fail_bind_phone));
                            } else {
                                view.showMessage(response.getMsg());
                            }
                        }

                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage(ResHelper.getString(R.string.str_fail_bind_phone));
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);


    }

    /**
     * 客服电话回拨
     */
    public void onQueryServiceCallBack(String phone, String token) {
        final GetbindphoneView view = (GetbindphoneView) mView;
        if (view == null) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.onQueryServiceCallBack(phone, token),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<ServiceCallbackResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<ServiceCallbackResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.callbackResultSucceed(response.getData());
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage("电话回拨失败请重试");
                            } else {
                                view.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage("电话回拨失败请重试");
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 验证用户是否绑定手机
     */
    public void verifyPhone() {
        final VerifyPhoneView view = (VerifyPhoneView) mView;
        if (view == null) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.verifyPhone(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.verifyPhoneSucceed(response.getData().flag);
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage(ResHelper.getString(R.string.str_fail_check_bound_phone));
                                view.verifyPhoneDefeated(true);
                            } else {
                                view.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        view.showMessage(ResHelper.getString(R.string.str_fail_check_bound_phone));
                        if (null != view) {
                            view.verifyPhoneDefeated(true);
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }


    /**
     * 验证其手机号是否已经绑定
     */
    public void checkPhone(final String phone) {
        CheckPhoneView view = (CheckPhoneView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.checkphone(phone),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.checkResultSucceed(response.getData().flag, phone);
                        } else {
                            view.showMessage(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage(ResHelper.getString(R.string.str_fail_check_bound_phone));
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 修改绑定手机
     */
    public void modifyPhone(String oldPhone, String mobile, String smsCode) {
        final BindPhoneView view = (BindPhoneView) mView;
        PNCheck.CheckResult checkResult = PNCheck.collect(PNCheck.checkPhoneNumber(mobile), PNCheck.checkSmsCode(smsCode));
        if (!checkResult.isResultOk) {
            LogUtils.e("bindphone error:" + checkResult.msg);
            if (null != view) {
                view.showMessage(checkResult.msg);
            }
            return;
        }
        if (view == null) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.modifyPhone(oldPhone, mobile, smsCode),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.bindPhone();
                        } else {
                            view.showMessage(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage(ResHelper.getString(R.string.str_fail_modification_phone));
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
