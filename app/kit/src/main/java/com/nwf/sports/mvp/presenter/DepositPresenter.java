package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.nwf.sports.mvp.api.IDepositApi;
import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.mvp.view.DepositPoinCardView;
import com.nwf.sports.mvp.view.DepositView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 存款处理
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class DepositPresenter<T extends IBaseView> extends BasePresenter {
    private T mView;
    private IDepositApi api = null;

    public DepositPresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IDepositApi.class);
    }

    /**
     * 获取存款列表
     */
    public void payChannel() {
        final DepositView view = (DepositView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.payChannel(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<DepositMannersVo>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<DepositMannersVo> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.setDepositList(response.getData());
                        } else {
                            view.showMessage(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage(e);
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }


    /**
     * 提交支付 在线支付、微信、支付宝、扫码、财付通等
     */
    public void onQueryOnlinePay(String amount, String bankcode, String handleFee, String paymannerid, String payid, String ipaddress) {
        final DepositView view = (DepositView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.onQueryOnlinePay(amount, bankcode, handleFee,
                paymannerid, payid, ipaddress),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<OnlinePay>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<OnlinePay> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.onQueryOnlinePayResult(response.getData());
                        } else {
                            view.onQueryPayResultFail(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.onQueryPayResultFail(e);
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 提交支付 BQ支付
     */
    public void onQueryFasterPay(String accountname, String amount, String bankcode, String bqpaytype, String depositor, String paymannerid) {
        final DepositView view = (DepositView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.onQueryFasterPay(accountname, amount, bankcode, bqpaytype, depositor, paymannerid),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<FasterPay>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<FasterPay> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.onQueryFasterPayResult(response.getData());
                        } else {
                            view.onQueryPayResultFail(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.onQueryPayResultFail(e);
                        }
                    }
                }, mContext));
        subscriptionsHelper.add(subscription);
    }

    /**
     * 提交支付 点卡支付
     */
    public void onRequestCardPay(Map<String, String> map) {
        final DepositPoinCardView view = (DepositPoinCardView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.onRequestCardPay(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<OnlinePay>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<OnlinePay> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            view.onRequestCardPay(response.getData());
                        } else {
                            view.showMessage(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            view.showMessage(e);
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
