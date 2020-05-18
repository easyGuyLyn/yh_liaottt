package com.nwf.sports.utils;

import android.os.Handler;
import android.view.View;

import com.dawoo.coretool.util.Check;

import timber.log.Timber;

/**
 * <p>类描述： 防止双击事件
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class DoubleClickHelper
{
    //双击的事件范围2S内有效
    private static final int MIN_DOUBLE_CLICK_TIME = 2000;
    //进入电子游戏的事件范围5s内有效
    private static final int MIN_ENTER_ELEC_GAME_TIME = 4000;
    //进入大厅游戏的事件范围8S内有效
    private static final int MIN_ENTER_HOME_GAME_TIME = 8000;
    private static long lastClickTime;
    private static long lastClickElecTime;
    private static long lastClickHomeTime;
    private static Handler mHandler = new Handler();
    private static DoubleClickHelper newInstance;

    public static DoubleClickHelper getNewInstance()
    {
        if (Check.isNull(newInstance))
        {
            newInstance = new DoubleClickHelper();
        }
        return newInstance;
    }

    //防止双击事件 是否双击了 false 为双击[点击了2次],true为 不是双击[首次点击]
    public boolean isDoubleClick()
    {
        boolean flag = false;
        long currClickTime = System.currentTimeMillis();
        if (currClickTime - lastClickTime >= MIN_DOUBLE_CLICK_TIME)
        {
            flag = true;
        }
        lastClickTime = currClickTime;
        return flag;
    }

    /**
     * 电子游戏时间限制
     *
     * @return
     */
    public boolean onEnterElecGame()
    {
        boolean flag = false;
        long currClickTime = System.currentTimeMillis();
        if (currClickTime - lastClickElecTime >= MIN_ENTER_ELEC_GAME_TIME)
        {
            flag = true;
            lastClickElecTime = currClickTime;
        }
        return flag;
    }

    /**
     * 大厅游戏时间限制
     *
     * @return
     */
    public boolean onEnterHomeGame()
    {
        boolean flag = false;
        long currClickTime = System.currentTimeMillis();
        if (currClickTime - lastClickHomeTime >= MIN_ENTER_HOME_GAME_TIME)
        {
            flag = true;
            lastClickHomeTime = currClickTime;
        }
        return flag;
    }

    //防止双击事件
    public void disabledView(final View view)
    {
        view.setClickable(false);
        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                view.setClickable(true);
            }
        }, MIN_DOUBLE_CLICK_TIME);
    }

    //防止快速点击，true快速点击 false 非快速点击
    public boolean isFastClick()
    {
        boolean isFastClick = true;
        long currClickTime = System.currentTimeMillis();
        Timber.d("点击:%d-%d=%d", currClickTime, lastClickTime, currClickTime - lastClickTime);
        final int interval = 1000;
        if (currClickTime - lastClickTime >= interval)
        {
            isFastClick = false;
            //Timber.d("非快速点击:%d-%d=%d",currClickTime,lastClickTime,currClickTime-lastClickTime);
            lastClickTime = currClickTime;
        }

        return isFastClick;
    }
}

