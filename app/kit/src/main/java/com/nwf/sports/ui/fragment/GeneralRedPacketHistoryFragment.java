package com.nwf.sports.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.mvp.model.RedPacketRecordListDetailsResult;
import com.nwf.sports.ui.activity.NiuniuRedPacketParticularsActivity;
import com.nwf.sports.ui.activity.RedPacketParticularsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述： 普通红包记录
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public class GeneralRedPacketHistoryFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.history_record)
    RecyclerView historyRecord;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    ArrayList<RedPacketRecordListDetailsResult.UserBalanceResultListBean> mHistoryItemResults = new ArrayList<>();
    CommonAdapter<RedPacketRecordListDetailsResult.UserBalanceResultListBean> mHistoryAdapter = null;

    int pageNo = 1;
    String type = "";

    public static GeneralRedPacketHistoryFragment newInstance(String type) {
        GeneralRedPacketHistoryFragment fragment = new GeneralRedPacketHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantValue.ARG_PARAM1, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_general_redpacket_history;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            type = getArguments().getString(ConstantValue.ARG_PARAM1);
        }

        historyRecord.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new CommonAdapter<RedPacketRecordListDetailsResult.UserBalanceResultListBean>(getActivity(), R.layout.activity_red_packet_history_item, mHistoryItemResults) {
            @Override
            public void convert(ViewHolder holder, RedPacketRecordListDetailsResult.UserBalanceResultListBean item, int position) {
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvMoney = holder.getView(R.id.tv_money);
                tvTitle.setText(item.getBalanceTitle());
                tvTime.setText(item.getBalanceTime());
                BigDecimal bigDecimal = new BigDecimal(item.getAmount());
                if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                    tvMoney.setText("+" + item.getAmount());
                    tvMoney.setTextColor(getActivity().getResources().getColor(R.color.color_FFA520));
                } else if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    tvMoney.setText(item.getAmount() + "");
                    tvMoney.setTextColor(getActivity().getResources().getColor(R.color.color_383D52));
                } else {
                    tvMoney.setText(item.getAmount() + "");
                    tvMoney.setTextColor(getActivity().getResources().getColor(R.color.color_383D52));
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        switch (item.getSendType()) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                intent = new Intent(getActivity(), RedPacketParticularsActivity.class);
                                break;
                            case 4:
                                intent = new Intent(getActivity(), NiuniuRedPacketParticularsActivity.class);
                                break;

                        }
                        intent.putExtra(ConstantValue.ARG_PARAM1, item.getRedPacketId());
                        intent.putExtra(ConstantValue.ARG_PARAM2, false);
                        getActivity().startActivity(intent);
                    }
                });
            }
        };
        historyRecord.setAdapter(mHistoryAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {
        AppService.Instance().QueryRedPacketRecord(type, pageNo, new AppService.QueryRedPacketRecordCallback() {

            @Override
            public void onSuccess(RedPacketRecordListDetailsResult redPacketRecordListDetailsResult) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                if (pageNo == 1) {
                    mHistoryItemResults.clear();
                }
                mHistoryItemResults.addAll(redPacketRecordListDetailsResult.getUserBalanceResultList());
                mHistoryAdapter.notifyDataSetChanged();
                if (mHistoryItemResults.size() >= redPacketRecordListDetailsResult.getTotalSize()) {
                    refreshLayout.setEnableLoadMore(false);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                showMessage(msg);
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData();
        refreshLayout.setEnableLoadMore(true);
    }
}
