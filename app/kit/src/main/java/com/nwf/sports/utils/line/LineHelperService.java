package com.nwf.sports.utils.line;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;

/**
 * <p>类描述：
 * 线路处理的服务
 * <p>创建人：Simon
 * <p>创建时间：2018-11-24
 * <p>修改人：Simon
 * <p>修改时间：2018-11-24
 * <p>修改备注：
 **/
public class LineHelperService extends IntentService {
    public static final String TAG = "LineHelperService";
    LineHelperUtil lineHelperUtil;
    public static final String CHANNEL_ID_LOCATION = "com.pn.app.line.LineHelperService";

    public LineHelperService() {
        super("LineHelperService");
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, LineHelperService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void initChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            // service的onCreate
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_LOCATION, "System", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, CHANNEL_ID_LOCATION)
                    .setSmallIcon(R.mipmap.ic_launcher)  // the status icon
                    .setWhen(System.currentTimeMillis())  // the time stamp
                    .setContentText("LineHelp服务正在运行")  // the contents of the entry
                    .build();
            startForeground(2, notification);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        initChannel();
        Log.e(TAG, "-----------------开启服线路服务--------------");
        if (intent == null) {
            return;
        }

        lineHelperUtil = new LineHelperUtil(getApplicationContext());
        lineHelperUtil.pingDomain();
        lineHelperUtil.setLineTaskProgressListener(new LineTaskProgressListener() {
            @Override
            public void onProgressBarChange(int current, int max) {
                switch (current) {
                    case LineHelperUtil.progress_finish:
                        IMApplication.isGain = true;
                        RxBus.get().post(ConstantValue.START_REQUEST, "START_REQUEST");
                        lineHelperUtil.onDestroy();
                        break;
                    case LineHelperUtil.progress_erre_CheckDomain:
                        IMApplication.isGain = false;
                        IMApplication.mLineErrorDialogBean = new LineErrorDialogBean(LineHelperUtil.PING_DOMAIN_ERRE, "系统异常，请联系客服");
                        RxBus.get().post(ConstantValue.PING_DOMAIN_ERRE, "PING_DOMAIN_ERRE");
                        lineHelperUtil.onDestroy();
                        Log.e(TAG, "Ping  线路异常  没有一个ping 过");
                        break;
                }
                Log.e(TAG, "-----------------服线路服务结束--------------");
            }

            @Override
            public void onErrorSimpleReason(String result) {

            }

            @Override
            public void onErrorComplexReason(LineErrorDialogBean lineErrorDialogBean) {

            }

            @Override
            public void onGetLineSuccess(String domain) {

            }
        });
    }

}