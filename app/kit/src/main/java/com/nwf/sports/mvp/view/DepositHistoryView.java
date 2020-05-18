package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.DepositHistoryResult;

/**
 * <p>类描述： 存款历史的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface DepositHistoryView extends IBaseView {

    void findHistorySucceed(DepositHistoryResult result);

    void findHistoryDefeated();

}
