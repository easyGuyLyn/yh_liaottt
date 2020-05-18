package com.nwf.sports.ui.activity;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dawoo.coretool.util.ToastUtil;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.adapter.CommonAdapter;
import com.nwf.sports.adapter.ViewHolder;
import com.nwf.sports.mvp.model.DownloadAppResult;
import com.nwf.sports.ui.views.PNTitleBar;
import com.nwf.sports.utils.UpdateTool;
import com.nwf.sports.utils.data.DataCenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * <p>类描述： 下载其他App列表
 * <p>创建人：Simon
 * <p>创建时间：2019-07-01
 * <p>修改人：Simon
 * <p>修改时间：2019-07-01
 * <p>修改备注：
 **/
public class DownloadAppsActivity extends BaseActivity {

    @BindView(R.id.v_title)
    public PNTitleBar vTitle;
    @BindView(R.id.download_record)
    public RecyclerView downloadRecord;

    List<DownloadAppResult.AppsBean> mDownloadAppResults = new ArrayList<>();
    CommonAdapter<DownloadAppResult.AppsBean> mDownloadAppCommonAdapter = null;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_download_apps);
    }

    @Override
    protected void initViews() {
        RxBus.get().register(this);
        if (getIntent() != null) {
            ArrayList<DownloadAppResult.AppsBean> appsBeans = getIntent().getParcelableArrayListExtra(ConstantValue.ARG_PARAM1);
            if (null != appsBeans && appsBeans.size() != 0) {
                assembleData(appsBeans);
            } else {
                assembleData(DataCenter.getInstance().getMyLocalCenter().getDownloadApp());
            }
        } else {
            assembleData(DataCenter.getInstance().getMyLocalCenter().getDownloadApp());
        }
        vTitle.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        downloadRecord.setLayoutManager(new LinearLayoutManager(this));
        mDownloadAppCommonAdapter = new CommonAdapter<DownloadAppResult.AppsBean>(this, R.layout.activity_item_appdownload, mDownloadAppResults) {
            @Override
            public void convert(ViewHolder holder, DownloadAppResult.AppsBean item, int position) {
                TextView title = holder.getView(R.id.tvname_item_appdownload);
                TextView description = holder.getView(R.id.tvdescription_item_appdownload);
                ImageView imageView = holder.getView(R.id.iv_item_appdownload);
                Button download = holder.getView(R.id.btn_item_appdownload);
                title.setText(item.getAppname());
                description.setText(item.getDescription());
                //加载图片
                Glide.with(mContext)
                        .load(item.getIconname())
                        .into(imageView);
                if (item.getCondition().equals("1")) {
                    download.setText("下载中");
                    download.setEnabled(false);
                } else if (item.getCondition().equals("2")) {
                    download.setText("安装");
                    download.setEnabled(true);
                } else if (item.getCondition().equals("3")) {
                    download.setText("已安装");
                    download.setEnabled(false);
                } else {
                    download.setText("下载");
                    download.setEnabled(true);
                }

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getCondition().equals("1")) {
                        } else if (item.getCondition().equals("2")) {
                            if (item.getLocation() != null) {
                                File file = new File(item.getLocation());
                                if (file.exists()) {
                                    UpdateTool.installApk(file, IMApplication.getContext());
                                } else {
                                    mDownloadAppResults.get(position).setLocation("");
                                    notifyData(position, "");
                                }
                            } else {
                                mDownloadAppResults.get(position).setLocation("");
                                notifyData(position, "");
                            }

                        } else if (item.getCondition().equals("3")) {
                        } else {
                            applyAuth(position);
                        }
                    }
                });
            }
        };
        downloadRecord.setAdapter(mDownloadAppCommonAdapter);
    }

    @Override
    protected void initData() {

    }

    private void applyAuth(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int hasAuth = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (hasAuth != PackageManager.PERMISSION_GRANTED) {
                // 提交请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                downloadApp(position);
            }
        } else {
            downloadApp(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                ToastUtil.showToastShort("您没有授权存储权限，请在设置中打开授权");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void downloadApp(int position) {
        DownloadAppResult.AppsBean appsBean = mDownloadAppResults.get(position);
        UpdateTool.downloadApk(appsBean.getApkname(), appsBean.getPackagename() + ".apk", new UpdateTool.DownLoadCallBack() {
            @Override
            public void onBefore() {
                IMApplication.isDownload = true;
                notifyData(position, "1");
            }

            @Override
            public void inProgress(float progress) {
            }

            @Override
            public void onSuccess(File response) {
                mDownloadAppResults.get(position).setLocation(response.getAbsolutePath());
                notifyData(position, "2");
                RxBus.get().post(ConstantValue.DOWNLOAD_APPS_SUCCEED, "2");
            }

            @Override
            public void onError(String err) {
                showMessage("下载失败！");
                notifyData(position, "");
            }
        });
    }

    /**
     * 更新数据
     */
    public void notifyData(int position, String condition) {
        mDownloadAppResults.get(position).setCondition(condition);
        DataCenter.getInstance().getMyLocalCenter().saveDownloadApp(mDownloadAppResults);
        mDownloadAppResults.clear();
        mDownloadAppResults.addAll(DataCenter.getInstance().getMyLocalCenter().getDownloadApp());
        mDownloadAppCommonAdapter.notifyDataSetChanged();
    }


    /**
     * 判断APP 是否有安装
     *
     * @param pkgName
     * @return
     */
    private boolean checkAppInstalled(String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = IMApplication.getContext().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;//true为安装了，false为未安装
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        assembleData(DataCenter.getInstance().getMyLocalCenter().getDownloadApp());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    /**
     * 组装数据
     */
    public void assembleData(List<DownloadAppResult.AppsBean> mDownload) {
        mDownloadAppResults.clear();
        mDownloadAppResults.addAll(mDownload);
        List<DownloadAppResult.AppsBean> downloadApp = DataCenter.getInstance().getMyLocalCenter().getDownloadApp();
        if (downloadApp.size() != 0) {
            for (DownloadAppResult.AppsBean data : downloadApp) {
                for (int i = 0; i < mDownloadAppResults.size(); i++) {
                    DownloadAppResult.AppsBean item = mDownloadAppResults.get(i);
                    if (item.getPackagename().equals(data.getPackagename())) {
                        item.setCondition(data.getCondition());
                        item.setLocation(data.getLocation());
                        boolean b = checkAppInstalled(item.getPackagename());
                        if (b) {
                            item.setCondition("3");
                        } else {
                            if (data.getCondition().equals("3")) {
                                item.setCondition("");
                            }
                        }
                        if (data.getCondition().equals("1") && !IMApplication.isDownload) {
                            downloadApp(i);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < mDownloadAppResults.size(); i++) {
                DownloadAppResult.AppsBean item = mDownloadAppResults.get(i);
                boolean b = checkAppInstalled(item.getPackagename());
                if (b) {
                    item.setCondition("3");
                }
            }
        }
        DataCenter.getInstance().getMyLocalCenter().saveDownloadApp(mDownloadAppResults);
        if (mDownloadAppCommonAdapter!=null){
            mDownloadAppCommonAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 刷新界面
     */
    @Subscribe(tags = {@Tag(ConstantValue.DOWNLOAD_APPS_SUCCEED)})
    public void refreshDownload(String type) {
        assembleData(DataCenter.getInstance().getMyLocalCenter().getDownloadApp());
    }
}
