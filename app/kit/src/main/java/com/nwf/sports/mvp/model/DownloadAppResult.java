package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class DownloadAppResult {


    /**
     * apps : [{"apkname":"https://down.ag8soft.com/e04/agin.apk","appname":"AG国际厅","description":"好路投注把握盈利机会","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/AG_setup.png","packagename":"com.aggaming.androidapp"},{"apkname":"http://e03phone.cs326.com/E03/android/AGQJCasino.apk","appname":"AG旗舰厅","description":"先发六张牌公平百家乐","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/AGQJCasino.png","packagename":"com.iv.baccaratE03"},{"apkname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/apk/yonglefish.apk","appname":"永乐捕鱼","description":"多捕多赢，常捕长乐","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/yonglefish.png","packagename":"com.nwf.fish"},{"apkname":"http://hunter2.agmjs.com/download/v1/fishhunter2.apk","appname":"捕鱼王2","description":"频爆大奖天天捕天天赢","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/fishhunter2.png","packagename":"com.qunl.Hunter"},{"apkname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/apk/Client.apk","appname":"PT电子游艺","description":"上亿奖池，奖金喷发中","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/Client.png","packagename":"com.playtech.ngm.nativeclient.luckydragon.greatfortune88"},{"apkname":"http://e03phone.azureorg.com/E03/android/lottery/Lottery.apk","appname":"AG快乐彩","description":"主流彩种，玩法丰富","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/Lottery.png","packagename":"com.iv.lotteryE03"},{"apkname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/apk/MobileCasino.apk","appname":"MG电子游艺","description":"经典老虎机好玩好盈利","iconname":"http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/MobileCasino.png","packagename":"air.com.sempris.mobileair"}]
     * title : 游戏app下载
     */

    public String title;
    public ArrayList<AppsBean> apps = new ArrayList<>();
    public String imgUrl;

    public DownloadAppResult(){
        apps=new ArrayList<>();
        title="";
        imgUrl="";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AppsBean> getApps() {
        if (null != apps && apps.size() != 0) {
            return apps;
        } else {
            return apps = new ArrayList<>();
        }
    }

    public void setApps(ArrayList<AppsBean> apps) {
        this.apps = apps;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static class AppsBean implements Parcelable {
        /**
         * apkname : https://down.ag8soft.com/e04/agin.apk
         * appname : AG国际厅
         * description : 好路投注把握盈利机会
         * iconname : http://uatsrsapi-e03.agg013.com/app/nwf/android/downloadapps/icon/AG_setup.png
         * packagename : com.aggaming.androidapp
         */

        private String apkname;
        private String appname;
        private String description;
        private String iconname;
        private String packagename;
        private String condition = "";//下载状态  1下载中 2下载完成 3已安装
        private String location = ""; //本地地址

        protected AppsBean(Parcel in) {
            apkname = in.readString();
            appname = in.readString();
            description = in.readString();
            iconname = in.readString();
            packagename = in.readString();
            condition = in.readString();
            location = in.readString();
        }

        public static final Creator<AppsBean> CREATOR = new Creator<AppsBean>() {
            @Override
            public AppsBean createFromParcel(Parcel in) {
                return new AppsBean(in);
            }

            @Override
            public AppsBean[] newArray(int size) {
                return new AppsBean[size];
            }
        };

        public String getApkname() {
            return apkname;
        }

        public void setApkname(String apkname) {
            this.apkname = apkname;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconname() {
            if (TextUtils.isEmpty(iconname)){
                return "";
            }
            return iconname;
        }

        public void setIconname(String iconname) {
            this.iconname = iconname;
        }

        public String getPackagename() {
            return packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public String getCondition() {
            if (TextUtils.isEmpty(condition)) {
                return "";
            } else {
                return condition;
            }
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getLocation() {
            if (TextUtils.isEmpty(location)) {
                return "";
            } else {
                return location;
            }
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(apkname);
            dest.writeString(appname);
            dest.writeString(description);
            dest.writeString(iconname);
            dest.writeString(packagename);
            dest.writeString(condition);
            dest.writeString(location);
        }
    }
}
