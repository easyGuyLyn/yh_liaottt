package com.nwf.sports.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.dawoo.coretool.util.ToastUtil;
import com.ivi.imsdk.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import androidx.core.content.FileProvider;
import okhttp3.Call;
import okhttp3.Request;

import static com.nwf.sports.IMApplication.getContext;

/**
 * 应用更新工具 下载
 * simon
 */
public class UpdateTool {
    @SuppressWarnings("unused")
    private static final String TAG = "AppUpdate";


    public static void downloadApk(String apkUrl, String fileName, DownLoadCallBack downLoadCallBack) {
        Log.e(TAG, "下载app地址 " + apkUrl);

//        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filePath = getContext().getExternalFilesDir(null).getAbsolutePath();

        OkHttpUtils.get()
                .url(apkUrl)
                .build()
                .execute(new FileCallBack(filePath, fileName) {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        ToastUtil.showToastLong("资源准备中...");
                        downLoadCallBack.onBefore();
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        Log.e(TAG, "onAfter ");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        downLoadCallBack.inProgress(progress);
                        Log.e("progress", progress + "");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError " + e.getMessage());
                        downLoadCallBack.onError("内部下载apk失败,错误代码： " + e.getMessage() + "/n 跳转浏览器下载中");
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if (response == null) {
                            downLoadCallBack.onError("下载的apk为空");
                            return;
                        }
                        downLoadCallBack.onSuccess(response);
                    }
                });
    }


    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public static void installApk(File apkFile, Context activity) {
        if (apkFile == null) return;
        Log.e(TAG, " 新apk 路径: " + apkFile.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, activity.getResources().getString(R.string.provider_auth), apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

    //下载回调
    public interface DownLoadCallBack {
        void onBefore();

        void inProgress(float progress);

        void onSuccess(File response);

        void onError(String err);
    }
}