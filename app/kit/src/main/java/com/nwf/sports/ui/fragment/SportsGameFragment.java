package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.presenter.SportsGamePresenter;
import com.nwf.sports.mvp.view.GameView;
import com.nwf.sports.ui.activity.webview.XPlayGameActivity;
import com.nwf.sports.utils.data.DataCenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.OnClick;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-02-01
 * <p>修改人：Simon
 * <p>修改时间：2020-02-01
 * <p>修改备注：
 **/
public class SportsGameFragment extends BaseFragment implements GameView {

    SportsGamePresenter mSportsGamePresenter = null;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_sports_game;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mSportsGamePresenter = new SportsGamePresenter(getActivity(), this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.img_entrance)
    public void onViewClicked() {
        if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            if (IMApplication.isChat) {
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("actype", "1");
            map.put("currency ", "CNY");
            map.put("gameId", "");
            map.put("gmid", "E03083");
            map.put("language", "zh");
            map.put("loginName", DataCenter.getInstance().getUserInfoBean().getUsername());
            mSportsGamePresenter.loginGame(map, "体育游戏");
        }
    }

    @Override
    public void loginGame(String url, String title) {
        Bundle mbundle = new Bundle();
        mbundle.putString(ConstantValue.ARG_PARAM1, url);
        mbundle.putString(ConstantValue.ARG_PARAM2, title);
        mbundle.putBoolean(ConstantValue.ARG_PARAM3, false);
        startActivity(new Intent(mContext, XPlayGameActivity.class).putExtras(mbundle));
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mSportsGamePresenter != null) {
            mSportsGamePresenter.onDestory();
        }
    }

}
