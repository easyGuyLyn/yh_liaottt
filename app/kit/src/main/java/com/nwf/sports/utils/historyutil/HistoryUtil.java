package com.nwf.sports.utils.historyutil;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.ResHelper;
import com.ivi.imsdk.R;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-04-24
 * <p>修改人：Simon
 * <p>修改时间：2019-04-24
 * <p>修改备注：
 **/
public class HistoryUtil {


    public static void getProgress(HistoryServiceEnum enm, Context context, TextView textView, int progress) {
        switch (enm) {
            case DEPOSIT:
                getDepositProgress(context, textView, progress);
                break;

            case WITHDRAW:
                getWithdrawalProgress(context, textView, progress);
                break;

            case PROMOTION:
                getDiscountsProgress(context, textView, progress);
                break;

            default:
                getDepositProgress(context, textView, progress);
                break;
        }
    }

    /**
     * 获取存款进度
     */
    public static void getDepositProgress(Context context, TextView textView, int progress) {
        String type = "";
        int typeColor = 0;

        if (DepositRequestProgressEnum.PENDING.getFlag() == progress
                || DepositRequestProgressEnum.ONGOING.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_pending);
            typeColor = R.color.color_9B9B9B;
        } else if (DepositRequestProgressEnum.APPROVED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_approval);
            typeColor = R.color.color_1ECE94;
        } else if (DepositRequestProgressEnum.CANCELLED.getFlag() == progress
                || DepositRequestProgressEnum.CANCEL_CSO.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_cancelled);
            typeColor = R.color.color_FF3300;
        } else if (DepositRequestProgressEnum.REJECTED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_rejected);
            typeColor = R.color.color_FF3300;
        } else {
            type = ResHelper.getString(R.string.str_progress_pending);
            typeColor = R.color.color_9B9B9B;
        }
        textView.setText(type);
        textView.setTextColor(context.getResources().getColor(typeColor));
    }

    /**
     * 获取取款进度
     */
    public static void getWithdrawalProgress(Context context, TextView textView, int progress) {
        String type = "";
        int typeColor = 0;

        if (WashProgressEnum.PENDING.getFlag() == progress
                || WashProgressEnum.ONGOING.getFlag() == progress
                || WashProgressEnum.PENDING_ONGOING.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_pending);
            typeColor = R.color.color_9B9B9B;
        } else if (WashProgressEnum.APPROVED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_approval);
            typeColor = R.color.color_1ECE94;
        } else if (WashProgressEnum.CANCELLED.getFlag() == progress ||
                WashProgressEnum.CANCEL_CSO.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_cancelled);
            typeColor = R.color.color_FF3300;
        } else if (WashProgressEnum.REJECTED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_rejected);
            typeColor = R.color.color_FF3300;
        } else {
            type = ResHelper.getString(R.string.str_progress_pending);
            typeColor = R.color.color_9B9B9B;
        }
        textView.setText(type);
        textView.setTextColor(context.getResources().getColor(typeColor));
    }

    /**
     * 获取取款进度
     */
    public static void getDiscountsProgress(Context context, TextView textView, int progress) {
        String type = "";
        int typeColor = 0;

        if (DiscountsProgressEnum.PENDING.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_auditing);
            typeColor = R.color.color_9B9B9B;
        } else if (DiscountsProgressEnum.PENDING_ONGOING.getFlag() == progress ||
                DiscountsProgressEnum.ONGOING.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_auditing);
            typeColor = R.color.color_9B9B9B;
        } else if (DiscountsProgressEnum.APPROVED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_approval);
            typeColor = R.color.color_1ECE94;
        } else if (DiscountsProgressEnum.CANCEL_CSO.getFlag() == progress ||
                DiscountsProgressEnum.CANCELLED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_cancelled);
            typeColor = R.color.color_FF3300;
        } else if (DiscountsProgressEnum.REJECTED.getFlag() == progress) {
            type = ResHelper.getString(R.string.str_progress_rejected);
            typeColor = R.color.color_FF3300;
        } else {
            type = ResHelper.getString(R.string.str_progress_auditing);
            typeColor = R.color.color_9B9B9B;
        }
        textView.setText(type);
        textView.setTextColor(context.getResources().getColor(typeColor));
    }


    /**
     * 没有数据的情况
     */
    public static void emptytint(HistoryServiceEnum enm, ImageView imageView, TextView textView) {
        int err = 0;
        String title = "";
        switch (enm) {
            case DEPOSIT:
                err = R.mipmap.icon_history_deposite_err;
                title = ResHelper.getString(R.string.str_history_deposit_empty_tint);
                break;

            case WITHDRAW:
                err = R.mipmap.icon_history_withdrawal_err;
                title = ResHelper.getString(R.string.str_history_withdraw_empty_tint);
                break;

            case PROMOTION:
                err = R.mipmap.icon_history_discounts_err;
                title = ResHelper.getString(R.string.str_history_activity_empty_tint);
                break;

            default:
                err = R.mipmap.icon_history_deposite_err;
                title = ResHelper.getString(R.string.str_history_deposit_empty_tint);
                break;
        }
        imageView.setBackgroundResource(err);
        textView.setText(title);
    }

    /**
     * 没有数据的情况
     */
    public static void getTitle(HistoryServiceEnum enm, TextView textView) {
        String title = "";
        switch (enm) {
            case DEPOSIT:
                title = ResHelper.getString(R.string.str_history_deposit_tint);
                break;

            case WITHDRAW:
                title = ResHelper.getString(R.string.str_history_withdraw_tint);
                break;

            case PROMOTION:
                title = ResHelper.getString(R.string.str_history_activity_tint);
                break;

            default:
                title = ResHelper.getString(R.string.str_history_deposit_tint);
                break;
        }
        textView.setText(title);
    }

}
