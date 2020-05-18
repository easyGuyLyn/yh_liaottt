package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.GetBalanceResult;

/**
 * <p>类描述： 本地余额的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface BalanceView extends IBaseView {
    void setBalance(GetBalanceResult result);

    void shortTime();
}
