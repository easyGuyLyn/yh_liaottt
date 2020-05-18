package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivi.imsdk.R;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * <p>类描述： 公用提醒弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class RemindCommonDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_titleupgrade)
    TextView mTvTitleupgrade;
    @BindView(R.id.tv_msg_upgrade)
    TextView mTvMsgUpgrade;
    @BindView(R.id.tv_relation)
    Button mTvRelation;

    private String title = "";
    private String content = "";
    private View.OnClickListener mOnRightButtonListener = null;
    private String mRightButtonName = "";

    boolean isClose = true;

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_remind_common;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isClose) {
            mIsOutCanback = true;
            mIsKeyCanback = true;
        } else {
            mIsOutCanback = false;
            mIsKeyCanback = false;
        }
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        super.onCreate(savedInstanceState);
    }

    public RemindCommonDialogFragment setClose(boolean close) {
        isClose = close;
        return this;
    }

    @Override
    protected void initViews(View view) {

        if (!TextUtils.isEmpty(content)) {
            mTvMsgUpgrade.setText(content);
        }
        if (!TextUtils.isEmpty(title)) {
            mTvTitleupgrade.setText(title);
        }

        if (!TextUtils.isEmpty(mRightButtonName)) {
            mTvRelation.setText(mRightButtonName);
        } else {
            mTvRelation.setText("确定");
        }

        if (mOnRightButtonListener != null) {
            mTvRelation.setOnClickListener(mOnRightButtonListener);
        } else {
            mTvRelation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                }
            });
        }
    }


    @Override
    protected void initData() {

    }

    public RemindCommonDialogFragment setTvTitle(String title) {
        this.title = title;
        return this;
    }

    public RemindCommonDialogFragment setTvContent(String content) {
        this.content = content;
        return this;
    }

    public RemindCommonDialogFragment setOnRightButtonListener(View.OnClickListener onClickListener, String name) {
        mOnRightButtonListener = onClickListener;
        mRightButtonName = name;
        return this;
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