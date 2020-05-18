package com.nwf.sports.ui.activity;

import com.ivi.imsdk.R;
import com.nwf.sports.ui.fragment.WithdrawalFragment;

/**
 * <p>类描述： 取款页面
 * <p>创建人：Simon
 * <p>创建时间：2020-02-14
 * <p>修改人：Simon
 * <p>修改时间：2020-02-14
 * <p>修改备注：
 **/
public class WithdrawalActivity extends BaseActivity {

    WithdrawalFragment withdrawalFragment = null;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_withdrawal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (withdrawalFragment!=null){
            withdrawalFragment.getloadData();
        }
    }

    @Override
    protected void initViews() {
        withdrawalFragment = WithdrawalFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFrameLayout, withdrawalFragment, "content")
                .commit();
    }

    @Override
    protected void initData() {

    }
}
