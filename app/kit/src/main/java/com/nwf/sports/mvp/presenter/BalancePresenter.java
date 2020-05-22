package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.mvp.api.IBalanceApi;
import com.nwf.sports.mvp.model.GetBalanceResult;
import com.nwf.sports.mvp.view.BalanceView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.data.IMDataCenter;

import rx.Subscription;
import timber.log.Timber;

/**
 * <p>类描述： 本地余额
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class BalancePresenter<T extends IBaseView> extends BasePresenter {

    private T mView;
    private IBalanceApi api = null;

    private static long lastGetBalanceTimeMS = 0;

    public BalancePresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IBalanceApi.class);
    }

    /**
     * 请求余额—本地余额
     */
    public void getBalance() {
        boolean islogin = IMDataCenter.getInstance().getUserInfoBean().isRealLogin;
        if (!islogin) {
            Timber.w("未登录的情况下不需要请求余额");
            return;
        }
        if (null == mView) {
            return;
        }
        long curTime = System.currentTimeMillis();
        if (curTime - lastGetBalanceTimeMS >= 5000) {
            Timber.d("ok，余额请求间隔比较大");
            lastGetBalanceTimeMS = curTime;
        } else {
            Timber.d("过滤短时间内的余额请求 %d-%d=%d", curTime, lastGetBalanceTimeMS, (curTime - lastGetBalanceTimeMS));
            ((BalanceView) mView).shortTime();
            return;
        }

        Subscription subscription = RxHelper.toSubscribe(api.getBalance(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<GetBalanceResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<GetBalanceResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess() && null != response.getData() && null != response.getData().getTotalBalance()) {
                            GetBalanceResult getBalanceResult = response.getData();
                            IMDataCenter.getInstance().getMyLocalCenter().saveBalance(getBalanceResult.getTotalBalance().toPlainString());
                            RxBus.get().post(ConstantValue.REFRESH_BALANCE, getBalanceResult.getTotalBalance().toPlainString()); //通知其他 界面余额数据变更
                            ((BalanceView) mView).setBalance(getBalanceResult);
                        } else {
                            mView.showMessage("刷新余额失败");
                        }

                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage("刷新余额失败"+e);
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
