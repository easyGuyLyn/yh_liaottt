package com.nwf.sports.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    Unbinder unbinder;
    protected Context mContext;
    //Fragment的View加载完毕的标记
    public boolean isViewCreated;
    //Fragment对用户可见的标记
    public boolean isUIVisible;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (0 != setLayoutId()) {
            View view = LayoutInflater.from(getContext()).inflate(setLayoutId(), null, false);
            AutoUtils.auto(view);
            unbinder = ButterKnife.bind(this, view);
            initViews(savedInstanceState);
            return view;
        }
        return null;
    }

    //设置布局文件的id
    public abstract int setLayoutId();

    //初始化一些必要的数据
    public abstract void initViews(@Nullable Bundle savedInstanceState);

    protected abstract void loadData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            loadData();

            LogUtils.e("可见,加载数据");
        }
    }

    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtil.showToastLong(message);
        }
    }

}
