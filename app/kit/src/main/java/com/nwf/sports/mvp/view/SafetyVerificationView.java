package com.nwf.sports.mvp.view;

/**
 * <p>类描述： 安全验证的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface SafetyVerificationView extends IBaseView {

    public void verifyBoundPhoneSucceed();

    public void verifyBoundPhoneDefeated( boolean isInvalid);


}
