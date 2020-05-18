package com.nwf.sports.mvp.model;

/**
 * <p>类描述： APP更新
 * <p>创建人：Simon
 * <p>创建时间：2019-06-26
 * <p>修改人：Simon
 * <p>修改时间：2019-06-26
 * <p>修改备注：
 **/
public class CheckupgradeResult {

    /**
     * terminal (string, optional): 系统分类 ,
     * title (string, optional): apk标题 ,
     * titleUrl (string, optional): apk下载地址 ,
     * upgrade (integer, optional): 是否强制升级 1是 0否 ,
     * upgradeNote (string, optional): 升级apk日志 ,
     * versionCode (string, optional): apk版本号 ,
     * versionName (string, optional): 版本名称
     */

    private String title;
    private String titleUrl;
    private int upgrade;
    private int versionCode;
    private String upgradeNote;
    private String versionName;
    private String terminal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpgradeNote() {
        return upgradeNote;
    }

    public void setUpgradeNote(String upgradeNote) {
        this.upgradeNote = upgradeNote;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
