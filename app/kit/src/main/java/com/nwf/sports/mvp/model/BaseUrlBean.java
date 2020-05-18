package com.nwf.sports.mvp.model;

/**
 * <p>类描述： 用于储存基础地址的Bean
 * <p>创建人：Simon
 * <p>创建时间：2018-11-27
 * <p>修改人：Simon
 * <p>修改时间：2018-11-27
 * <p>修改备注：
 **/
public class BaseUrlBean {
    private String url;
    private String name;
    private String imurl;

    public BaseUrlBean(String url, String name, String imurl) {
        this.url = url;
        this.name = name;
        this.imurl = imurl;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
