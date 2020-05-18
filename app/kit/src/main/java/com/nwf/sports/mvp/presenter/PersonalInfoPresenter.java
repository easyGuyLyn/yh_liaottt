package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IPersonalInfoApi;
import com.nwf.sports.mvp.model.MyPriceResult;
import com.nwf.sports.mvp.model.PersonalInfoResult;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.PersonalInfoView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import rx.Subscription;

/**
 * <p>类描述： 个人主页接口
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class PersonalInfoPresenter<T extends IBaseView> extends BasePresenter {
    private PersonalInfoView mView;
    private IPersonalInfoApi api = null;

    public PersonalInfoPresenter(Context context, PersonalInfoView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IPersonalInfoApi.class);
    }

    /**
     * 获取用户信息
     */
    public void getPersonalInfo() {
        Subscription subscription = RxHelper.toSubscribe(api.getPersonalInfo()
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<PersonalInfoResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<PersonalInfoResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.PersonalInfoSucceed(response.getData());
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("");
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(e);
                        }
                    }
                }, mContext, false));
        subscriptionsHelper.add(subscription);
    }


    /**
     * 退出登录
     */
    public void logout() {
        Subscription subscription = RxHelper.toSubscribe(api.logout()
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<MyPriceResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<MyPriceResult> response) {
                        if (null != mView) {
                            if (response.isSuccess()) {
                                mView.logoutSucceed();
                            } else {
                                if (TextUtils.isEmpty(response.getMsg())) {
                                    mView.showMessage("退出登录失败");
                                } else {
                                    mView.showMessage(response.getMsg());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(e);
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
