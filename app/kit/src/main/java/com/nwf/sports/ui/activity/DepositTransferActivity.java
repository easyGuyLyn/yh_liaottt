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

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.RegexUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.DepositTransferBean;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.BankDrawableUtil;
import com.nwf.sports.utils.LimitInputTextWatcher;
import com.nwf.sports.utils.data.DataCenter;
import com.nwf.sports.utils.data.MyLocalCenter;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：  BQ存款 填写姓名和选择银行
 * <p>创建人：Simon
 * <p>创建时间：2019-04-11
 * <p>修改人：Simon
 * <p>修改时间：2019-04-11
 * <p>修改备注：
 **/
public class DepositTransferActivity extends BaseActivity {


    @BindView(R.id.v_dep_transfer_title)
    PNTitleBar vDepTransferTitle;
    @BindView(R.id.rv_dep_bq_type)
    RecyclerView rvDepBqType;
    @BindView(R.id.edt_dep_name)
    EditText edtDepName;
    @BindView(R.id.ivw_del)
    ImageView ivwDel;
    @BindView(R.id.ll_dep_name)
    LinearLayout llDepName;
    @BindView(R.id.rv_dep_bank_list)
    RecyclerView rvDepBankList;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.v_root)
    LinearLayout vRoot;

    ArrayList<DepositMannersVo.BankVo> mBankVosList = new ArrayList<>(); //银行
    CommonAdapter<DepositMannersVo.BankVo> mBankVosAdapter = null;
    String mBankVo = "";

    ArrayList<DepositMannersVo.TransferTypeVo> mTransferTypeVoList = new ArrayList<>(); //支付类型
    CommonAdapter<DepositMannersVo.TransferTypeVo> mTransferTypeVoAdapter = null;
    String mTransferTypeVo = "";
    int selectPosition = 0;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_deposit_transfer);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            mTransferTypeVoList = getIntent().getParcelableArrayListExtra(ConstantValue.ARG_PARAM1);
            LogUtils.e(mTransferTypeVoList.toString());
        } else {
            showMessage("系统异常请重试");
            finish();
        }
        vDepTransferTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        isExist();

        mBankVosAdapter = new CommonAdapter<DepositMannersVo.BankVo>(this, R.layout.activity_deposit_transfer_bank_item, mBankVosList) {
            @Override
            public void convert(ViewHolder holder, DepositMannersVo.BankVo item, int position) {
                ImageView imBank = holder.getView(R.id.im_bank);
                TextView imBankName = holder.getView(R.id.tv_bank_name);
                imBankName.setText(item.getBankAccountName());
                imBank.setImageDrawable(BankDrawableUtil.getDrawable(item.getBankAccountCode()));

                if (TextUtils.isEmpty(mBankVo)) {
                    if (position == 0) {
                        holder.getConvertView().setSelected(true);
                        mBankVo = item.bankAccountCode;
                        DataCenter.getInstance().getMyLocalCenter().saveDepositTransferBank(item.bankAccountCode, mTransferTypeVoList.get(selectPosition).getDesc());
                        setButtonEnable();
                    }
                } else {
                    if (mBankVo.equals(item.bankAccountCode)) {
                        holder.getConvertView().setSelected(true);
                    } else {
                        holder.getConvertView().setSelected(false);
                    }
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (mBankVo.equals(item.bankAccountCode)) {
////                            mBankVo = "";
//                        } else {
                        mBankVo = item.bankAccountCode;
                        mBankVosAdapter.notifyDataSetChanged();
//                            mTransferTypeVoList.get(selectPosition).typeBank = item.bankAccountCode;
                        DataCenter.getInstance().getMyLocalCenter().saveDepositTransferBank(item.bankAccountCode, mTransferTypeVoList.get(selectPosition).getDesc());
//                        }
                        setButtonEnable();
                    }
                });
            }
        };
        rvDepBankList.setLayoutManager(new GridLayoutManager(this, 3));
        rvDepBankList.setAdapter(mBankVosAdapter);

        mTransferTypeVoAdapter = new CommonAdapter<DepositMannersVo.TransferTypeVo>(this, R.layout.activity_deposit_transfer_type_item, mTransferTypeVoList) {
            @Override
            public void convert(ViewHolder holder, DepositMannersVo.TransferTypeVo item, int position) {
                TextView tvType = holder.getView(R.id.tv_type);
                tvType.setText(item.getDesc());

                if (TextUtils.isEmpty(mTransferTypeVo)) {
                    if (position == 0) {
                        holder.getConvertView().setSelected(true);
                        mTransferTypeVo = item.code;
                        mBankVosList.clear();
                        mBankVosList.addAll(item.getExtra());
                        selectPosition = position;
                    }
                } else {
                    if (mTransferTypeVo.equals(item.code)) {
                        holder.getConvertView().setSelected(true);
                        mBankVosList.clear();
                        mBankVosList.addAll(item.getExtra());
                        selectPosition = position;
                    } else {
                        holder.getConvertView().setSelected(false);
                    }
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTransferTypeVo.equals(item.code)) {
//                            mTransferTypeVo = "";
                        } else {
                            mTransferTypeVo = item.code;
                            selectPosition = position;
                            mTransferTypeVoAdapter.notifyDataSetChanged();
                            mBankVo="";
                            mBankVosList.clear();
                            mBankVosList.addAll(item.getExtra());
                            mBankVosAdapter.notifyDataSetChanged();
                            DataCenter.getInstance().getMyLocalCenter().saveDepositTransferType(mTransferTypeVo);
                        }
                        setButtonEnable();
                    }
                });
            }
        };
        rvDepBqType.setLayoutManager(new GridLayoutManager(this, 3));
        rvDepBqType.setAdapter(mTransferTypeVoAdapter);

        edtDepName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }
        });
        edtDepName.addTextChangedListener(new LimitInputTextWatcher(edtDepName,LimitInputTextWatcher.CHARACTER_LETTER_DOT));

    }

    public void isExist() {
        MyLocalCenter myLocalCenterCenter = DataCenter.getInstance().getMyLocalCenter();
        edtDepName.setText(myLocalCenterCenter.getDepositTransferName());
        mTransferTypeVo = myLocalCenterCenter.getDepositTransferType();

        if (!TextUtils.isEmpty(mTransferTypeVo)) {
            boolean isexist = false;
            for (DepositMannersVo.TransferTypeVo data : mTransferTypeVoList) {
                if (mTransferTypeVo.equals(data.code)) {
                    isexist = true;
                    mBankVo = myLocalCenterCenter.getDepositTransferBank(data.getDesc());
                }
            }
            if (!isexist) {
                mTransferTypeVo = "";
                myLocalCenterCenter.saveDepositTransferType("");
            }
        }
        setButtonEnable();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_ok, R.id.ivw_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String name = edtDepName.getText().toString().trim();

                if (!RegexUtils.isZhNum(name)) {
                    showMessage(getString(R.string.str_dep_name_not_rule));
                    return;
                }
                MyLocalCenter myLocalCenterCenter = DataCenter.getInstance().getMyLocalCenter();
                myLocalCenterCenter.saveDepositTransferName(name);

                DepositTransferBean depositTransferBean = new DepositTransferBean(name, mBankVo, mTransferTypeVo);
                Bundle mbundle = new Bundle();
                mbundle.putSerializable(ConstantValue.ARG_PARAM1, depositTransferBean);
                setResult(ConstantValue.DEPOSIT_BQ, new Intent().putExtras(mbundle));

                finish();
                break;
            case R.id.ivw_del:
                edtDepName.setText("");
                break;
        }
    }

    private void setButtonEnable() {
        boolean status = true;
        if (TextUtils.isEmpty(mBankVo) || TextUtils.isEmpty(mTransferTypeVo) || TextUtils.isEmpty(edtDepName.getText().toString().trim())) {
            status = false;
        }
        btnOk.setEnabled(status);
    }

}
