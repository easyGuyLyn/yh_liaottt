package com.nwf.sports.mvp.model;

/**
 * Created by ak on 2017/8/11.
 */

public class ServiceCallbackResult
{
    private ResultBean result;

    public ResultBean getResult()
    {
        return result;
    }

    public void setResult(ResultBean result)
    {
        this.result = result;
    }

    public static class ResultBean
    {
        /**
         * connectUrl : https://www.h88online.com/chat/chatClient/chatbox.jsp?companyID=8997&configID=8
         * phoneCallBackUrl : http://pbx-web-api.cdnv6.com:5000/web_api
         */

        private String connectUrl;
        private String phoneCallBackUrl;

        public String getConnectUrl()
        {
            return connectUrl;
        }

        public void setConnectUrl(String connectUrl)
        {
            this.connectUrl = connectUrl;
        }

        public String getPhoneCallBackUrl()
        {
            return phoneCallBackUrl;
        }

        public void setPhoneCallBackUrl(String phoneCallBackUrl)
        {
            this.phoneCallBackUrl = phoneCallBackUrl;
        }
    }
}
