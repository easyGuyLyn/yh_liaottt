package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.MyBankResult;

/**
 * Created by Simon on 2018/10/23 0023.
 */

public interface LineHelperView extends IBaseView {
     void setComplete();
     void setMyBankData(MyBankResult data);
     void setError(int errcode);
}
