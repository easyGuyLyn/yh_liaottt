package com.nwf.sports.mvp.model;

/**
 * <p>类描述：领奖活动
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class MyPriceVo {

    public String actType; // 领奖类型
    public String title; //
    public String htmlUrl; // 对应 H5 接口
    public String iconUrl; // 对应图标
    public String remark; //

    @Override
    public String toString() {
        return "MyPriceVo{" +
                "actType=" + actType +
                ", title='" + title + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

}
