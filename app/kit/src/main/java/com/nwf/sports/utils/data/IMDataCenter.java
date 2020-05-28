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

public class IMDataCenter {

    private String game_token = "";
    private String game_u2token = "";
    private String loginName = "";
    private String productId = "";


    public String getGame_token() {
        return game_token;
    }

    public void setGame_token(String game_token) {
        this.game_token = game_token;
    }

    public String getGame_u2token() {
        return game_u2token;
    }

    public void setGame_u2token(String game_u2token) {
        this.game_u2token = game_u2token;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    private static IMDataCenter instance;
    private UserInfoCenter userInfoCenter = new UserInfoCenter(); //用户数据
    private MyBankRepositoryCenter repository = new MyBankRepositoryCenter(); //银行卡数据
    private MyLocalCenter mMyLocalCenter = new MyLocalCenter(); //本地数据

    public static IMDataCenter getInstance() {
        if (instance == null) {
            synchronized (IMDataCenter.class) {
                if (instance == null) {
                    instance = new IMDataCenter();
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


}
