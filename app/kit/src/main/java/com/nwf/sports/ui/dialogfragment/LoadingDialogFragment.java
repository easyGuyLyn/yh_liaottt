package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ivi.imsdk.R;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * 等待 Dialog
 */
public class LoadingDialogFragment extends BaseDialogFragment {


    @BindView(R.id.loading_pro)
    ImageView loadingPro;

    @Override
    protected int getViewId() {
        return R.layout.loading_dialogfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsOutCanback = true;
        mIsKeyCanback = true;
        isFlags = false;
        AnimationsStyle = -1;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        //加载图片
        Glide.with(getActivity())
                .load(R.drawable.loading)
                .apply(options)
                .into(loadingPro);

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

}
