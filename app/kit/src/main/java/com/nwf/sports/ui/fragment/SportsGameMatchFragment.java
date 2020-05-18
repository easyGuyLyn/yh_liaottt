package com.nwf.sports.ui.fragment;

import android.os.Bundle;

import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-02-01
 * <p>修改人：Simon
 * <p>修改时间：2020-02-01
 * <p>修改备注：
 **/
public class SportsGameMatchFragment extends BaseFragment {

    @BindView(R.id.rv_game_list)
    RecyclerView rvGameList;

    List<String> mMatchList = new ArrayList<>();
    private CommonAdapter<String> mMatchAdapter = null;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_sports_program;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
//        mMatchList.add("");
        rvGameList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchAdapter = new CommonAdapter<String>(getActivity(), R.layout.fragment_home_match_item, mMatchList) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
            }
        };
        rvGameList.setAdapter(mMatchAdapter);
    }

    @Override
    protected void loadData() {

    }

}
