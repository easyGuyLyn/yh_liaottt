package com.nwf.sports.mvp.model;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2020-02-04
 * <p>修改人：Simon
 * <p>修改时间：2020-02-04
 * <p>修改备注：
 **/
public class HomeRedPacketGameBean {
    public int type; //0福利红包  1扫雷  2牛牛红包
    public int bg;

    public HomeRedPacketGameBean(int type, int bg) {
        this.type = type;
        this.bg = bg;
    }
}
