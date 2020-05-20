package com.nwf.sports.utils;

import com.nwf.sports.utils.data.DataCenter;

/**
 * Created by Nereus on 2017/5/18.
 */

public abstract class Constant {
    //防止重复点击按钮，避免冲突触发业务
    public static final int throttleWindowTime = 5;
    //有些按钮可以快速点击，例如刷新余额
    public static final int throttleWindowTime_short = 2;
    //发送验证码时长
    public static final int ACTION_SEND_AUTH_CODE = 60;
    //短信验证码的长度
    public static final int CODE_LENGTH = 6;
    //发送验证码类型
    public static final String PRODUCT_N_SEND_AUTH_CODE_TYPE = "60025";

    //发送注册验证码类型
    public static final String PRODUCT_N_SEND_AUTH_CODE_REGISTER = "6";

    //发送登录验证码类型
    public static final String PRODUCT_N_SEND_AUTH_CODE_LOGIN = "10";

    //找回密码验证码类型
    public static final String PRODUCT_N_FIND_PWD_CODE_LOGIN = "5";

    // 常规验证码(身份验证))
    public static final String PRODUCT_N_SEND_AUTH_CODE_PHONE = "11";

    // 验证码1:手机绑定
    public static final String PRODUCT_N_SEND_AUTH_CODE_BINDPHONE = "1";

    //发送验证码有效时长
    public static final String PRODUCT_N_SEND_AUTH_CODE_DURATION = "1";

    //产品的首字母
    public static final String PRODUCT_INITIAL = "v";

    //产品的ID
    public static final String PRODUCT_ID = DataCenter.getInstance().getProductId();

    //产品的平台
    public static final String PRODUCT_PLATFORM = "android";

    //产品的平台类型  终端 0 pc 1 h5 2 android 3 ios
    public static final String DEVICETYPE = "2";

    //注册的平台类型   0 office注册, 1 PC ,2 H5 ,3 android ,4 ios
    public static final String REGISTERTYPE = "3";

    //注册的平台类型   平台 0 主战 1 体育 6 棋牌 3 视讯 5 电游 8 捕鱼 7体育APP
    public static final String WEBSITETYPE = "0";

    //APPTYPE    7体育APP
    public static final String APPTYPE = "7";

    //检查更新类型  2 棋牌 7体育APP
    public static final String CHECKUPGRADETYPE = "7";

    //产品的币种
    public static final String PRODUCT_CURRENCY = "CNY";

    //产品的语言
    public static final String PRODUCT_LANGUAGE = "CN";

    //产品的预定字符串
    public static final String PRODUCT_RESERVE = "e04qawsed";

    //用户账号的密码多次输入错误，账号已被锁定请五分钟后重试或联系客服解锁
    public static final String PRODUCT_PWD_MUL_ERROR = "W00005";

    //进入游戏加密秘钥
    public static final String PRODUCT_ENCRYPT_KEY = "jhs#%!fde";

    //进入游戏解密秘钥
    public static final String PRODUCT_DECODE_KEY = "cca619fb";

    //进入游戏URL地址配置第一步
    public static final String PRODUCT_ENTER_GAME_URL_1 = "doBusiness.do";

    //进入游戏URL地址配置第2步
    public static final String PRODUCT_ENTER_GAME_URL_2 = "transferAsyncNewApi.do";

    //进入游戏URL地址配置第3步
    public static final String PRODUCT_ENTER_GAME_URL_3 = "forwardGame.do";

    //flurry统计的Key
    public static final String FLURRY_KEY = "42HKSKXYKPNNWMG9WP4J";

    //官网渠道id 1000638832
    public static final String CHANNEL_ID = "";
}
