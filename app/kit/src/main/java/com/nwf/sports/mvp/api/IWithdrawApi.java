package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.PreviousWithdrawResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


/**
 * <p>类描述： 取款模块的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IWithdrawApi {
    //提交取款
    @POST("api/withdraw/apply")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> withdrawMoney(@Field("bankId") String bankId, @Field("withdrawAmount") String withdrawAmount);

    //获取上一笔提现进度
    @GET("api/withdraw/previousProgress")
    public Observable<AppTextMessageResponse<PreviousWithdrawResult>> queryPreviousWithdraw();

    //取消存款
    @POST("api/withdraw/cancel")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse> cancelWithdraw(@Field("requestId") String withdrawRequestId);
}
