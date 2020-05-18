package com.nwf.sports.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.SpannableStringUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.DepositTransferBean;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.ui.dialogfragment.DepositGuideDialogFragment;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.ActivityUtil;
import com.nwf.sports.utils.BankDrawableUtil;
import com.nwf.sports.utils.CLipHelper;
import com.nwf.sports.utils.HideDataUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： BQ存款 银卡信息
 * <p>创建人：Simon
 * <p>创建时间：2019-04-11
 * <p>修改人：Simon
 * <p>修改时间：2019-04-11
 * <p>修改备注：
 **/
public class DepositbankFasterActivity extends BaseActivity {

    @BindView(R.id.v_dep_transfer_title)
    PNTitleBar vDepTransferTitle;

    @BindView(R.id.show_layout)
    LinearLayout showLayout; //导航栏
    @BindView(R.id.bank_show_layout)
    LinearLayout bankShowLayout;//银行卡界面
    @BindView(R.id.alipay_relativelayout)
    RelativeLayout alipayRelativelayout; //支付宝展示界面

    @BindView(R.id.bank_tv)
    TextView bankTv;
    @BindView(R.id.bank_below)
    TextView bankBelow;

    @BindView(R.id.alipay_tv)
    TextView alipayTv;
    @BindView(R.id.alipay_below)
    TextView alipayBelow;

    @BindView(R.id.tv_deposite_faster_bankname)
    TextView tvDepositeFasterBankname;
    @BindView(R.id.ivw_del)
    ImageView ivwDel;
    @BindView(R.id.tv_deposite_faster_accountnumber)
    TextView tvDepositeFasterAccountnumber;
    @BindView(R.id.im_deposite_faster_accountnumber)
    ImageView imDepositeFasterAccountnumber;
    @BindView(R.id.rl_deposite_faster_accountnumber)
    LinearLayout rlDepositeFasterAccountnumber;
    @BindView(R.id.tv_deposite_faster_bankcity)
    TextView tvDepositeFasterBankcity;
    @BindView(R.id.ll_dep_bank_city)
    LinearLayout llDepBankCity;
    @BindView(R.id.ll_dep_bank_branch_name)
    TextView llDepBankBranchName;
    @BindView(R.id.ll_dep_bank_branch)
    LinearLayout llDepBankBranch;
    @BindView(R.id.tv_deposite_faster_accountname)
    TextView tvDepositeFasterAccountname;
    @BindView(R.id.im_deposite_faster_accountname)
    ImageView imDepositeFasterAccountname;
    @BindView(R.id.rl_deposite_faster_accountname)
    LinearLayout rlDepositeFasterAccountname;
    @BindView(R.id.tv_deposite_faster_ebankmoney)
    TextView tvDepositeFasterEbankmoney;
    @BindView(R.id.ll_dep_bank_amount)
    LinearLayout llDepBankAmount;
    @BindView(R.id.tvw_deposit_faster_tip)
    TextView tvwDepositFasterTip;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    @BindView(R.id.alipay_imag)
    ImageView alipayImag;
    @BindView(R.id.btn_to_zfb)
    TextView btnToZfb;
    @BindView(R.id.tv_conceal_service)
    TextView tvConcealService;
    @BindView(R.id.tvw_dep_guide)
    TextView tvwDepGuide;

    private String paymannerid = "";  //存款方式ID
    private String money = "";//存款金额
    private DepositTransferBean mDepositTransferBean = null;    // 存款人姓名 以及类型
    FasterPay fasterPay = null;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_deposit_bank_faster);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            paymannerid = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            money = getIntent().getStringExtra(ConstantValue.ARG_PARAM2);
            mDepositTransferBean = (DepositTransferBean) getIntent().getSerializableExtra(ConstantValue.ARG_PARAM3);
            fasterPay = getIntent().getParcelableExtra(ConstantValue.ARG_PARAM4);
        } else {
            showMessage("系统异常请重试");
            finish();
        }
        onContractService();

        vDepTransferTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String amount = fasterPay.getOrder().getAmount();
        if (Double.valueOf(money) < Double.valueOf(amount)) {
            amount = "<b>" + amount + "</b>"; // android:textStyle="bold"
        } else {
            tvwDepositFasterTip.setVisibility(View.GONE);
        }
        tvDepositeFasterEbankmoney.setText(Html.fromHtml(amount));
        tvDepositeFasterBankname.setText(fasterPay.getOrder().getBankname());
        tvDepositeFasterBankcity.setText(fasterPay.getOrder().getBankcity());
        llDepBankBranchName.setText(fasterPay.getOrder().getBankaddress());
        BankDrawableUtil.setDrawable(tvDepositeFasterBankname, fasterPay.getOrder().getBankcode());

        if (!Check.isNull(fasterPay.getOrder().getChangeBQBankDisplay())) { //判断银行卡号、姓名是否显示全部，以及是否可以复制
            tvDepositeFasterAccountnumber.setText(fasterPay.getOrder().getAccountnumber().replaceAll("\\d{4}(?!$)", "$0 "));
            tvDepositeFasterAccountname.setText(fasterPay.getOrder().getAccountname());
            imDepositeFasterAccountnumber.setVisibility(View.VISIBLE);
            imDepositeFasterAccountname.setVisibility(View.VISIBLE);
            rlDepositeFasterAccountnumber.setClickable(true);
            rlDepositeFasterAccountname.setClickable(true);
        } else {
            String s = HideDataUtil.hideCardNo(fasterPay.getOrder().getAccountnumber(), 0, 4);
            tvDepositeFasterAccountnumber.setText(s.replaceAll("(.{4})", "$1\t\t"));
            tvDepositeFasterAccountname.setText(HideDataUtil.hideName(fasterPay.getOrder().getAccountname()));
            imDepositeFasterAccountnumber.setVisibility(View.INVISIBLE);
            imDepositeFasterAccountname.setVisibility(View.INVISIBLE);
            rlDepositeFasterAccountnumber.setClickable(false);
            rlDepositeFasterAccountname.setClickable(false);
        }
        if (null == fasterPay.getOrder().getBqCourses() || fasterPay.getOrder().getBqCourses().isEmpty()) {//是否显示查看教程
            tvwDepGuide.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(fasterPay.getOrder().getQrcode()) || !checkAliPayInstalled(mContext)) {  //判断是否显示导航栏
            showLayout.setVisibility(View.GONE);
            setShowLayout(true);
        } else {
            showLayout.setVisibility(View.VISIBLE);
            setShowLayout(true);
        }
    }

    @Override
    protected void initData() {

    }


    private void onContractService() {

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //联系客服
                ActivityUtil.skipToService(mContext);
//                onStartService();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //变化的颜色值
                ds.setColor(getResources().getColor(R.color.color_FF3300));
                //是否有下划线
                ds.setUnderlineText(true);
            }
        };

        CharSequence charSequence = new SpannableStringUtils.Builder()
                .append("若收款卡号和收款姓名被隐藏显示，").setForegroundColor(getResources().getColor(R.color.color_4A4A4A))
                .append("请联系客服").setForegroundColor(getResources().getColor(R.color.color_FF3300)).setClickSpan(clickableSpan)
//                .setBackgroundColor(getResources().getColor(R.color.white))
                .create();
        tvConcealService.setMovementMethod(LinkMovementMethod.getInstance());
        tvConcealService.setText(charSequence);
        tvConcealService.setVisibility(View.VISIBLE);
    }


    public void setShowLayout(boolean isShowLayout) {
        if (isShowLayout) {
            bankTv.setSelected(true);
            alipayTv.setSelected(false);
            alipayRelativelayout.setVisibility(View.GONE);
            alipayBelow.setVisibility(View.GONE);
            bankShowLayout.setVisibility(View.VISIBLE);
            bankBelow.setVisibility(View.VISIBLE);
        } else {
            bankTv.setSelected(false);
            alipayTv.setSelected(true);
            alipayRelativelayout.setVisibility(View.VISIBLE);
            alipayBelow.setVisibility(View.VISIBLE);
            bankShowLayout.setVisibility(View.GONE);
            bankBelow.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rl_deposite_faster_accountnumber, R.id.ll_dep_bank_city, R.id.ll_dep_bank_branch, R.id.rl_deposite_faster_accountname
            , R.id.ll_dep_bank_amount, R.id.rl_deposite_faster_ebankmoney_remark, R.id.btn_ok, R.id.bank_layout, R.id.alipay_layout, R.id.btn_to_zfb, R.id.tvw_dep_guide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_deposite_faster_accountnumber: //收款卡号
                CLipHelper.copy(mContext, fasterPay.getOrder().getAccountnumber());
                break;
            case R.id.ll_dep_bank_city: //开户城市
                CLipHelper.copy(mContext, fasterPay.getOrder().getBankcity());
                break;
            case R.id.ll_dep_bank_branch: //开户支行
                CLipHelper.copy(mContext, fasterPay.getOrder().getBankaddress());
                break;
            case R.id.rl_deposite_faster_accountname: //收款姓名
                CLipHelper.copy(mContext, fasterPay.getOrder().getAccountname());
                break;
            case R.id.ll_dep_bank_amount: //收款金额
                CLipHelper.copy(mContext, fasterPay.getOrder().getAmount());
                break;
            case R.id.tvw_dep_guide:
                DepositGuideDialogFragment depositGuideDialogFragment = DepositGuideDialogFragment.newInstance(fasterPay.getOrder().getBqCourses(), mDepositTransferBean.getBqpaytypeCode());
                DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), depositGuideDialogFragment);
                return;
            case R.id.btn_ok:
                finish();
                return;
            case R.id.btn_to_zfb:
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(fasterPay.getOrder().getQrcode());
                    intent.setData(uri);
                    startActivity(intent);
                } catch (Exception exception) {
                    showMessage("请确定是否安装支付宝");
                }
                return;
            case R.id.bank_layout:
                setShowLayout(true);
                return;
            case R.id.alipay_layout:
                setShowLayout(false);
                return;
            default:
                break;
        }
        showMessage("复制成功");
    }

    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

}
