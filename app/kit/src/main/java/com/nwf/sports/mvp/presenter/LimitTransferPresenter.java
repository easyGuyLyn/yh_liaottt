package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IHomeApi;
import com.nwf.sports.mvp.model.LimitTransferResult;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.LimitTransferView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 额度转移
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LimitTransferPresenter<T extends IBaseView> extends BasePresenter {

    private LimitTransferView mView;
    private IHomeApi api = null;

    public LimitTransferPresenter(Context context, LimitTransferView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IHomeApi.class);
    }

    /**
     * 获取刷新额度进游戏
     */
    public void transferTargetGame(Map<String, String> map) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.transferTargetGame(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<LimitTransferResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<LimitTransferResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
//                            mView.checkupgrade(response.getData());
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
//                                mView.showMessage("获取app数据失败");
                            } else {
//                                mView.showMessage(response.getMsg());
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
