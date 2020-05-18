package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.SubscriptionHelper;

/**
 * Created by benson on 17-12-21.
 */

public abstract class BasePresenter<T extends IBaseView> {
    protected Context mContext;
    protected T mView;
    protected SubscriptionHelper subscriptionsHelper = new SubscriptionHelper();

    public BasePresenter(Context mContext, T view) {
        this.mContext = mContext;
        this.mView = view;
    }

    /**
     * 绑定View
     */
    public void onAttch(T view) {
        this.mView = view;

    }


    public void ondetach() {
        mView = null;
    }

    public void onDestory() {
        clearSubscription();
        this.mContext = null;
        mView = null;
    }

    void clearSubscription() {
        subscriptionsHelper.unsubscribe();
        this.mContext = null;
        mView = null;
    }

    /**
     * 对 ACTIVITY 生命周期进行管理
     *
     * @return
     */
//    protected LifecycleProvider getLifecycleProvider() {
//        LifecycleProvider provider = null;
//        if (null != mContext && mContext instanceof LifecycleProvider) {
//            provider = (LifecycleProvider) mContext;
//        }
//        return provider;
//    }


}

