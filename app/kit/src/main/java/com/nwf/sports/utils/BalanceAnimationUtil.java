package com.nwf.sports.utils;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * <p>类描述： 本地动画
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public class BalanceAnimationUtil {


    public static void startAnimation(ImageView imBalanceRefresh) {
        if (null != imBalanceRefresh) {
            RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1000);
            animation.setRepeatCount(-1);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatMode(Animation.RESTART);
            imBalanceRefresh.startAnimation(animation);
        }
    }

    public static void stopAnimation(ImageView imBalanceRefresh) {
        if (null != imBalanceRefresh) {
            imBalanceRefresh.clearAnimation();
        }
    }
}
