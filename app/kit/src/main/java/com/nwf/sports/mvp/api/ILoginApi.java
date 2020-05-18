package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 登录的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/

public interface ILoginApi {

    //账号登录
    @POST("api/login")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> loginByUsername(@FieldMap Map<String, String> map);

    //手机登录
    @POST("api/fastLogin")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> fastLogin(@FieldMap Map<String, String> map);

    //手机登录 带有聊天登录
    @POST("api/fastLogin-sport")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> fastLoginSport(@FieldMap Map<String, String> map);

    //账号登录 带有聊天登录
    @POST("api/login-sport")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> loginByUsernameSport(@FieldMap Map<String, String> map);


}
