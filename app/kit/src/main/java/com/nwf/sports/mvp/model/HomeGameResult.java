package com.nwf.sports.mvp.model;

import java.util.List;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-06-21
 * <p>修改人：Simon
 * <p>修改时间：2019-06-21
 * <p>修改备注：
 **/
public class HomeGameResult {


    /**
     * gmid : E03077
     * flag : 1
     * transferFlag : 1
     * gameProvider : 开元棋牌
     * gameItem : [{"gameProvider":"开元棋牌","gameId":"2666666","gameCode":"E03077","display":"0","englishName":"","chineseName":"测试手机","gameType":"1","gameStyle":null,"trial":"1","mobile":"0","isHot":"0","isNew":"0","isRecommend":"0","picture1":"1","filePath":"http://10.91.11.21/wms/uploads/images/boardgames/E03M/kyqp/2666666.png","isPromotion":"0"}]
     */

    private String gmid;
    private int flag;
    private int transferFlag;
    private String gameProvider;
    private List<GameItemBean> gameItem;

    public String getGmid() {
        return gmid;
    }

    public void setGmid(String gmid) {
        this.gmid = gmid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(int transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getGameProvider() {
        return gameProvider;
    }

    public void setGameProvider(String gameProvider) {
        this.gameProvider = gameProvider;
    }

    public List<GameItemBean> getGameItem() {
        return gameItem;
    }

    public void setGameItem(List<GameItemBean> gameItem) {
        this.gameItem = gameItem;
    }

    public static class GameItemBean {
        /**
         * gameProvider : 开元棋牌
         * gameId : 2666666
         * gameCode : E03077
         * display : 0
         * englishName :
         * chineseName : 测试手机
         * gameType : 1
         * gameStyle : null
         * trial : 1
         * mobile : 0
         * isHot : 0
         * isNew : 0
         * isRecommend : 0
         * picture1 : 1
         * filePath : http://10.91.11.21/wms/uploads/images/boardgames/E03M/kyqp/2666666.png
         * isPromotion : 0
         */

        private String gameProvider;
        private String gameId;
        private String gameCode;
        private String display;
        private String englishName;
        private String chineseName;
        private String gameType;
        private Object gameStyle;
        private String trial;
        private String mobile;
        private String isHot;
        private String isNew;
        private String isRecommend;
        private String picture1;
        private String filePath;
        private String isPromotion;
        private String gmid; //厅id  用于推荐游戏
        private int flag; //记录推荐游戏是否维护

        public String getGameProvider() {
            return gameProvider;
        }

        public void setGameProvider(String gameProvider) {
            this.gameProvider = gameProvider;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public Object getGameStyle() {
            return gameStyle;
        }

        public void setGameStyle(Object gameStyle) {
            this.gameStyle = gameStyle;
        }

        public String getTrial() {
            return trial;
        }

        public void setTrial(String trial) {
            this.trial = trial;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public String getIsNew() {
            return isNew;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public String getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(String isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getPicture1() {
            return picture1;
        }

        public void setPicture1(String picture1) {
            this.picture1 = picture1;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getIsPromotion() {
            return isPromotion;
        }

        public void setIsPromotion(String isPromotion) {
            this.isPromotion = isPromotion;
        }

        public String getGmid() {
            return gmid;
        }

        public void setGmid(String gmid) {
            this.gmid = gmid;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}
