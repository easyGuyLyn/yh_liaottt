package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.nwf.sports.mvp.api.ILoginApi;
import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 账号检测
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LoginPresenter<T extends IBaseView> extends BasePresenter {
    private CommonView mView;
    private ILoginApi api = null;

    public LoginPresenter(Context context, CommonView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(ILoginApi.class);
    }

    /**
     * 账号登录
     *
     */
    public void loginByUsername(Map<String, String> map) {
        Subscription subscription = RxHelper.toSubscribe(api.loginByUsernameSport(map)
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<LoginResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<LoginResult> loginResult) {
                        if (null != mView) {
                            if (loginResult.isSuccess()) {
                                mView.setData(loginResult.getData());
                            } else {
                                mView.showMessage(loginResult.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mView.showMessage(e);
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }


    /**
     * 手机号登录
     *
     */
    public void fastLogin(Map<String, String> map) {
        Subscription subscription = RxHelper.toSubscribe(api.fastLoginSport(map)
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<LoginResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<LoginResult> loginResult) {
                        if (null != mView) {
                            if (loginResult.isSuccess()) {
                                mView.setData(loginResult.getData());
                            } else {
                                mView.showMessage(loginResult.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mView.showMessage(e);
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
