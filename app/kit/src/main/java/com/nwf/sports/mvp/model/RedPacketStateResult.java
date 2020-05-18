package com.nwf.sports.mvp.model;


/**
 * <p>类描述： 查询红包状态
 * <p>创建人：Simon
 * <p>创建时间：2019-03-26
 * <p>修改人：Simon
 * <p>修改时间：2019-03-26
 * <p>修改备注：
 **/
public class RedPacketStateResult {


    /**
     * packetId : 4bbf98ec1c3e41aeb04691818e0581eb
     * productId : E03
     * userName : vhunter666
     * sendTitle : 恭喜发财，大吉大利
     * sendAmount : 1.0
     * sendNumber : 2
     * sendType : 0
     * groupId : q1rcqcII
     * flag : 0   状态（0可用，1过期，2已抢完）
     * grabPacketKey : null
     * pageNo : null
     * pageSize : null
     * receiveAmount : 0.12
     * receiveNumber : 1
     */

    private String packetId;
    private String productId;
    private String userName;
    private String sendTitle;
    private String sendAmount;
    private String sendNumber;
    private String sendType;
    private String groupId;
    private int flag;
    private String grabPacketKey;
    private String pageNo;
    private String pageSize;
    private String receiveAmount;
    private String receiveNumber;


    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
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

    public String getSendTitle() {
        return sendTitle;
    }

    public void setSendTitle(String sendTitle) {
        this.sendTitle = sendTitle;
    }

    public String getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(String sendAmount) {
        this.sendAmount = sendAmount;
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getGrabPacketKey() {
        return grabPacketKey;
    }

    public void setGrabPacketKey(String grabPacketKey) {
        this.grabPacketKey = grabPacketKey;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
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
}
