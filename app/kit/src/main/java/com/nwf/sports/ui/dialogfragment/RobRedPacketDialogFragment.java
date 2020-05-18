package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.message.RedPack;

/**
 * <p>类描述： 抢红包弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class RobRedPacketDialogFragment extends BaseDialogFragment {

    @BindView(R.id.im_close)
    ImageView imClose;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_red_packet_name)
    TextView tvRedPacketName;
    @BindView(R.id.tv_red_packet_type)
    TextView tvRedPacketType;
    @BindView(R.id.tv_rob_red_packet)
    ImageView tvRobRedPacket;
    @BindView(R.id.im_portrait)
    ImageView imPortrait;

    private View.OnClickListener mListener = null;
    RedPack redPack = null;
    String sender = "";
    String portrait = "";

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_rob_red_pacet;
    }

    public void setListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public static RobRedPacketDialogFragment getInstance(RedPack redPack, String sender, String portrait) {
        RobRedPacketDialogFragment robRedPacketDialogFragment = new RobRedPacketDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantValue.ARG_PARAM1, redPack);
        bundle.putString(ConstantValue.ARG_PARAM2, sender);
        bundle.putString(ConstantValue.ARG_PARAM3, portrait);
        robRedPacketDialogFragment.setArguments(bundle);
        return robRedPacketDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        mIsOutCanback = true;
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            redPack = getArguments().getParcelable(ConstantValue.ARG_PARAM1);
            sender = getArguments().getString(ConstantValue.ARG_PARAM2);
            portrait = getArguments().getString(ConstantValue.ARG_PARAM3);
        }
    }

    @Override
    protected void initViews(View view) {
        if (mListener != null) {
            tvRobRedPacket.setOnClickListener(mListener);
        }
    }

    @Override
    protected void initData() {
        if (redPack == null) {
            ToastUtil.showToastLong("系统异常");
            dismissAllowingStateLoss();
        }
        if (redPack.redPacketType.equals("3")) {
            tvRedPacketType.setText("发了一个扫雷红包，金额随机");
        } else if (redPack.redPacketType.equals("4")) {
            tvRedPacketType.setText("发了一个牛牛红包，金额随机");
        } else {
            tvRedPacketType.setText("发了一个手气红包，金额随机");
        }
        tvRedPacketName.setText(redPack.redPacketName);
        tvUserName.setText(sender);

        Glide.with(getActivity())
                .load(portrait)
                .apply(new RequestOptions().placeholder(R.mipmap.im_avatar_def).centerCrop())
                .into(imPortrait);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.im_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                dismissAllowingStateLoss();
                break;
        }
    }
}
