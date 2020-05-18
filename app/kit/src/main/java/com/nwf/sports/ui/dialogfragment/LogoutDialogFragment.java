package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivi.imsdk.R;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * <p>类描述： 退出登录弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class LogoutDialogFragment extends BaseDialogFragment {


    @BindView(R.id.btn_confirm_exit_logout)
    Button btnConfirmExitLogout;
    @BindView(R.id.btn_cancel_logout)
    Button btnCancelLogout;

    private View.OnClickListener mOnClickListener = null;

    @Override
    protected int getViewId() {
        return R.layout.layout_logout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsOutCanback = true;
        intScreenWProportion = 1;
        intScreenHProportion = 1;
        AnimationsStyle = -1;
        super.onCreate(savedInstanceState);
    }

    public LogoutDialogFragment setOnLeftButtonListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }

    @Override
    protected void initViews(View view) {
        if (mOnClickListener != null) {
            btnConfirmExitLogout.setOnClickListener(mOnClickListener);
        } else {
            btnConfirmExitLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                }
            });
        }
        btnCancelLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

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

}
