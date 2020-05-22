package com.nwf.sports.ui.activity;

import android.content.Intent;
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
import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.SpannableStringUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.mvp.model.PointCardVo;
import com.nwf.sports.mvp.presenter.DepositPresenter;
import com.nwf.sports.mvp.view.DepositPoinCardView;
import com.nwf.sports.ui.dialogfragment.DepositPointCardTypeDialogFragment;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.data.IMDataCenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 点卡支付
 * <p>创建人：Simon
 * <p>创建时间：2019-04-12
 * <p>修改人：Simon
 * <p>修改时间：2019-04-12
 * <p>修改备注：
 **/
public class DepositPointCardActivity extends BaseActivity implements DepositPoinCardView {

    DepositPointCardTypeDialogFragment dialogFragmentType = null;//选择点卡类型Dialog
    List<PointCardVo> mDepositPointCardTypeBeen = new ArrayList<>(); //选择点卡类型数据源
    CommonAdapter<PointCardVo> mDepositPointCardTypeAdapter = null; //选择点卡类型Adapter

    DepositPointCardTypeDialogFragment dialogFragmentSum = null;//选择点卡金额Dialog
    List<String> mDepositPointCardSumBeen = new ArrayList<>(); //选择点卡金额数据源
    CommonAdapter<String> mDepositPointCardSumAdapter = null; //选择点卡金额Adapter

    @BindView(R.id.v_dep_name_title)
    PNTitleBar mVDepNameTitle;
    @BindView(R.id.tvw_pc_type)
    TextView mTvwPcType;
    @BindView(R.id.tvw_pc_amount)
    TextView mTvwPcAmount;
    @BindView(R.id.edt_pc_id)
    EditText mEdtPcId;
    @BindView(R.id.edt_pc_pwd)
    EditText mEdtPcPwd;
    @BindView(R.id.tvw_pc_handle_fee)
    TextView mTvwPcHandleFee;
    @BindView(R.id.tvw_pc_real_amount)
    TextView mTvwPcRealAmount;
    @BindView(R.id.btn_ok)
    TextView mBtnOk;
    @BindView(R.id.hint)
    TextView mHint;

    ArrayList<PointCardVo> cardList = new ArrayList<>(); //数据源
    public int selectCard = -1;  //选择的第几个点卡
    public int selectMoney = -1;  //选择的金额
    DepositPresenter mDepositPoinCardPresenter = null;

    private String paymannerid = "";
    private String payid = "";
    private String postUrl = "";

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_deposit_point_card);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            cardList = getIntent().getParcelableArrayListExtra(ConstantValue.ARG_PARAM1);
            paymannerid = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
            payid = getIntent().getStringExtra(ConstantValue.ARG_PARAM3);
            postUrl = getIntent().getStringExtra(ConstantValue.ARG_PARAM4);
        }
        mDepositPoinCardPresenter = new DepositPresenter(this, this);


        mVDepNameTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDepositPointCardTypeBeen.clear();
        mDepositPointCardTypeBeen.addAll(cardList);
        String type = (String) SPTool.get(ConstantValue.POINT_CARD_TYPE_NAME, "");
        if (!TextUtils.isEmpty(type)) {
            for (int i = 0; i < mDepositPointCardTypeBeen.size(); i++) {
                if (mDepositPointCardTypeBeen.get(i).getName().equals(type)) {
                    mTvwPcType.setText(type);
                    selectCard = i;
                }
            }
        }
        mDepositPointCardTypeAdapter = new CommonAdapter<PointCardVo>(this, R.layout.item_dep_point_card, mDepositPointCardTypeBeen) {
            @Override
            public void convert(ViewHolder holder, final PointCardVo item, final int position) {
                LinearLayout linearLayout = holder.getView(R.id.v_root);
                TextView textView = holder.getView(R.id.tvw_dep_pc_text);
                ImageView imageView = holder.getView(R.id.ivw_dep_pc_logo);
                textView.setText(item.getName());
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.nwf_viewpage_item_loading);
                //加载图片
                Glide.with(mContext)
                        .load(item.getIconUrl())
                        .apply(options)
                        .into(imageView);
                if (position == selectCard) {
                    linearLayout.setBackgroundResource(R.mipmap.nwf_deposit_point_card_item_pressed);
                } else {
                    linearLayout.setBackgroundResource(R.drawable.bg_deposit_point_card_item_normal);
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTvwPcType.setText(item.getName());
                        if (selectCard == position) {
                            if (dialogFragmentType != null) {
                                dialogFragmentType.dismissAllowingStateLoss();
                            }
                        } else {
                            selectCard = position;
                        }

                        mTvwPcAmount.setText("");
                        mTvwPcAmount.setHint("请选择");
                        selectMoney = -1;
                        mTvwPcHandleFee.setText("");
                        mTvwPcRealAmount.setText("");
                        mTvwPcHandleFee.setHint("--");
                        mTvwPcRealAmount.setHint("--");
                        mDepositPointCardTypeAdapter.notifyDataSetChanged();
                        setButton();
                        if (dialogFragmentType != null) {
                            dialogFragmentType.dismissAllowingStateLoss();
                        }
                    }
                });
            }
        };

        mDepositPointCardSumAdapter = new CommonAdapter<String>(this, R.layout.item_dep_point_card_sum, mDepositPointCardSumBeen) {
            @Override
            public void convert(ViewHolder holder, final String item, final int position) {
                LinearLayout linearLayout = holder.getView(R.id.v_root);
                TextView textView = holder.getView(R.id.tvw_dep_pc_sum);
                textView.setText(item + "元");
                if (position == selectMoney) {
                    linearLayout.setBackgroundResource(R.mipmap.bg_deposit_point_card_money_pressed);
                } else {
                    linearLayout.setBackgroundResource(R.drawable.bg_deposit_point_card_item_normal);
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTvwPcAmount.setText(item);
                        if (selectMoney == position) {
                            if (dialogFragmentType != null) {
                                dialogFragmentType.dismissAllowingStateLoss();
                            }
                        } else {
                            selectMoney = position;
                            BigDecimal handleFee = new BigDecimal(mDepositPointCardTypeBeen.get(selectCard).getValue()).divide(new BigDecimal(100)).multiply
                                    (new BigDecimal(item));
                            mTvwPcHandleFee.setText(handleFee.toString() + "元");
                            mTvwPcRealAmount.setText(new BigDecimal(item).subtract(handleFee).toString() + "元");
                            setButton();
                        }
                        mDepositPointCardSumAdapter.notifyDataSetChanged();
                        if (dialogFragmentSum != null) {
                            dialogFragmentSum.dismissAllowingStateLoss();
                        }
                    }
                });
            }
        };
        mEdtPcId.addTextChangedListener(mTextWatcher);
        mEdtPcPwd.addTextChangedListener(mTextWatcher);
        showDialogFragmentType();

        CharSequence charSequence = new SpannableStringUtils.Builder()
                .append("* 请务必使用 ").setForegroundColor(getResources().getColor(R.color.color_4A4A4A))
                .append(" 与您所选面额相同的卡进行支付").setForegroundColor(getResources().getColor(R.color.color_FF3300))
                .append("，否则引起的交易失败或金额丢失，我方不予承担！\n如：您选择10元面额但使用30元卡支付，则系统认为实际交付金额为10元，高于10元部分不予退还。").setForegroundColor(getResources().getColor(R.color
                        .color_4A4A4A))
                .create();
        mHint.setText(charSequence);

    }


    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setButton();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void initData() {

    }

    @Override
    public void onRequestCardPay(OnlinePay onlinePay) {
        if (Check.isNull(onlinePay)) {
            return;
        }
        Bundle mbundle = new Bundle();
        mbundle.putSerializable(ConstantValue.ARG_PARAM1, "收单成功，支付确认中");
        setResult(ConstantValue.DEPOSIT_POINT_CARD, new Intent().putExtras(mbundle));
        finish();
    }


    @OnClick({R.id.tvw_pc_type, R.id.tvw_pc_amount, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvw_pc_type:
                showDialogFragmentType();
                break;
            case R.id.tvw_pc_amount:
                if (selectCard == -1) {
                    showMessage("请先选择点卡类型！");
                    return;
                }
                mDepositPointCardSumBeen.clear();
                List<String> cardValues = mDepositPointCardTypeBeen.get(selectCard).getCardValues();
                mDepositPointCardSumBeen.addAll(cardValues);
                if (mDepositPointCardSumAdapter == null) {
                    showMessage("暂无点卡金额！");
                    return;
                }
                mDepositPointCardSumAdapter.notifyDataSetChanged();
                dialogFragmentSum = new DepositPointCardTypeDialogFragment();
                dialogFragmentSum.setData(this, mDepositPointCardSumAdapter, 4, "点卡类型");
                DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), dialogFragmentSum);
                break;
            case R.id.btn_ok:
                SPTool.put(ConstantValue.POINT_CARD_TYPE_NAME, mDepositPointCardTypeBeen.get(selectCard).getName());
                PointCardVo pointCardVo = mDepositPointCardTypeBeen.get(selectCard);
                Map<String,String> map=new HashMap<>();
                map.put("accountname", IMDataCenter.getInstance().getUserInfoBean().username);
                map.put("cardAmt",mTvwPcAmount.getText().toString());
                map.put("cardCode",pointCardVo.getCode());
                map.put("cardNo", mEdtPcId.getText().toString());
                map.put("cardPwd",mEdtPcPwd.getText().toString());
                map.put("handleFee",pointCardVo.getValue());
                map.put("mincredit",pointCardVo.getCardValues().get(0));
                map.put("payid",payid);
                map.put("paymannerid",paymannerid);
                map.put("postUrl",postUrl);
                mDepositPoinCardPresenter.onRequestCardPay(map);

                break;
        }
    }

    private void setButton() {
        if (!TextUtils.isEmpty(mEdtPcId.getText().toString().trim()) && !TextUtils.isEmpty(mEdtPcPwd.getText().toString().trim()) && selectCard != -1 &&
                selectMoney != -1) {
            mBtnOk.setEnabled(true);
        } else {
            mBtnOk.setEnabled(false);
        }
    }

    public void showDialogFragmentType() {
        if (mDepositPointCardTypeBeen == null) {
            showMessage("暂无点卡类型！");
            return;
        }
        if (mDepositPointCardTypeAdapter == null || mDepositPointCardTypeBeen.size() == 0) {
            showMessage("暂无点卡类型！");
            return;
        }
        dialogFragmentType = new DepositPointCardTypeDialogFragment();
        dialogFragmentType.setData(this, mDepositPointCardTypeAdapter, 3, "点卡类型");
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), dialogFragmentType);
    }
}
