package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.GetBalanceResult;
import com.nwf.sports.mvp.model.MyBankItemResult;
import com.nwf.sports.mvp.model.MyBankResult;
import com.nwf.sports.mvp.model.PreviousWithdrawResult;
import com.nwf.sports.mvp.presenter.BalancePresenter;
import com.nwf.sports.mvp.presenter.BankPresenter;
import com.nwf.sports.mvp.presenter.PhonePresenter;
import com.nwf.sports.mvp.presenter.WithdrawPresenter;
import com.nwf.sports.mvp.view.BalanceView;
import com.nwf.sports.mvp.view.BankView;
import com.nwf.sports.mvp.view.VerifyPhoneView;
import com.nwf.sports.mvp.view.WithdrawalView;
import com.nwf.sports.ui.activity.AddBankActivity;
import com.nwf.sports.ui.activity.BindPhoneActivity;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.HintCommonDialogFragment;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.BalanceAnimationUtil;
import com.nwf.sports.utils.BindPhoneFlowEnum;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.GameShipHelper;
import com.nwf.sports.utils.InputMethodUtils;
import com.nwf.sports.utils.PNCheck;
import com.nwf.sports.utils.data.IMDataCenter;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * <p>类描述： 取款
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class WithdrawalFragment extends BaseFragment implements BalanceView, WithdrawalView, BankView, VerifyPhoneView {

    private static final int WITHDRAWA_NOW = 0; //取款布局
    private static final int WITHDRAWA_ADDBANK = 1; //未绑定银行卡布局
    private static final int WITHDRAWA_INDENT = 2; //已有取款任务布局
    private static final int WITHDRAWA_RETRY = 3; //重试
    private static final int WITHDRAWA_BINDPHONE = 4; //未绑定手机号布局

    //本地余额布局
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.im_balance_refresh)
    ImageView imBalanceRefresh;

    //取款布局
    @BindView(R.id.layout_withdraw_now)
    View groupWithdrawNow;
    @BindView(R.id.et_withdrawal_money)
    EditText etWithdrawalMoney;
    @BindView(R.id.bt_all_money)
    TextView btAllMoney;
    @BindView(R.id.btn_withdraw_now)
    TextView btnWithdrawNow;
    @BindView(R.id.withdrawal_bank_list)
    RecyclerView withdrawalBankList;
    @BindView(R.id.switch_bank)
    TextView switchBank;
    @BindView(R.id.err_money_withdraw)
    TextView errMoneyWithdraw;
    List<MyBankItemResult> mBankListAll = new ArrayList<>();
    List<MyBankItemResult> mBankList = new ArrayList<>();
    CommonAdapter<MyBankItemResult> mBankdapter = null;

//    //未绑定手机号布局
//    @BindView(R.id.layout_bindphone_withdraw)
//    View bindphoneWithdraw;
//    @BindView(R.id.btn_go_bindphone)
//    TextView btnGoBindphone;
//    @BindView(R.id.tv_add_bank)
//    LinearLayout tvAddBank;

    //未绑定银行卡布局  未绑定手机号 共用同一布局
    @BindView(R.id.layout_add_bank_withdraw)
    View addBankWithdraw;
    @BindView(R.id.btn_go_bindbank)
    TextView btnGoBindbank;

    //已有取款任务布局
    @BindView(R.id.layout_indent)
    View indent;
    @BindView(R.id.im_wait)
    ImageView imWait;
    @BindView(R.id.btn_cancel_withdrawal)
    TextView btnCancelWithdrawal;
    @BindView(R.id.tv_to_service)
    TextView tvToService;

    //重试布局
    @BindView(R.id.btn_retry)
    TextView btnRetry;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.withdrawal_title)
    LinearLayout withdrawalTitle;

    BalancePresenter mBalancePresenter = null; //本地余额
    WithdrawPresenter mWithdrawPresenter = null; //取款
    BankPresenter mBankPresenter = null; //银行卡
    PhonePresenter mPhonePresenter = null; //手机号

    List<View> mViews = new ArrayList<>();

    private int present = -1; //显示布局的记录
    private String selectedBankId = "0"; //选择银行卡
    private String strBalance = ""; //余额
    HintCommonDialogFragment mHintCommonDialogFragment = null;
    PreviousWithdrawResult mPreviousWithdrawResult = null;

    public static WithdrawalFragment newInstance() {
        WithdrawalFragment fragment = new WithdrawalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_withdrawal;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        mBalancePresenter = new BalancePresenter(getActivity(), this);
        mWithdrawPresenter = new WithdrawPresenter(getActivity(), this);
        mBankPresenter = new BankPresenter(getActivity(), this);
        mPhonePresenter = new PhonePresenter(getActivity(), this);
        imgBack.setVisibility(View.VISIBLE);
        mViews.clear();
        mViews.add(groupWithdrawNow);
        mViews.add(addBankWithdraw);
        mViews.add(indent);
        mViews.add(btnRetry);

        mBankdapter = new CommonAdapter<MyBankItemResult>(getActivity(), R.layout.fragment_withdrawal_bank_item, mBankList) {
            @Override
            public void convert(ViewHolder holder, MyBankItemResult item, int position) {
                ImageView withdrawalBankBg = holder.getView(R.id.withdrawal_bank_bg);
                ImageView withdrawalBankIcon = holder.getView(R.id.withdrawal_bank_icon);
                ImageView withdrawalBankSelect = holder.getView(R.id.withdrawal_bank_select);
                TextView withdrawalBankName = holder.getView(R.id.withdrawal_bank_name);
                TextView withdrawalBankBranch = holder.getView(R.id.withdrawal_bank_branch);
                TextView withdrawalBankAccount = holder.getView(R.id.withdrawal_bank_account);
                TextView accountName = holder.getView(R.id.account_name);
                withdrawalBankName.setText(item.getBankName());
                withdrawalBankBranch.setText(item.getBranch());
                withdrawalBankAccount.setText(item.getCardNo());
                accountName.setText(item.getName());

                if (item.getBankInfo() != null) {
                    RequestOptions options = new RequestOptions().placeholder(R.mipmap.nwf_bg_bank_green);
                    //加载图片
                    Glide.with(mContext)
                            .load(item.getBankInfo().getBg())
                            .apply(options)
                            .into(withdrawalBankBg);
                    options = new RequestOptions().placeholder(R.mipmap.nwf_bank_nor);

                    //加载图片
                    Glide.with(mContext)
                            .load(item.getBankInfo().getIcon())
                            .apply(options)
                            .into(withdrawalBankIcon);
                }

                if (selectedBankId.equals("0") && position == 0) {
                    selectedBankId = item.getId();
                    withdrawalBankSelect.setVisibility(View.VISIBLE);
                }
                if (selectedBankId.equals(item.getId())) {
                    withdrawalBankSelect.setVisibility(View.VISIBLE);
                } else {
                    withdrawalBankSelect.setVisibility(View.GONE);
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedBankId.equals(item.getId())) {
                            return;
                        }
                        selectedBankId = item.getId();
                        sortBank();
                    }
                });

            }
        };
        withdrawalBankList.setLayoutManager(new LinearLayoutManager(getActivity()));
        withdrawalBankList.setAdapter(mBankdapter);


        etWithdrawalMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Timber.d("onTextChanged [%s] %d %d %d", s, start, before, count);
                String text = s.toString();
                if (Check.isEmpty(text)) {
                    btnWithdrawNow.setEnabled(false);
                    return;
                }
                checkWithdrawButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextViewLinkUtil.textLinkOnClick(getContext(), "取款有问题？", "联系客服",
                "", "#100000", false, tvToService,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
                        ActivityUtil.skipToService(getActivity());
                    }
                });
    }

    /**
     * 对银行列表进行排序
     */
    public void sortBank() {
        if (mBankListAll.size() == 0) {
            return;
        }
        MyBankItemResult myBankItemResult = null;
        for (int i = 0; i < mBankListAll.size(); i++) {
            if (selectedBankId.equals(mBankListAll.get(i).getId())) {
                myBankItemResult = mBankListAll.get(i);
                break;
            }
        }
        if (myBankItemResult != null) {
            mBankListAll.remove(myBankItemResult);
            mBankListAll.add(0, myBankItemResult);
        }
        mBankList.clear();
        mBankList.add(mBankListAll.get(0));
        Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_withdrawal_dropdown);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switchBank.setCompoundDrawables(null, null, drawable, null);
        mBankdapter.notifyDataSetChanged();

    }

    @Override
    protected void loadData() {
        isUIVisible = true;
        isViewCreated = true;
        if (IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
            etWithdrawalMoney.setText("");
            errMoneyWithdraw.setVisibility(View.GONE);
            strBalance = "";
            selectedBankId = "0";
            present = -1;
            mPreviousWithdrawResult = null;
            mHintCommonDialogFragment = null;
            mBalancePresenter.getBalance();
            mPhonePresenter.verifyPhone();
        }
    }

    /**
     * activity 获取加载数据
     */
    public void getloadData() {
        loadData();
    }

    @OnClick({R.id.im_balance_refresh, R.id.btn_withdraw_now, R.id.switch_bank, R.id.btn_retry,
            R.id.btn_go_bindbank, R.id.btn_cancel_withdrawal, R.id.bt_all_money, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_balance_refresh:
                BalanceAnimationUtil.startAnimation(imBalanceRefresh);
                mBalancePresenter.getBalance();
                break;
            case R.id.btn_withdraw_now:
                InputMethodUtils.hideSoftInput(getView());
                withdrawNow();
                break;
            case R.id.bt_all_money:
                if (TextUtils.isEmpty(strBalance) || strBalance.equals("0")) {
                    showMessage("金额需要大于1");
                    return;
                }
                int i = strBalance.indexOf(".");
                if (i == -1) {
                    if (strBalance.length() > 7) {
                        showMessage("单笔限额：100元～500万，请重新输入");
                        return;
                    }
                    etWithdrawalMoney.setText(strBalance);
                    etWithdrawalMoney.setSelection(strBalance.length());
                } else {
                    String substring = strBalance.substring(0, strBalance.indexOf("."));
                    if (substring.length() > 7) {
                        showMessage("单笔限额：100元～500万，请重新输入");
                        return;
                    }
                    etWithdrawalMoney.setText(substring);
                    etWithdrawalMoney.setSelection(substring.length());
                }
                break;
            case R.id.switch_bank:
                if (present != WITHDRAWA_NOW) {
                    return;
                }
                if (mBankdapter.getItemCount() == 1) {
                    if (mBankListAll != null && mBankListAll.size() > 1) {
                        if (mBankListAll.size() == 1) {
                            return;
                        }
                        mBankList.clear();
                        mBankList.addAll(mBankListAll);
                        Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.nwf_icon_withdrawal_right);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        switchBank.setCompoundDrawables(null, null, drawable, null);
                    }
                } else {
                    Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_withdrawal_dropdown);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    switchBank.setCompoundDrawables(null, null, drawable, null);
                    if (mBankListAll != null && mBankListAll.size() != 0) {
                        mBankList.clear();
                        mBankList.add(mBankListAll.get(0));
                    }
                }
                mBankdapter.notifyDataSetChanged();
                break;
            case R.id.btn_go_bindbank:
                if (present == WITHDRAWA_BINDPHONE) {
                    Bundle mbundle = new Bundle();
                    mbundle.putString(ConstantValue.BIND_PHONE_FLOW, BindPhoneFlowEnum.TOBINDBANK.getServicename());
                    startActivity(new Intent(getActivity(), BindPhoneActivity.class).putExtras(mbundle));
                } else if (present == WITHDRAWA_ADDBANK) {
                    startActivity(new Intent(getActivity(), AddBankActivity.class));
                } else {
                    showMessage("请刷新界面");
                }
                break;
            case R.id.btn_cancel_withdrawal:
                mHintCommonDialogFragment = new HintCommonDialogFragment()
                        .setTvTitle("取消确认")
                        .setTvContent(getString(R.string.str_title_cancel_withdraw))
                        .setOnLeftButtonListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHintCommonDialogFragment.dismissAllowingStateLoss();
                            }
                        }, getString(R.string.str_btn_donot_cancel))
                        .setOnRightButtonListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mPreviousWithdrawResult != null) {
                                    mWithdrawPresenter.cancelWithdraw(mPreviousWithdrawResult.getRequestId());
                                } else {
                                    mHintCommonDialogFragment.dismissAllowingStateLoss();
                                }
                            }
                        }, getString(R.string.str_btn_confirm_cancel));
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), mHintCommonDialogFragment);
                break;
            case R.id.btn_retry:
                loadData();
                break;
            case R.id.img_back:
                getActivity().finish();
                break;

            default:
                break;
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
        strBalance = money;
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
        tvBalance.setText(GameShipHelper.formatMoney(money));
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mBalancePresenter != null) {
            mBalancePresenter.onDestory();
        }
        if (mWithdrawPresenter != null) {
            mWithdrawPresenter.onDestory();
        }
        if (mBankPresenter != null) {
            mBankPresenter.onDestory();
        }
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
        RxBus.get().unregister(this);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
    }

    /**
     * 显示布局
     */
    public void switchShow(int sele) {
        present = sele;
        withdrawalTitle.setVisibility(View.VISIBLE);
        switch (sele) {
            case WITHDRAWA_NOW:
                Reset(WITHDRAWA_NOW);
                break;
            case WITHDRAWA_BINDPHONE:
//                Reset(WITHDRAWA_BINDPHONE);
//                break;
            case WITHDRAWA_ADDBANK:
                Reset(WITHDRAWA_ADDBANK);
                break;
            case WITHDRAWA_INDENT:
                withdrawalTitle.setVisibility(View.GONE);
                Reset(WITHDRAWA_INDENT);
                break;
            case WITHDRAWA_RETRY:
                withdrawalTitle.setVisibility(View.GONE);
                Reset(WITHDRAWA_RETRY);
                break;
            default:
                break;
        }
    }

    public void Reset(int sele) {
        for (int i = 0; i < mViews.size(); i++) {
            if (sele == i) {
                mViews.get(i).setVisibility(View.VISIBLE);
            } else {
                mViews.get(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setMyBank(MyBankResult data) {
        if (null != data && null != data.bankList) {
            if (data.bankList.isEmpty()) { // 显示添加银行卡界面
                switchShow(WITHDRAWA_ADDBANK);
            } else {
                mBankListAll.clear();
                mBankListAll.addAll(data.bankList);
                switchShow(WITHDRAWA_NOW);
                mBankList.clear();
                if (mBankListAll != null && mBankListAll.size() != 0) {
                    for (int index = 0; index < mBankListAll.size(); index++) {
                        MyBankItemResult item = mBankListAll.get(index);
                        if (item.getDef().equals("1")) {
                            mBankList.add(item);
                            mBankListAll.remove(item);
                            mBankListAll.add(0, item);
                            break;
                        }
                    }
                    if (mBankList.size() == 0) {
                        mBankList.add(mBankListAll.get(0));
                    }
                }
                mBankdapter.notifyDataSetChanged();
                mWithdrawPresenter.queryPreviousWithdraw();
            }

        } else { //点击重试
            switchShow(WITHDRAWA_RETRY);
        }
    }

    @Override
    public void setMyBankDefeated() {
        switchShow(WITHDRAWA_RETRY);
    }

    @Override
    public void withdrawMoney() {
        loadData();
    }

    @Override
    public void queryPreviousWithdraw(PreviousWithdrawResult result) {
        if (null != result && (result.getFlag() == 99 || result.getFlag() == 2 || result.getFlag() == -1 || result.getFlag() == -2 || result.getFlag() == -3)) { //取款布局
            switchShow(WITHDRAWA_NOW);
        } else {//存在订单
            mPreviousWithdrawResult = result;
            switchShow(WITHDRAWA_INDENT);
        }
    }

    @Override
    public void queryDefeated() {
        switchShow(WITHDRAWA_RETRY);
    }

    @Override
    public void cancelWithdrawMoney() {
        mHintCommonDialogFragment.dismissAllowingStateLoss();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUIVisible) {
            loadData();
        }
    }

    private boolean checkWithdrawButton() {

        boolean enable = checkAll();
        btnWithdrawNow.setEnabled(enable);
        return enable;
    }

    private void withdrawNow() {
        if (checkAll()) {
            if (DoubleClickHelper.getNewInstance().isFastClick()) {
                return;
            }
            String money = etWithdrawalMoney.getText().toString();
            mWithdrawPresenter.withdrawMoney(selectedBankId, money);
        }
    }


    private boolean checkAll() {
        boolean moneyOk = checkMoney();
        boolean bankOk = (!selectedBankId.equals("0"));
        return moneyOk && bankOk;
    }

    private boolean checkMoney() {
        if (TextUtils.isEmpty(strBalance)) {
            mBalancePresenter.getBalance();
            LogUtils.e("请求余额失败");
            return false;
        }
        String money = etWithdrawalMoney.getText().toString().trim();
        PNCheck.CheckResult checkResult = PNCheck.checkWithdrawMoney(money, strBalance);
        if (checkResult.isResultOk) {
            errMoneyWithdraw.setVisibility(View.INVISIBLE);
        } else {
            errMoneyWithdraw.setVisibility(View.VISIBLE);
            errMoneyWithdraw.setText(checkResult.msg);
        }
        return checkResult.isResultOk;
    }

    @Override
    public void verifyPhoneSucceed(boolean isSuccess) {
        if (isSuccess) {//已绑定手机号，去检查我的银行卡
            mBankPresenter.getMyBank();
        } else {//没有绑定手机号，去绑定
            switchShow(WITHDRAWA_BINDPHONE);
        }
    }

    @Override
    public void verifyPhoneDefeated(boolean isShow) {  //检查手机号失败重试
        switchShow(WITHDRAWA_RETRY);
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
        if (mWithdrawPresenter != null) {
            mWithdrawPresenter.onDestory();
        }
        if (mBankPresenter != null) {
            mBankPresenter.onDestory();
        }
        if (mPhonePresenter != null) {
            mPhonePresenter.onDestory();
        }
        mBalancePresenter = new BalancePresenter(getActivity(), this);
        mWithdrawPresenter = new WithdrawPresenter(getActivity(), this);
        mBankPresenter = new BankPresenter(getActivity(), this);
        mPhonePresenter = new PhonePresenter(getActivity(), this);
    }

}
