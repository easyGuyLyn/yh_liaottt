package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.RegisterResult;

public interface CheckAccountView extends IBaseView {

    public void onIllegal(boolean illegal, String message);

    public void onCheckAccountSuccess(RegisterResult registerResult);

}
