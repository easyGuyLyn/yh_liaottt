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
public class RedPacketDetailResult {


    /**
     * userName : null
     * grabMoney : 0
     * senderUserId : VcW4V4GG
     * senderUserPortrait : http://10.91.6.19:80/fs/5/2020/01/30/16/8c2cf3fdbb1547f9bfc6d2cfbda2f4b7
     * sendUserDisplayName : 用户9093
     * sendMoney : 12.0
     * sendNumber : 1
     * receiveAmount : 12.0
     * receiveNumber : 1
     * sendTitle : 恭喜发财，大吉大利
     * specialNumber : 0
     * grabMoneyStr :
     * nnDesc :
     * flag : 2
     * sendType : 4
     * bankerWinTotal : 0
     * playerWinTotal : 0
     * packetReceiveLogVoList : [{"id":"a395b6116a1440d1a987d8493e37aae5","productId":"E03","userName":"vhunter666","sendTitle":null,"packetId":"9070b06fd66d4d4a8c4486042876309f","amount":12,"sendType":4,"createdTime":"2020-02-10T14:51:34.000+0000","isBest":"true","isThunder":"","otherRewards":"","otherRewardsMoney":0,"displayName":"用户9093","userId":"VcW4V4GG","portrait":"http://10.91.6.19:80/fs/5/2020/01/30/16/8c2cf3fdbb1547f9bfc6d2cfbda2f4b7","createDateStr":"2020-02-10 22:51:34","amountStr":"12.00","nnDesc":"牛2"}]
     */

    private String userName;
    private String grabMoney;
    private String senderUserId;
    private String senderUserPortrait;
    private String sendUserDisplayName;
    private String sendMoney;
    private String sendNumber;
    private String receiveAmount;
    private String receiveNumber;
    private String sendTitle;
    private String specialNumber;
    private String grabMoneyStr;
    private String nnDesc;
    private int flag;
    private int sendType;
    private String bankerWinTotal;
    private String playerWinTotal;
    private List<PacketReceiveLogVoListBean> packetReceiveLogVoList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGrabMoney() {
        return grabMoney;
    }

    public void setGrabMoney(String grabMoney) {
        this.grabMoney = grabMoney;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderUserPortrait() {
        return senderUserPortrait;
    }

    public void setSenderUserPortrait(String senderUserPortrait) {
        this.senderUserPortrait = senderUserPortrait;
    }

    public String getSendUserDisplayName() {
        return sendUserDisplayName;
    }

    public void setSendUserDisplayName(String sendUserDisplayName) {
        this.sendUserDisplayName = sendUserDisplayName;
    }

    public String getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(String sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber;
    }

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(String receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public String getSendTitle() {
        return sendTitle;
    }

    public void setSendTitle(String sendTitle) {
        this.sendTitle = sendTitle;
    }

    public String getSpecialNumber() {
        return specialNumber;
    }

    public void setSpecialNumber(String specialNumber) {
        this.specialNumber = specialNumber;
    }

    public String getGrabMoneyStr() {
        return grabMoneyStr;
    }

    public void setGrabMoneyStr(String grabMoneyStr) {
        this.grabMoneyStr = grabMoneyStr;
    }

    public String getNnDesc() {
        return nnDesc;
    }

    public void setNnDesc(String nnDesc) {
        this.nnDesc = nnDesc;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getBankerWinTotal() {
        return bankerWinTotal;
    }

    public void setBankerWinTotal(String bankerWinTotal) {
        this.bankerWinTotal = bankerWinTotal;
    }

    public String getPlayerWinTotal() {
        return playerWinTotal;
    }

    public void setPlayerWinTotal(String playerWinTotal) {
        this.playerWinTotal = playerWinTotal;
    }

    public List<PacketReceiveLogVoListBean> getPacketReceiveLogVoList() {
        return packetReceiveLogVoList;
    }

    public void setPacketReceiveLogVoList(List<PacketReceiveLogVoListBean> packetReceiveLogVoList) {
        this.packetReceiveLogVoList = packetReceiveLogVoList;
    }

    public static class PacketReceiveLogVoListBean {
        /**
         * id : a395b6116a1440d1a987d8493e37aae5
         * productId : E03
         * userName : vhunter666
         * sendTitle : null
         * packetId : 9070b06fd66d4d4a8c4486042876309f
         * amount : 12.0
         * sendType : 4
         * createdTime : 2020-02-10T14:51:34.000+0000
         * isBest : true
         * isThunder :
         * otherRewards :
         * otherRewardsMoney : 0
         * displayName : 用户9093
         * userId : VcW4V4GG
         * portrait : http://10.91.6.19:80/fs/5/2020/01/30/16/8c2cf3fdbb1547f9bfc6d2cfbda2f4b7
         * createDateStr : 2020-02-10 22:51:34
         * amountStr : 12.00
         * nnDesc : 牛2
         */

        private String id;
        private String productId;
        private String userName;
        private Object sendTitle;
        private String packetId;
        private String amount;
        private int sendType;
        private String createdTime;
        private boolean isBest;
        private boolean isThunder;
        private String otherRewards;
        private String otherRewardsMoney;
        private String displayName;
        private String userId;
        private String portrait;
        private String createDateStr;
        private String amountStr;
        private String nnDesc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getSendTitle() {
            return sendTitle;
        }

        public void setSendTitle(Object sendTitle) {
            this.sendTitle = sendTitle;
        }

        public String getPacketId() {
            return packetId;
        }

        public void setPacketId(String packetId) {
            this.packetId = packetId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public boolean getIsBest() {
            return isBest;
        }

        public void setIsBest(boolean isBest) {
            this.isBest = isBest;
        }

        public boolean getIsThunder() {
            return isThunder;
        }

        public void setIsThunder(boolean isThunder) {
            this.isThunder = isThunder;
        }

        public String getOtherRewards() {
            return otherRewards;
        }

        public void setOtherRewards(String otherRewards) {
            this.otherRewards = otherRewards;
        }

        public String getOtherRewardsMoney() {
            return otherRewardsMoney;
        }

        public void setOtherRewardsMoney(String otherRewardsMoney) {
            this.otherRewardsMoney = otherRewardsMoney;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getCreateDateStr() {
            return createDateStr;
        }

        public void setCreateDateStr(String createDateStr) {
            this.createDateStr = createDateStr;
        }

        public String getAmountStr() {
            return amountStr;
        }

        public void setAmountStr(String amountStr) {
            this.amountStr = amountStr;
        }

        public String getNnDesc() {
            return nnDesc;
        }

        public void setNnDesc(String nnDesc) {
            this.nnDesc = nnDesc;
        }
    }
}
