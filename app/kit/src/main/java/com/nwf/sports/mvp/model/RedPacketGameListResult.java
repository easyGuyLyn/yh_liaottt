package com.nwf.sports.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-28
 * <p>修改人：Simon
 * <p>修改时间：2020-01-28
 * <p>修改备注：
 **/
public class RedPacketGameListResult implements Serializable {


    /**
     * redPacketGroupVoList : [{"id":"djeifjewfsfdsf","productId":"E03","groupId":"AOBAAAHH","groupType":0,"groupName":"这种","groupNotice":"聊天吹水，10点到12点","groupRemark":"发包流水1000抢包流水500即可进群","minJoinMoney":100,"minMoney":100,"maxMoney":900,"odds":1.8,"redpacketNumber":8,"createdTime":"2020-02-03T13:30:47.000+0000","createdBy":"admin","updatedTime":"2020-02-03T13:30:47.000+0000","updatedBy":"admin","flag":0,"portrait":"http://10.91.6.19:80/fs/5/2020/01/28/20/1e5a2fe0fa6f42d99122b5457900f480","userId":null,"canInGroup":false,"inGroup":false}]
     * pageIndex : 0
     * pageSize : 0
     * totalSize : 0
     */

    private int pageIndex;
    private int pageSize;
    private int totalSize;
    private List<RedPacketGroupVoListBean> redPacketGroupVoList;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    public List<RedPacketGroupVoListBean> getRedPacketGroupVoList() {
        return redPacketGroupVoList;
    }

    public void setRedPacketGroupVoList(List<RedPacketGroupVoListBean> redPacketGroupVoList) {
        this.redPacketGroupVoList = redPacketGroupVoList;
    }

    public static class RedPacketGroupVoListBean implements Serializable {
        /**
         * id : djeifjewfsfdsf
         * productId : E03
         * groupId : AOBAAAHH
         * groupType : 0
         * groupName : 这种
         * groupNotice : 聊天吹水，10点到12点
         * groupRemark : 发包流水1000抢包流水500即可进群
         * minJoinMoney : 100.0
         * minMoney : 100.0
         * maxMoney : 900.0
         * odds : 1.8
         * redpacketNumber : 8
         * createdTime : 2020-02-03T13:30:47.000+0000
         * createdBy : admin
         * updatedTime : 2020-02-03T13:30:47.000+0000
         * updatedBy : admin
         * flag : 0
         * portrait : http://10.91.6.19:80/fs/5/2020/01/28/20/1e5a2fe0fa6f42d99122b5457900f480
         * userId : null
         * canInGroup : false
         * inGroup : false
         */

        private String id;
        private String productId;
        private String groupId;
        private int groupType;
        private String groupName;
        private String groupNotice;
        private String groupRemark;
        private double minJoinMoney;
        private double minMoney;
        private double maxMoney;
        private double odds;
        private int redpacketNumber;
        private String createdTime;
        private String createdBy;
        private String updatedTime;
        private String updatedBy;
        private int flag;
        private String portrait;
        private String userId;
        private boolean canInGroup;
        private boolean inGroup;

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

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupNotice() {
            return groupNotice;
        }

        public void setGroupNotice(String groupNotice) {
            this.groupNotice = groupNotice;
        }

        public String getGroupRemark() {
            return groupRemark;
        }

        public void setGroupRemark(String groupRemark) {
            this.groupRemark = groupRemark;
        }

        public double getMinJoinMoney() {
            return minJoinMoney;
        }

        public void setMinJoinMoney(double minJoinMoney) {
            this.minJoinMoney = minJoinMoney;
        }

        public double getMinMoney() {
            return minMoney;
        }

        public void setMinMoney(double minMoney) {
            this.minMoney = minMoney;
        }

        public double getMaxMoney() {
            return maxMoney;
        }

        public void setMaxMoney(double maxMoney) {
            this.maxMoney = maxMoney;
        }

        public double getOdds() {
            return odds;
        }

        public void setOdds(double odds) {
            this.odds = odds;
        }

        public int getRedpacketNumber() {
            return redpacketNumber;
        }

        public void setRedpacketNumber(int redpacketNumber) {
            this.redpacketNumber = redpacketNumber;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isCanInGroup() {
            return canInGroup;
        }

        public void setCanInGroup(boolean canInGroup) {
            this.canInGroup = canInGroup;
        }

        public boolean isInGroup() {
            return inGroup;
        }

        public void setInGroup(boolean inGroup) {
            this.inGroup = inGroup;
        }
    }
}
