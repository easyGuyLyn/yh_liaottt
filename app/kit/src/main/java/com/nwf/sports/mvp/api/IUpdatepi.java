package com.nwf.sports.mvp.api;


import com.nwf.sports.mvp.model.CheckupgradeResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 更新appAPI
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IUpdatepi {

    @POST("api/checkupgrade")//请求APP 更新信息
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CheckupgradeResult>> checkupgrade(@Field("appType") String appType);

}
