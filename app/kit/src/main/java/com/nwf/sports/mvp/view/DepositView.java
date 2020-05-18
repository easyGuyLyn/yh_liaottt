package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.mvp.model.OnlinePay;

/**
 * <p>类描述： 存款的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface DepositView extends IBaseView {
    void setDepositList(DepositMannersVo depositList);

//    void onQuerydepositstatusResult(Checkdepositstatus str);

    void onQueryOnlinePayResult(OnlinePay onlinePay);

    void onQueryFasterPayResult(FasterPay fasterPay);

//    void onRequestCardPay(OnlinePay onlinePay);

    void onQueryPayResultFail(String msg);
}
