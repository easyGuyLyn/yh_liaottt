package com.nwf.sports.mvp.presenter;


import android.content.Context;

import com.nwf.sports.mvp.api.IRegisterNApi;
import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.view.CommonView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.data.DataCenter;

import java.util.Map;

import rx.Subscription;

/**
 * Created by Nereus on 2017/4/21.
 * 描述	字段名称	字段含义	类型(长度)	字段说明	可为空	样例
 * 请求参数
 * accountname	游戏账号名	String(5-18)	5~11位小写字母、数字及其组合	不能为空
 * password	登录密码	String(16)	6~16位字母、数字组合	不能为空
 */

public class RegisterPresenter<T extends IBaseView> extends BasePresenter {

    private T mView;
    private IRegisterNApi api;

    public RegisterPresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IRegisterNApi.class);
    }

    /**
     * 手机号注册
     */
    public void fastRegister(Map<String, String> map) {
        CommonView view = (CommonView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.fastRegisterSport(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<LoginResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<LoginResult> loginResult) {
                        if (null != view) {
                            if (loginResult.isSuccess()) {
                                view.setData(loginResult.getData());
                                DataCenter.getInstance().getMyLocalCenter().recordBoundPhoneEvent();
                            } else {
                                view.showMessage(loginResult.getMsg());
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
     * 注册账号
     */
    public void registerAccountPwd(Map<String, String> map) {
        CommonView view = (CommonView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.registerAccountSport(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<LoginResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<LoginResult> loginResult) {
                        if (null != view) {
                            if (loginResult.isSuccess()) {
                                if (loginResult.getData().getToken() != null) {
                                    view.setData(loginResult.getData());
                                } else {
                                    view.showMessage(loginResult.getMsg());
                                }
                            } else {
                                view.showMessage(loginResult.getMsg());
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
