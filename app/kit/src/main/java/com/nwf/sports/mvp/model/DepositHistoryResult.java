package com.nwf.sports.mvp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class DepositHistoryResult
{

    /**
     * list : [{"orderNo":"E0319062111192375","commitTime":"2019-06-21","amount":200,"orderFlag":0},{"orderNo":"E0319062111191988","commitTime":"2019-06-21","amount":200,"orderFlag":0},{"orderNo":"E0319062111190499","commitTime":"2019-06-21","amount":200,"orderFlag":0},{"orderNo":"E0319062111185179","commitTime":"2019-06-21","amount":100,"orderFlag":0},{"orderNo":"E0319062111184649","commitTime":"2019-06-21","amount":1000,"orderFlag":0},{"orderNo":"E0319062111184249","commitTime":"2019-06-21","amount":1000,"orderFlag":0},{"orderNo":"E0319062111183844","commitTime":"2019-06-21","amount":1000,"orderFlag":0},{"orderNo":"E0319062111183462","commitTime":"2019-06-21","amount":1000,"orderFlag":0},{"orderNo":"E0319062111182799","commitTime":"2019-06-21","amount":500,"orderFlag":0},{"orderNo":"E0319062111182180","commitTime":"2019-06-21","amount":500,"orderFlag":0}]
     * total : 27
     */

    private int total;
    private List<PayMentRecordListBean> list;

    public DepositHistoryResult(){
        list =new ArrayList<>();
    }

    public boolean isEmpty()
    {
        return null != list && !list.isEmpty();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PayMentRecordListBean> getList() {
        return list;
    }

    public void setList(List<PayMentRecordListBean> list) {
        this.list = list;
    }

    public static class PayMentRecordListBean {
        /**
         * orderNo : E0319062111192375
         * commitTime : 2019-06-21
         * amount : 200
         * orderFlag : 0   订单状态 0 - 初始状态（等待支付） 1 成功 2 拒绝 ,
         */

        private String orderNo;
        private String commitTime;
        private int amount;
        private int orderFlag;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getCommitTime() {
            return commitTime;
        }

        public void setCommitTime(String commitTime) {
            this.commitTime = commitTime;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getOrderFlag() {
            return orderFlag;
        }

        public void setOrderFlag(int orderFlag) {
            this.orderFlag = orderFlag;
        }
    }
}
