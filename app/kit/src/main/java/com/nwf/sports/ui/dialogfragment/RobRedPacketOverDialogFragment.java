package com.nwf.sports.ui.dialogfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.NiuniuRedPacketParticularsActivity;
import com.nwf.sports.ui.activity.RedPacketParticularsActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.message.RedPack;

/**
 * <p>类描述： 抢红包派完弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class RobRedPacketOverDialogFragment extends BaseDialogFragment {

    @BindView(R.id.im_close)
    ImageView imClose;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_red_packet_name)
    TextView tvRedPacketName;
    @BindView(R.id.tv_particulars)
    TextView tvParticulars;

    RedPack redPack = null;
    String sender = "";

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_rob_red_pacet_over;
    }


    public static RobRedPacketOverDialogFragment getInstance(RedPack redPack, String sender) {
        RobRedPacketOverDialogFragment robRedPacketDialogFragment = new RobRedPacketOverDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantValue.ARG_PARAM1, redPack);
        bundle.putString(ConstantValue.ARG_PARAM2, sender);
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
        }
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected void initData() {
        if (redPack == null) {
            ToastUtil.showToastLong("系统异常");
            dismissAllowingStateLoss();
        }
//        tvRedPacketName.setText(redPack.redPacketName);
//        tvUserName.setText(sender + "的红包");
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

    @OnClick({R.id.im_close, R.id.tv_particulars})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                dismissAllowingStateLoss();
                break;
            case R.id.tv_particulars:
                Intent intent = null;
                if (redPack.redPacketType.equals("4")) {
                    intent = new Intent(getActivity(), NiuniuRedPacketParticularsActivity.class);
                } else {
                    intent = new Intent(getActivity(), RedPacketParticularsActivity.class);
                }
                intent.putExtra(ConstantValue.ARG_PARAM1, redPack.redPacketId);
                getActivity().startActivity(intent);
                dismissAllowingStateLoss();
                break;
        }
    }
}
