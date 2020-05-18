package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IHistoryApi;
import com.nwf.sports.mvp.model.DepositHistoryResult;
import com.nwf.sports.mvp.model.WithdrawalHistoryResult;
import com.nwf.sports.mvp.view.DepositHistoryView;
import com.nwf.sports.mvp.view.HistoryView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 历史记录接口
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class HistoryPresenter<T extends IBaseView> extends BasePresenter {
    private T mView;
    private IHistoryApi api = null;

    public HistoryPresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IHistoryApi.class);
    }

    /**
     * 获取存款记录
     */
    public void depositHistory(Map<String, String> map) {
        DepositHistoryView view = (DepositHistoryView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.depositHistory(map)
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<DepositHistoryResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<DepositHistoryResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            DepositHistoryResult data = response.getData();
                            view.findHistorySucceed(data);
                        } else {
                            if (!TextUtils.isEmpty(response.getMsg())) {
                                view.showMessage(response.getMsg());
                            }
                            view.findHistoryDefeated();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            view.showMessage("获取存款列表失败");
                            view.findHistoryDefeated();
                        }
                    }
                }, mContext, false));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 获取款记录
     */
    public void withdrawHistory(Map<String, Object> map) {
        HistoryView view = (HistoryView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.withdrawHistory(map)
                , new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<WithdrawalHistoryResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<WithdrawalHistoryResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            WithdrawalHistoryResult data = response.getData();
                            view.findHistorySucceed(data);
                        } else {
                            if (!TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage(response.getMsg());
                            }
                            view.findHistoryDefeated();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage("获取取款列表失败");
                            view.findHistoryDefeated();
                        }
                    }
                }, mContext, false));
        subscriptionsHelper.add(subscription);
    }

    @Override
    public void onDestory() {
        super.onDestory();
        api = null;
    }
}
