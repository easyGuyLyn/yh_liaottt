package cn.wildfire.chat.kit.net;

public enum IMErrorCodeEnum {

    ERROR_CODE_CREDIT_NOT_ENOUGH(999989,"账户余额不足"),
    ERROR_CODE_CREDIT_BILLNO_EXIST(999988,"订单已经存在"),
    ERROR_CODE_CREDIT_BILLNO_FAIL(999987,"建立订单失败"),
    ERROR_CODE_CREDIT_BILLNO_NOT_EXIST(999986,"订单不存在"),
    ERROR_CODE_PACKET_NOT_EXIST(999985,"红包不存在"),
    ERROR_CODE_PACKET_FINSH_(999985,"红包抢完了"),
    ERROR_CODE_PACKET_EXPAIRE(999984,"该红包已经失效"),
    ERROR_CODE_PACKET_LINQU_ALREDY(999984,"该红包已经领取过了"),
    ERROR_CODE_PACKET_BALANCE_LOW_SAOLEI(999983,"不满足扫雷红包的最低金额"),
    ERROR_CODE_PACKET_BALANCE_LOW_NIU(999982,"不满足牛牛红包的最低金额"),
    ERROR_CODE_PACKET_BUSY(999984,"当前抢红包人太多了，再试一次吧!"),
    ERROR_CODE_PACKET_AMOUNT_NUMBER(999983,"红包金额与人数不符合"),
    ERROR_CODE_PACKET_AMOUNT_(999982,"红包金额超出限制额度"),
    ERROR_CODE_PACKET_GROUP_NOT_EXIT(999981,"红包群不存在"),
    ERROR_CODE_PACKET_SEND_(999980,"发红包失败"),
    ERROR_CODE_PACKET_BALANCE_UPDATE_AMOUT(999979,"额度更新失败，发红包失败"),
    ERROR_CODE_PACKET_FINSH_OR_NOT_EXIST(999979,"红包不存在或已经失效!"),
    ERROR_CODE_PACKET_GRAB(999977,"抢红包失败!"),
    ERROR_CODE_PACKET_RECEIVED(999976,"已领取过红包!"),
    ERROR_CODE_PACKET_NOT_ALLOW(999975,"您不能抢该红包!"),
    ERROR_CODE_PACKET_GRAB_ISOVER(999974,"红包已抢完!"),
    ERROR_CODE_PACKET_GRAB_ISNOTEXIST(999973,"红包不存在!"),
    ERROR_CODE_PACKET_GRAB_VALIDEXIST(999972,"校验红包有限期任务失败!"),
    ERROR_CODE_TOKEN_INVALID(999955,"TOKEN失效");



    private int code;
    private String name;


    IMErrorCodeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
