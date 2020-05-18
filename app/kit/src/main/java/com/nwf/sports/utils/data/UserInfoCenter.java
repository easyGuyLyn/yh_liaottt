package com.nwf.sports.utils.data;

import android.text.TextUtils;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.google.gson.Gson;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.mvp.model.UserInfoBean;

/**
 * <p>类描述： 用户数据处理
 * <p>创建人：Simon
 * <p>创建时间：2019-03-21
 * <p>修改人：Simon
 * <p>修改时间：2019-03-21
 * <p>修改备注：
 **/
public class UserInfoCenter {


    /**
     * 获取UserInfo
     */
    public UserInfoBean getUserInfoBean() {
        UserInfoBean userInfoBean = new UserInfoBean();
        String userInfoBeanString = (String) SPTool.get(ConstantValue.USERINFO, "");
        if (!TextUtils.isEmpty(userInfoBeanString)) {
            try {
                userInfoBean = new Gson().fromJson(userInfoBeanString, UserInfoBean.class);
            } catch (Exception e) {
                userInfoBean = new UserInfoBean();
                LogUtils.e("解析json异常");
                return userInfoBean;
            }
        }
        LogUtils.e("UserInfoBean == " + userInfoBean.toString());
        return userInfoBean;
    }

    /**
     * 保存UserInfo
     */
    public void setUserInfoBean(UserInfoBean userInfoBean) {
        Gson gson = new Gson();
        String userInfoString = "";
        try {
            userInfoString = gson.toJson(userInfoBean);
        } catch (Exception e) {
            userInfoString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.USERINFO, userInfoString);
        LogUtils.e("UserInfoBean ==" + SPTool.get(ConstantValue.USERINFO, ""));
    }

    /**
     * 清除UserInfo
     */
    public void clearUserInfoBean() {
        SPTool.remove(ConstantValue.USERINFO);
    }

}
