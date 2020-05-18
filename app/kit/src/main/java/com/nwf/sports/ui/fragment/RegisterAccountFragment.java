package com.nwf.sports.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.dawoo.coretool.util.RegexUtils;
import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.listener.SwitchoverListener;
import com.nwf.sports.mvp.model.RegisterResult;
import com.nwf.sports.mvp.model.SkipLoginBean;
import com.nwf.sports.mvp.presenter.CheckAccountPresenter;
import com.nwf.sports.mvp.view.CheckAccountView;
import com.nwf.sports.ui.views.RecommendAccountWindow;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.DoubleClickHelper;
import com.nwf.sports.utils.textviewlink.TextViewLinkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 账号注册
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class RegisterAccountFragment extends BaseFragment implements CheckAccountView {

    SwitchoverListener mSwitchoverListener;

    @BindView(R.id.tv_login)
    public TextView mTvLogin;
    @BindView(R.id.skip_second)
    public TextView skipSecond;
    @BindView(R.id.tv_register_account_err)
    public TextView tvRegisterAccountErr;
    @BindView(R.id.ed_account)
    public EditText mEdAccount;
    RecommendAccountWindow mRecommendAccountWindo = null;
    List<String> mRecommendAccountWindoList = new ArrayList<>();
    CommonAdapter<String> mRecommendAccountWindoAdapter = null;
    private String account;
    CheckAccountPresenter mCheckAccountPresenter;     //检查账号

    public static RegisterAccountFragment newInstance(SwitchoverListener switchoverListener) {
        RegisterAccountFragment fragment = new RegisterAccountFragment();
        fragment.setSwitchoverListener(switchoverListener);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setSwitchoverListener(SwitchoverListener switchoverListener) {
        this.mSwitchoverListener = switchoverListener;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_register_account;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mCheckAccountPresenter = new CheckAccountPresenter(getActivity(), this);
        TextViewLinkUtil.textLinkOnClick(getContext(), "已有账号？", "立即登录",
                "", "#100000", false, mTvLogin,
                new TextViewLinkUtil.TextViewLinkClickableSpan() {
                    @Override
                    public void show(int position, String linkString) {
                        SkipLoginBean skipLoginBean = new SkipLoginBean("LoginAccouutFragment", "");
                        RxBus.get().post(ConstantValue.SKIP_LOGIN, skipLoginBean);
                    }
                });
        mRecommendAccountWindoAdapter = new CommonAdapter<String>(getActivity(), R.layout.view_recommend_account_item, mRecommendAccountWindoList) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
                TextView textView = holder.getView(R.id.tv_register_recommend_account);
                textView.setText(item.substring(1,account.length()));
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        account = item.substring(1,account.length());
                        mEdAccount.setText(account);
                        mEdAccount.setSelection(mEdAccount.getText().length());
                        mRecommendAccountWindo.dissMissPopWindow();
                    }
                });
            }
        };
        mEdAccount.addTextChangedListener(AccountWatcher);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.skip_phone, R.id.skip_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.skip_phone:
                if (mSwitchoverListener != null) {
                    mSwitchoverListener.OnSwitchoverListener("RegisterPhoneFragment");
                }
                break;
            case R.id.skip_second:
                if (onVeifyCanRegisterAccount() && DoubleClickHelper.getNewInstance().isDoubleClick()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userName", Constant.PRODUCT_INITIAL + account);
                    mCheckAccountPresenter.onCheckAccount(map);
                }
                break;
        }
    }


    TextWatcher AccountWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {
            if (s.length() >= 5) {
                onVeifyCanRegisterAccount();
            } else {
                skipSecond.setEnabled(false);
                tvRegisterAccountErr.setText(getResources().getString(R.string.n_register_account_expression));
                tvRegisterAccountErr.setTextColor(getResources().getColor(R.color.color_4A4A4A));
            }
        }
    };

    /**
     * 校验注册按钮是否可点击
     *
     * @return
     */
    private boolean onVeifyCanRegisterAccount() {
        boolean isCanRegister = false;
        account = mEdAccount.getText().toString().trim();
        if (RegexUtils.isAccount(account)) {
            isCanRegister = true;
            tvRegisterAccountErr.setText(getResources().getString(R.string.n_register_account_expression));
            tvRegisterAccountErr.setTextColor(getResources().getColor(R.color.color_4A4A4A));
            skipSecond.setEnabled(true);
        } else {
            isCanRegister = false;
            tvRegisterAccountErr.setText(getString(R.string.n_register_account_expression_error));
            tvRegisterAccountErr.setTextColor(getResources().getColor(R.color.color_FF3300));
            skipSecond.setEnabled(false);
        }
        return isCanRegister;
    }

    @Override
    public void onIllegal(boolean illegal, String message) {
        showMessage(message);
    }

    @Override
    public void onCheckAccountSuccess(RegisterResult registerResult) {
        if (Check.isNull(registerResult)) {
            return;
        }
        if (registerResult.isFlag()) {
            if (mSwitchoverListener != null) {
                mSwitchoverListener.OnSwitchoverListener("RegisterAccountSecondFragment");
                RxBus.get().post(ConstantValue.REGISTER_ACCOUNT, account);
            }
        }else {
            mRecommendAccountWindoList.clear();
            mRecommendAccountWindoList.addAll(registerResult.getRecommendAccount());
            mRecommendAccountWindo = new RecommendAccountWindow(getActivity(), mRecommendAccountWindoAdapter);
            mRecommendAccountWindo.doTogglePopupWindow(mEdAccount);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCheckAccountPresenter != null) {
            mCheckAccountPresenter.onDestory();
        }
    }
}
