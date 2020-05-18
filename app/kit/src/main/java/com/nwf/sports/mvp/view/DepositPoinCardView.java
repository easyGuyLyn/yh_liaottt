package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.OnlinePay;

/**
 * <p>类描述：点卡支付View
 * <p>创建人：Simon
 * <p>创建时间：2019-3-18
 * <p>修改人：Simon
 * <p>修改时间：2019-3-18
 * <p>修改备注：
 **/
public interface DepositPoinCardView extends IBaseView
{
    void onRequestCardPay(OnlinePay onlinePay);

}
