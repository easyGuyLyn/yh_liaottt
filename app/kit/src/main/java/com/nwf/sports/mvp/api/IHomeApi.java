package com.nwf.sports.mvp.api;

import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;
import com.nwf.sports.mvp.model.LimitTransferResult;
import com.nwf.sports.net.request.AppTextMessageResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * <p>类描述： 首页的接口API
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public interface IHomeApi {

    //获取游戏开关列表
    @POST("api/game/list")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<List<HomeGameResult>>> gameList(@FieldMap Map<String, String> map);

    //加载游戏接口
    @POST("api/game/loginGame")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> loginGame(@FieldMap Map<String, String> map);

    //加载游戏接口试玩
    @POST("api/game/trial")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<String>> trialGame(@FieldMap Map<String, String> map);

    //首页数据
    @POST("api/homePage")
    public Observable<AppTextMessageResponse<HomeDiscountsResult>> homePage();

    //首页下载App
    @POST("api/game/download")
    public Observable<AppTextMessageResponse<DownloadAppResult>> downloadApps();

    //刷新额度
    @POST("api/game/transferTargetGame")
    @FormUrlEncoded
    public Observable<AppTextMessageResponse<LimitTransferResult>> transferTargetGame(@FieldMap Map<String, String> map);

}
