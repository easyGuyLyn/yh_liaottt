package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.ui.activity.webview.IntroduceActivity;
import com.nwf.sports.ui.views.PNTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-02-13
 * <p>修改人：Simon
 * <p>修改时间：2020-02-13
 * <p>修改备注：
 **/
public class RedPacketRegulationActivity extends BaseActivity {
    @BindView(R.id.v_dep_name_title)
    PNTitleBar vDepNameTitle;
    @BindView(R.id.img_saolei_introduce)
    ImageView imgSaoleiIntroduce;
    @BindView(R.id.img_saolei_regulation)
    ImageView imgSaoleiRegulation;
    @BindView(R.id.img_niuniu_introduce)
    ImageView imgNiuniuIntroduce;
    @BindView(R.id.img_niuniu_regulation)
    ImageView imgNiuniuRegulation;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_red_packet_regulation);
    }

    @Override
    protected void initViews() {
        vDepNameTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_saolei_introduce, R.id.img_saolei_regulation, R.id.img_niuniu_introduce, R.id.img_niuniu_regulation})
    public void onViewClicked(View view) {
        Intent intent = new Intent(RedPacketRegulationActivity.this, IntroduceActivity.class);
        switch (view.getId()) {
            case R.id.img_saolei_introduce:
                intent.putExtra(ConstantValue.ARG_PARAM1, "玩法介绍");
                intent.putExtra(ConstantValue.ARG_PARAM2, "https://m.f6601.com/slhbplay.htm");
                break;
            case R.id.img_saolei_regulation:
                intent.putExtra(ConstantValue.ARG_PARAM1, "游戏规则");
                intent.putExtra(ConstantValue.ARG_PARAM2, "https://m.f6601.com/slhbrule.htm");
                break;
            case R.id.img_niuniu_introduce:
                intent.putExtra(ConstantValue.ARG_PARAM1, "玩法介绍");
                intent.putExtra(ConstantValue.ARG_PARAM2, "https://m.f6601.com/nnhbplay.htm");
                break;
            case R.id.img_niuniu_regulation:
                intent.putExtra(ConstantValue.ARG_PARAM1, "游戏规则");
                intent.putExtra(ConstantValue.ARG_PARAM2, "https://m.f6601.com/nnhbrule.htm");
                break;
        }
        startActivity(intent);
    }
}
