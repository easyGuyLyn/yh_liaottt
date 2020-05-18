package com.nwf.sports.ui.dialogfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.nwf.sports.IMApplication;
import com.ivi.imsdk.R;
import com.nwf.sports.mvp.model.CheckupgradeResult;
import com.nwf.sports.utils.UpdateTool;

import java.io.File;
import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类描述： 更新弹窗
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class UpdateDialogFragment extends BaseDialogFragment {

    @BindView(R.id.im_close)
    ImageView imClose;
    @BindView(R.id.information_content)
    TextView informationContent;
    @BindView(R.id.immediately_upgrade)
    TextView immediatelyUpgrade;
    @BindView(R.id.plan_information)
    LinearLayout planInformation;
    @BindView(R.id.progress_plan)
    ProgressBar progressPlan;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.ly_plan)
    LinearLayout lyPlan;
    private File mApk;
    CheckupgradeResult mCheckupgradeResult = null;

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_update;
    }

    public void setCheckupgradeResult(CheckupgradeResult checkupgradeResult) {
        mCheckupgradeResult = checkupgradeResult;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        if (mCheckupgradeResult != null) {
            if (mCheckupgradeResult.getUpgrade() == 1) {
                mIsOutCanback = false;
                mIsKeyCanback = false;
            } else {
                mIsOutCanback = true;
                mIsKeyCanback = true;
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {

        if (mCheckupgradeResult != null) {
            String upgradeNote = mCheckupgradeResult.getUpgradeNote();
            String replace = upgradeNote.replace(";", "\n");
            informationContent.setText(replace);
            if (mCheckupgradeResult.getUpgrade() == 1) {
                concealClose(View.GONE);
            } else {
                concealClose(View.VISIBLE);
            }
        }

    }

    public void concealClose(int visibility) {
        if (mCheckupgradeResult != null) {
            if (mCheckupgradeResult.getUpgrade() == 1) {
                imClose.setVisibility(View.GONE);
            } else {
                imClose.setVisibility(visibility);
            }
        }
    }

    @Override
    protected void initData() {

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

    @OnClick({R.id.im_close, R.id.immediately_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                dismissAllowingStateLoss();
                break;
            case R.id.immediately_upgrade:
                applyAuth();
                break;
        }
    }


    private void applyAuth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int hasAuth = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (hasAuth != PackageManager.PERMISSION_GRANTED) {
                // 提交请求权限
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                downloadApp();
            }
        } else {
            downloadApp();
        }
    }

    private void downloadApp() {
//        String url = "https://e03-static.czsjnp.com//app/android/upgrade/app-release.apk";
        String url = mCheckupgradeResult.getTitleUrl();
        UpdateTool.downloadApk(url, "app_release.apk", new UpdateTool.DownLoadCallBack() {
            @Override
            public void onBefore() {
                planInformation.setVisibility(View.GONE);
                lyPlan.setVisibility(View.VISIBLE);
                concealClose(View.GONE);
            }

            @Override
            public void inProgress(float progress) {
                DecimalFormat fnum = new DecimalFormat("##0.00");
                int progressNum = (int) (Double.parseDouble(fnum.format(progress)) * 100);
                if (progressPlan != null) {
                    progressPlan.setProgress(progressNum);
                    tvPlan.setText("正在更新：" + progressNum + "%");
                }
            }

            @Override
            public void onSuccess(File response) {
                mApk = response;
                if (imClose != null) {
                    concealClose(View.VISIBLE);
                }
                if (mApk != null) {
                    UpdateTool.installApk(mApk, IMApplication.getContext());
                } else {
                    ToastUtil.showToastLong("apk 丢失，重新下载中....");
                    applyAuth();
                }
            }

            @Override
            public void onError(String erre) {
                ToastUtil.showToastLong(erre);
                concealClose(View.VISIBLE);
                planInformation.setVisibility(View.VISIBLE);
                lyPlan.setVisibility(View.GONE);
                immediatelyUpgrade.setText("重新下载");
                //下载失败就跳浏览器下载
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                getActivity().startActivity(intent);
            }
        });
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                permissionsResult(grantResults);
//            } else {
//                // Permission Denied
//                permissionsResult(grantResults);
//                ToastUtil.showToastShort("您没有授权存储权限，请在设置中打开授权");
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


    public void permissionsResult(@NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadApp();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
