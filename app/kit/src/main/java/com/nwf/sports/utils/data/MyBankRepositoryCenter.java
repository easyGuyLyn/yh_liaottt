package com.nwf.sports.utils.data;

import android.text.TextUtils;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.mvp.model.BankInfo;
import com.nwf.sports.mvp.model.MyBankResult;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>类描述： 银行卡数据管理
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/

public class MyBankRepositoryCenter {


    public MyBankRepositoryCenter() {

    }

    /**
     * 保存添加银行卡银行相关信息
     */
    public void saveAllBanks(List<BankInfo> allBankInfoResult) {
        Gson gson = new Gson();
        String banksString = "";
        try {
            banksString = gson.toJson(allBankInfoResult);
        } catch (Exception e) {
            banksString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.BANK_ALLMESSAGE, banksString);
        LogUtils.e("saveAllBanks ==" + SPTool.get(ConstantValue.BANK_ALLMESSAGE, ""));
    }

    /**
     * 获取添加银行卡银行相关信息
     *
     * @return
     */
    public List<BankInfo> getAllBanks() {
        List<BankInfo> allBankInfoResult = new ArrayList<>();
        String BankString = (String) SPTool.get(ConstantValue.BANK_ALLMESSAGE, "");
        if (!TextUtils.isEmpty(BankString)) {
            try {
                allBankInfoResult = new Gson().fromJson(BankString, new TypeToken<List<BankInfo>>() {
                }.getType());
            } catch (Exception e) {
                allBankInfoResult = new ArrayList<>();
                LogUtils.e("解析json异常");
                return allBankInfoResult;
            }
        }
        LogUtils.e("getMyBanks == " + allBankInfoResult.toString());
        return allBankInfoResult;
    }

    /**
     * 保存银行卡列表
     *
     * @param myBankResult
     */
    public void saveMyBanks(MyBankResult myBankResult) {
        Gson gson = new Gson();
        String banksString = "";
        try {
            banksString = gson.toJson(myBankResult);
        } catch (Exception e) {
            banksString = "";
            LogUtils.e("gson.toJson 异常");
        }
        SPTool.put(ConstantValue.BANKNUMBER_LIST, banksString);
        LogUtils.e("saveMyBanks ==" + SPTool.get(ConstantValue.BANKNUMBER_LIST, ""));
    }

    /**
     * 获取银行卡
     *
     * @return
     */
    public MyBankResult getMyBanks() {
        MyBankResult items = new MyBankResult();
        String BankString = (String) SPTool.get(ConstantValue.BANKNUMBER_LIST, "");
        if (!TextUtils.isEmpty(BankString)) {

            try {
                items = new Gson().fromJson(BankString, MyBankResult.class);
            } catch (Exception e) {
                items = new MyBankResult();
                LogUtils.e("解析json异常");
                return items;
            }

        }
        LogUtils.e("getMyBanks == " + items.toString());
        return items;
    }

    /**
     * 清空银行卡
     */
    public void deleteAll() {
        SPTool.remove(ConstantValue.BANKNUMBER_LIST);
    }


    /**
     * 保存银行卡张数
     *
     * @param number
     */
    public void saveBankNumber(int number) {
        SPTool.put(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.BANKNUMBER, number);
    }

    /**
     * 获取用户银行卡张数
     *
     * @return
     */
    public int getBankNumber() {
        return (int) SPTool.get(IMDataCenter.getInstance().getUserInfoBean().username + ConstantValue.BANKNUMBER, 0);
    }

}
