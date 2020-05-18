package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述：优惠列表
 * <p>创建人：Simon
 * <p>创建时间：2019-05-15
 * <p>修改人：Simon
 * <p>修改时间：2019-05-15
 * <p>修改备注：
 **/
public class DiscountsActivity extends BaseActivity {


    @BindView(R.id.v_title)
    PNTitleBar vTitle;
    @BindView(R.id.rv_discounts_list)
    RecyclerView rvDiscountsList;

    private CommonAdapter<HomeDiscountsResult.PromotionsListBean> mHomeDiscountsAdapter = null; //优惠adapter
    List<HomeDiscountsResult.PromotionsListBean> mDiscounts = new ArrayList<>();

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_discounts);
    }

    @Override
    protected void initViews() {
        if (getIntent()!=null){
            List<HomeDiscountsResult.PromotionsListBean> promotionsListBeans = getIntent().getParcelableArrayListExtra(ConstantValue.ARG_PARAM1);
            mDiscounts.clear();
            mDiscounts.addAll(promotionsListBeans);
        }
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vTitle.setMoreListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.skipToService(mContext);
            }
        });

        rvDiscountsList.setLayoutManager(new LinearLayoutManager(mContext));
        mHomeDiscountsAdapter = new CommonAdapter<HomeDiscountsResult.PromotionsListBean>(mContext, R.layout.activity_discounts_item, mDiscounts) {
            @Override
            public void convert(ViewHolder holder, HomeDiscountsResult.PromotionsListBean item, int position) {
                ImageView imageView = holder.getView(R.id.imageview);
                TextView title = holder.getView(R.id.title);
                TextView time = holder.getView(R.id.time);
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(27);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
                //加载图片
                Glide.with(mContext)
                        .load(item.getUrl())
                        .apply(options)
                        .into(imageView);

                title.setText(item.getTitle());
                time.setText(item.getPromotionsBeginTime()+"-"+item.getPromotionsEndTime());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle mbundle = new Bundle();
                        mbundle.putString(ConstantValue.ARG_PARAM1, item.getTitle());
                        mbundle.putString(ConstantValue.ARG_PARAM2, item.getActionUrl());
                        startActivity(new Intent(DiscountsActivity.this, IntroduceActivity.class).putExtras(mbundle));
                    }
                });
            }
        };
        rvDiscountsList.setAdapter(mHomeDiscountsAdapter);
    }

    @Override
    protected void initData() {

    }

}
