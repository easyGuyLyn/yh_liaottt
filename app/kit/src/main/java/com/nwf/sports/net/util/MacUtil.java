package com.nwf.sports.net.util;

import com.nwf.sports.net.request.AppTextMessageRequest;

/**
 * Created by Nereus on 2017/8/24.
 */

public class MacUtil {

    public static String generateMac(AppTextMessageRequest messageRequest)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(messageRequest.getAppRefer());
        builder.append(messageRequest.getDigiSign());
        builder.append(messageRequest.getEncryptType());
        builder.append(messageRequest.getPid());
        builder.append(messageRequest.getTimestamp());
        builder.append(messageRequest.getLocale());
        builder.append(messageRequest.getDeviceId());
        if(null != messageRequest.getData())
        {
            builder.append((String)messageRequest.getData());
        }
        String tokenmd5 = Md5Utils.getMd5(messageRequest.getToken());
        builder.append(tokenmd5);
        String mac = Md5Utils.getMd5(builder.toString());
        //Timber.d("mac:%s",mac);
        return mac;
    }
}
