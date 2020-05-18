package com.nwf.sports.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.ivi.imsdk.R;


/**
 * 自定义公共标题控件
 *
 */

public class PNTitleBar extends RelativeLayout {
    //是否显示返回按钮的图标
    private boolean backImageShow;
    //标题文字
    private String titleText;
    //标题文字的颜色
    private int textColor;
    //标题更多文字的颜色
    private int moreColor;
    //是否显示更多的图标
    private boolean moreImageShow;
    //更多的文字
    private String moreText;
    //返回按钮
    private ImageView ivBack;
    //标题
    private TextView tvTitle;
    //更多
    private TextView tvMore;
    //默认显示更多的图标
    private int moreImage;
    //只显示更多文本
    private boolean moreTextOnly;
    public PNTitleBar(Context context) {
        super(context);
    }

    public PNTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_title_bar, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.nwf_NTitleBar, 0, 0);
        try {
            titleText = ta.getString(R.styleable.nwf_NTitleBar_titleText);
            backImageShow = ta.getBoolean(R.styleable.nwf_NTitleBar_backImageShow, true);
            moreImageShow = ta.getBoolean(R.styleable.nwf_NTitleBar_moreImageShow, true);
            moreText = ta.getString(R.styleable.nwf_NTitleBar_moreText);
            moreImage = ta.getResourceId(R.styleable.nwf_NTitleBar_moreImage,R.mipmap.icon_service);
            textColor = ta.getColor(R.styleable.nwf_NTitleBar_titleColor,context.getResources().getColor(R.color.color_030303));
            moreColor = ta.getColor(R.styleable.nwf_NTitleBar_moreColor,context.getResources().getColor(R.color.color_030303));
            moreTextOnly=ta.getBoolean(R.styleable.nwf_NTitleBar_moreTextOnly, false);
            setUpView(context);
        } finally {
            ta.recycle();
        }
    }

    //代码里面更改更多的图标
    public void setMoreImage(Drawable drawable){
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvMore.setCompoundDrawables(drawable,null,null,null);
    }


    private void setUpView(Context context){
        ivBack = (ImageView)findViewById(R.id.iv_com_title_back);
        tvTitle = (TextView)findViewById(R.id.tv_com_title_name);
        tvMore = (TextView)findViewById(R.id.tv_com_title_more);
        tvTitle.setTextColor(textColor);
        tvMore.setTextColor(moreColor);
        //返回按钮是否显示
        if(!backImageShow){
            ivBack.setVisibility(View.GONE);
        }
        //更多图标或者更多文字是否显示，如果只显示文字，可以设置moreImage为一个背景色即可，当然这里可以改成一个TextView和ImageView一起来做，但是目前已经满足需求了，如果后期有需要再重写
        if (!moreImageShow){
            tvMore.setVisibility(View.GONE);
        }
        if(!moreTextOnly)
        {
            Drawable drawable = context.getResources().getDrawable(moreImage);
            /// 获取图标的大小，这一步必须要做,否则不会显示
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvMore.setCompoundDrawables(drawable,null,null,null);
        }

        tvMore.setText(moreText);
        tvTitle.setText(titleText);
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title){
        titleText = title;
        tvTitle.setText(title);
    }

    /**
     * 设置扩展消息
     * @param moreText 扩展消息
     */
    public void setMoreText(String moreText){
        moreText = moreText;
        tvMore.setText(moreText);
    }

    /**
     * 设置返回消息事件
     * @param listener 返回消息listener
     */
    public void setBackListener(OnClickListener listener){
        ivBack.setOnClickListener(listener);
    }

    /**
     * 设置扩展事件
     * @param listener 扩展事件listener
     */
    public void setMoreListener(OnClickListener listener){
        if (!TextUtils.isEmpty(moreText)||!Check.isNull(moreImage)) {
            tvMore.setOnClickListener(listener);
        }
    }
}
