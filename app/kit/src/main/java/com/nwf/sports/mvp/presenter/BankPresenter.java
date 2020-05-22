package com.nwf.sports.mvp.presenter;

import android.content.Context;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.api.IMyBankApi;
import com.nwf.sports.mvp.model.BankInfo;
import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.MyBankResult;
import com.nwf.sports.mvp.view.AddBankView;
import com.nwf.sports.mvp.view.BankManagementView;
import com.nwf.sports.mvp.view.BankView;
import com.nwf.sports.mvp.view.IBaseView;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.net.RxHelper;
import com.nwf.sports.net.request.AppTextMessageResponse;
import com.nwf.sports.net.rx.ProgressSubscriber;
import com.nwf.sports.net.rx.SubscriberOnNextListener;
import com.nwf.sports.utils.data.IMDataCenter;
import com.nwf.sports.utils.data.MyBankRepositoryCenter;

import java.util.List;
import java.util.Map;

import rx.Subscription;
import timber.log.Timber;

/**
 * <p>类描述： 银行卡
 * <p>创建人：Simon
 * <p>创建时间：2019-04-15
 * <p>修改人：Simon
 * <p>修改时间：2019-04-15
 * <p>修改备注：
 **/
public class BankPresenter<T extends IBaseView> extends BasePresenter {

    private T mView;
    private IMyBankApi api = null;


    public BankPresenter(Context context, T mView) {
        super(context, mView);
        this.mView = mView;
        api = RetrofitHelper.getService(IMyBankApi.class);
    }

    /**
     * 获取银行卡列表
     */
    public void getMyBank() {
        boolean islogin = IMDataCenter.getInstance().getUserInfoBean().isRealLogin;
        if (!islogin) {
            Timber.w("未登录的情况下不需要请求银行卡");
            return;
        }
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.getMyBank(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<MyBankResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<MyBankResult> response) {
                        if (null == mView) {
                            return;
                        }
                        MyBankResult localMyBankResult = loadLocalData();
                        if (!response.isSuccess() && !localMyBankResult.bankList.isEmpty()) {
                            response.setSuccess();
                            response.setData(localMyBankResult);
                        }
                        if (response.isSuccess()) {
                            IMDataCenter.getInstance().getMyBankRepositoryCenter().saveBankNumber(response.getData().bankList.size());
                            IMDataCenter.getInstance().getMyBankRepositoryCenter().saveMyBanks(response.getData());
                            ((BankView) mView).setMyBank(response.getData());
                        } else {
                            //真的失败了
                            ((BankView) mView).setMyBankDefeated();
                        }

                        if (!response.isSuccess() && !localMyBankResult.bankList.isEmpty()) {
                            ((BankView) mView).setMyBank(localMyBankResult);
                            return;
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            MyBankResult localMyBankResult = loadLocalData();
                            if (!localMyBankResult.bankList.isEmpty()) {
                                AppTextMessageResponse response = new AppTextMessageResponse();
                                response.setSuccess();
                                response.setData(localMyBankResult);
                                onNext(response);
                            } else {
                                //真的失败了
                                ((BankView) mView).setMyBankDefeated();
                            }
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }

    /**
     * 获取银行相关信息，例如银行列表、银行卡类型(借记卡/信用卡)
     */
    public void getAllBankInfo() {
        if (null == mView) {
            return;
        }
        AddBankView view = (AddBankView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.getAllBankInfo(),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<List<BankInfo>>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<List<BankInfo>> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess() && response.getData() != null) {
                            IMDataCenter.getInstance().getMyBankRepositoryCenter().saveAllBanks(response.getData());
                            view.AllBankInfoSucceed(response.getData());
                        } else {
                            List<BankInfo> allBanks = IMDataCenter.getInstance().getMyBankRepositoryCenter().getAllBanks();
                            if (allBanks != null) {
                                view.AllBankInfoSucceed(allBanks);
                            } else {
                                view.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != view) {
                            List<BankInfo> allBanks = IMDataCenter.getInstance().getMyBankRepositoryCenter().getAllBanks();
                            if (allBanks != null) {
                                view.AllBankInfoSucceed(allBanks);
                            } else {
                                view.showMessage(e);
                            }
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }

    /**
     * 添加银行卡
     */
    public void addBank(Map<String, String> map) {
        if (null == mView) {
            return;
        }
        AddBankView view = (AddBankView) mView;
        Subscription subscription = RxHelper.toSubscribe(api.addBank(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()) {
                            if (response.getData().flag) {
                                view.showMessage(ResHelper.getString(R.string.str_add_bank_ok));
                                view.addBankSucceed();
                            } else {
                                view.showMessage(ResHelper.getString(R.string.str_cannot_add_bank));
                            }
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage(ResHelper.getString(R.string.str_cannot_add_bank));
                            } else {
                                view.showMessage(response.getMsg());
                            }
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
     * 修改银行卡
     */
    public void modifyBank(Map<String, String> map) {
        if (null == mView) {
            return;
        }
        AddBankView view = (AddBankView) mView;

        Subscription subscription = RxHelper.toSubscribe(api.modifyBank(map),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == view) {
                            return;
                        }
                        if (response.isSuccess()&&response.getData().flag) {
                            view.showMessage(ResHelper.getString(R.string.str_modify_bank_ok));
                            view.addBankSucceed();
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                view.showMessage(ResHelper.getString(R.string.str_cannot_modify_bank));
                            } else {
                                view.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_cannot_modify_bank));
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }

    /**
     * 删除银行卡
     */
    public void delBankCard(String id) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.delBankCard(id),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            if (response.getData().flag) {
                                mView.showMessage(ResHelper.getString(R.string.str_cannot_delete_ok));
                                ((BankManagementView) mView).delBankSucceed();
                            } else {
                                mView.showMessage(ResHelper.getString(R.string.str_cannot_delete_bank));
                            }

                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                mView.showMessage(ResHelper.getString(R.string.str_cannot_delete_bank));
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage(ResHelper.getString(R.string.str_cannot_delete_bank));
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }

    /**
     * 设置默认银行卡
     */
    public void setDefaultMyBank(String bankAccountNoDes) {
        if (null == mView) {
            return;
        }
        Subscription subscription = RxHelper.toSubscribe(api.setDefaultMyBank(bankAccountNoDes),
                new ProgressSubscriber<>(new SubscriberOnNextListener<AppTextMessageResponse<CommunalResult>>() {
                    @Override
                    public void onNext(AppTextMessageResponse<CommunalResult> response) {
                        if (null == mView) {
                            return;
                        }
                        if (response.isSuccess()) {
                            if (response.getData().flag) {
                                ((BankManagementView) mView).setDefaultBankSuccess();
                            } else {
                                mView.showMessage("设置默认银行卡失败");
                            }
                        } else {
                            if (Check.isEmpty(response.getMsg())) {
                                mView.showMessage("设置默认银行卡失败");
                            } else {
                                mView.showMessage(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String e) {
                        if (null != mView) {
                            mView.showMessage("设置默认银行卡失败");
                        }
                    }
                }, mContext));

        subscriptionsHelper.add(subscription);
    }


    private MyBankResult loadLocalData() {
        MyBankRepositoryCenter repository = IMDataCenter.getInstance().getMyBankRepositoryCenter();
        MyBankResult result = repository.getMyBanks();
        return result;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        api = null;
    }
}
