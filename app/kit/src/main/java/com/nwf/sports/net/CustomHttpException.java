package com.nwf.sports.net;

import android.content.Context;

import com.nwf.sports.IMApplication;
import com.ivi.imsdk.R;

/**
 * Created by benson on 18-1-8.
 */

public class CustomHttpException extends RuntimeException {

    public CustomHttpException(int code) {
        throw new RuntimeException(getCustomHttpException(code));
    }

    public static String getCustomHttpException(int code) {
        Context context = IMApplication.getContext();
        switch (code) {
            case -1:
                return context.getString(R.string.http_code_network);
            default:
        }
        return context.getString(R.string.http_code_default, code);
    }


}
