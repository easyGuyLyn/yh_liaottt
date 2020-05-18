package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.LoginResult;
import com.nwf.sports.mvp.model.RegisterResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 注册模块的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IRegisterNApi {

    @POST("api/check/username")//校验账号是否存在
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<RegisterResult>> registerByPhone(@FieldMap Map<String,String> map);

    @POST("api/register")//注册账号
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> registerAccount(@FieldMap Map<String,String> map);

    @POST("api/register-sport")//注册账号  带聊天
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> registerAccountSport(@FieldMap Map<String,String> map);

    // 手机号快速注册
    @POST("api/fastRegister")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> fastRegister(@FieldMap Map<String,String> map);

    // 手机号快速注册  带聊天
    @POST("api/fastRegister-sport")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LoginResult>> fastRegisterSport(@FieldMap Map<String,String> map);

}
