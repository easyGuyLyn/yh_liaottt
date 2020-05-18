package com.nwf.sports.mvp.view;

import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.mvp.model.HomeDiscountsResult;
import com.nwf.sports.mvp.model.HomeGameResult;

import java.util.List;

/**
 * <p>类描述： 首页的View
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public interface HomeView extends IBaseView {
    void gameListSuccess(List<HomeGameResult> gameResults);

    void homePageSuccess(HomeDiscountsResult homeDiscountsResult);

    void downloadApps(DownloadAppResult downloadAppResult);

    void loginGame(String url,String title);
}
