package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.SpannableStringUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.mvp.model.CreditQueryResult;
import com.nwf.sports.mvp.model.ExtraBean;
import com.nwf.sports.mvp.model.RedPacketResult;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LoadingDialogFragment;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.MD5Util;
import com.nwf.sports.utils.data.DataCenter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.model.Conversation;

/**
 * <p>类描述： 发送普通红包
 * <p>创建人：Simon
 * <p>创建时间：2019-07-17
 * <p>修改人：Simon
 * <p>修改时间：2019-07-17
 * <p>修改备注：
 **/
public class SendRedPacketActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_money)
    EditText tvMoney;
    @BindView(R.id.tv_hint)
    EditText tvHint;
    @BindView(R.id.tv_money_number)
    EditText tvMoneyNumber;
    @BindView(R.id.tv_money_big)
    TextView tvMoneyBig;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    String target = "";
    ExtraBean mExtraBean = null;
    Conversation.ConversationType type = null;
    CreditQueryResult mCreditQueryResult = null;
    @BindView(R.id.tv_balance_name)
    TextView tvBalanceName;
    @BindView(R.id.tv_amount_restrict)
    TextView tvAmountRestrict;
    @BindView(R.id.tv_number_restrict)
    TextView tvNumberRestrict;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_send_red_packet);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            target = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            type = (Conversation.ConversationType) getIntent().getSerializableExtra(ConstantValue.ARG_PARAM2);
            mExtraBean = getIntent().getParcelableExtra(ConstantValue.ARG_PARAM3);
        }
        tvMoney.setFilters(new InputFilter[]{new PointInputFilter()});
        tvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String s = editable.toString();
                int len = s.length();
//                if (len > 1 && s.startsWith("0")) {
//                    editable.replace(0, 1, "");
//                }
                if (TextUtils.isEmpty(editable) || s.equals("0") || s.equals(".")) {
                    tvMoneyBig.setText("0.00");
                    return;
                } else {
                    BigDecimal bigDecimal = new BigDecimal(s);
                    tvMoneyBig.setText(bigDecimal.toPlainString());
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (type.getValue() == Conversation.ConversationType.Single.getValue()) {
            tvMoneyNumber.setText("1");
            tvMoneyNumber.setFocusable(false);
        }
        if (mExtraBean != null) {
            if (mExtraBean.lowestValue.equals("0") && mExtraBean.hightestValue.equals("0")) {
                tvAmountRestrict.setVisibility(View.INVISIBLE);
            } else {
                tvAmountRestrict.setVisibility(View.VISIBLE);
                CharSequence charSequence = new SpannableStringUtils.Builder()
                        .append("红包金额范围 ").setForegroundColor(getResources().getColor(R.color.color_9B9B9B))
                        .append(mExtraBean.lowestValue + "-" + mExtraBean.hightestValue).setForegroundColor(getResources().getColor(R.color.color_1ECE94))
                        .append(" 元").setForegroundColor(getResources().getColor(R.color.color_9B9B9B))
                        .create();
                tvAmountRestrict.setText(charSequence);
            }

            if (mExtraBean.minRedpacketNumber == 0 && mExtraBean.maxRedpacketNumber == 0) {
                tvNumberRestrict.setVisibility(View.INVISIBLE);
            } else {
                tvNumberRestrict.setVisibility(View.VISIBLE);
                CharSequence charSequence = new SpannableStringUtils.Builder()
                        .append("红包个数范围 ").setForegroundColor(getResources().getColor(R.color.color_9B9B9B))
                        .append(mExtraBean.minRedpacketNumber + "-" + mExtraBean.maxRedpacketNumber).setForegroundColor(getResources().getColor(R.color.color_1ECE94))
                        .append(" 个").setForegroundColor(getResources().getColor(R.color.color_9B9B9B))
                        .create();
                tvNumberRestrict.setText(charSequence);
            }
        } else {
            tvAmountRestrict.setVisibility(View.INVISIBLE);
            tvNumberRestrict.setVisibility(View.INVISIBLE);
        }

    }

    LoadingDialogFragment mLoadingDialogFragment = null;

    @Override
    protected void initData() {

        AppService.Instance().creditQuery(new AppService.CreditQueryCallback() {

            @Override
            public void onSuccess(CreditQueryResult statusResult) {
                mCreditQueryResult = statusResult;
                tvBalance.setText(statusResult.getBalance());
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.showToastLong(msg);
            }
        });

    }


    @OnClick(R.id.btn_ok)
    public void onViewClicked() {

        String trim = tvMoney.getText().toString().trim();
        String trim1 = tvMoneyNumber.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showMessage("请填写金额");
            return;
        }
        if (TextUtils.isEmpty(trim1)) {
            showMessage("请填写发送数量");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productId", Constant.PRODUCT_ID);
        params.put("userName", DataCenter.getInstance().getUserInfoBean().username);
        params.put("sendTitle", tvHint.getText().toString().trim());
        params.put("sendNumber", tvMoneyNumber.getText().toString().trim());
        if (type.getValue() == Conversation.ConversationType.Single.getValue()) {  //红包类型（0手气红包，1普通红包，2单人红包，3埋雷红包，4牛牛红包）
            params.put("sendType", "2");
        } else {
            params.put("sendType", "0");
        }
        params.put("sendAmount", tvMoney.getText().toString().trim());
        params.put("groupId", target);
        params.put("specialNumber", "");
        params.put("grabPacketKey", MD5Util.md5(target + Constant.PRODUCT_ID  + DataCenter.getInstance().getUserInfoBean().username, MD5Util.md5Key));

        mLoadingDialogFragment = new LoadingDialogFragment();
        DialogFramentManager.getInstance().showDialog(getSupportFragmentManager(), mLoadingDialogFragment);

        AppService.Instance().sendRedPacket(params, new AppService.SendRedPacketCallback() {
            @Override
            public void onSuccess(RedPacketResult redPacketResult) {
                Intent data = new Intent();
                data.putExtra("redPacketData", redPacketResult);
                setResult(RESULT_OK, data);
                finish();
                DialogFramentManager.getInstance().clearDialog();
            }

            @Override
            public void onFailure(int code, String msg) {
                DialogFramentManager.getInstance().clearDialog();
                ToastUtil.showToastLong(msg);
            }
        });

    }


    public class PointInputFilter implements InputFilter {

        private int decimal_digits = 2;//小数的位数
        private int integer_digits = 10;//整数位的位数
        private int total_digits = 13; //整数部分 + “小数点” + 小数部分
        private int currentLimitDigits = integer_digits;


        public PointInputFilter(int DECIMAL_DIGITS, int INTEGER_DIGITS, int TOTAL_DIGITS) {
            this.decimal_digits = DECIMAL_DIGITS;
            this.integer_digits = INTEGER_DIGITS;
            this.total_digits = TOTAL_DIGITS;
        }

        public PointInputFilter() {

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if ("0".equals(dest.toString()) && !".".equals(source.toString()))
                return "";
            if (decimal_digits == 0 && ".".equals(source.toString())) {
                return "";
            }
            // 删除等特殊字符，直接返回
            if ("".equals(source.toString())) {
                return null;
            }

            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            switch (splitArray.length) {
                case 1:
                    if (dValue.indexOf(".") != -1) {
                        currentLimitDigits = total_digits;
                    } else {
                        currentLimitDigits = integer_digits;
                    }

                    if (source.length() > 1) {

                        String sValue = source.toString();
                        String[] subSplitArray = sValue.split("\\.");
                        switch (subSplitArray.length) {
                            case 1:
                                if (source.length() + dest.length() > currentLimitDigits) {
                                    return source.subSequence(0, currentLimitDigits - dest.length());
                                }
                                break;
                            case 2:
                                String content = "";

                                if (dstart == dest.length()) {
                                    if (subSplitArray[0].length() + dest.length() > integer_digits) {
                                        content += subSplitArray[0].subSequence(0, integer_digits - dest.length());
                                    } else {
                                        content += subSplitArray[0];
                                    }

                                    if (subSplitArray[1].length() > decimal_digits) {
                                        content += "." + subSplitArray[1].substring(0, decimal_digits);

                                    } else {
                                        content += "." + subSplitArray[1];
                                    }
                                    return content;

                                } else {
                                    if (subSplitArray[0].length() + dest.length() > integer_digits) {
                                        content += subSplitArray[0].subSequence(0, integer_digits - dest.length());
                                    } else {
                                        content += subSplitArray[0];
                                    }
                                }
                                return content;

                            default:
                                break;
                        }

                    }

                    if (splitArray[0].length() >= currentLimitDigits && !".".equals(source.toString())) {
                        return "";
                    }

                    break;

                case 2:
                    String integerValue = splitArray[0];
                    String dotValue = splitArray[1];
                    int dotIndex = dValue.indexOf(".");

                    if (dstart <= dotIndex) {

                        if (integerValue.length() >= integer_digits) {
                            return "";
                        } else if (source.length() + integerValue.length() >= integer_digits) {
                            return source.subSequence(0, integer_digits - integerValue.length());
                        }

                    } else {

                        if (dotValue.length() >= decimal_digits) {
                            return "";
                        } else if (source.length() + dotValue.length() >= decimal_digits) {
                            return source.subSequence(0, decimal_digits - dotValue.length());
                        }
                    }

                    break;
                default:
                    break;
            }

            return null;
        }
    }
}
