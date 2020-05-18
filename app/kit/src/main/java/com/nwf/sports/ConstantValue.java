package com.nwf.sports;

/**
 * <p>类描述： 定义的常量
 * <p>创建人：Simon
 * <p>创建时间：2018-2-14
 * <p>修改人：Simon
 * <p>修改时间：2018-2-14
 * <p>修改备注：
 **/
public interface ConstantValue {

    /**
     * 传递参数
     */
    String ARG_PARAM1 = "param1";
    String ARG_PARAM2 = "param2";
    String ARG_PARAM3 = "param3";
    String ARG_PARAM4 = "param4";

    /**
     * activity 返回
     */
    int REQUEST_CODE1 = 10001;
    int REQUEST_CODE2 = 10002;
    int REQUEST_CODE3 = 10003;
    int REQUEST_CODE4 = 10004;
    //BQ存款返回
    int DEPOSIT_BQ = 10005;
    //点卡支付返回
    int DEPOSIT_POINT_CARD = 10006;

    /***************************传递的key指*****************************************************************************************/
    /**
     * 确定绑定手机号执行的流程
     */
    String BIND_PHONE_FLOW = "BIND_PHONE_FLOW";

    /**
     * 确定绑定银行卡执行的流程
     */
    String BIND_BANK_FLOW = "BIND_BANK_FLOW";

    /**
     * 注册手机号传递
     */
    String REGISTER_PHONE = "REGISTER_PHONE";

    /**
     * 注册账号传递
     */
    String REGISTER_ACCOUNT = "REGISTER_ACCOUNT";

    /**
     * 跳转注册
     */
    String SKIP_REGISTER = "SKIP_REGISTER";

    /**
     * 跳转登录
     */
    String SKIP_LOGIN = "SKIP_LOGIN";

    /**
     * 登出帐户
     */
    String EVENT_TYPE_LOGOUT = "EVENT_TYPE_LOGOUT";

    /**
     * 登录成功
     */
    String EVENT_TYPE_LOGIN = "EVENT_TYPE_LOGIN";

    /**
     * 退出登录
     */
    String LOG_OUT = "LOG_OUT";

    /**
     * 银行卡变动
     */
    String BANK_MANAGEMENT_ALTERATION = "BANK_MANAGEMENT_ALTERATION";

    /**
     * 首页切换变动
     */
    String MAINACTIVITY_SWITCHOVER = "MAINACTIVITY_SWITCHOVER";

    /**
     * 刷新活动页
     */
    String INTRODUCE_RELOAD = "INTRODUCE_RELOAD";

    /**
     * 跳转到首页 某一个Fragment
     */
    String SKIP_MIAN_TYPE = "SKIP_MIAN_TYPE";

    /**
     * 跳转到首页 打开登录、注册等
     */
    String MAIN_OPEN_TYPE = "MAIN_OPEN_TYPE";

    /**
     * 下载成功刷新数据
     */
    String DOWNLOAD_APPS_SUCCEED = "DOWNLOAD_APPS_SUCCEED";

    /**
     * token 失效
     */
    String TOKEN_LOSE_EFFICACY = "TOKEN_LOSE_EFFICACY";

    /**
     * 刷新紅包余额
     */
    String REFRESH_RED_OACKET_LIMIT = "REFRESH_RED_OACKET_LIMIT";

    /**
     * 刷新user信息
     */
    String REFRESH_USERINFO = "REFRESH_USERINFO";

    /**
     * 刷新余额信息
     */
    String REFRESH_BALANCE = "REFRESH_BALANCE";

    /**
     * 地区限制
     */
    String REGION_ASTRICT = "REGION_ASTRICT";

    /**
     * chcek线路异常
     */
    String PING_DOMAIN_ERRE = "PING_DOMAIN_ERRE";

    /**
     * 开始请求
     */
    String START_REQUEST = "START_REQUEST";

    /*****************************SharedPreferences存储字段***************************************************************************************/
    /**
     * 首页bnner与优惠数据
     */
    String HOMEPAGE = "HOMEPAGE";

    /**
     * 存款数据
     */
    String DEPOSIT = "DEPOSIT";

    /**
     * 存款输入的金额
     */
    String DEPOSITLASTAMOUNT = "DEPOSITLASTAMOUNT";

    /**
     * 用户银行卡张数储存字段
     */
    String BANKNUMBER = "_banknumber";

    /**
     * 用户银行卡列表储存字段
     */
    String BANKNUMBER_LIST = "banknumber_list";

    /**
     * 获取添加银行卡相关信息
     */
    String BANK_ALLMESSAGE = "BANK_ALLMESSAGE";

    /**
     * 获取添加个人信息
     */
    String USERINFO = "USERINFO";

    /**
     * 用户是否已经真实登录
     */
    String ISREALLOGIN = "isRealLogin";

    /**
     * 用户账号
     */
    String USERNAME = "username";

    /**
     * 用户密码
     */
    String PASSWORD = "password";

    /**
     * 用户星级
     */
    String STARLEVEL = "levelNum";

    /**
     * 用户token
     */
    String TOKEN = "token";

    /**
     * 手机号验证时间
     */
    String KEY_TIME_BOUND_PHONE = "key_time_bound_phone";

    /**
     * 手机号
     */
    String _BINDPHONE = "_BINDPHONE";

    /**
     * 用户余额
     */
    String _BALANCE = "_BALANCE";

    /**
     * BQ存款 银行保存
     */
    String DEPOSIT_TRANSFER_BANK = "DEPOSIT_TRANSFER_BANK";

    /**
     * BQ存款 姓名保存
     */
    String DEPOSIT_TRANSFER_NAME = "DEPOSIT_TRANSFER_NAME";

    /**
     * BQ存款 存款类型保存
     */
    String DEPOSIT_TRANSFER_TYPE = "DEPOSIT_TRANSFER_TYPE";

    /**
     * 保存点卡支付方式
     */
    String POINT_CARD_TYPE_NAME = "POINT_CARD_TYPE_NAME";

    /**
     * 保存历史记录
     */
    String _HISTORY = "_HISTORY";

    /**
     * 回拨电话
     */
    String SERVICE_CALLBACK = "SERVICE_CALLBACK";

    /**
     * 保存 下载app 数据
     */
    String DOWNLOAD_APPS = "DOWNLOAD_APPS";

    /**
     * 保存 下载app 数据
     */
    String DOWNLOAD_APPS_DATA = "DOWNLOAD_APPS_DATA";

    /**
     * 保存 游戏列表 数据
     */
    String GAME_LIST = "GAME_LIST";

    /**
     * 保存选择的url
     */
    String SAVA_URL = "SAVA_URL";
    /**
     * 保存选择的IMurl
     */
    String SAVA_IM_URL = "SAVA_IM_URL";

    /**
     * 是否修改过用户名
     */
    String MODIFICATION_NAME = "MODIFICATION_NAME";

    /************************雅虎统计字段***************************************/
    String NET_DOMAIN_JSON = "NET_DOMAIN_JSON"; // 请求到域名json列表失败
    String NET_DOMAIN = "NET_DOMAIN"; // 没有域名Check过
    String REQUEST_ERROR = "REQUEST_ERROR"; // 请求异常

}
