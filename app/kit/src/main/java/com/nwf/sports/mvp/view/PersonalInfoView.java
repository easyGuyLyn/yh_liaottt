package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.PersonalInfoResult;

/**
 * <p>类描述： 我的界面的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface PersonalInfoView extends IBaseView {

    void PersonalInfoSucceed(PersonalInfoResult result);

    void logoutSucceed();
}
