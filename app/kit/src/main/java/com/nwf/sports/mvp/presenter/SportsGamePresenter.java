package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IHomeApi;
import com.nwf.sports.mvp.view.GameView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 游戏
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class SportsGamePresenter<T extends IBaseView> extends BasePresenter {

    private GameView mView;
    private IHomeApi api = null;

    public SportsGamePresenter(Context context, GameView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IHomeApi.class);
    }
    /**
     * 真实账号进入游戏
     */
    public void loginGame(Map<String, String> map, String title) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.loginGame(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<String>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<String> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.loginGame(response.getData(), title);
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("进入游戏失败");
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

    /**
     * 刷新额度
     */
    public void transferTargetGame(Map<String, String> map) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.transferTargetGame(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<String>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<String> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
//                            mView.loginGame(response.getData(), title);
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("刷新额度失败");
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
