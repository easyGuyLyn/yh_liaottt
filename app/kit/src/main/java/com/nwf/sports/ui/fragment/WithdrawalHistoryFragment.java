package com.nwf.sports.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.coretool.util.date.DateUtils;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.WithdrawalHistoryResult;
import com.nwf.sports.mvp.presenter.HistoryPresenter;
import com.nwf.sports.mvp.view.HistoryView;
import com.nwf.sports.utils.data.IMDataCenter;
import com.nwf.sports.utils.historyutil.HistoryServiceEnum;
import com.nwf.sports.utils.historyutil.HistoryUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述： 取款记录
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public class WithdrawalHistoryFragment extends BaseFragment implements HistoryView, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.history_record)
    RecyclerView historyRecord;
    @BindView(R.id.history_record_layout)
    LinearLayout historyRecordLayout;
    @BindView(R.id.iv_empty_hint_history)
    ImageView ivEmptyHintHistory;
    @BindView(R.id.tv_empty_hint_history)
    TextView tvEmptyHintHistory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.group_empty_history)
    LinearLayout groupEmptyHistory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    HistoryServiceEnum mHistoryServiceEnum = HistoryServiceEnum.DEPOSIT;
    HistoryPresenter mHistoryPresenter = null;

    ArrayList<WithdrawalHistoryResult.ListBean> mHistoryItemResults = new ArrayList<>();
    CommonAdapter<WithdrawalHistoryResult.ListBean> mHistoryAdapter = null;

    int pageNo = 1;
    String presentData = "";
    String beforeData = "";

    public static WithdrawalHistoryFragment newInstance(HistoryServiceEnum enm) {
        WithdrawalHistoryFragment fragment = new WithdrawalHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantValue.ARG_PARAM1, enm);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_history_withdrawal;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mHistoryServiceEnum = (HistoryServiceEnum) getArguments().getSerializable(ConstantValue.ARG_PARAM1);
        }
        mHistoryPresenter = new HistoryPresenter(getActivity(), this);
        historyRecord.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new CommonAdapter<WithdrawalHistoryResult.ListBean>(getActivity(), R.layout.activity_history_item, mHistoryItemResults) {
            @Override
            public void convert(ViewHolder holder, WithdrawalHistoryResult.ListBean item, int position) {
                TextView tvStatusItemHistory = holder.getView(R.id.tv_status_item_history);
                holder.setText(R.id.tv_date_item_history, item.getCreateDate());
                holder.setText(R.id.tv_money_item_history, item.getAmount() + "元");
//                if (!Check.isEmpty(item.description)) {
//                    String desc = item.description;
//                    holder.setText(R.id.tv_note_item_history, desc);
//                } else {
//                    holder.setVisible(R.id.tv_note_item_history, false);
//                }
                HistoryUtil.getProgress(mHistoryServiceEnum, getActivity(), tvStatusItemHistory, item.getFlag());

            }
        };
        historyRecord.setAdapter(mHistoryAdapter);
        beforeData = DateTool.convert2String(DateUtils.getBeginDayOfBeforeday(10), DateTool.FMT_DATE_TIME);
        presentData = DateTool.convert2String(DateUtils.getDayEnd(), DateTool.FMT_DATE_TIME);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("beginTime", beforeData);
        map.put("endTime", presentData);
        map.put("pageNo", pageNo);
        map.put("pageSize", "15");
        map.put("userName", IMDataCenter.getInstance().getUserInfoBean().getUsername());
        map.put("withdrawalFlag", "");
        mHistoryPresenter.withdrawHistory(map);
    }

    @Override
    public void findHistorySucceed(WithdrawalHistoryResult result) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (pageNo == 1) {
            if (!result.isEmpty()) {
                findHistoryDefeated();
                return;
            } else {
                historyRecordLayout.setVisibility(View.VISIBLE);
                groupEmptyHistory.setVisibility(View.GONE);
            }
            mHistoryItemResults.clear();
        }
        mHistoryItemResults.addAll(result.getList());
        mHistoryAdapter.notifyDataSetChanged();

        if (mHistoryItemResults.size() >= result.getTotal()) {
            refreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void findHistoryDefeated() {
        historyRecordLayout.setVisibility(View.GONE);
        groupEmptyHistory.setVisibility(View.VISIBLE);
//        HistoryUtil.emptytint(mHistoryServiceEnum, ivEmptyHintHistory, tvEmptyHintHistory);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHistoryPresenter != null) {
            mHistoryPresenter.onDestory();
        }
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

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }
}
