package com.nwf.sports.utils;


/**
 * <p>类描述： 确定绑定手机号的流程标识
 * <p>创建人：Simon
 * <p>创建时间：2019-04-17
 * <p>修改人：Simon
 * <p>修改时间：2019-04-17
 * <p>修改备注：
 **/
public enum BindBankFlowEnum {


    TOINTRODUCE("IntroduceActivity");

    private String servicename;

    private BindBankFlowEnum(String name) {
        this.servicename = name;
    }

    public final String getServicename() {
        return servicename;
    }
}
