package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.hwangjr.rxbus.RxBus;
import com.ivi.imsdk.BuildConfig;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.BaseUrlBean;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.ui.views.PNTitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>类描述： APP 信息以及切换网络环境
 * <p>创建人：Simon
 * <p>创建时间：2019-07-17
 * <p>修改人：Simon
 * <p>修改时间：2019-07-17
 * <p>修改备注：
 **/
public class AboutActivity extends BaseActivity {

    @BindView(R.id.v_dep_name_title)
    PNTitleBar vDepNameTitle;
    @BindView(R.id.ivw_logo)
    ImageView ivwLogo;
    @BindView(R.id.tvw_version_name)
    TextView tvwVersionName;
    @BindView(R.id.tvw_channel)
    TextView tvwChannel;
    @BindView(R.id.tvw_app_type)
    TextView tvwAppType;
    @BindView(R.id.tvw_sys_version_name)
    TextView tvwSysVersionName;
    @BindView(R.id.tvw_cel_type)
    TextView tvwCelType;
    @BindView(R.id.tvw_cel_factory)
    TextView tvwCelFactory;
    @BindView(R.id.tvw_cel_product)
    TextView tvwCelProduct;
    @BindView(R.id.tvw_url_real)
    TextView tvwUrlReal;
    @BindView(R.id.tvw_im_url_real)
    TextView tvwImUrlReal;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.baseurl_select)
    RecyclerView mBaseurlSelectRecyclerView;

    private List<BaseUrlBean> mBaseUrlBeen = new ArrayList<>(); //url数据源
    private CommonAdapter<BaseUrlBean> mAboutSelectAdapter = null;
    int clickCount = 0;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initViews() {
        vDepNameTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        mBaseurlSelectRecyclerView.setLayoutManager(gridLayoutManager);

        tvwUrlReal.setText(RetrofitHelper.baseUrl());
        tvwImUrlReal.setText(RetrofitHelper.imUrl());

        String verName = "";
        try {
            verName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName; // 获取的版本名称
            verName += "   (" + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode + ")";
        } catch (PackageManager.NameNotFoundException e) {
            verName = "1.0.0";
        }

        tvwVersionName.setText(verName);
        tvwAppType.setText(BuildConfig.DEBUG ? "debug" : "release");

        tvwSysVersionName.setText(Build.VERSION.RELEASE + "  (API:" + Build.VERSION.SDK_INT + ")");
        tvwCelType.setText(Build.MODEL);
        tvwCelFactory.setText(Build.BRAND);
        tvwCelProduct.setText(Build.PRODUCT);
        setBaseUrls();
    }

    @Override
    protected void initData() {

    }


    /**
     * 组装url数据
     */
    public void setBaseUrls() {
        mBaseUrlBeen.clear();
        List<String> urlList = Arrays.asList(getResources().getStringArray(R.array.base_url_array));
        List<String> urlNameList = Arrays.asList(getResources().getStringArray(R.array.base_url_name_array));
        List<String> imUrlNameList = Arrays.asList(getResources().getStringArray(R.array.base_im_url_name_array));
        for (int i = 0; i < urlList.size(); i++) {
            mBaseUrlBeen.add(new BaseUrlBean(urlList.get(i), urlNameList.get(i), imUrlNameList.get(i)));
        }
        mAboutSelectAdapter = new CommonAdapter<BaseUrlBean>(this, R.layout.activity_about_select_item, mBaseUrlBeen) {
            @Override
            public void convert(ViewHolder holder, BaseUrlBean item, int position) {
                Button title = holder.getView(R.id.title);
                title.setText(item.getName());
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        switch (position) {
                            case 0: //正式环境
                                SPTool.clear();
                                ActivityStackManager.getInstance().finishAllActivity();
//                                intent = new Intent(AboutActivity.this, LauncherActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
                                break;
                            case 1: //UAT环境
                            case 2://本地环境
                            case 3://Jonathan环境
                                SPTool.clear();
                                RetrofitHelper.setClientDomain(item.getUrl());
                                SPTool.put(ConstantValue.SAVA_URL, item.getUrl());
                                SPTool.put(ConstantValue.SAVA_IM_URL, item.getImurl());
//                                RxBus.get().post(ConstantValue.START_REQUEST, "START_REQUEST");
                                RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
//                                intent = new Intent(AboutActivity.this, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
                                break;
                            default:
                                SPTool.clear();
                                RetrofitHelper.setClientDomain(item.getUrl());
                                SPTool.put(ConstantValue.SAVA_URL, item.getUrl());
                                SPTool.put(ConstantValue.SAVA_IM_URL, item.getImurl());
//                                RxBus.get().post(ConstantValue.START_REQUEST, "START_REQUEST");
                                RxBus.get().post(ConstantValue.LOG_OUT, "LOG_OUT");
//                                intent = new Intent(AboutActivity.this, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
                                break;
                        }
                        ActivityStackManager.getInstance().finishAllActivity();
                    }
                });
            }
        };
        mBaseurlSelectRecyclerView.setAdapter(mAboutSelectAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivw_logo)
    public void onViewClicked() {
        if (clickCount++ >= 20) {
            llMore.setVisibility(View.VISIBLE);
            mBaseurlSelectRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
