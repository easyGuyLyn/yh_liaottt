package com.nwf.sports.mvp.api;


import com.nwf.sports.mvp.model.GetBalanceResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 本地余额接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IBalanceApi {

    @POST("api/balance")//请求余额—本地余额
    public Observable<AppTextMessageResponse<GetBalanceResult>> getBalance();
}
