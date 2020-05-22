package com.nwf.sports.mvp.model;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-01-28
 * <p>修改人：Simon
 * <p>修改时间：2020-01-28
 * <p>修改备注：
 **/
public class RedPacketGameListDetailsResult {


    /**
     * groupId : AOBAAAHH
     * userId : puqYpYAA
     * canInGroup : false
     * inGroup : true
     */

    private String groupId;
    private String userId;
    private boolean canInGroup;
    private boolean inGroup;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
