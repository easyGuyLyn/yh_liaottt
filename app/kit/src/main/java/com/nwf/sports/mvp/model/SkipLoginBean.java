package com.nwf.sports.mvp.model;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-04-02
 * <p>修改人：Simon
 * <p>修改时间：2019-04-02
 * <p>修改备注：
 **/
public class SkipLoginBean {
    public String type;
    public String phone;

    public SkipLoginBean(String type, String phone) {
        this.phone = phone;
        this.type = type;
    }

}
