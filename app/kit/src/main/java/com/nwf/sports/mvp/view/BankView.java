package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.MyBankResult;

/**
 * <p>类描述： 银行卡的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface BankView extends IBaseView {
    void setMyBank(MyBankResult data);

    void setMyBankDefeated();
}
