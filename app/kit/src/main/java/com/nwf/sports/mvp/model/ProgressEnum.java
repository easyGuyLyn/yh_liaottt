package com.nwf.sports.mvp.model;

/**
 * Created by Nereus on 2017/6/23.
 */
public enum ProgressEnum {
    //正在处理中，即将被处理
    PENDING,
    //已通过审核
    APPROVAL,
    //已处理或者说系统已经发起转账，但不保证立即到账
    COMPLETE,
    //客户取消
    USER_CANCEL,
    //后台取消
    CANCEL,
    //系统拒绝
    REJECTED,
    INSPECT,    //一审 PENDING
    INSPECT_APPROVED,
    CANCELLED,
    CANCEL_CSO
}
