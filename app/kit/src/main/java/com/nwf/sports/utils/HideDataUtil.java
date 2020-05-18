package com.nwf.sports.utils;

import android.text.TextUtils;

/**
 * 隐秘数据工具类
 *
 * @version 1.0
 */
public class HideDataUtil {
    /**
     * 方法描述 隐藏银行卡号中间的字符串（使用*号）
     *
     * @param cardNo
     * @return
     * @date 2019年4月3日 上午10:37:00
     */
    public static String hideCardNo(String cardNo, int leftLength, int rightLength) {
        if (TextUtils.isEmpty(cardNo)) {
            return cardNo;
        }

        int length = cardNo.length();
        int beforeLength = leftLength;
        int afterLength = rightLength;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }

    /**
     * 方法描述 隐藏姓名中的名字
     *
     * @param name
     * @return
     * @date 2019年4月3日 上午10:38:51
     */
    public static String hideName(String name) {
        if (TextUtils.isEmpty(name)) {
            return name;
        }
        String substring = name.substring(0, 1);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < name.length() - 1; i++) {
            sb.append("*");
        }
        return substring + sb.toString();
    }
}
