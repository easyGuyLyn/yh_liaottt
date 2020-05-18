package com.nwf.sports.mvp.api;


import com.nwf.sports.mvp.model.DepositHistoryResult;
import com.nwf.sports.mvp.model.WithdrawalHistoryResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 历史记录
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public interface IHistoryApi {

    //获取存款记录
    @POST("api/pay/record")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<DepositHistoryResult>> depositHistory(@FieldMap Map<String,String> map);

    //获取取款记录
    @POST("api/withdraw/record")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<WithdrawalHistoryResult>> withdrawHistory(@FieldMap Map<String,Object> map);

    //删除存款记录
    @POST("delDepositRecor")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> delDepositRecor(@Field("delrecorList") String list);

    //删除取款记录
    @POST("delWithdrawalRecor")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> delWithdrawalRecor(@Field("delrecorList") String list);

    //删除优惠记录
    @POST("delPromotionRecor")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> delPromotionRecor(@Field("delrecorList") String list);

    //删除洗码记录
    @POST("delWashCodeRecor")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> delWashCodeRecor(@Field("delrecorList") String list);

}
