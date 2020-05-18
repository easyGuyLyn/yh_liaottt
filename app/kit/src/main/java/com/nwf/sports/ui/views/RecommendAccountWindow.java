package com.nwf.sports.ui.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dawoo.coretool.util.activity.DensityUtil;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>类描述：弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-29
 * <p>修改人：Simon
 * <p>修改时间：2019-03-29
 * <p>修改备注：
 **/
public class RecommendAccountWindow {

    private View mContentView;
    private PopupWindow mPopWindow = null;
    private RecyclerView mRecyclerView = null;
    private final Context mContext;

    public RecommendAccountWindow(Context context, CommonAdapter adapter) {
        mContext = context;
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.view_recommend_account, null);
            mRecyclerView = mContentView.findViewById(R.id.recycle_view);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mContentView.measure(0, 0);
        if (mPopWindow == null) {
            int measuredHeight = mContentView.getMeasuredHeight();
            mPopWindow = new PopupWindow(mContentView, DensityUtil.dp2px(mContext, mContentView.getMeasuredWidth() * 3 / 4), ViewGroup.LayoutParams.WRAP_CONTENT, true);
//            mPopWindow = new PopupWindow();
            mPopWindow.setTouchable(true);
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        }
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) mContext).getWindow().setAttributes(lp);
            }
        });
    }

    public void doTogglePopupWindow(View view) {
        if (mPopWindow == null) {
            return;
        }

        if (mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        } else {
            // 设置背景颜色变暗
//            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
//            lp.alpha = 0.7f;
//            ((Activity) mContext).getWindow().setAttributes(lp);
            mPopWindow.setWidth(view.getWidth());
            mPopWindow.showAsDropDown(view);
        }
    }

    public void dissMissPopWindow() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }
}
