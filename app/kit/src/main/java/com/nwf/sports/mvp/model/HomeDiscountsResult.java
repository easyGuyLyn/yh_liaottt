package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>类描述： 首页
 * <p>创建人：Simon
 * <p>创建时间：2019-06-26
 * <p>修改人：Simon
 * <p>修改时间：2019-06-26
 * <p>修改备注：
 **/
public class HomeDiscountsResult implements Parcelable {

    /**
     * banner {
     * actionUrl (string, optional): 跳转的HTML5 页面地址 ,
     * content (string, optional): 内容 ,
     * index (integer, optional): 排序 ,
     * url (string, optional): 图片地址
     * }
     * 优惠实体 {
     * actionUrl (string, optional): 跳转HTML5 页面 ,
     * content (string, optional): 内容 ,
     * index (integer, optional): 排序的位置 ,
     * promotionsBeginTime (string, optional): 优惠开始时间 ,
     * promotionsEndTime (string, optional): 结束时间 ,
     * title (string, optional): 标题 ,
     * type (string, optional): 优惠类型 ,
     * url (string, optional): 图片地址
     * }
     */
    private List<BannerListBean> bannerList = new ArrayList<>();
    private List<PromotionsListBean> promotionsList = new ArrayList<>();

    public HomeDiscountsResult() {

    }

    protected HomeDiscountsResult(Parcel in) {
        promotionsList = in.createTypedArrayList(PromotionsListBean.CREATOR);
    }

    public static final Creator<HomeDiscountsResult> CREATOR = new Creator<HomeDiscountsResult>() {
        @Override
        public HomeDiscountsResult createFromParcel(Parcel in) {
            return new HomeDiscountsResult(in);
        }

        @Override
        public HomeDiscountsResult[] newArray(int size) {
            return new HomeDiscountsResult[size];
        }
    };

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<PromotionsListBean> getPromotionsList() {
        return promotionsList;
    }

    public void setPromotionsList(List<PromotionsListBean> promotionsList) {
        this.promotionsList = promotionsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(promotionsList);
    }

    public static class BannerListBean {
        /**
         * url : https://e03-static.czsjnp.com/static/E03P/_default/__static/__images/banner/banner_ag20190628.jpg
         * content : banner1
         * index : 1
         * actionUrl : https://f6601.com/slot.htm?type=pplhj
         */

        private String url;
        private String content;
        private int index;
        private String actionUrl;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }
    }

    public static class PromotionsListBean implements Parcelable {
        /**
         * index : 1
         * url : https://e03-static.czsjnp.com/static/E03P/_default/__static/__images/promotions/img_s/prom_phone20190627.jpg
         *  "minImgUrl":"https://e03-static.czsjnp.com/static/E03P/_default/__static/__images/promotions/img_s/prom_phone20190627.jpg",
         * title : 永乐力挺HUAWEI
         * content : 存款就送
         * type : null
         * promotionsBeginTime : 201906011
         * promotionsEndTime : 201906027
         * actionUrl : http://bbs.happy887.net/forum.php?mod=viewthread&tid=689938&page=1&extra=&_dsign=b24e2a77
         */

        private int index;
        private String url;
        private String minImgUrl;
        private String title;
        private String content;
        private String type;
        private String promotionsBeginTime;
        private String promotionsEndTime;
        private String actionUrl;

        public PromotionsListBean() {

        }

        protected PromotionsListBean(Parcel in) {
            index = in.readInt();
            url = in.readString();
            title = in.readString();
            content = in.readString();
            promotionsBeginTime = in.readString();
            promotionsEndTime = in.readString();
            actionUrl = in.readString();
        }

        public static final Creator<PromotionsListBean> CREATOR = new Creator<PromotionsListBean>() {
            @Override
            public PromotionsListBean createFromParcel(Parcel in) {
                return new PromotionsListBean(in);
            }

            @Override
            public PromotionsListBean[] newArray(int size) {
                return new PromotionsListBean[size];
            }
        };

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPromotionsBeginTime() {
            return promotionsBeginTime;
        }

        public void setPromotionsBeginTime(String promotionsBeginTime) {
            this.promotionsBeginTime = promotionsBeginTime;
        }

        public String getPromotionsEndTime() {
            return promotionsEndTime;
        }

        public void setPromotionsEndTime(String promotionsEndTime) {
            this.promotionsEndTime = promotionsEndTime;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public String getMinImgUrl() {
            return minImgUrl;
        }

        public void setMinImgUrl(String minImgUrl) {
            this.minImgUrl = minImgUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(index);
            dest.writeString(url);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeString(promotionsBeginTime);
            dest.writeString(promotionsEndTime);
            dest.writeString(actionUrl);
        }
    }
}
