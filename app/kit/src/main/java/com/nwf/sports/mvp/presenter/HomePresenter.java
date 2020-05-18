package com.nwf.sports.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.nwf.sports.mvp.api.IHomeApi;
import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;
import com.nwf.sports.mvp.view.HomeView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 首页
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class HomePresenter<T extends IBaseView> extends BasePresenter {

    private HomeView mView;
    private IHomeApi api = null;

    public HomePresenter(Context context, HomeView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IHomeApi.class);
    }

    /**
     * 获取游戏列表
     */
    public void gameList() {
        if (null == mView) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        Subscription subscription = RxHelper.toSubscribe(api.gameList(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<List<HomeGameResult>>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<List<HomeGameResult>> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.gameListSuccess(response.getData());
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("获取首页游戏失败");
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
     * 获取首页banner 优惠等数据
     */
    public void homePage() {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.homePage(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<HomeDiscountsResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<HomeDiscountsResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.homePageSuccess(response.getData());
                        } else {
                            if (TextUtils.isEmpty(response.getMsg())) {
                                mView.showMessage("获取首页数据失败");
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
     * 获取首页下载APP数据
     */
    public void downloadApps() {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.downloadApps(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<DownloadAppResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<DownloadAppResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.downloadApps(response.getData());
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
     * 测试账号进入游戏
     */
    public void trialGame(Map<String, String> map, String title) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.trialGame(map),
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
