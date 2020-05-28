package com.nwf.sports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.chat.login.model.LoginResult;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.ui.activity.MainActivity;
import com.nwf.sports.utils.SingleToast;
import com.nwf.sports.utils.data.IMDataCenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import cn.wildfire.chat.kit.ChatManagerHolder;
import ivi.net.base.netlibrary.callback.RequestCallBack;
import ivi.net.base.netlibrary.model.ResponseModel;
import ivi.net.base.netlibrary.request.Request;

/**
 *
 **/

public class IMServicesManger {


    private static boolean isLocalEnvironment_;//是否是本地测试环境

    private static String redPacketServerHost_;//红包服务器的目前网关

    private static String productId_;//产品id


    public static String getRedPacketServerHost() {
        return redPacketServerHost_;
    }


    public static String getProductId() {
        return productId_;
    }


    public static boolean isLocalEnvironment() {
        return isLocalEnvironment_;
    }


    //+ "/redenvelope"

    //纯聊天
    public static void initParam(boolean isLocalEnvironment, String productId) {

        isLocalEnvironment_ = isLocalEnvironment;
        productId_ = productId;

    }

    //聊天+红包游戏
    public static void initParam(boolean isLocalEnvironment, String redPacketServerHost, String productId) {

        isLocalEnvironment_ = isLocalEnvironment;
        redPacketServerHost_ = redPacketServerHost;
        productId_ = productId;

        RetrofitHelper.APP_SERVER_ADDRESS = getRedPacketServerHost() + "/redenvelope";

    }


    public static void startImService(Activity activity, String loginName) {


        if (TextUtils.isEmpty(getProductId())) {
            SingleToast.showLongMsg("请对 IMServicesManger 设置 productId ");
            return;
        }

        if (TextUtils.isEmpty(getRedPacketServerHost())) {
            SingleToast.showLongMsg("请对 IMServicesManger 设置 redPacketServerHost ");
            return;
        }


        //自己主app 必须是登录状态
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", ChatManagerHolder.gChatManager.getClientId());
        params.put("platform", new Integer(2));
        params.put("productId", getProductId());
        params.put("loginName", loginName);

        Request.with(activity).loading().post("/im/login", params, new RequestCallBack<LoginResult>() {
            @Override
            public void onGatewaySuccess(@Nullable LoginResult loginResult, ResponseModel.Head head) {

                ChatManagerHolder.gChatManager.connect(loginResult.getUserId(), loginResult.getToken());

                SharedPreferences sp = IMApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit()
                        .putString("id", loginResult.getUserId())
                        .putString("token", loginResult.getToken())
                        .apply();

                IMDataCenter.getInstance().setLoginName(loginName);
                IMDataCenter.getInstance().setProductId(getProductId());


                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);


                RxBus.get().post(ConstantValue.EVENT_TYPE_LOGIN, "login");

            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });
    }


}
