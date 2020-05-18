package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.BankInfo;
import com.nwf.sports.mvp.model.CommunalResult;
import com.nwf.sports.mvp.model.MyBankResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 银行的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IMyBankApi {

    /**
     * 获取银行卡列表
     *
     * @return
     */
    @GET("api/banks")
    public Observable<AppTextMessageResponse<MyBankResult>> getMyBank();

    /**
     * 删除银行接口
     *
     * @return
     */
    @POST("api/banks/delete")
    @FormUrlEncoded
    Observable<AppTextMessageResponse<CommunalResult>> delBankCard(@Field("id") String id);


    /**
     * 获取银行相关信息，例如银行列表、银行卡类型(借记卡/信用卡)
     *
     * @return
     */
    @GET("api/banks/bankList.json")
    public Observable<AppTextMessageResponse<List<BankInfo>>> getAllBankInfo();

    /**
     * 添加银行卡
     *
     * @return
     */
    @POST("api/banks/add")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> addBank(@FieldMap Map<String, String> map);

    /**
     * 修改 银行卡
     *
     * @return
     */
    @POST("api/banks/update")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<CommunalResult>> modifyBank(@FieldMap Map<String, String> map);

    /**
     * 设置默认银行卡
     *
     * @return
     */
    @POST("api/banks/setDefault")
    @FormUrlEncoded
    Observable<AppTextMessageResponse<CommunalResult>> setDefaultMyBank(@Field("id") String id );

}
