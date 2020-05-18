package com.nwf.sports.ui.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.ui.views.BannerIndicatorView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 转账教程
 * <p>创建人：Simon
 * <p>创建时间：2019-04-11
 * <p>修改人：Simon
 * <p>修改时间：2019-04-11
 * <p>修改备注：
 **/
public class DepositGuideDialogFragment extends BaseDialogFragment {

    @BindView(R.id.banner_indicator)
    BannerIndicatorView mBannerIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.spn_title)
    Spinner spnTitle;
    @BindView(R.id.tvw_title)
    TextView tvwTitle;
    ArrayList<FasterPay.GuideVo> mGuideVos = new ArrayList<>();//0 是支付宝  1 是微信 2是百度
    FasterPay.GuideVo curVo;
    String transferTypeCode = "2"; //2 是支付宝  1 是微信 0是银行
    DepGuideAdapter mAdapter = null;

    public static DepositGuideDialogFragment newInstance(ArrayList<FasterPay.GuideVo> list, String transferTypeCode) {
        DepositGuideDialogFragment depositGuideDialogFragment = new DepositGuideDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ConstantValue.ARG_PARAM1, list);
        args.putString(ConstantValue.ARG_PARAM2, transferTypeCode);
        depositGuideDialogFragment.setArguments(args);
        return depositGuideDialogFragment;
    }

    @Override
    protected int getViewId() {
        intScreenWProportion = 1;
        intScreenHProportion = 1;
        AnimationsStyle = -1;
        return R.layout.dialogfragment_deposit_guide;
    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            mGuideVos = getArguments().getParcelableArrayList(ConstantValue.ARG_PARAM1);
            transferTypeCode = getArguments().getString(ConstantValue.ARG_PARAM2);
        }

//        mBannerIndicator.bindWithViewPager();
        mBannerIndicator.setCellCount(5);

        ArrayList<String> listStr = new ArrayList<>();
        for (FasterPay.GuideVo t : mGuideVos) {
            listStr.add(t.getTitle());
        }
        String[] mItems = listStr.toArray(new String[listStr.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.fragment_deposit_guide_item, mItems);
        spnTitle.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.fragment_deposit_guide_item);

        spnTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curVo = mGuideVos.get(position);
                tvwTitle.setText(curVo.getTitle());
                mAdapter = new DepGuideAdapter(getContext(), curVo.getIconList());
                viewPager.setAdapter(mAdapter);
                mBannerIndicator.bindWithViewPager(viewPager, curVo.getIconList().size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (transferTypeCode.equals("1")) {
            spnTitle.setSelection(1); // 微信
        } else {
            spnTitle.setSelection(0); // 必选,默认选中第一个
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.transparency, R.id.ivw_close, R.id.tvw_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.transparency:
            case R.id.ivw_close:
                dismissAllowingStateLoss();
                break;
            case R.id.tvw_title:
                spnTitle.performClick();
                break;
        }
    }


    public class DepGuideAdapter extends PagerAdapter {
        Context mContext;
        ArrayList<String> mList;

        public DepGuideAdapter(Context ctx, ArrayList<String> mList) {
            this.mContext = ctx;
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public ImageView instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView ivw = new ImageView(mContext);
            ivw.setPadding(2, 2, 2, 2);
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.nwf_viewpage_item_loading);
            //加载图片
            Glide.with(mContext)
                    .load(mList.get(position))
                    .apply(options)
                    .into(ivw);
            container.addView(ivw);
            return ivw;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ImageView ivw = (ImageView) object;
            container.removeView(ivw);
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

    }

}
