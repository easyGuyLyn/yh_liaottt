package com.dawoo.coretool.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.dawoo.coretool.util.packageref.Utils;

import androidx.annotation.StringRes;

/**
 * Created by benson on 17-12-28.
 */

public class ToastUtil
{
    public static void showResShort(@StringRes int stringId)
    {
        Context context = Utils.getContext();
        if (!commonCheck(context))
            return;
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Looper.prepare();
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void showResLong(@StringRes int stringId)
    {
        Context context = Utils.getContext();
        if (!commonCheck(context))
            return;
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Looper.prepare();
            Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    public static void showToastShort(String msg)
    {
        Context context = Utils.getContext();
        if (!commonCheck(context))
            return;
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }

    public static void showToastLong(String msg)
    {
        Context context = Utils.getContext();
        if (!commonCheck(context))
            return;
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    private static boolean commonCheck(Context context)
    {
        if (context == null)
        {
            return false;

        }
        if (context instanceof Activity)
        {
            if (((Activity) context).isFinishing())
            {
                return false;
            }
            if (((Activity) context).isDestroyed())
            {
                return false;
            }
        }
        return true;
    }

}
