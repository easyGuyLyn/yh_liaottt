package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.LogUtils;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.BankInfo;
import com.nwf.sports.mvp.model.MyBankItemResult;
import com.nwf.sports.mvp.model.ProvinceCity;
import com.nwf.sports.mvp.presenter.BankPresenter;
import com.nwf.sports.mvp.view.AddBankView;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.dialogfragment.AddBankListDialogFragment;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.SafetyVerificationDialogFragment;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.AssetsUtils;
import com.nwf.sports.utils.BindBankFlowEnum;
import com.nwf.sports.utils.InputMethodUtils;
import com.nwf.sports.utils.PNCheck;
import com.nwf.sports.utils.data.IMDataCenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 添加银行卡界面
 * <p>创建人：Simon
 * <p>创建时间：2019-04-17
 * <p>修改人：Simon
 * <p>修改时间：2019-04-17
 * <p>修改备注：
 **/
public class AddBankActivity extends BaseActivity implements AddBankView {

    @BindView(R.id.v_dep_name_title)
    PNTitleBar vDepNameTitle;
    @BindView(R.id.ed_addbank_name)
    EditText edAddbankName;
    @BindView(R.id.ed_addbank_account)
    EditText edAddbankAccount;
    @BindView(R.id.tv_addbank_bank)
    TextView tvAddbankBank;
    @BindView(R.id.addbank_bank_layout)
    LinearLayout addbankBankLayout;
    @BindView(R.id.tv_addbank_city)
    TextView tvAddbankCity;
    @BindView(R.id.addbank_city_layout)
    LinearLayout addbankCityLayout;
    @BindView(R.id.ed_addbank_branch)
    EditText edAddbankBranch;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.tv_addbank_account_error_auth)
    TextView tvAddbankAccountErrorAuth;
    @BindView(R.id.tv_addbank_branch_error_auth)
    TextView tvAddbankBranchErrorAuth;
    @BindView(R.id.ivw_bank_logo)
    ImageView ivwBankLogo;
    @BindView(R.id.ly_addbank_layout)
    LinearLayout lyAddbankLayout;
    @BindView(R.id.group_addbank)
    ScrollView groupAddbank;

    BankPresenter mBankPresenter = null;

    private List<String> bankCityCityList = new ArrayList<>(); // 城市
    private List<List<String>> bankCityDistrictList = new ArrayList<>(); //区县
    private String selectedCountry = "";//选择的区县
    private String selectedCity = ""; //选择的城市

    private ArrayList<BankInfo> mBankInfos = new ArrayList<>(); //银行
    private BankInfo mBankInfo = null;
    private SafetyVerificationDialogFragment mSafetyVerificationDialogFragment = null;
    private MyBankItemResult mMyBankItemResult = null; //修改银行卡     判断其是否为空
    private String name;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_add_bank);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            mMyBankItemResult = getIntent().getParcelableExtra(ConstantValue.ARG_PARAM1);
            name = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
        }
        if (Check.isNull(mMyBankItemResult)) {
            vDepNameTitle.setTitle("添加银行卡");
        } else {
            vDepNameTitle.setTitle("修改银行卡");
        }

        mBankPresenter = new BankPresenter(this, this);
        edAddbankAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkSubmitButton();
                checkBankNo();
            }
        });
        edAddbankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkSubmitButton();
            }
        });
        edAddbankBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkBankSite();
                checkSubmitButton();
            }
        });
        vDepNameTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        groupAddbank.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
//        if (TextUtils.isEmpty(IMDataCenter.getInstance().getUserInfoBean().getPhone())) {
//            ToastUtil.showToastLong("未绑定手机号");
//            return;
//        }
        boolean b = IMDataCenter.getInstance().getMyLocalCenter().boundPhoneRecently();
        if (!b) {  //绑定手机10分钟后是不需要验证
            mSafetyVerificationDialogFragment = new SafetyVerificationDialogFragment()
                    .setOnLeftButtonListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mSafetyVerificationDialogFragment != null) {
                                mSafetyVerificationDialogFragment.dismissAllowingStateLoss();
                            }
                            finish();
                        }
                    }, "取消").setOnRightButtonListener(null, "确定");
            DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mSafetyVerificationDialogFragment);
        }
        analysis();
        mBankPresenter.getAllBankInfo();
        groupAddbank.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.addbank_bank_layout, R.id.addbank_city_layout, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addbank_bank_layout:
                AddBankListDialogFragment addBankListDialogFragment = AddBankListDialogFragment.getInstance(mBankInfos);
                addBankListDialogFragment.setCurBankVo(mBankInfo);
                addBankListDialogFragment.setChoiceBankListener(new AddBankListDialogFragment.ChoiceBankListener() {
                    @Override
                    public void onChoiceBank(BankInfo bankVo) {
                        mBankInfo = bankVo;
                        RequestOptions options = new RequestOptions().placeholder(R.mipmap.nwf_bg_bank_green);
                        //设置银行Logo
                        Glide.with(mContext)
                                .load(Uri.parse(bankVo.getIcon()))
                                .apply(options)
                                .into(ivwBankLogo);
                        ivwBankLogo.setVisibility(View.VISIBLE);
                        tvAddbankBank.setText(bankVo.getName());
                        checkSubmitButton();
                    }
                });
                DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), addBankListDialogFragment);
                break;
            case R.id.addbank_city_layout:
                if (bankCityCityList == null || bankCityCityList.size() == 0) {
                    analysis();
                }
                InputMethodUtils.hideSoftInput(view);
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        LogUtils.e("选择了" + bankCityCityList.get(options1) + bankCityDistrictList.get(options1).get(option2));
                        selectedCountry = bankCityCityList.get(options1);
                        selectedCity = bankCityDistrictList.get(options1).get(option2);
                        tvAddbankCity.setText(selectedCountry + " " + selectedCity);
                        checkSubmitButton();
                    }
                }).setSubmitColor(getResources().getColor(R.color.color_FF3300)).setCancelColor(getResources().getColor(R.color.color_4A4A4A)).build();
                pvOptions.setPicker(bankCityCityList, bankCityDistrictList);
                pvOptions.show();
                break;
            case R.id.btn_ok:
                if (!Check.isNull(mMyBankItemResult)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("bankAccountName", edAddbankName.getText().toString()); //登录类型 1 为account 2 为moblie
                    map.put("bankAccountNo", edAddbankAccount.getText().toString());
                    map.put("bankCity", selectedCity);
                    map.put("bankCountry", selectedCountry);
                    map.put("bankName", tvAddbankBank.getText().toString());
                    map.put("branchName", edAddbankBranch.getText().toString());
                    map.put("refer", "");
                    map.put("customerBankId", mMyBankItemResult.getId());
                    mBankPresenter.modifyBank(map);
                    break;
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("bankAccountName", edAddbankName.getText().toString()); //登录类型 1 为account 2 为moblie
                    map.put("bankAccountNo", edAddbankAccount.getText().toString());
                    map.put("bankCity", selectedCity);
                    map.put("bankCountry", selectedCountry);
                    map.put("bankName", tvAddbankBank.getText().toString());
                    map.put("branchName", edAddbankBranch.getText().toString());
                    map.put("refer", "");
                    mBankPresenter.addBank(map);
                }
        }
    }

    public void analysis() {
        try {
            String JsonData = AssetsUtils.getStringFromAssert(AddBankActivity.this, "city.json");
            ProvinceCity provinceCity = new Gson().fromJson(JsonData, ProvinceCity.class);
            bankCityCityList.addAll(Arrays.asList(provinceCity.provinces));
            bankCityDistrictList.addAll(provinceCity.cities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改银行卡 写入数据
     *
     * @param item
     */
    private void fillData(MyBankItemResult item) {
        edAddbankName.setText(item.getName());
        edAddbankName.setSelection(item.getName().length());
        edAddbankName.setEnabled(false);
        lyAddbankLayout.setEnabled(false);
        /*etBankaccountno.setText(item.bankAccountNo);
        groupselectedbankname.setVisibility(View.VISIBLE);
        tvHintBankName.setVisibility(View.GONE);
        Picasso.with(getContext()).load(Uri.parse(item.reserve2)).into(ivIconBank);
        tvBankname.setText(item.bankName);
        setCityText(item.bankCountry,item.bankCity);
        etBanksite.setText(item.branchName);*/
    }

    /**
     * 银行卡 检查
     *
     * @return
     */
    private boolean checkBankNo() {
        String bankno = edAddbankAccount.getText().toString();
        PNCheck.CheckResult checkResult = PNCheck.checkAccountNO(bankno);
        if (!checkResult.isResultOk) {
            tvAddbankAccountErrorAuth.setVisibility(View.VISIBLE);
            tvAddbankAccountErrorAuth.setText(checkResult.msg);
        } else {
            tvAddbankAccountErrorAuth.setVisibility(View.GONE);
        }
        return checkResult.isResultOk;
    }

    /**
     * 判断确定按钮
     *
     * @return
     */
    private boolean checkSubmitButton() {
        String bankName = tvAddbankBank.getText().toString();
        String bankSite = edAddbankBranch.getText().toString();
        String bankno = edAddbankAccount.getText().toString();
        String name = edAddbankName.getText().toString();
        PNCheck.CheckResult checkResult = PNCheck.collect(
                PNCheck.checkAccountSite(bankSite),
                PNCheck.checkAccountNO(bankno),
                PNCheck.checkNotEmpty(name),
                PNCheck.checkNotEmpty(selectedCity),
                PNCheck.checkNotEmpty(selectedCountry),
                PNCheck.checkNotEmpty(bankName));
        if (checkResult.isResultOk) {
            btnOk.setEnabled(true);
        } else {
            btnOk.setEnabled(false);
        }
        return checkResult.isResultOk;
    }

    /**
     * 支行判断
     *
     * @return
     */
    private boolean checkBankSite() {
        String bankSite = edAddbankBranch.getText().toString();
        PNCheck.CheckResult checkResult = PNCheck.checkAccountSite(bankSite);
        if (checkResult.isResultOk) {
            tvAddbankBranchErrorAuth.setVisibility(View.GONE);
        } else {
            tvAddbankBranchErrorAuth.setText(checkResult.msg);
            tvAddbankBranchErrorAuth.setVisibility(View.VISIBLE);
        }
        return checkResult.isResultOk;
    }

    @Override
    public void AllBankInfoSucceed(List<BankInfo> bankInfos) {
        mBankInfos.clear();
        mBankInfos.addAll(bankInfos);
        if (Check.isNull(mMyBankItemResult)) {
//            String realName = IMDataCenter.getInstance().getUserInfoBean().getRealName();
            if (!TextUtils.isEmpty(name)) {
                edAddbankName.setText(name);
                edAddbankName.setSelection(name.length());
                edAddbankName.setEnabled(false);
                lyAddbankLayout.setEnabled(false);
            }
        } else {
            fillData(mMyBankItemResult);
        }
    }

    @Override
    public void addBankSucceed() {
        String stringExtra = getIntent().getStringExtra(ConstantValue.BIND_BANK_FLOW);
        if (null != stringExtra && stringExtra.equals(BindBankFlowEnum.TOINTRODUCE.getServicename())) {
            String title = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
            String url = getIntent().getStringExtra(ConstantValue.ARG_PARAM3);
            Bundle mbundle = new Bundle();
            mbundle.putString(ConstantValue.ARG_PARAM1, title);
            mbundle.putString(ConstantValue.ARG_PARAM2, url);
            startActivity(new Intent(AddBankActivity.this, IntroduceActivity.class).putExtras(mbundle));
        }
        finish();
        RxBus.get().post(ConstantValue.BANK_MANAGEMENT_ALTERATION, "BankManagementActivity");
    }
}
