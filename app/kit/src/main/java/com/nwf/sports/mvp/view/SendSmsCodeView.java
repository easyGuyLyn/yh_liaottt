package com.nwf.sports.mvp.view;

/**
 * <p>类描述： 验证码View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface SendSmsCodeView extends IBaseView {

    public void setSendSmsCodeError(String errmsg);

    public void setSendSmsCodeComplete();
}
