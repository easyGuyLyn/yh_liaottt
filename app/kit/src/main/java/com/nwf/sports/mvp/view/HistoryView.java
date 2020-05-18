package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.WithdrawalHistoryResult;

/**
 * <p>类描述： 历史的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface HistoryView extends IBaseView {

    void findHistorySucceed(WithdrawalHistoryResult result);

    void findHistoryDefeated();

}
