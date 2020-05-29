package com.nwf.sports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.nwf.sports.chat.login.model.GameTokenBean;
import com.nwf.sports.chat.login.model.InGameResult;
import com.nwf.sports.chat.login.model.LoginResult;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.ui.activity.RedpacketGameActivity;
import com.nwf.sports.utils.SingleToast;
import com.nwf.sports.utils.data.IMDataCenter;

import java.io.UnsupportedEncodingException;
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

public class RedPacketIMServicesManger {


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

    //聊天+红包游戏
    public static void initParam(boolean isLocalEnvironment, String redPacketServerHost, String productId) {

        isLocalEnvironment_ = isLocalEnvironment;
        redPacketServerHost_ = redPacketServerHost;
        productId_ = productId;

        RetrofitHelper.RED_PACKET_SERVER_ADDRESS = getRedPacketServerHost() + "/redenvelope";

    }


    public static void startImService(Activity activity, String loginName) {


        if (TextUtils.isEmpty(getProductId())) {
            SingleToast.showLongMsg("请对 RedPacketIMServicesManger 设置 productId ");
            return;
        }

        if (TextUtils.isEmpty(getRedPacketServerHost())) {
            SingleToast.showLongMsg("请对 RedPacketIMServicesManger 设置 redPacketServerHost ");
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

                inGame(activity);

            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });
    }


    public static void inGame(Activity activity) {

        Map<String, Object> params = new HashMap<>();
        params.put("gameCode", "A01095");
        params.put("gameKind", 13);
        params.put("incILog", 1);
        params.put("isWithTransfer", 1);
        params.put("incILog", 1);
        params.put("productId", IMDataCenter.getInstance().getProductId());
        params.put("verticalApp", 1);
        params.put("loginName", IMDataCenter.getInstance().getLoginName());

        Request.with(IMApplication.getContext()).post("/game/inGame", params, new RequestCallBack<InGameResult>() {
            @Override
            public void onGatewaySuccess(@Nullable InGameResult loginResult, ResponseModel.Head head) {

                if (loginResult != null && !TextUtils.isEmpty(loginResult.getUrl())) {

                    String data = decodeToString(loginResult.getUrl());

                    GameTokenBean gameTokenBean = new Gson().fromJson(data, GameTokenBean.class);

                    IMDataCenter.getInstance().setGame_u2token(gameTokenBean.getGame_u2token());
                    IMDataCenter.getInstance().setGame_token(gameTokenBean.getGame_token());


                    Intent intent = new Intent(activity, RedpacketGameActivity.class);
                    activity.startActivity(intent);


                }

            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });


    }


    public static String decodeToString(String str) {
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}
