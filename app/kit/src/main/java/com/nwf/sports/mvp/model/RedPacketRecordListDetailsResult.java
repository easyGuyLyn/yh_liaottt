package com.nwf.sports.mvp.model;

import java.util.List;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-28
 * <p>修改人：Simon
 * <p>修改时间：2020-01-28
 * <p>修改备注：
 **/
public class RedPacketRecordListDetailsResult {


    /**
     * userBalanceResultList : [{"userName":"vhunter68","balanceTitle":"来自用户2501的红包","balanceTime":"2020-02-13 22:08:00","amount":2.99,"redPacketId":"0fce3b13de9446f1b2f8eea34acbde2e"},{"userName":"vhunter68","balanceTitle":"给群聊天吹水，10点到12点的红包","balanceTime":"2020-02-13 22:07:59","amount":-3,"redPacketId":"0fce3b13de9446f1b2f8eea34acbde2e"},{"userName":"vhunter68","balanceTitle":"来自用户2501的红包","balanceTime":"2020-02-13 22:07:27","amount":2.8,"redPacketId":"8e2bdbb7aeb24df595afd143af2e1b78"},{"userName":"vhunter68","balanceTitle":"给群用户9093,用户2501等的红包","balanceTime":"2020-02-13 22:07:24","amount":-3,"redPacketId":"8e2bdbb7aeb24df595afd143af2e1b78"},{"userName":"vhunter666","balanceTitle":"来自用户9093的红包","balanceTime":"2020-02-13 22:04:48","amount":0.28,"redPacketId":"5a0064d472344bcfb5e63e5ed685faf6"},{"userName":"vhunter666","balanceTitle":"给群用户9093,用户2501等的红包","balanceTime":"2020-02-13 21:52:09","amount":-1,"redPacketId":"5a0064d472344bcfb5e63e5ed685faf6"},{"userName":"vhunter666","balanceTitle":"给群用户9093,用户2501等的红包","balanceTime":"2020-02-13 21:47:23","amount":-2,"redPacketId":"c94c0bfd3cbe4adeb8a29c176c708f28"},{"userName":"vhunter666","balanceTitle":"给群用户9093,用户2501等的红包","balanceTime":"2020-02-13 21:45:05","amount":-2,"redPacketId":"8abefc1a817443e0aaa11428fa13b7bf"},{"userName":"vhunter666","balanceTitle":"给群开始扫雷了，老哥们的红包","balanceTime":"2020-02-13 21:38:46","amount":-2,"redPacketId":"d2fc48371c0345fda77e0b710ce3b694"},{"userName":"vhunter666","balanceTitle":"给群聊天吹水，10点到12点的红包","balanceTime":"2020-02-13 20:53:42","amount":-1,"redPacketId":"3d2049d61d294c3eb0bbc8c48c783c60"}]
     * pageNo : 1
     * pageSize : 20
     * totalSize : 10
     */

    private int pageNo;
    private int pageSize;
    private int totalSize;
    private List<UserBalanceResultListBean> userBalanceResultList;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<UserBalanceResultListBean> getUserBalanceResultList() {
        return userBalanceResultList;
    }

    public void setUserBalanceResultList(List<UserBalanceResultListBean> userBalanceResultList) {
        this.userBalanceResultList = userBalanceResultList;
    }

    public static class UserBalanceResultListBean {
        /**
         * userName : vhunter68
         * balanceTitle : 来自用户2501的红包
         * balanceTime : 2020-02-13 22:08:00
         * amount : 2.99
         * redPacketId : 0fce3b13de9446f1b2f8eea34acbde2e
         */

        private String userName;
        private String balanceTitle;
        private String balanceTime;
        private double amount;
        private String redPacketId;
        private int sendType;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBalanceTitle() {
            return balanceTitle;
        }

        public void setBalanceTitle(String balanceTitle) {
            this.balanceTitle = balanceTitle;
        }

        public String getBalanceTime() {
            return balanceTime;
        }

        public void setBalanceTime(String balanceTime) {
            this.balanceTime = balanceTime;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(String redPacketId) {
            this.redPacketId = redPacketId;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }
    }
}
