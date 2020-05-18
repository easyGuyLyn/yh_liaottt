package com.nwf.sports.mvp.model;

import java.util.List;

/**
 * Created by Nereus on 2017/6/8.
 */
public class RegisterResult extends TokenResult{


    /**
     * recommendAccount : ["vle09824js","vle09824bl","vle09824bp"]
     * flag : false
     */

    private boolean flag;
    private List<String> recommendAccount;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getRecommendAccount() {
        return recommendAccount;
    }

    public void setRecommendAccount(List<String> recommendAccount) {
        this.recommendAccount = recommendAccount;
    }
}
