package com.nwf.sports.mvp.model;

import java.util.List;

/**
 * Created by Nereus on 2017/6/20.
 */
public class WithdrawalHistoryResult
{

    /**
     * list : [{"bankAccountNo":"66694949949494969","amount":1000000,"flag":-3,"requestId":"E031906211115C1B001","bankAccountType":"借记卡","bankName":"平安银行","flagZH":"","remark":"\nASDASD\nASDASD\nASDASD","createDate":"2019-06-21 11:15:41"},{"bankAccountNo":"6565646646767679","amount":999014,"flag":-1,"requestId":"E031906191047C1B002","bankAccountType":"借记卡","bankName":"中国农业银行","flagZH":"取消","remark":"Canceled by vsimon999","createDate":"2019-06-19 10:47:54"},{"bankAccountNo":"6565646646767679","amount":999999,"flag":-1,"requestId":"E031906191047C1B001","bankAccountType":"借记卡","bankName":"中国农业银行","flagZH":"取消","remark":"Canceled by vsimon999","createDate":"2019-06-19 10:47:10"},{"bankAccountNo":"6565646646767679","amount":100000,"flag":-1,"requestId":"E031906191044C1B001","bankAccountType":"借记卡","bankName":"中国农业银行","flagZH":"取消","remark":"Canceled by vsimon999","createDate":"2019-06-19 10:44:43"},{"bankAccountNo":"6565646646767679","amount":100000,"flag":-1,"requestId":"E031906151216C1B001","bankAccountType":"借记卡","bankName":"中国农业银行","flagZH":"取消","remark":"Canceled by vsimon999","createDate":"2019-06-15 12:16:42"}]
     * total : 5
     */

    private int total;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * bankAccountNo : 66694949949494969
         * amount : 1000000
         * flag : -3
         * requestId : E031906211115C1B001
         * bankAccountType : 借记卡
         * bankName : 平安银行
         * flagZH :
         * remark :
         ASDASD
         ASDASD
         ASDASD
         * createDate : 2019-06-21 11:15:41
         */

        private String bankAccountNo;
        private float amount;
        private int flag;
        private String requestId;
        private String bankAccountType;
        private String bankName;
        private String flagZH;
        private String remark;
        private String createDate;

        public String getBankAccountNo() {
            return bankAccountNo;
        }

        public void setBankAccountNo(String bankAccountNo) {
            this.bankAccountNo = bankAccountNo;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getBankAccountType() {
            return bankAccountType;
        }

        public void setBankAccountType(String bankAccountType) {
            this.bankAccountType = bankAccountType;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getFlagZH() {
            return flagZH;
        }

        public void setFlagZH(String flagZH) {
            this.flagZH = flagZH;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
