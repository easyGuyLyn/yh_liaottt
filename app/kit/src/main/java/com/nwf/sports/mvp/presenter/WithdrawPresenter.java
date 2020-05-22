package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.api.IWithdrawApi;
import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.PreviousWithdrawResult;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.WithdrawalView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.data.IMDataCenter;

import rx.Subscription;
import timber.log.Timber;

/**
 * <p>类描述： 取款
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class WithdrawPresenter<T extends IBaseView> extends BasePresenter {

    private T mView;
    private IWithdrawApi api = null;

    public WithdrawPresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IWithdrawApi.class);
    }

    /**
     * 提交取款
     *
     * @param bankid
     * @param money
     */
    public void withdrawMoney(String bankid, String money) {
        boolean islogin = IMDataCenter.getInstance().getUserInfoBean().isRealLogin;
        if (!islogin) {
            Timber.w("未登录的情况下不需要请求余额");
            return;
        }
        if (null == mView) {
            return;
        }

        Subscription subscription = RxHelper.toSubscribe(api.withdrawMoney(bankid, money),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == mView) {
                            return;
                        }

                        if (response.isSuccess()) {
                            ((WithdrawalView) mView).withdrawMoney();
                        } else {
                            mView.showMessage(response.getMsg());
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


    /**
     * 查询上一笔订单
     */
    public void queryPreviousWithdraw() {

        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.queryPreviousWithdraw(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<PreviousWithdrawResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<PreviousWithdrawResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            ((WithdrawalView) mView).queryPreviousWithdraw(response.getData());
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                mView.showMessage(ResHelper.getString(R.string.str_fail_query_withdraw_progress));
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                            ((WithdrawalView) mView).queryDefeated();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_fail_query_withdraw_progress));
                            ((WithdrawalView) mView).queryDefeated();
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }


    /**
     * 取消取款
     */
    public void cancelWithdraw(String id) {

        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.cancelWithdraw(id),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse>() {
                    @Override
                    public void onNext(AppTextMessageResponse response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.showMessage(ResHelper.getString(R.string.str_success_cancel_withdraw));
                            ((WithdrawalView) mView).cancelWithdrawMoney();
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                mView.showMessage(ResHelper.getString(R.string.str_fail_cancel_withdraw));
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_fail_cancel_withdraw));
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
