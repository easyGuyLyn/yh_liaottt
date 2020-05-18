package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;

import com.ivi.imsdk.R;

import androidx.annotation.Nullable;
import butterknife.OnClick;

/**
 * <p>类描述： 押金规则
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class GuaranteeRuleDialogFragment extends BaseDialogFragment {


    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_guarantee_rule;
    }


    public static GuaranteeRuleDialogFragment getInstance() {
        GuaranteeRuleDialogFragment robRedPacketDialogFragment = new GuaranteeRuleDialogFragment();
        Bundle bundle = new Bundle();
        robRedPacketDialogFragment.setArguments(bundle);
        return robRedPacketDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        mIsOutCanback = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.tv_roger})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_roger:
                dismissAllowingStateLoss();
                break;
        }
    }
}
