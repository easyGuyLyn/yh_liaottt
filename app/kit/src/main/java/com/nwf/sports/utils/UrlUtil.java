package com.nwf.sports.utils;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-07-23
 * <p>修改人：Simon
 * <p>修改时间：2019-07-23
 * <p>修改备注：
 **/
public class UrlUtil {

    /**
     * AS 真人棋牌 type
     *   exitGame  12
     *   regiesterRealAccount 9
     *   gameRule 7
     *   customerService 13
     *   accountPromotion 10
     *   accountRecord 1
     *   deposit 6
     *   peraterRecord 2
     *   updatePersonalData 3
     *   withdraw 4
     */

    /***
     * 获取url 指定name的value;
     * @param url
     * @param name
     * @return
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

}
