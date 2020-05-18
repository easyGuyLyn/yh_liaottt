package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.ServiceCallbackResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 手机号模块的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IPhoneApi {

    //电话回拨方式
    @POST("api/serviceurl")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<ServiceCallbackResult>> onQueryServiceCallBack(@Field("phone") String phone, @Field("phoneToken") String phoneToken);

    //发送验证码
    @POST("api/sms/verifyCode")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse> sendSmsCodePhone(@FieldMap Map<String, String> map);

    //验证是否绑定手机号
    @POST("api/verifyPhone")
    public Observable<AppTextMessageResponse<CommunalResult>> verifyPhone();

    //验证旧手机的验证码
    @POST("api/authSmsCode")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse> verifysms(@Field("smsCode") String smsCode);

    //绑定手机号
    @POST("api/bindPhone")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> bindPhone(@Field("mobile") String phone, @Field("smsCode") String smscode);

    //修改绑定手机号
    @POST("api/modifyPhone")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> modifyPhone(@Field("oldPhone") String oldPhone, @Field("mobile") String phone, @Field("smsCode") String smscode);

    // 验证其手机号是否已经绑定
    @POST("api/check/phone")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> checkphone(@Field("mobile") String mobile);

}
