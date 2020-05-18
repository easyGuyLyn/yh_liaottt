package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.chat.AppService;
import com.nwf.sports.mvp.model.RedPacketDetailResult;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 红包详情
 * <p>创建人：Simon
 * <p>创建时间：2020-01-28
 * <p>修改人：Simon
 * <p>修改时间：2020-01-28
 * <p>修改备注：
 **/
public class RedPacketParticularsActivity extends BaseActivity {
    @BindView(R.id.iv_com_title_back)
    ImageView ivComTitleBack;
    @BindView(R.id.tv_red_packet_username)
    TextView tvRedPacketUsername;
    @BindView(R.id.tv_red_packet_name)
    TextView tvRedPacketName;
    @BindView(R.id.tv_money_big)
    TextView tvMoneyBig;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_red_packet_username_portrait)
    ImageView tvRedPacketUsernamePortrait;
    @BindView(R.id.red_packet_record)
    RecyclerView redPacketRecord;
    @BindView(R.id.tv_com_title_more)
    TextView tvComTitleMore;

    RedPacketDetailResult redPacketDetailResult;
    ArrayList<RedPacketDetailResult.PacketReceiveLogVoListBean> mRedPacketDetailResults = new ArrayList<>();
    CommonAdapter<RedPacketDetailResult.PacketReceiveLogVoListBean> mRedPacketDetailAdapter = null;

    String redPacketId = "";
    boolean isShow = false;
    @BindView(R.id.tv_com_title_name)
    TextView tvComTitleName;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_red_packet_particulars);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            redPacketId = getIntent().getStringExtra(ConstantValue.ARG_PARAM1);
            isShow = getIntent().getBooleanExtra(ConstantValue.ARG_PARAM2, false);
        }

        if (isShow) {
            tvComTitleMore.setVisibility(View.VISIBLE);
        } else {
            tvComTitleMore.setVisibility(View.GONE);
        }

        ivComTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AppService.Instance().RedPacketDetail(redPacketId, 1 + "", 10 + "", new AppService.RedPacketDetailCallback() {

            @Override
            public void onSuccess(RedPacketDetailResult redPacketDetailResult) {
                mRedPacketDetailResults.clear();
                mRedPacketDetailResults.addAll(redPacketDetailResult.getPacketReceiveLogVoList());
                mRedPacketDetailAdapter.notifyDataSetChanged();

                Glide.with(RedPacketParticularsActivity.this)
                        .load(redPacketDetailResult.getSenderUserPortrait())
                        .apply(new RequestOptions().placeholder(R.mipmap.im_avatar_def).centerCrop())
                        .into(tvRedPacketUsernamePortrait);
                tvMoneyBig.setText(redPacketDetailResult.getGrabMoney());
                tvRedPacketUsername.setText(redPacketDetailResult.getSendUserDisplayName() + "的红包");
                tvRedPacketName.setText(redPacketDetailResult.getSendTitle());
                String sendMoney = redPacketDetailResult.getSendMoney();
                String sendNumber = redPacketDetailResult.getSendNumber();
                String receiveNumber = redPacketDetailResult.getReceiveNumber();
                String receiveAmount = redPacketDetailResult.getReceiveAmount();
                tvCollect.setText("已领取" + receiveNumber + "/" + sendNumber + "个,共" + receiveAmount + "/" + sendMoney + "元");
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.showToastLong(msg);
            }
        });

        mRedPacketDetailAdapter = new CommonAdapter<RedPacketDetailResult.PacketReceiveLogVoListBean>(this, R.layout.activity_red_packet_particulars_item, mRedPacketDetailResults) {
            @Override
            public void convert(ViewHolder holder, RedPacketDetailResult.PacketReceiveLogVoListBean item, int position) {
                TextView tvBest = holder.getView(R.id.tv_best);
                ImageView imThunder = holder.getView(R.id.im_thunder);
                TextView tvExtra = holder.getView(R.id.tv_extra);
                ImageView portrait = holder.getView(R.id.portrait);

                tvBest.setVisibility(View.GONE);
                imThunder.setVisibility(View.GONE);
                tvExtra.setVisibility(View.GONE);

                holder.setText(R.id.tv_user_name, item.getDisplayName());
                holder.setText(R.id.tv_money, item.getAmount());
                holder.setText(R.id.tv_time, item.getCreateDateStr());
                if (item.getIsBest()) {
                    tvBest.setVisibility(View.VISIBLE);
                }
                if (item.getIsThunder()) {
                    imThunder.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(item.getOtherRewards())) {
                    tvExtra.setVisibility(View.VISIBLE);
                    tvExtra.setText(item.getOtherRewards() + item.getOtherRewardsMoney());
                }

                Glide.with(RedPacketParticularsActivity.this)
                        .load(item.getPortrait())
                        .apply(new RequestOptions().placeholder(R.mipmap.im_avatar_def).centerCrop())
                        .into(portrait);

            }
        };
        redPacketRecord.setLayoutManager(new LinearLayoutManager(this));
        redPacketRecord.setAdapter(mRedPacketDetailAdapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_com_title_more)
    public void onViewClicked() {
        startActivity(new Intent(this, RedPacketHistoryActivity.class));
    }
}
