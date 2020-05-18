package com.nwf.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-03-29
 * <p>修改人：Simon
 * <p>修改时间：2019-03-29
 * <p>修改备注：
 **/
public class BannerPaddingViewHolder implements MZViewHolder<HomeDiscountsResult.BannerListBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.main_home_viewpage_item, null);
        AutoUtils.autoSize(view);
        mImageView = (ImageView) view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeDiscountsResult.BannerListBean data) {

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(27);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.nwf_banner_placeholder);
        //加载图片
        Glide.with(context)
                .load(data.getUrl())
                .apply(options)
                .into(mImageView);
    }
}
