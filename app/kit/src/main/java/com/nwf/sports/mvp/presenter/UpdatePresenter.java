package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IUpdatepi;
import com.nwf.sports.mvp.model.CheckupgradeResult;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.UpdateView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.Constant;

import rx.Subscription;

/**
 * <p>类描述： 首页
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class UpdatePresenter<T extends IBaseView> extends BasePresenter {

    private UpdateView mView;
    private IUpdatepi api = null;

    public UpdatePresenter(Context context, UpdateView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IUpdatepi.class);
    }

    /**
     * 获取APP版本
     */
    public void checkupgrade() {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.checkupgrade(Constant.CHECKUPGRADETYPE),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CheckupgradeResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CheckupgradeResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.checkupgrade(response.getData());
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("获取app数据失败");
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
                }, mContext));

        subscriptionsHelper.add(subscription);
    }

    @Override
    public void onDestory() {
        super.onDestory();
        api = null;
    }
}
