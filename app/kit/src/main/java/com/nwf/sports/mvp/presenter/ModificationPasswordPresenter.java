package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.api.IModificationPasswordApi;
import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.mvp.view.ModificationPasswordView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.data.IMDataCenter;

import rx.Subscription;

/**
 * <p>类描述： 修改密码
 * <p>创建人：Simon
 * <p>创建时间：2019-04-29
 * <p>修改人：Simon
 * <p>修改时间：2019-04-29
 * <p>修改备注：
 **/
public class ModificationPasswordPresenter<T extends IBaseView> extends BasePresenter {

    private ModificationPasswordView mView;
    private IModificationPasswordApi api = null;

    public ModificationPasswordPresenter(Context mContext, ModificationPasswordView view) {
        super(mContext, view);
        this.mView = view;
        api = RetrofitHelper.getService(IModificationPasswordApi.class);
    }



    /**
     * 修改登录密码
     */
    public void modifyPwd(String password, String newpwd) {
        if (null == mView) {
            return;
        }
        UserInfoBean userInfoBean = IMDataCenter.getInstance().getUserInfoBean();
        Subscription subscription = RxHelper.toSubscribe(api.modifyPwd(userInfoBean.username, password, newpwd),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
//                            IMDataCenter.getInstance().getUserInfoBean().setPassword(response.getData().password);
                            mView.showMessage(ResHelper.getString(R.string.str_success_modify_loginpwd));
                            mView.modifyPwdSucceed();
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                mView.showMessage(ResHelper.getString(R.string.str_fail_modify_loginpwd));
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                            mView.modifyPwdDefeated();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_fail_modify_loginpwd));
                            mView.modifyPwdDefeated();
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
