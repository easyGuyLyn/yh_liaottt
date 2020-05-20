package cn.wildfire.chat.kit.net.base;

/**
 * Created by imndx on 2017/12/16.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.Keep;

/**
 * 用来表示result的状态，上层基本不用关注
 */

@Keep
public class StatusResult {


    @SerializedName("head")
    private Head head;


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    @Keep
    public static class Head implements Serializable {

        @SerializedName("cost")
        private double cost;

        @SerializedName("errCode")
        private String errCode;

        @SerializedName("errMsg")
        private String errMsg;


        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }


        @Override
        public String toString() {
            return "Head{" +
                    "cost=" + cost +
                    ", errCode='" + errCode + '\'' +
                    ", errMsg='" + errMsg + '\'' +
                    '}';
        }
    }
}
