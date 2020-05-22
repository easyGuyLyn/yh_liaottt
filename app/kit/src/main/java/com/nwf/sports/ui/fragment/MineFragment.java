package com.nwf.sports.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.GetBalanceResult;
import com.nwf.sports.mvp.model.PersonalInfoResult;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.mvp.presenter.BalancePresenter;
import com.nwf.sports.mvp.presenter.PersonalInfoPresenter;
import com.nwf.sports.mvp.view.BalanceView;
import com.nwf.sports.mvp.view.PersonalInfoView;
import com.nwf.sports.ui.activity.BankManagementActivity;
import com.nwf.sports.ui.activity.BindPhoneActivity;
import com.nwf.sports.ui.activity.HistoryActivity;
import com.nwf.sports.ui.activity.ManagementPhoneActivity;
import com.nwf.sports.ui.activity.ModificationPasswordActivity;
import com.nwf.sports.ui.activity.RedPacketHistoryActivity;
import com.nwf.sports.ui.activity.WithdrawalActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LogoutDialogFragment;
import com.nwf.sports.ui.dialogfragment.ModificationNameDialogFragment;
import com.nwf.sports.ui.views.StarBarView;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.BalanceAnimationUtil;
import com.nwf.sports.utils.BindPhoneFlowEnum;
import com.nwf.sports.utils.GameShipHelper;
import com.nwf.sports.utils.data.IMDataCenter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.GlideApp;
import cn.wildfire.chat.kit.WfcScheme;
import cn.wildfire.chat.kit.common.OperateResult;
import cn.wildfire.chat.kit.qrcode.QRCodeActivity;
import cn.wildfire.chat.kit.third.utils.ImageUtils;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.model.UserInfo;

/**
 * <p>类描述： 个人主页
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class MineFragment extends BaseFragment implements BalanceView, PersonalInfoView {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_imuser_name)
    TextView tvImuserName;
    @BindView(R.id.sbv_starbar)
    StarBarView sbvStarbar;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.im_balance_refresh)
    ImageView imBalanceRefresh;
    @BindView(R.id.ly_account_records)
    LinearLayout lyAccountRecords;
    @BindView(R.id.ly_mobile_management)
    LinearLayout lyMobileManagement;
    @BindView(R.id.ly_bank_management)
    LinearLayout lyBankManagement;
    @BindView(R.id.ly_modification_password)
    LinearLayout lyModificationPassword;
    @BindView(R.id.ly_service)
    LinearLayout lyService;
    @BindView(R.id.ly_check_update)
    LinearLayout lyCheckUpdate;
    @BindView(R.id.tv_secede)
    TextView tvSecede;
    @BindView(R.id.image_head)
    ImageView imageHead;

    BalancePresenter mBalancePresenter = null; //本地余额
    PersonalInfoPresenter mPersonalInfoPresenter = null;
    UserViewModel userViewModel = null;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        mBalancePresenter = new BalancePresenter(getActivity(), this);
        mPersonalInfoPresenter = new PersonalInfoPresenter(getActivity(), this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin && IMApplication.isGain) {
            mPersonalInfoPresenter.getPersonalInfo();
        }
    }

    @Override
    protected void loadData() {
        isUIVisible = true;
        isViewCreated = true;
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
            mPersonalInfoPresenter.getPersonalInfo();
        }
    }


    @OnClick({R.id.im_balance_refresh, R.id.ly_account_records, R.id.ly_mobile_management, R.id.ly_bank_management,
            R.id.ly_modification_password, R.id.ly_service, R.id.ly_check_update, R.id.tv_secede, R.id.ly_qr_code, R.id.ly_redpacket, R.id.ly_withdrawal
            , R.id.tv_imuser_name, R.id.image_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_balance_refresh:
                BalanceAnimationUtil.startAnimation(imBalanceRefresh);
                mBalancePresenter.getBalance();
                break;
            case R.id.ly_account_records: //账户记录
                if (IMApplication.isChat) {
                    return;
                }
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
            case R.id.ly_mobile_management: //手机管理
                if (IMApplication.isChat) {
                    return;
                }
                if (TextUtils.isEmpty(IMDataCenter.getInstance().getUserInfoBean().getPhone())) {
                    Bundle mbundle = new Bundle();
                    mbundle.putString(ConstantValue.BIND_PHONE_FLOW, BindPhoneFlowEnum.TOMAIN.getServicename());
                    startActivity(new Intent(getActivity(), BindPhoneActivity.class).putExtras(mbundle));
                } else {
                    startActivity(new Intent(getActivity(), ManagementPhoneActivity.class));
                }
                break;
            case R.id.ly_bank_management: //银行卡管理
                if (IMApplication.isChat) {
                    return;
                }
                startActivity(new Intent(getActivity(), BankManagementActivity.class));
                break;
            case R.id.ly_redpacket: //红包记录
                if (IMApplication.isChat) {
                    return;
                }
                startActivity(new Intent(getActivity(), RedPacketHistoryActivity.class));
                break;
            case R.id.ly_withdrawal: //取款
                if (IMApplication.isChat) {
                    return;
                }
                startActivity(new Intent(getActivity(), WithdrawalActivity.class));
                break;
            case R.id.ly_qr_code: //二维码
                UserInfo userInfo = userViewModel.getUserInfo(userViewModel.getUserId(), false);
                String qrCodeValue = WfcScheme.QR_CODE_PREFIX_USER + userInfo.uid;
                startActivity(QRCodeActivity.buildQRCodeIntent(getActivity(), "二维码", userInfo.portrait, qrCodeValue));
                break;
            case R.id.ly_modification_password: //修改密码
                if (IMApplication.isChat) {
                    return;
                }
                startActivity(new Intent(getActivity(), ModificationPasswordActivity.class));
                break;
            case R.id.ly_service: //联系客服
                if (IMApplication.isChat) {
                    return;
                }
                ActivityUtil.skipToService(getActivity());
                break;
            case R.id.tv_imuser_name: //用户名
                ModificationNameDialogFragment modificationNameDialogFragment = new ModificationNameDialogFragment();
                modificationNameDialogFragment.setSucceedListener(new ModificationNameDialogFragment.SucceedListener() {
                    @Override
                    public void succeed() {
                        imUserInfo();
                    }
                });
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), modificationNameDialogFragment);
                break;
            case R.id.image_head: //头像
                updatePortrait();
                break;
            case R.id.tv_secede:  //退出登录
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
                logoutDialogFragment.setOnLeftButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFramentManager.getInstance().clearDialog();
                        mPersonalInfoPresenter.logout();
                    }
                });
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), logoutDialogFragment);
                break;
        }
    }

    private static final int REQUEST_CODE_PICK_IMAGE = 100;

    private void updatePortrait() {
        ImagePicker.picker().pick(this, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            String imagePath = ImageUtils.genThumbImgFile(images.get(0).path).getAbsolutePath();
            MutableLiveData<OperateResult<Boolean>> result = userViewModel.updateUserPortrait(imagePath);
            result.observe(this, booleanOperateResult -> {
                if (booleanOperateResult.isSuccess()) {
                    Toast.makeText(getActivity(), "更新头像成功", Toast.LENGTH_SHORT).show();
                    imUserInfo();
                } else {
                    Toast.makeText(getActivity(), "更新头像失败: " + booleanOperateResult.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void setBalance(GetBalanceResult result) {
        setMoney(result.getTotalBalance().toPlainString());
    }

    @Override
    public void shortTime() {
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
    }

    private void setMoney(String money) {
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
        tvBalance.setText(GameShipHelper.formatMoney(money));
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mBalancePresenter != null) {
            mBalancePresenter.onDestory();
        }
        if (mPersonalInfoPresenter != null) {
            mPersonalInfoPresenter.onDestory();
        }
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
        RxBus.get().unregister(this);
    }

    @Override
    public void PersonalInfoSucceed(PersonalInfoResult result) {
        UserInfoBean userInfoBean = IMDataCenter.getInstance().getUserInfoBean();
        userInfoBean.setUsername(result.getUserName());
        userInfoBean.setPhone(result.getMobile());
        userInfoBean.setRealName(result.getRealName());
        userInfoBean.setDepositLevel(result.getLevelNum());
        userInfoBean.setLocalBalance(result.getLocalBalance());
        IMDataCenter.getInstance().setUserInfoBean(userInfoBean);
        setMoney(result.getLocalBalance() + "");
        sbvStarbar.setStarRating(result.getLevelNum());
        imUserInfo();
        tvUserName.setText("账号：" + result.getUserName());
    }

    public void imUserInfo() {
        UserInfo userInfo = userViewModel.getUserInfo(userViewModel.getUserId(), false);
        tvImuserName.setText(userInfo.displayName);
        GlideApp
                .with(mContext)
                .load(userInfo.portrait)
                .transforms(new CenterCrop(), new RoundedCorners(10))
                .error(R.mipmap.im_default_header)
                .into(imageHead);
    }

    @Override
    public void logoutSucceed() {
        RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
    }

    /**
     * 刷新用户信息
     */
    @Subscribe(tags = {@Tag(ConstantValue.REFRESH_USERINFO)})
    public void refreshUserinfo(String string) {
        loadData();
    }

    /**
     * 余额刷新成功
     */
    @Subscribe(tags = {@Tag(ConstantValue.REFRESH_BALANCE)})
    public void refreshBalance(String string) {
        setMoney(string);
    }

    /**
     * 线路chcek完成 刷新网络层
     */
    @Subscribe(tags = {@Tag(ConstantValue.START_REQUEST)})
    public void startRequest(String type) {
        if (mBalancePresenter != null) {
            mBalancePresenter.onDestory();
        }
        if (mPersonalInfoPresenter != null) {
            mPersonalInfoPresenter.onDestory();
        }
        mBalancePresenter = new BalancePresenter(getActivity(), this);
        mPersonalInfoPresenter = new PersonalInfoPresenter(getActivity(), this);
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin && IMApplication.isGain) {
            mPersonalInfoPresenter.getPersonalInfo();
        }
    }

}
