package com.nwf.sports.utils.data;

import com.nwf.sports.mvp.model.UserInfoBean;

/**
 * <p>类描述： 数据中心
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/

public class DataCenter {
    private static DataCenter instance;
    private UserInfoCenter userInfoCenter = new UserInfoCenter(); //用户数据
    private MyBankRepositoryCenter repository = new MyBankRepositoryCenter(); //银行卡数据
    private MyLocalCenter mMyLocalCenter = new MyLocalCenter(); //本地数据
    private String Cookie;

    public static DataCenter getInstance() {
        if (instance == null) {
            synchronized (DataCenter.class) {
                if (instance == null) {
                    instance = new DataCenter();
                }
            }
        }
        return instance;
    }

    public UserInfoCenter getUserInfoCenter() {
        return userInfoCenter;
    }

    public MyLocalCenter getMyLocalCenter() {
        return mMyLocalCenter;
    }

    public MyBankRepositoryCenter getMyBankRepositoryCenter() {
        return repository;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoCenter.getUserInfoBean();
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        userInfoCenter.setUserInfoBean(userInfoBean);
    }
    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

}
