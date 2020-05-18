package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.GrowUpLevelResult;
import com.nwf.sports.mvp.model.TotalAmountResult;

/**
 * <p>类描述： 本地余额的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface LevelView extends BalanceView {

    void setTotalAmountResult(TotalAmountResult totalAmountResult);

    void setGrowUpLevelResult(GrowUpLevelResult growUpLevelResult);
}
