package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.ToastUtil;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.MyBankItemResult;
import com.nwf.sports.mvp.model.MyBankResult;
import com.nwf.sports.mvp.presenter.BankPresenter;
import com.nwf.sports.mvp.view.BankManagementView;
import com.nwf.sports.mvp.view.BankView;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.SafetyVerificationDialogFragment;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.BindPhoneFlowEnum;
import com.nwf.sports.utils.data.DataCenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述： 银行卡列表
 * <p>创建人：Simon
 * <p>创建时间：2019-04-26
 * <p>修改人：Simon
 * <p>修改时间：2019-04-26
 * <p>修改备注：
 **/
public class BankManagementActivity extends BaseActivity implements BankView, BankManagementView {

    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.bank_record)
    RecyclerView bankRecord;

    List<MyBankItemResult> mBankList = new ArrayList<>();
    CommonAdapter<MyBankItemResult> mBankdapter = null;
    BankPresenter mBankPresenter;

    private SafetyVerificationDialogFragment mSafetyVerificationDialogFragment = null;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_management_bank);
    }

    @Override
    protected void initViews() {
        RxBus.get().register(this);
        mBankPresenter = new BankPresenter(this, this);
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBankdapter = new CommonAdapter<MyBankItemResult>(this, R.layout.activity_management_bank_item, mBankList) {
            @Override
            public void convert(ViewHolder holder, MyBankItemResult item, int position) {
                ImageView withdrawalBankBg = holder.getView(R.id.withdrawal_bank_bg);
                ImageView withdrawalBankIcon = holder.getView(R.id.withdrawal_bank_icon);
                ImageView defaultBankSelect = holder.getView(R.id.default_bank_select);
                TextView withdrawalBankName = holder.getView(R.id.withdrawal_bank_name);
                TextView withdrawalBankBranch = holder.getView(R.id.withdrawal_bank_branch);
                TextView withdrawalBankAccount = holder.getView(R.id.withdrawal_bank_account);
                TextView accountName = holder.getView(R.id.account_name);
                TextView tvDefault = holder.getView(R.id.tv_default);
                RelativeLayout bankLayout = holder.getView(R.id.bank_layout);
                LinearLayout tvAddBank = holder.getView(R.id.tv_add_bank);
                TextView tvBankDelete = holder.getView(R.id.tv_bank_delete);
                TextView tvBankModification = holder.getView(R.id.tv_bank_modification);

                if (item.isEmpty()) {   //添加银行卡UI
                    tvAddBank.setVisibility(View.VISIBLE);
                    bankLayout.setVisibility(View.GONE);
                    tvAddBank.setOnClickListener(new View.OnClickListener() {  //添加银行卡
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(DataCenter.getInstance().getUserInfoBean().getPhone())) {
                                ToastUtil.showToastLong("请先绑定手机号");
                                Bundle mbundle = new Bundle();
                                mbundle.putString(ConstantValue.BIND_PHONE_FLOW, BindPhoneFlowEnum.TOBINDBANK.getServicename());
                                startActivity(new Intent(BankManagementActivity.this, BindPhoneActivity.class).putExtras(mbundle));
                            } else {
                                Bundle mbundle = new Bundle();
                                if (null != mBankList && mBankList.size() > 1){
                                    mbundle.putString(ConstantValue.ARG_PARAM2, mBankList.get(0).getName());
                                }
                                startActivity(new Intent(BankManagementActivity.this, AddBankActivity.class).putExtras(mbundle));
                            }

                        }
                    });
                } else {
                    tvAddBank.setVisibility(View.GONE);
                    bankLayout.setVisibility(View.VISIBLE);
                    withdrawalBankName.setText(item.getName());
                    withdrawalBankBranch.setText(item.getBranch());
                    withdrawalBankAccount.setText(item.getCardNo());
                    accountName.setText(item.getBankName());

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

                    if (item.getDef().equals("1")) {
                        tvDefault.setVisibility(View.VISIBLE);
                        defaultBankSelect.setImageResource(R.mipmap.icon_bank_switch_pressed);
                    } else {
                        tvDefault.setVisibility(View.GONE);
                        defaultBankSelect.setImageResource(R.mipmap.nwf_icon_bank_switch_normal);
                    }
                    if (null == mBankList || mBankList.size() <= 2) {
                        tvBankDelete.setVisibility(View.GONE); // 当只有一张银行卡时要把删除按钮隐藏掉,不能删除银行卡
                    } else {
                        tvBankDelete.setVisibility(View.VISIBLE);
                    }

                    defaultBankSelect.setOnClickListener(new View.OnClickListener() { //设置默认
                        @Override
                        public void onClick(View v) {
                            if (item.getDef().equals("1")) {
                                //showToast("已经是默认银行卡");
                                return;
                            }
                            mBankPresenter.setDefaultMyBank(item.getId());
                        }
                    });

                    tvBankDelete.setOnClickListener(new View.OnClickListener() { //删除银行卡
                        @Override
                        public void onClick(View v) {
                            mSafetyVerificationDialogFragment = new SafetyVerificationDialogFragment()
                                    .setOnLeftButtonListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (mSafetyVerificationDialogFragment != null) {
                                                mSafetyVerificationDialogFragment.dismissAllowingStateLoss();
                                            }
                                            finish();
                                        }
                                    }, "取消").setOnBakeListener(new SafetyVerificationDialogFragment.BakeListener() {
                                        @Override
                                        public void onAction() {
                                            mBankPresenter.delBankCard(item.getId());
                                        }
                                    });
                            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mSafetyVerificationDialogFragment);
                        }
                    });

                    tvBankModification.setOnClickListener(new View.OnClickListener() { //修改银行卡
                        @Override
                        public void onClick(View v) {
                            Bundle mbundle = new Bundle();
                            mbundle.putParcelable(ConstantValue.ARG_PARAM1, item);
                            startActivity(new Intent(BankManagementActivity.this, AddBankActivity.class).putExtras(mbundle));
                        }
                    });
                }

            }
        };
        bankRecord.setLayoutManager(new LinearLayoutManager(this));
        bankRecord.setAdapter(mBankdapter);
    }

    @Override
    protected void initData() {
        mBankPresenter.getMyBank();
    }

    @Override
    public void setMyBank(MyBankResult data) {
        mBankList.clear();
        final int MAX_ITEMS = data.maxbanknumber;
        if (Check.isEmpty(data.bankList)) {
            mBankList.add(new MyBankItemResult());
        } else {
            mBankList.addAll(data.bankList);
            if (data.bankList.size() < 3) {
                mBankList.add(new MyBankItemResult());
            }
        }
        mBankdapter.notifyDataSetChanged();
    }

    @Override
    public void setMyBankDefeated() {
        showMessage("获取银行卡失败请重试");
        finish();
    }


    @Override
    public void delBankSucceed() {
        showMessage("删除银行卡成功");
        initData();
    }

    @Override
    public void setDefaultBankSuccess() {
        showMessage("您已经成功修改默认银行卡");
        initData();
    }

    /**
     * 收到银行卡
     */
    @Subscribe(tags = {@Tag(ConstantValue.BANK_MANAGEMENT_ALTERATION)})
    public void receptionPhone(String s) {
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBankPresenter != null) {
            mBankPresenter.onDestory();
        }
        RxBus.get().unregister(this);
    }
}
