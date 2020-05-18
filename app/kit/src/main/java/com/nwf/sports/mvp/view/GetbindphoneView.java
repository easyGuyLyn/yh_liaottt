package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.ServiceCallbackResult;

/**
 * <p>类描述： 获取绑定的手机号View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface GetbindphoneView extends IBaseView {
    void getBindPhoneViewSucceed(String phone);

    void callbackResultSucceed(ServiceCallbackResult serviceCallbackResult);
}
