package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.nwf.sports.mvp.api.IRegisterNApi;
import com.nwf.sports.mvp.model.RegisterResult;
import com.nwf.sports.mvp.view.CheckAccountView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;

import java.util.Map;

import rx.Subscription;

/**
 * <p>类描述： 账号检测
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class CheckAccountPresenter<T extends IBaseView> extends BasePresenter {
    private CheckAccountView mView;
    private IRegisterNApi api = null;

    public CheckAccountPresenter(Context context, CheckAccountView mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IRegisterNApi.class);
    }

    /**
     * 检查手机号或账号是否已经注册
     */
    public void onCheckAccount(Map<String, String> map) {
        Subscription subscription = RxHelper.toSubscribe(api.registerByPhone(map), new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<RegisterResult>>() {
            @Override
            public void onNext(AppTextMessageResponse<RegisterResult> registerResult) {//RegisterResult
                if (null != mView) {
                    //检验手机或者账号是否合法
                    if (registerResult.isSuccess()) {
                        mView.onCheckAccountSuccess(registerResult.getData());
                    } else {
                        //不合法显示提示文本消息
                        mView.onIllegal(true, registerResult.getMsg());
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
