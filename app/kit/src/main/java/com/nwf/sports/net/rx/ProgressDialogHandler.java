package com.nwf.sports.net.rx;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.nwf.sports.ui.dialogfragment.DialogFramentManager;
import com.nwf.sports.ui.dialogfragment.LoadingDialogFragment;

import androidx.appcompat.app.AppCompatActivity;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private volatile LoadingDialogFragment pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        //&& !DialogFramentManager.getInstance().isShowLoading("LoadingDialogFragment")
        if (pd == null) {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
                if (((Activity) context).isDestroyed()) {
                    return;
                }
                pd = new LoadingDialogFragment();
                AppCompatActivity activity = (AppCompatActivity) ActivityStackManager.getInstance().getFirstActivity();
                if (activity != null && pd != null) {
                    DialogFramentManager.getInstance().showDialog(activity.getSupportFragmentManager(), pd);
                }
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null && context != null && pd.getDialog() != null && pd.getDialog().isShowing()) {
            if (context instanceof Activity) {

                if (((Activity) context).isFinishing()) {
                    pd = null;
                    return;
                }
                if (((Activity) context).isDestroyed()) {
                    pd = null;
                    return;
                }

                pd.dismissAllowingStateLoss();
                pd = null;

            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
