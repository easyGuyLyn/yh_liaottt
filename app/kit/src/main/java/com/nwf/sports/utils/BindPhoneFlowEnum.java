package com.nwf.sports.utils;


/**
 * <p>类描述： 确定验证手机号的流程标识
 * <p>创建人：Simon
 * <p>创建时间：2019-04-17
 * <p>修改人：Simon
 * <p>修改时间：2019-04-17
 * <p>修改备注：
 **/
public enum BindPhoneFlowEnum
{

    TOMAIN("MainActivity"), TOBINDBANK("AddBankActivity"), TOINTRODUCE("IntroduceActivity");

    private String servicename;

    private BindPhoneFlowEnum(String name)
    {
        this.servicename = name;
    }

    public final String getServicename()
    {
        return servicename;
    }
}
