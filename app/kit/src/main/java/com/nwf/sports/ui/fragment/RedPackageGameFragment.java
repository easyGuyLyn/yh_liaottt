package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.HomeRedPacketGameBean;
import com.nwf.sports.ui.activity.RedpacketGameActivity;

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
public class RedPackageGameFragment extends BaseFragment {

    @BindView(R.id.rv_game_list)
    RecyclerView rvGameList;

    List<HomeRedPacketGameBean> mRedPacket = new ArrayList<>();
    private CommonAdapter<HomeRedPacketGameBean> mRedPacketAdapter = null;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_redpacket_game;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        rvGameList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRedPacketAdapter = new CommonAdapter<HomeRedPacketGameBean>(getActivity(), R.layout.fragment_home_redpacket_item, mRedPacket) {
            @Override
            public void convert(ViewHolder holder, HomeRedPacketGameBean item, int position) {
                ImageView imageView = holder.getView(R.id.img_type_bg);
                imageView.setImageResource(item.bg);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IMApplication.isChat){
                            return;
                        }
                        Intent intent = new Intent(getActivity(), RedpacketGameActivity.class);
                        intent.putExtra(ConstantValue.ARG_PARAM1,item.type);
                        startActivity(intent);
                    }
                });
            }
        };
        rvGameList.setAdapter(mRedPacketAdapter);
    }

    @Override
    protected void loadData() {
        mRedPacket.add(new HomeRedPacketGameBean(0, R.drawable.bg_hb_game_chat));
        mRedPacket.add(new HomeRedPacketGameBean(1, R.drawable.bg_hb_game_saolei));
        mRedPacket.add(new HomeRedPacketGameBean(2, R.drawable.bg_hb_game_niu));
        mRedPacketAdapter.notifyDataSetChanged();
    }

}
