package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.BankInfo;

import java.util.List;

/**
 * <p>类描述： 添加修改银行卡的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface AddBankView extends IBaseView {

    void AllBankInfoSucceed(List<BankInfo> bankInfos);

    void addBankSucceed();

}
