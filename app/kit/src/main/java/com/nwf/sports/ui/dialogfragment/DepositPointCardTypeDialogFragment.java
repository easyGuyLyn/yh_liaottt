package com.nwf.sports.ui.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：选择点卡类型
 * <p>创建人：Simon
 * <p>创建时间：2019-02-12
 * <p>修改人：Simon
 * <p>修改时间：2019-02-12
 * <p>修改备注：
 **/
public class DepositPointCardTypeDialogFragment extends BaseDialogFragment {

    public Context mContext;
    @BindView(R.id.ivw_close)
    ImageView mClose;
    @BindView(R.id.rv_main)
    RecyclerView mRvMain;
    @BindView(R.id.title)
    TextView mTitle;

    CommonAdapter commonAdapter = null;
    int cont = 3;
    String titleString = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsOutCanback = true;
        mIsKeyCanback = true;
        AnimationsStyle = -1;
        intScreenWProportion=1;
        intScreenHProportion=1;
        super.onCreate(savedInstanceState);
    }

    public void setData(Context context, CommonAdapter commonAdapter, int cont, String title) {
        this.mContext = context;
        this.commonAdapter = commonAdapter;
        this.cont = cont;
        this.titleString = title;
    }

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_point_card;
    }

    @Override
    protected void initViews(View view) {
        if (TextUtils.isEmpty(titleString)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setText(titleString);
        }
        mRvMain.setLayoutManager(new GridLayoutManager(getContext(), cont));
        mRvMain.setAdapter(commonAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ivw_close, R.id.transparency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivw_close:
            case R.id.transparency:
                dismissAllowingStateLoss();
                break;
        }
    }
}
