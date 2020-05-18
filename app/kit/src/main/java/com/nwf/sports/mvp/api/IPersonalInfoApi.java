package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.PersonalInfoResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 我的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IPersonalInfoApi {

    //获取用户接口
    @POST("api/userInfo")
    public Observable<AppTextMessageResponse<PersonalInfoResult>> getPersonalInfo();


    //退出登录
    @POST("api/logout")
    public Observable<AppTextMessageResponse> logout();
}
