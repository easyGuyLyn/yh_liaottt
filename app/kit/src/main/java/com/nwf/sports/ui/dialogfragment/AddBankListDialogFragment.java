package com.nwf.sports.ui.dialogfragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.Check;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.BankInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：银行列表
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class AddBankListDialogFragment extends BaseDialogFragment {

    @BindView(R.id.rv_bank_list)
    RecyclerView rvBankList;

    List<BankInfo> bankList = new ArrayList<>();
    CommonAdapter<BankInfo> mbankadapter = null;

    BankInfo curBankVo = null;

    ChoiceBankListener mChoiceBankListener = null;

    public static AddBankListDialogFragment getInstance(ArrayList<BankInfo> list) {
        AddBankListDialogFragment loginDialogFragment = new AddBankListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ConstantValue.ARG_PARAM1, list);
        loginDialogFragment.setArguments(bundle);
        return loginDialogFragment;
    }

    public BankInfo getCurBankVo() {
        return curBankVo;
    }

    public void setCurBankVo(BankInfo curBankVo) {
        this.curBankVo = curBankVo;
        if (mbankadapter != null) {
            mbankadapter.notifyDataSetChanged();
        }
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
        return R.layout.dialogfragment_addbank_banklist;
    }

    @Override
    protected void initViews(View view) {
        rvBankList.setLayoutManager(new LinearLayoutManager(getContext()));
        mbankadapter = new CommonAdapter<BankInfo>(getActivity(), R.layout.item_addbank_bank, bankList) {
            @Override
            public void convert(ViewHolder holder, BankInfo item, int position) {
                ImageView ivwDepBankLogo = holder.getView(R.id.ivw_bank_logo);
                TextView tvItemPayQuota = holder.getView(R.id.tv_item_pay_quota);

                RequestOptions options = new RequestOptions().placeholder(R.mipmap.nwf_bg_bank_green);
                //设置银行Logo
                Glide.with(mContext)
                        .load(Uri.parse(item.getIcon()))
                        .apply(options)
                        .into(ivwDepBankLogo);
                tvItemPayQuota.setText(item.getName());

//                if (!Check.isNull(curBankVo) && curBankVo.getName().equals(item.getName())) {
//                    holder.getConvertView().setSelected(true);
//                } else {
//                    holder.getConvertView().setSelected(false);
//                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Check.isNull(curBankVo) && curBankVo.getName().equals(item.getName())) {

                        } else {
                            curBankVo = item;
                        }
                        mbankadapter.notifyDataSetChanged();
                        if (mChoiceBankListener != null) {
                            mChoiceBankListener.onChoiceBank(curBankVo);
                        }
                        dismissAllowingStateLoss();
                    }
                });
            }
        };
        rvBankList.setAdapter(mbankadapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.transparency, R.id.ivw_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.transparency:
            case R.id.ivw_close:
                dismissAllowingStateLoss();
                break;
        }
    }


    public interface ChoiceBankListener {
        void onChoiceBank(BankInfo bankVo);
    }
}
