package com.nwf.sports.mvp.model;

/**
 * Created by ak on 2017/8/23.
 */

public class NEnterGameResult {

    /**
     * gameUrl : http://walletapi.agg079.com/mgForwardGame.do?params=4uPSJOXhSI5NbmN89S9nT8zJQwUjDozPS99LFPpk97nnAE8Wpk0lKbhLWyToTWj2GC1nYC9/J3Pfvphb7sCX2YK25miH3QbnmsoCBlRQSiSQpYpJnZuxee8YkWhLOn6/5f36tL1Q30Xstc/Gc5Nr1RDues3QgKvQjRctdgaavFqXIQWHl94IhA==
     * type : post
     */

    private String gameUrl;
    private String type;
    private String uuId;
    private String ptKey ;
    private String ptMd5;
    private String ptGameKey ;
    private String ptLoginName;

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getPtKey() {
        return ptKey;
    }

    public void setPtKey(String ptKey) {
        this.ptKey = ptKey;
    }

    public String getPtMd5() {
        return ptMd5;
    }

    public void setPtMd5(String ptMd5) {
        this.ptMd5 = ptMd5;
    }

    public String getPtGameKey() {
        return ptGameKey;
    }

    public void setPtGameKey(String ptGameKey) {
        this.ptGameKey = ptGameKey;
    }

    public String getPtLoginName() {
        return ptLoginName;
    }

    public void setPtLoginName(String ptLoginName) {
        this.ptLoginName = ptLoginName;
    }
}
