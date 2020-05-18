package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.DepositMannersVo;
import com.nwf.sports.mvp.model.FasterPay;
import com.nwf.sports.mvp.model.OnlinePay;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>类描述： 存款的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IDepositApi {

    //获取存款方式列表
    @GET("api/pay/channel")
    public Observable<AppTextMessageResponse<DepositMannersVo>> payChannel();


    //在线支付、微信、支付宝、扫码、财付通等

    /**
     * amount (number, optional): 支付金额 ,
     * bankcode (string, optional): 银行编号 ,
     * handleFee (number, optional): 手续费 ,
     * ipAddress (string, optional): ip地址 ,
     * payid (string, optional): 支付ID ,
     * paymannerid (string, optional): 在线支付类型
     *
     * @return
     */
    @POST("api/pay/onlinePay")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<OnlinePay>> onQueryOnlinePay(
            @Field("amount") String amount,
            @Field("bankcode") String bankcode,
            @Field("handleFee") String handleFee,
            @Field("paymannerid") String paymannerid,
            @Field("payid") String payid,
            @Field("ipAddress") String ipAddress);

    //BQ支付

    /**
     * accountname (string, optional): 账号 ,
     * amount (number, optional): 额度 ,
     * bankcode (string, optional): 银行编码 ,
     * bqpaytype (string, optional): bq存款转账类型，0-普通转账，1-微信转账，2-支付宝转账，不传默认0 ,
     * depositor (string, optional): 真实姓名 ,
     * paymannerid (string, optional): 支付方式
     *
     * @return
     */
    @POST("api/pay/bq")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<FasterPay>> onQueryFasterPay(
            @Field("accountname") String accountname,
            @Field("amount") String amount,
            @Field("bankcode") String bankcode,
            @Field("bqpaytype") String bqpaytype,
            @Field("depositor") String depositor,
            @Field("paymannerid") String paymannerid);

    //点卡支付

    /**
     * accountname (string, optional): 用户名 ,
     * cardAmt (string, optional): 点卡金额，如果多个用逗号,隔开 ,
     * cardCode (string, optional): 点卡类型编号 ,
     * cardNo (string, optional): 点卡卡号，如果多个用逗号,隔开 ,
     * cardPwd (string, optional): 点卡密码，如果多个用逗号,隔开 ,
     * handleFee (string, optional): 手续费比 ,
     * mincredit (string, optional): 点卡界面最小点卡值 ,
     * payid (string, optional),
     * paymannerid (string, optional),
     * postUrl (string, optional): 第二、三步提交域名(点卡支付实际支付接口和点卡支付获取支付结果接口
     *
     * @return
     */
    @POST("api/pay/cardPay")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<OnlinePay>> onRequestCardPay(@FieldMap Map<String, String> map);


}
