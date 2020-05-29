package com.nwf.sports.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dawoo.coretool.util.LogUtils;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.mvp.model.UserInfoBean;
import com.nwf.sports.utils.data.IMDataCenter;

/**
 * 一些页面的跳转
 * Created by benson on 18-1-14.
 */

public class ActivityUtil {


    /**
     * 开启客服界面
     */
    public static void skipToService(Context context) {
        //context.startActivity(new Intent(context,ServiceActivity.class));
    }


    /**
     * 开启支付界面
     */
    public static void transiteToALiPay(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 支付跳转到网页
     *
     * @param context
     * @param onlinePay
     */
    public static void skipBrowser(Context context, OnlinePay onlinePay) {
        UserInfoBean localUserInfo = IMDataCenter.getInstance().getUserInfoBean();
        StringBuilder payString = new StringBuilder();

        payString.append("product=").append(Constant.PRODUCT_ID).
                append("&billno=").append(onlinePay.getBillno()).
                append("&amount=").append(onlinePay.getAmount()).
                append("&loginname=").append(localUserInfo.getUsername()).
                append("&currency=").append(Constant.PRODUCT_CURRENCY).
                append("&customerType=").append(1).
                append("&keycode=").append(onlinePay.getKeycode());

        LogUtils.e(onlinePay.getUrl() + "?" + payString.toString());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(onlinePay.getUrl() + "?" + payString.toString());
        intent.setData(uri);
        context.startActivity(intent);
    }

}
