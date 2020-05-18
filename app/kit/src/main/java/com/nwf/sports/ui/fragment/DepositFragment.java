package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.NetworkUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.DepositTransferBean;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.mvp.model.GetBalanceResult;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.mvp.presenter.BalancePresenter;
import com.nwf.sports.mvp.presenter.DepositPresenter;
import com.nwf.sports.mvp.view.BalanceView;
import com.nwf.sports.mvp.view.DepositView;
import com.nwf.sports.ui.activity.DepositPointCardActivity;
import com.nwf.sports.ui.activity.DepositTransferActivity;
import com.nwf.sports.ui.activity.DepositbankFasterActivity;
import com.nwf.sports.ui.activity.webview.OnlinePlayActivity;
import com.nwf.sports.ui.dialogfragment.DepositChoiceBankDialogFragment;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.HintCommonDialogFragment;
import com.nwf.sports.ui.dialogfragment.PhoneBindDingDialogFragment;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.BalanceAnimationUtil;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.GameShipHelper;
import com.nwf.sports.utils.InputMethodUtils;
import com.nwf.sports.utils.data.DataCenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 存款页面
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class DepositFragment extends BaseFragment implements DepositView, BalanceView {

    private static final int DEFAULT_LOW_VALUE = 10; // 最小限额

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.im_balance_refresh)
    ImageView imBalanceRefresh;
    @BindView(R.id.et_deposit_money)
    EditText etDepositMoney;
    @BindView(R.id.rv_dep_amount)
    RecyclerView rvDepAmount;
    @BindView(R.id.rv_dep_manners)
    RecyclerView rvDepManners;
    @BindView(R.id.btn_dep_to_pay)
    TextView btnDepToPay;
    @BindView(R.id.tvw_dep_money_err)
    TextView tvwDepositMoneyError;
    @BindView(R.id.ll_money_fee)
    View llMoneyFee;
    @BindView(R.id.tvw_money_fee)
    TextView tvwMoneyFee;

    private List<String> mMoneyStringList = new ArrayList<>();
    private CommonAdapter<String> mMoneyAdapter = null;
    private int amountNumber = -1;  //选择金额的下标

    private List<DepositMannersVo.DepMannersVo> mPayTypeList = new ArrayList<>();//存款方式数据列表
    private CommonAdapter<DepositMannersVo.DepMannersVo> mPayTypedapter = null; //存款方式adapter

    private DepositPresenter mDepositPresenter = null; //存款网络层
    private DepositMannersVo mDepositMannersVo; //存款方式
    private String quotaValue = "100,500,1000,5000,10000";
    private float amount = 0; //输入框的金额记录
    private String money = "0"; //输入框的金额

    private DepositMannersVo.DepMannersVo curSelected = new DepositMannersVo.DepMannersVo(); // 当前选中的存款方式的
    private DepositMannersVo.SubPaymentVo paymentListBean = new DepositMannersVo.SubPaymentVo();//具体某一种转账汇款的方式
    private String transferTypeCode = "";
    private String bankcode = ""; // 100016在线支付银行编码 取银行的id
    private boolean isNeedSysBrowser;  // 是否需要使用手机自带的浏览器打开(默认:否)

    private DepositTransferBean mDepositTransferBean = null;    //Bq存款

    private BalancePresenter mBalancePresenter = null; //本地余额

    private HintCommonDialogFragment hintCommonDialogFragment = null;

    private Handler mHandler = new Handler();


    public static DepositFragment newInstance() {
        DepositFragment fragment = new DepositFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_deposit;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        mDepositPresenter = new DepositPresenter(getActivity(), this);
        mBalancePresenter = new BalancePresenter(getActivity(), this);
        etDepositMoney.addTextChangedListener(moneyWatcher);
        setAdapter();
        //缓存
        mDepositMannersVo = DataCenter.getInstance().getMyLocalCenter().getDepositManners();
        if (null != mDepositMannersVo) {
            setDepositList(mDepositMannersVo);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDepositPresenter != null) {
            mDepositPresenter.onDestory();
        }
        if (mBalancePresenter != null) {
            mBalancePresenter.onDestory();
        }
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
        RxBus.get().unregister(this);
    }

    /**
     * 控制输入框金额 与列表交互
     */
    TextWatcher moneyWatcher = new TextWatcher() {

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            tvwDepositMoneyError.setVisibility(View.GONE); // 默认隐藏
            llMoneyFee.setVisibility(View.GONE);
            onVerifyNext(false);

            String text = s.toString();
            LogUtils.e(text);
            etDepositMoney.setSelection(s.length());

            if (text.length() > 0) {
                if (amountNumber != -1 && null != mMoneyAdapter && (TextUtils.isEmpty(mMoneyStringList.get(amountNumber)) || !text.equals(mMoneyStringList.get(amountNumber)))) {
                    amountNumber = -1;
                    mMoneyAdapter.notifyDataSetChanged();
                }
            } else {
                amount = 0;
                money = "0";
                curSelected = new DepositMannersVo.DepMannersVo();
                amountNumber = -1;
                if (null != mMoneyAdapter) {
                    mMoneyAdapter.notifyDataSetChanged(); // 金额清空后,下面的金额选择项要取消选中状态
                }
            }

            if (text.startsWith("0") || text.startsWith(".")) {  //不能以0 或者小数点开始
                text = "";
                etDepositMoney.setText(text);
                etDepositMoney.setSelection(text.length());
                onVerifyNext(false);
                return;
            }

            // 小数点后不得超过2位
            if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                text = text.substring(0, text.indexOf(".") + 3);
                etDepositMoney.setText(text);
                etDepositMoney.setSelection(text.length());
            }

            // 存款方式 最低最高限额
            if (text.length() > 0) {
                float amo = Float.valueOf(text);
                if (mDepositMannersVo != null) {
                    amount = amo;
                    if (amount < mDepositMannersVo.lowestvalue) {
                        tvwDepositMoneyError.setVisibility(View.VISIBLE);
                        // tvwDepositMoneyError.setText("小于最低存款限额（" + mVo.lowestvalue + "元）");
                        String txt = getResources().getString(R.string.str_dep_amount_too_low);
                        tvwDepositMoneyError.setText(String.format(txt, convertTo2BitDot(mDepositMannersVo.lowestvalue)));
                    } else if (amount > mDepositMannersVo.highestvalue) {
                        tvwDepositMoneyError.setVisibility(View.VISIBLE);
                        // tvwDepositMoneyError.setText("大于最高存款限额（" + mVo.highestvalue + "元）");
                        String txt = getResources().getString(R.string.str_dep_amount_too_high);
                        tvwDepositMoneyError.setText(String.format(txt, convertTo2BitDot(mDepositMannersVo.highestvalue)));
                    }
                }
            } else {
                if (null != mPayTypedapter && null != mDepositMannersVo && null != mDepositMannersVo.newPayList) {
                    mPayTypeList.clear();
                    mPayTypeList.addAll(mDepositMannersVo.newPayList);
                }
            }
            money = s.toString();
            if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(curSelected.getPaymentKey())) {
                quotaInstall(curSelected.subPaymentList);
            } else {
                mPayTypedapter.notifyDataSetChanged();
            }
        }
    };


    /**
     * 定额数据设置
     */
    public void quotaInstall(List<DepositMannersVo.SubPaymentVo> subPaymentList) {
        paymentListBean = new DepositMannersVo.SubPaymentVo();
        // 挑选一个存款方式 List<SubPaymentVo> subPaymentList
        for (DepositMannersVo.SubPaymentVo vo : subPaymentList) {
            LogUtils.e(vo.toString());
            // 先判断 最小值、最大值
            if (amount < vo.lowestvalue || amount > vo.highestvalue) {
                LogUtils.e("存款金额不在区间内");
                continue;
            }

            // BQ/在线支付 存款,必须至少有一个银行,否则不能使用
            if ("1".equals(vo.paymannerid)) {
                if (vo.extra == null || vo.extra.isEmpty()) {
                    LogUtils.e("在线支付 银行列表为空 ");
                    continue;
                }
            }

            // 如果是定额支付,且输入的金额不在定额列表范围内,则进入下一个循环
            // 支付宝支付仅支持特定额度，已为您自动调整金额
            if (null != vo.getAmountList() && vo.getAmountList().size() > 0) {
                List<String> lst = vo.getAmountList();
                String txt = String.valueOf(amount);
                txt = txt.endsWith(".0") || txt.endsWith(".00") ? txt.substring(0, txt.indexOf(".")) : txt;
                if (lst.contains(txt)) {
                    LogUtils.e("money 包含在定额列表");
                } else if (amount < Float.valueOf(lst.get(0))) {
                    LogUtils.e("money 小于最低定额");
                    continue;
                } else if (amount > Float.valueOf(lst.get(lst.size() - 1))) {
                    LogUtils.e("money 大于最高定额");
                    continue;
                } else {
                    for (int i = 0; i < lst.size() - 1; i++) {  //自动调整金额
                        if (amount > Float.valueOf(lst.get(i)) && amount < Float.valueOf(lst.get(i + 1))) {
                            LogUtils.e("money: " + lst.get(i + 1));
                            etDepositMoney.setText(lst.get(i + 1));
                            // 支付宝支付仅支持特定额度，已为您自动调整金额
                            tvwDepositMoneyError.setText(R.string.str_dep_zfb_fixed_amount);
                            tvwDepositMoneyError.setVisibility(View.VISIBLE);
                            mMoneyAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
            paymentListBean = vo;
            onVerifyNext(true); // 存款/下一步按钮 置为可点击
            mPayTypedapter.notifyDataSetChanged();
            return; // 取第一个可用的
        }

        // 如果选中,但是子存款方式 没有一个可以用,也要设置为未选中+不可点击
        if (TextUtils.isEmpty(paymentListBean.paymannerid)) {
            // 选择的存款方式 没有实际可用的子存款方式
            LogUtils.e("选择的存款方式 没有实际可用的子存款方式.");
            curSelected = new DepositMannersVo.DepMannersVo();
        }
        mPayTypedapter.notifyDataSetChanged();
    }

    /**
     * 设置Adapter
     */
    public void setAdapter() {
        mMoneyAdapter = new CommonAdapter<String>(getActivity(), R.layout.fragment_deposit_amount_item, mMoneyStringList) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
                TextView textView = holder.getView(R.id.tv_money);
                textView.setText(item);
                if (amountNumber != -1 && amountNumber == position) {
                    textView.setSelected(true);
                } else {
                    textView.setSelected(false);
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (amountNumber != position) {
                            amountNumber = position;
                            etDepositMoney.setText(item); // addWatcher 用到
                            etDepositMoney.setSelection(item.length());
                            mMoneyAdapter.notifyDataSetChanged();
                        } else {
                            amountNumber = -1;
                            mMoneyAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        rvDepAmount.setAdapter(mMoneyAdapter);

        mPayTypedapter = new CommonAdapter<DepositMannersVo.DepMannersVo>(getActivity(), R.layout.fragment_deposit_type_item, mPayTypeList) {
            @Override
            public void convert(ViewHolder holder, DepositMannersVo.DepMannersVo item, int position) {
                LinearLayout convertView = (LinearLayout) holder.getConvertView();
                TextView type = holder.getView(R.id.tv_type);
                TextView tvTypeQuota = holder.getView(R.id.tv_type_quota);
                ImageView imDepositeIcon = holder.getView(R.id.im_deposite_icon);

                String min = String.valueOf(item.lowestvalue).replace(".0", "");
                String max = String.valueOf(item.highestvalue).replace(".0", "");
                if (item.highestvalue >= 10000) {
                    max = String.valueOf((long) item.highestvalue / 10000) + "万";
                }
                String amountDesc = min + "~" + max;
                if ("DKZF".equalsIgnoreCase(item.getPaymentKey())) {
                    amountDesc = "固定额度"; // 点卡支付要显示 固定额度
                }

                type.setText(item.payName);
                tvTypeQuota.setText(amountDesc);
                convertView.setEnabled(false);
                type.setEnabled(false);

                Glide.with(mContext)
                        .load(Uri.parse(item.getIconUrl()))
                        .into(imDepositeIcon);

                if (isCanCheck(item)) {
                    convertView.setEnabled(true);
                    type.setEnabled(true);
                    if (null != curSelected && !TextUtils.isEmpty(curSelected.getPaymentKey()) && item.getPaymentKey().equals(curSelected.getPaymentKey())) {
                        Glide.with(mContext)
                                .load(Uri.parse(item.getSelectIconUrl()))
                                .into(imDepositeIcon);
                        convertView.setSelected(true);
                    } else {
                        convertView.setSelected(false);
                    }
                } else {
                    convertView.setEnabled(false);
                    type.setEnabled(false);
                    convertView.setSelected(false);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onVerifyNext(false); // 存款/下一步 默认不可点击,有符合条件的 才可以点击
                        paymentListBean = new DepositMannersVo.SubPaymentVo();
                        transferTypeCode = "";
                        tvwDepositMoneyError.setVisibility(View.GONE);
                        llMoneyFee.setVisibility(View.GONE);

                        InputMethodUtils.hideSoftInput(getActivity());
                        if (null != curSelected && item.getPaymentKey().equals(curSelected.getPaymentKey())) {
                            curSelected = new DepositMannersVo.DepMannersVo(); // 如果同一个按钮选中了又取消,重置
                            mPayTypedapter.notifyDataSetChanged();
                            return;
                        }
                        LogUtils.e("isChecked: " + item.getPaymentKey().equals(curSelected.getPaymentKey()) + ", " + " ---> " + item.paymentKey + ", " + item.payName);
                        LogUtils.e(item.toString());
                        curSelected = item;
                        quotaInstall(item.subPaymentList);
//                        // 如果选中,但是子存款方式 没有一个可以用,也要设置为未选中+不可点击
//                        if (TextUtils.isEmpty(paymentListBean.paymannerid)) {
//                            // 选择的存款方式 没有实际可用的子存款方式
//                            LogUtils.e("选择的存款方式 没有实际可用的子存款方式.");
//                            convertView.setEnabled(false);
//                            convertView.setSelected(false);
//                            type.setEnabled(false);
//                            curSelected = new DepositMannersVo.DepMannersVo();
//                        }
//                        mPayTypedapter.notifyDataSetChanged();
                    }
                });

            }

            /**
             * 判断其输入金额是否在存款区间  是否有在定额区间
             * @param t DepMannersVo
             * @return true-可选中可点击,有可用的子存款方式;<br /> false-不可用
             */
            private boolean isCanCheck(DepositMannersVo.DepMannersVo t) {

                // 如果子存款方式 列表为空,则无法使用
                if (t.subPaymentList == null || t.subPaymentList.isEmpty()) {
                    LogUtils.e("没有子存款方式");
                    return false;
                }

                // 挑选一个存款方式 List<SubPaymentVo> subPaymentList
                for (DepositMannersVo.SubPaymentVo vo : t.subPaymentList) {

                    // 先判断 最小值、最大值 不在区间则进入下一个循环
                    if (amount < vo.lowestvalue || amount > vo.highestvalue) {
                        LogUtils.e("存款金额不在区间内 ");
                        continue;
                    }

                    // 在线支付 存款,必须至少有一个银行,否则不能使用
                    if ("1".equals(vo.paymannerid)) {
                        if (vo.extra == null || vo.extra.isEmpty()) {
                            LogUtils.e(" 在线支付 银行列表为空");
                            continue;
                        }
                    }

                    // 支付宝/微信 手续费
                    if (null != curSelected.paymentKey && ("ZFB".equals(curSelected.paymentKey) && t.paymentKey.equals("ZFB") || "WX".equals(curSelected.paymentKey) && t.paymentKey.equals("WX"))) {
                        if (vo.getHandleFee() > 0) {
                            String s = etDepositMoney.getText().toString();
                            if (!TextUtils.isEmpty(s)) {
                                float fee = Float.valueOf(etDepositMoney.getText().toString()) * vo.getHandleFee() / 100;
                                tvwMoneyFee.setText(convertTo2BitDot(fee));
                                llMoneyFee.setVisibility(View.VISIBLE);
                            }
                        } else {
                            llMoneyFee.setVisibility(View.GONE);
                        }
                    }

                    // 如果是定额支付,且输入的金额不在定额列表范围内,则进入下一个循环
                    // 支付宝支付仅支持特定额度，已为您自动调整金额
                    if (null != vo.getAmountList() && vo.getAmountList().size() > 0) {
                        List<String> lst = vo.getAmountList();
                        String txt = String.valueOf(amount);
                        txt = txt.endsWith(".0") || txt.endsWith(".00") ? txt.substring(0, txt.indexOf(".")) : txt;
                        if (lst.contains(txt)) {
                            LogUtils.e("money 包含在定额列表");
                            return true;
                        } else if (amount < Float.valueOf(lst.get(0))) {
                            LogUtils.e("money 小于最低定额");
                            continue;
                        } else if (amount > Float.valueOf(lst.get(lst.size() - 1))) {
                            LogUtils.e("money 大于最高定额");
                            continue;
                        } else {
                            // 在最小值和最大值中间,可以向上取整
                            return true;
                        }
                    }
                    return true; // 取第一个可用的
                } // for 循环结束

                // 存款方式(大类) 没有实际可用的子存款方式
                LogUtils.e(t.getPayName() + ", No SubPaymentVo available...");
                return false;
            }
        };
        rvDepManners.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvDepManners.setAdapter(mPayTypedapter);
    }

    @Override
    protected void loadData() {
        isUIVisible = true;
        isViewCreated = true;
        if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            mDepositPresenter.payChannel();
            mBalancePresenter.getBalance();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.im_balance_refresh, R.id.btn_dep_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_balance_refresh:
                BalanceAnimationUtil.startAnimation(imBalanceRefresh);
                mBalancePresenter.getBalance();
                break;
            case R.id.btn_dep_to_pay:
                if (TextUtils.isEmpty(paymentListBean.paymannerid) || TextUtils.isEmpty(curSelected.paymentKey)) {
                    showMessage("请选择支付方式");
                    return;
                }
                LogUtils.e("curSelected===" + curSelected.toString());
                LogUtils.e("paymentListBean==" + paymentListBean.toString());
                LogUtils.e("money==" + money);
                LogUtils.e("paymannerid: " + paymentListBean.paymannerid + ", " + " payid: " + paymentListBean.payid + ",curSelected.paymentKey: " + curSelected.paymentKey + ",transferTypeCode: " + transferTypeCode + ",bankcode: " + bankcode);
                toPay();
                break;
        }
    }

    public void toPay() {
        money = etDepositMoney.getText().toString();
        DataCenter.getInstance().getMyLocalCenter().saveDepositMount(money);
        LogUtils.e("paymannerid: " + paymentListBean.paymannerid + ", money: " + money);
        if (Check.isNull(paymentListBean.paymannerid) && !btnDepToPay.isClickable()) {
            return;
        }
        if (!DoubleClickHelper.getNewInstance().isDoubleClick()) {
            return;
        }
        isNeedSysBrowser = false; // 默认设置为false, 如果是指定的(支付宝支付)再改为true
        switch (paymentListBean.paymannerid) {
            case "Deposit":
                LogUtils.e("Deposit, NOT USE...");
                break;
            case "BQ"://转账汇款
                String bindPhone = DataCenter.getInstance().getUserInfoBean().getPhone();
                if (!TextUtils.isEmpty(bindPhone)) {
                    Bundle mbundle = new Bundle();
                    mbundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, paymentListBean.transferTypeList);
                    startActivityForResult(new Intent(mContext, DepositTransferActivity.class).putExtras(mbundle), ConstantValue.DEPOSIT_BQ);
                } else {
                    PhoneBindDingDialogFragment phoneBindDingDialogFragment = new PhoneBindDingDialogFragment();
                    DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), phoneBindDingDialogFragment);
                }
                break;
            case "8"://微信支付
            case "9"://支付宝支付
                isNeedSysBrowser = true;
            case "10": // V 币支付
            case "11": // 手机 QQ wap 支付
            case "15": // 银联扫码 12-->15
            case "18": // 快捷支付（IVI:快捷网银支付PC)
            case "19": // 快捷支付 (IVI:快捷网银支付MOBILE) 13--->19
            case "16": // 京东扫码 14-->16
            case "23": // 微信条码支付15--->23
            case "7":  // 财付通扫码支付
            case "6":  // 微信扫码支付
            case "5":  // 支付宝扫码支付
            case "17": // 京东WAP
            case "21": // 银联WAP
            case "20": // 比特币支付
            case "25": // 虚拟币支付
            case "41": // 钻石币支付
            case "24": // ATMC支付
            case "27": // 云闪付支付
                mDepositPresenter.onQueryOnlinePay(money, bankcode, paymentListBean.getHandleFee() + "", paymentListBean.paymannerid, paymentListBean.payid, NetworkUtils.getIPAddress(true));
                break;
            case "1"://银联在线支付
                // 弹出银行列表框
                DepositChoiceBankDialogFragment depositChoiceBankDialogFragment = DepositChoiceBankDialogFragment.getInstance(paymentListBean.extra);
                depositChoiceBankDialogFragment.setChoiceBankListener(new DepositChoiceBankDialogFragment.ChoiceBankListener() {
                    @Override
                    public void onChoiceBank(DepositMannersVo.BankVo bankVo) {
                        LogUtils.e(bankVo.toString());
                        mDepositPresenter.onQueryOnlinePay(money, bankVo.getId(), paymentListBean.getHandleFee() + "", paymentListBean.paymannerid, paymentListBean.payid, NetworkUtils.getIPAddress(true));
                    }
                });
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), depositChoiceBankDialogFragment);
                break;
            case "2":  // 点卡支付(card)
                isNeedSysBrowser = true;
                Bundle mbundle = new Bundle();
                mbundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, paymentListBean.cardList);
                mbundle.putString(ConstantValue.ARG_PARAM2, paymentListBean.paymannerid);
                mbundle.putString(ConstantValue.ARG_PARAM3, paymentListBean.payid);
                mbundle.putString(ConstantValue.ARG_PARAM4, paymentListBean.postUrl);
                startActivityForResult(new Intent(mContext, DepositPointCardActivity.class).putExtras(mbundle), ConstantValue.DEPOSIT_POINT_CARD);
                break;
            default:
                LogUtils.e("switch---default ***************************");
                mDepositPresenter.onQueryOnlinePay(money, bankcode, paymentListBean.getHandleFee() + "", paymentListBean.paymannerid, paymentListBean.payid, NetworkUtils.getIPAddress(true));
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantValue.DEPOSIT_BQ) {
            mDepositTransferBean = (DepositTransferBean) data.getSerializableExtra(ConstantValue.ARG_PARAM1);
            mDepositPresenter.onQueryFasterPay(DataCenter.getInstance().getUserInfoBean().username, money, mDepositTransferBean.getBankAccountCode(), mDepositTransferBean.getBqpaytypeCode(), mDepositTransferBean.getAccountName(), paymentListBean.paymannerid);
        }
        if (resultCode == ConstantValue.DEPOSIT_POINT_CARD) {
            String title = data.getStringExtra(ConstantValue.ARG_PARAM1);
            showOnlinePayResultDialog(title);
        }
    }

    @Override
    public void setDepositList(DepositMannersVo depositList) {
        mDepositMannersVo = depositList;
        DataCenter.getInstance().getMyLocalCenter().saveDeposit(depositList);
        if (null == mDepositMannersVo || mDepositMannersVo.newPayList.size() == 0) {
            etDepositMoney.setEnabled(false);
            //处理没有存款方式
//            llDepositInformation.setVisibility(View.GONE);
//            llDepositNoInformation.setVisibility(View.VISIBLE);
            return;
        }

        if (mDepositMannersVo.lowestvalue < DEFAULT_LOW_VALUE) {
            mDepositMannersVo.lowestvalue = DEFAULT_LOW_VALUE; // 针对输入框的
        }

        for (DepositMannersVo.DepMannersVo vo : mDepositMannersVo.newPayList) {
            if (Float.valueOf(vo.getLowestvalue()) < DEFAULT_LOW_VALUE) {
                vo.setLowestvalue(DEFAULT_LOW_VALUE); // 针对存款方式(大类)
            }
        }

        if (!TextUtils.isEmpty(mDepositMannersVo.quotaValue)) {
            quotaValue = mDepositMannersVo.quotaValue;
        }

        String[] array = quotaValue.split(",");
        mMoneyStringList.clear();
        mMoneyStringList.addAll(Arrays.asList(array));
        int col = array.length >= 6 ? 6 : array.length;
        rvDepAmount.setLayoutManager(new GridLayoutManager(getContext(), col));
        mMoneyAdapter.notifyDataSetChanged();
        rvDepAmount.setVisibility(View.VISIBLE);

        amount = TextUtils.isEmpty(money) ? 0 : Float.valueOf(money);
        mPayTypeList.clear();
        mPayTypeList.addAll(mDepositMannersVo.newPayList);
        rvDepManners.setVisibility(View.VISIBLE);
        etDepositMoney.setText(DataCenter.getInstance().getMyLocalCenter().getDepositMount());
        etDepositMoney.setEnabled(true);
    }


    @Override
    public void onQueryOnlinePayResult(OnlinePay onlinePay) {
        if (Check.isNull(onlinePay)) {
            return;
        }
        LogUtils.e("pay url: " + onlinePay.getUrl());
        Bundle mbundle = new Bundle();
        mbundle.putParcelable(ConstantValue.ARG_PARAM1, onlinePay);
        mbundle.putString(ConstantValue.ARG_PARAM2, paymentListBean.getPaymannername());
        mbundle.putBoolean(ConstantValue.ARG_PARAM3, isNeedSysBrowser);
        startActivity(new Intent(mContext, OnlinePlayActivity.class).putExtras(mbundle));
        showOnlinePayResultDialog("");
    }

    public void showOnlinePayResultDialog(String content) {
        String title = "请在新打开的页面完成存款";
        if (!TextUtils.isEmpty(content)) {
            title = content;
        }

        hintCommonDialogFragment = new HintCommonDialogFragment()
                .setTvTitle("付款提示")
                .setTvContent(title)
                .setOnLeftButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintCommonDialogFragment.dismissAllowingStateLoss();
                        ActivityUtil.skipToService(getActivity());
                    }
                }, "转账遇到问题")
                .setOnRightButtonListener(null, "知道了");
        mHandler.postDelayed(new Runnable() {
            public void run() {
                DialogFramentManager.getInstance().showDialog(getChildFragmentManager(), hintCommonDialogFragment);
            }
        }, 500);

    }

    @Override
    public void onQueryFasterPayResult(FasterPay fasterPay) {
//        mDepositPresenter.payChannel();
        if (Check.isNull(fasterPay) || Check.isNull(fasterPay.getOrder()) || Check.isNull(fasterPay.getOrder().getAccountnumber())) {
            showMessage(fasterPay.getMessage());
            return;
        }
        Bundle mbundle = new Bundle();
        mbundle.putString(ConstantValue.ARG_PARAM1, paymentListBean.paymannerid);
        mbundle.putString(ConstantValue.ARG_PARAM2, money);
        mbundle.putSerializable(ConstantValue.ARG_PARAM3, mDepositTransferBean);
        mbundle.putParcelable(ConstantValue.ARG_PARAM4, fasterPay);
        startActivity(new Intent(mContext, DepositbankFasterActivity.class).putExtras(mbundle));
        showOnlinePayResultDialog("");
    }

    @Override
    public void onQueryPayResultFail(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showMessage(msg);
        }
        mDepositPresenter.payChannel();
    }

    //校验按钮是否可以点击
    private void onVerifyNext(boolean isCanNext) {
        if (Check.isNull(btnDepToPay)) {
            return;
        }
        btnDepToPay.setEnabled(isCanNext);
    }


    /**
     * 转换为2个小数点的数值
     *
     * @param inputMoney
     * @return
     */
    private static String convertTo2BitDot(double inputMoney) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(inputMoney);
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
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            super.showMessage(message);
        }
        BalanceAnimationUtil.stopAnimation(imBalanceRefresh);
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
        if (mBalancePresenter != null) {
            mBalancePresenter.onDestory();
        }
        mDepositPresenter = new DepositPresenter(getActivity(), this);
        mBalancePresenter = new BalancePresenter(getActivity(), this);
    }

}
