package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.utils.BankDrawableUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：银行列表 存款
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class DepositChoiceBankDialogFragment extends BaseDialogFragment {


    @BindView(R.id.rv_dep_bank_list)
    RecyclerView rvDepBankList;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    List<DepositMannersVo.BankVo> bankList = new ArrayList<>();
    CommonAdapter<DepositMannersVo.BankVo> mbankadapter = null;

    DepositMannersVo.BankVo curBankVo = null;

    ChoiceBankListener mChoiceBankListener = null;

    public static DepositChoiceBankDialogFragment getInstance(ArrayList<DepositMannersVo.BankVo> list) {
        DepositChoiceBankDialogFragment loginDialogFragment = new DepositChoiceBankDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, list);
        loginDialogFragment.setArguments(bundle);
        return loginDialogFragment;
    }

    public void setChoiceBankListener(ChoiceBankListener choiceBankListener) {
        mChoiceBankListener = choiceBankListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bankList = getArguments().getParcelableArrayList(ConstantValue.ARG_PARAM1);
        }
    }

    @Override
    protected int getViewId() {
        intScreenWProportion = 1;
        intScreenHProportion = 1;
        AnimationsStyle = -1;
        return R.layout.dialogfragment_deposit_bank_list;
    }

    @Override
    protected void initViews(View view) {
        rvDepBankList.setLayoutManager(new LinearLayoutManager(getContext()));
        mbankadapter = new CommonAdapter<DepositMannersVo.BankVo>(getActivity(), R.layout.item_deposit_bank, bankList) {
            @Override
            public void convert(ViewHolder holder, DepositMannersVo.BankVo item, int position) {
                ImageView ivwDepBankLogo = holder.getView(R.id.ivw_dep_bank_logo);
                ImageView ckbRight = holder.getView(R.id.ckb_right);
                TextView tvItemPayQuota = holder.getView(R.id.tv_item_pay_quota);
                //  设置银行Logo
                ivwDepBankLogo.setImageDrawable(BankDrawableUtil.getDrawable(item.getBankAccountCode()));
                tvItemPayQuota.setText(item.getBankAccountName());

                if (!Check.isNull(curBankVo)&&curBankVo.getBankAccountCode().equals(item.getBankAccountCode())) {
                    ckbRight.setVisibility(View.VISIBLE);
                } else {
                    ckbRight.setVisibility(View.GONE);
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Check.isNull(curBankVo)&&curBankVo.getBankAccountCode().equals(item.getBankAccountCode())) {
                            curBankVo = null;
                        } else {
                            curBankVo = item;
                        }
                        checkBtn();
                        mbankadapter.notifyDataSetChanged();
                    }
                });
            }
        };
        rvDepBankList.setAdapter(mbankadapter);
    }

    public void checkBtn() {
        if (!Check.isNull(curBankVo)) {
            btnOk.setEnabled(true);
        } else {
            btnOk.setEnabled(false);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.transparency, R.id.ivw_close, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.transparency:
            case R.id.ivw_close:
                dismissAllowingStateLoss();
                break;
            case R.id.btn_ok:
                if (!Check.isNull(curBankVo) && mChoiceBankListener != null) {
                    mChoiceBankListener.onChoiceBank(curBankVo);
                }
                dismissAllowingStateLoss();
                break;
        }
    }


    public interface ChoiceBankListener {
        void onChoiceBank(DepositMannersVo.BankVo bankVo);
    }
}
