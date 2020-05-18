package com.nwf.sports.mvp.view;


import com.nwf.sports.mvp.model.CheckIfBindPhoneResult;

/**
 * Created by Simon on 2018/10/23 0023.
 */

public interface GetPhoneView extends IBaseView {
     void inquireSucceed(CheckIfBindPhoneResult checkIfBindPhoneResult);
     void inquireDefeated(boolean isShow);
}
