package com.nwf.sports.utils;


import com.nwf.sports.utils.annotation.Msg;

/**
 * Created by Nereus on 2017/6/5.
 */
public final class AppStatus
{

    private AppStatus()
    {
    }

    @Msg("成功")
    public static final String SUCCESS = "200";
    @Msg("")
    public static final String FAIL = "400";
    @Msg("服务器错误")
    public static final String SERVER_ERROR = "501";
    @Msg("服务器异常")
    public static final String SYS_EXCEPTION = "500";
    @Msg("非法请求")
    public static final String MAC_ERROR = "900";
    @Msg("网络异常")
    public static final String NET_EXCEPTION = "901";
    @Msg("服务器异常")
    public static final String SERVICE_EXCEPTION = "999";

    //账户名称不存在
    @Msg("账号名不存在")
    public static final String ERROR_ACCOUNT_NAME_NOT_FOUND = "450";
    //账号名称非法
    @Msg("非法账号名")
    public static final String ERROR_ACCOUNT_NAME_INVALID = "451";
    //密码格式非法
    @Msg("非法密码")
    public static final String ERROR_PASSWORD_INVALID = "452";
    //推荐码格式非法
    @Msg("非法推荐码")
    public static final String ERROR_RECOMMAND_CODE_INVALID = "453";
    //电话号码格式非法
    @Msg("非法电话号码")
    public static final String ERROR_PHONE_INVALID = "454";
    //电话号码已存在
    @Msg("电话号码已存在")
    public static final String ERROR_PHONE_ALREADY_EXIST = "1303";
    //账户名已存在
    @Msg("账号名称已存在")
    public static final String ERROR_USERNAME_ALREADY_EXIST = "1301";
    //游戏账号名称已存在
    @Msg("账号名已存在")
    public static final String ERROR_ACCOUNT_NAME_ALREADY_EXIST = "455";

    //短信验证码发送次数太多
    @Msg("发送短信验证码次数过多")
    public static final String ERROR_SEND_SMS_TOO_MANY = "456";
    //短信验证码发送异常
    @Msg("发送短信验证码失败")
    public static final String ERROR_SEND_SMS = "457";

    //发送短信验证码 短信类型错误
    @Msg("短信类型错误")
    public static final String ERROR_SMS_TYPE = "458";

    //短信验证码非法
    @Msg("短信验证码非法")
    public static final String ERROR_SMSCODE_INVALID = "459";

    //尊敬的客户，您还没有向这个手机号发送短信验证码
    @Msg("没有向这个手机号发送短信验证码")
    public static final String ERROR_SMSCODE_NOT_SEND = "460";

    //登录 用户不存在
    @Msg("用户不存在")
    public static final String ERROR_ACCOUNT_NOT_FOUND = "461";
    //登录 密码过期
    @Msg("密码过期")
    public static final String ERROR_LOGIN_PWD_EXPIRED = "462";
    //登录 密码(凭证)错误
    @Msg("密码错误")
    public static final String ERROR_LOGIN_CREDENTIAL = "463";

    //md5生成失败
    @Msg("生成失败")
    public static final String ERROR_MD_CREATE = "464";

    //有空的输入
    @Msg("请求不合理")
    public static final String ERROR_EMPTY_INPUT = "465";
    //相同的输入，例如修改密码时新旧密码相同
    @Msg("新旧密码相同")
    public static final String ERROR_EQUALS_INPUT = "466";
    //用户id未知
    @Msg("id未知")
    public static final String ERROR_USER_ID_UNKNOWN = "467";
    //用户未绑定手机号
    @Msg("未绑定手机号")
    public static final String ERROR_PHONE_NOT_BOUND = "468";
    //不需要刷新token
    @Msg("不需要刷新token")
    public static final String ERROR_REFRESHTOKEN_NO_NEED = "469";
    //洗码时用户等级过低<1
    @Msg("用户等级过低")
    public static final String ERROR_REBATE_LEVEL_TOO_LOW = "470";
    //没有查询到有效投注金额
    @Msg("没有查询到有效投注金额")
    public static final String ERROR_BET_MONEY_NOT_FOUND = "471";
    //没有查询到洗码数据
    @Msg("没有查询到洗码数据")
    public static final String ERROR_BET_DATA_NOT_FOUND = "472";
    //不足100不支持洗码
    @Msg("金额过低,不能洗码")
    public static final String ERROR_BET_NOT_SUPPORT = "473";
    //提交洗码失败
    @Msg("洗码失败")
    public static final String ERROR_APPLY_REBATE = "474";
    //修改手机号时 原手机号输入不正确
    @Msg("手机号输入不正确")
    public static final String ERROR_OLDPHONE_INCORRECT = "475";
    //修改手机号时 原手机号和新手机号相同
    @Msg("原手机号和新手机号不能相同")
    public static final String ERROR_PHONE_EQUALS = "476";
    //目前您的首存优惠正在处理中，暂时无法结算洗码
    @Msg("目前您的首存优惠正在处理中，暂时无法结算洗码")
    public static final String ERROR_UNTREATED_FIRST_DEPOSIT = "477";
    //您已享受过首存优惠，暂时无法享受洗码优惠
    @Msg("已享受过首存优惠，暂时无法享受洗码优惠")
    public static final String ERROR_ENJOYED_FIRST_DEPOSIT = "478";

    //添加银行卡时，此银行卡号已经存在，请勿重复添加
    @Msg("此银行卡号已经存在，请勿重复添加")
    public static final String ERROR_CARD_NO_ALREADY_INUSE = "479";
    //银行账号号码格式不正确
    @Msg("银行卡号非法")
    public static final String ERROR_BANK_ACCOUNT_NO_INVALID = "480";
    //银行省份格式不正确
    @Msg("银行省份格式不正确")
    public static final String ERROR_BANK_COUNTRY_INVALID = "481";
    //银行支行格式不正确
    @Msg("银行支行格式不正确")
    public static final String ERROR_BANK_BRANCH_INVALID = "482";
    //银行名称超乎想象
    @Msg("银行名称超乎想象")
    public static final String ERROR_BANK_NAME_UNEXPECTED = "483";
    //银行账号类型超乎想象
    @Msg("银行账号类型不正确")
    public static final String ERROR_BANK_ACCOUNT_TYPE_UNEXPECTED = "484";
    //添加银行卡时真实姓名和开户人名称不一致
    @Msg("真实姓名和开户人名称不一致")
    public static final String ERROR_BANK_ACCOUNT_NAME_NOT_EQUALS = "485";

    //修改银行卡时银行卡信息没有变化
    @Msg("银行卡信息没有变化")
    public static final String ERROR_BANK_ACCOUNT_EQUALS = "486";

    //不存在此取款银行账号
    @Msg("不存在此取款银行账号")
    public static final String ERROR_BANK_ACCOUNT_NOT_EXIST = "487";

    //修改银行卡时已经有银改提案
    @Msg("修改银行卡时已经有银改提案")
    public static final String ERROR_MODIFY_BANK_REQUEST_ALREADY_EXIST = "1103";

    //取款金额不在设定范围内
    @Msg("取款金额超出范围")
    public static final String ERROR_WITHDRAW_MONEY_BEYOND_AREA = "488";
    //取款提案已经存在
    @Msg("取款提案已经存在")
    public static final String ERROR_WITHDRAW_REQUEST_ALREADY_EXIST = "489";
    //余额不足
    @Msg("余额不足")
    public static final String ERROR_BALANCE_NOT_SUFFICIENT = "490";
    //取款金额格式不正确
    @Msg("取款金额格式不正确")
    public static final String ERROR_WITHDRAW_MONEY_INVALID = "491";
    //资金历史查询，没有此查询类别
    @Msg("没有此查询类别")
    public static final String ERROR_HISTORY_SERVICE_NOT_FOUND = "492";
    //转额 不存在此游戏平台
    @Msg("不存在此游戏平台")
    public static final String ERROR_GAME_PLATFORM_NOT_FOUND = "493";
    //升级 apk文件不存在
    @Msg("没有可用的升级程序")
    public static final String ERROR_UPGRADE_APK_NOT_FOUND = "494";
    //升级 配置文件不存在
    @Msg("没有可用的升级信息")
    public static final String ERROR_UPGRADE_CONFIG_NOT_FOUND = "495";
    //服务不可用
    @Msg("服务维护中")
    public static final String ERROR_SERVICE_UNAVAILABLE = "496";
    //取款id格式非法
    @Msg("id非法")
    public static final String ERROR_WITHDRAW_REQUEST_ID_INVALID = "497";
    //取款提案已通过审核，不可取消
    @Msg("取款提案已通过审核，不可取消")
    public static final String ERROR_WITHDRAW_NOT_ALLOWED = "1201";

    @Msg("取款已批准，不能取消取款")
    public static final String ERROR_UNABLE_CANCEL_WITHDRAW = "498";
    @Msg("取款已取消，不能再取消")
    public static final String ERROR_WITHDRAW_ALREADY_CANCELLED = "499";
    @Msg("取款已支付，不能取消")
    public static final String ERROR_WITHDRAW_ALREADY_PAID = "520";

}
