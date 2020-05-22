package com.nwf.sports.chat;


import com.nwf.sports.chat.login.model.LoginResult;
import com.nwf.sports.chat.login.model.PCSession;
import com.nwf.sports.mvp.model.CreditQueryResult;
import com.nwf.sports.mvp.model.RedPacketDetailResult;
import com.nwf.sports.mvp.model.RedPacketGameListDetailsResult;
import com.nwf.sports.mvp.model.RedPacketGameListResult;
import com.nwf.sports.mvp.model.RedPacketRecordListDetailsResult;
import com.nwf.sports.mvp.model.RedPacketResult;
import com.nwf.sports.mvp.model.RedPacketStateResult;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.utils.Constant;
import com.nwf.sports.utils.MD5Util;
import com.nwf.sports.utils.data.DataCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.wildfire.chat.kit.AppServiceProvider;
import cn.wildfire.chat.kit.ChatManagerHolder;
import cn.wildfire.chat.kit.group.GroupAnnouncement;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfire.chat.kit.net.base.StatusResult;

public class AppService implements AppServiceProvider {
    private static AppService Instance = new AppService();

    private AppService() {

    }

    public static AppService Instance() {
        return Instance;
    }

    public interface LoginCallback {
        void onUiSuccess(LoginResult loginResult);

        void onUiFailure(int code, String msg);
    }

    @Deprecated //"已经废弃，请使用smsLogin"
    public void namePwdLogin(String account, String password, LoginCallback callback) {

        String url = RetrofitHelper.imUrl() + "/api/login";
        Map<String, Object> params = new HashMap<>();
        params.put("name", account);
        params.put("password", password);

        try {
            params.put("clientId", ChatManagerHolder.gChatManager.getClientId());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onUiFailure(-1, "网络出来问题了。。。");
            return;
        }

        OKHttpHelper.post(url, params, new SimpleCallback<LoginResult>() {
            @Override
            public void onUiSuccess(LoginResult loginResult) {
                callback.onUiSuccess(loginResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    public void smsLogin(String phoneNumber, String authCode, LoginCallback callback) {

        String url = RetrofitHelper.imUrl() + "/login";
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", phoneNumber);
        params.put("code", authCode);


        //Platform_iOS = 1,
        //Platform_Android = 2,
        //Platform_Windows = 3,
        //Platform_OSX = 4,
        //Platform_WEB = 5,
        //Platform_WX = 6,
        params.put("platform", new Integer(2));

        try {
            params.put("clientId", ChatManagerHolder.gChatManager.getClientId());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onUiFailure(-1, "网络出来问题了。。。");
            return;
        }

        OKHttpHelper.post(url, params, new SimpleCallback<LoginResult>() {
            @Override
            public void onUiSuccess(LoginResult loginResult) {
                callback.onUiSuccess(loginResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    public interface SendCodeCallback {
        void onUiSuccess();

        void onUiFailure(int code, String msg);
    }

    public void requestAuthCode(String phoneNumber, SendCodeCallback callback) {

        String url = RetrofitHelper.imUrl() + "/send_code";
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", phoneNumber);
        OKHttpHelper.post(url, params, new SimpleCallback<StatusResult>() {
            @Override
            public void onUiSuccess(StatusResult statusResult) {
                if (statusResult.getHead().getErrCode().equals("0000")) {
                    callback.onUiSuccess();
                } else {
                    callback.onUiFailure(0, statusResult.getHead().getErrMsg());
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(-1, msg);
            }
        });

    }


    public interface SendRedPacketCallback {
        void onSuccess(RedPacketResult redPacketResult);

        void onFailure(int code, String msg);
    }

    /**
     * 发送红包
     *
     * @param callback
     */
    public void sendRedPacket(Map<String, Object> params, SendRedPacketCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/send-packet";

        OKHttpHelper.post(url, params, new SimpleCallback<RedPacketResult>() {
            @Override
            public void onUiSuccess(RedPacketResult redPacketResult) {
                callback.onSuccess(redPacketResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }


    public interface RobRedPacketCallback {
        void onSuccess();

        void onFailure(int code, String msg);
    }

    /**
     * 抢红包
     *
     * @param callback
     */
    public void robRedPacket(String packetId, String groupId, RobRedPacketCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/grab-packet";
        Map<String, Object> params = new HashMap<>();

        params.put("productId", DataCenter.getInstance().getProductId());
        params.put("userName", DataCenter.getInstance().getLoginName());
        params.put("packetId", packetId);
        params.put("groupId", groupId);
        //  params.put("grabPacketKey", MD5Util.md5(groupId + Constant.PRODUCT_ID + packetId + username, MD5Util.md5Key));//MD5(groupId+productId+packetId+userName,加密KEY)

        OKHttpHelper.post(url, params, new SimpleCallback<StatusResult>() {
            @Override
            public void onUiSuccess(StatusResult statusResult) {
                callback.onSuccess();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface CreditQueryCallback {
        void onSuccess(CreditQueryResult statusResult);

        void onFailure(int code, String msg);
    }

    /**
     * 查询红包游戏余额
     *
     * @param callback
     */
    public void creditQuery(CreditQueryCallback callback) {

        String url = RetrofitHelper.imUrl() + "/credit/query";
        Map<String, Object> params = new HashMap<>();
        String username = DataCenter.getInstance().getLoginName();
        params.put("pid", Constant.PRODUCT_ID);
        params.put("loginname", username);
//        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        long time = System.currentTimeMillis();
        params.put("timestamp", time);
        params.put("uuid", UUID.randomUUID().toString());
        params.put("keyCode", MD5Util.md5(Constant.PRODUCT_ID + username + time, "feas!#%"));

        OKHttpHelper.post(url, params, new SimpleCallback<CreditQueryResult>() {
            @Override
            public void onUiSuccess(CreditQueryResult creditQueryResult) {
                callback.onSuccess(creditQueryResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface QueryPacketStateCallback {
        void onSuccess(RedPacketStateResult redPacketStateResult);

        void onFailure(int code, String msg);
    }

    /**
     * 查询红包状态
     *
     * @param callback
     */
    public void queryPacketState(String packetId, QueryPacketStateCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/query-packet";
        Map<String, Object> params = new HashMap<>();
        params.put("packetId", packetId);
        params.put("productId", DataCenter.getInstance().getProductId());
        params.put("userName", DataCenter.getInstance().getLoginName());

        OKHttpHelper.post(url, params, new SimpleCallback<RedPacketStateResult>() {
            @Override
            public void onUiSuccess(RedPacketStateResult redPacketStateResult) {
                callback.onSuccess(redPacketStateResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface RedPacketDetailCallback {
        void onSuccess(RedPacketDetailResult statusResult);

        void onFailure(int code, String msg);
    }

    /**
     * 红包详情
     *
     * @param callback
     */
    public void RedPacketDetail(String packetId, String pageNo, String pageSize, RedPacketDetailCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/receive-packet-detail";
        Map<String, Object> params = new HashMap<>();

        params.put("userName", DataCenter.getInstance().getLoginName());
        params.put("pageNo", pageNo);
        params.put("packetId", packetId);
        params.put("pageSize", pageSize);
        params.put("productId", DataCenter.getInstance().getProductId());

        OKHttpHelper.post(url, params, new SimpleCallback<RedPacketDetailResult>() {
            @Override
            public void onUiSuccess(RedPacketDetailResult statusResult) {
                callback.onSuccess(statusResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface QueryRedpacketGroupCallback {
        void onSuccess(List<RedPacketGameListResult.RedPacketGroupVoListBean>  statusResult);

        void onFailure(int code, String msg);
    }

    /**
     * 群列表接口
     *
     * @param callback
     */
    public void QueryRedpacketGroup(int groupType, QueryRedpacketGroupCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/query-redpacket-group";
        Map<String, Object> params = new HashMap<>();
        String username = DataCenter.getInstance().getLoginName();
        params.put("userName", username);
        params.put("productId", DataCenter.getInstance().getProductId());
        params.put("groupType", groupType);

        OKHttpHelper.post(url, params, new SimpleCallback<List<RedPacketGameListResult.RedPacketGroupVoListBean>>() {
            @Override
            public void onUiSuccess(List<RedPacketGameListResult.RedPacketGroupVoListBean>  statusResult) {
                callback.onSuccess(statusResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface QueryGroupJoinInfoCallback {
        void onSuccess(RedPacketGameListDetailsResult statusResult);

        void onFailure(int code, String msg);
    }

    /**
     * 查询进群信息接口
     *
     * @param callback
     */
    public void QueryGroupJoinInfo(String groupId, QueryGroupJoinInfoCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/query-group-join-info";
        Map<String, Object> params = new HashMap<>();

        String username = DataCenter.getInstance().getLoginName();
        params.put("userName", username);
        params.put("groupId", groupId);
        params.put("productId", DataCenter.getInstance().getProductId());

        OKHttpHelper.post(url, params, new SimpleCallback<RedPacketGameListDetailsResult>() {
            @Override
            public void onUiSuccess(RedPacketGameListDetailsResult statusResult) {
                callback.onSuccess(statusResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface QueryRedPacketRecordCallback {
        void onSuccess(RedPacketRecordListDetailsResult redPacketRecordListDetailsResult);

        void onFailure(int code, String msg);
    }

    /**
     * 查询红包记录
     *
     * @param callback
     */
    public void QueryRedPacketRecord(String type, int pageNo, QueryRedPacketRecordCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/query-my-packet-list";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", DataCenter.getInstance().getLoginName());
        params.put("type", type);
        params.put("pageNo", pageNo);
        params.put("pageSize", "20");
        params.put("productId", DataCenter.getInstance().getProductId());

        OKHttpHelper.post(url, params, new SimpleCallback<RedPacketRecordListDetailsResult>() {
            @Override
            public void onUiSuccess(RedPacketRecordListDetailsResult statusResult) {
                callback.onSuccess(statusResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface QueryChangenameFlagCallback {
        void onSuccess(boolean isModification);

        void onFailure(int code, String msg);
    }

    /**
     * 查询昵称是否修改
     *
     * @param callback
     */
    public void queryChangenameFlag(QueryChangenameFlagCallback callback) {

        String url = RetrofitHelper.imUrl() + "/game/api/packet/query-changename-flag";
        Map<String, Object> params = new HashMap<>();
      //  if (DataCenter.getInstance().getUserInfoBean().isRealLogin) {
            String username = DataCenter.getInstance().getLoginName();
            params.put("userName", username);
       // }
        params.put("productId", Constant.PRODUCT_ID);

        OKHttpHelper.post(url, params, new SimpleCallback<Boolean>() {
            @Override
            public void onUiSuccess(Boolean statusResult) {
                callback.onSuccess(statusResult);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });

    }

    public interface ScanPCCallback {
        void onUiSuccess(PCSession pcSession);

        void onUiFailure(int code, String msg);
    }

    public void scanPCLogin(String token, ScanPCCallback callback) {
        String url = RetrofitHelper.imUrl() + "/scan_pc";
        url += "/" + token;
        OKHttpHelper.post(url, null, new SimpleCallback<PCSession>() {
            @Override
            public void onUiSuccess(PCSession pcSession) {
                if (pcSession.getStatus() == 1) {
                    callback.onUiSuccess(pcSession);
                } else {
                    callback.onUiFailure(pcSession.getStatus(), "");
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(-1, msg);
            }
        });
    }

    public interface PCLoginCallback {
        void onUiSuccess();

        void onUiFailure(int code, String msg);
    }

    public void confirmPCLogin(String token, String userId, PCLoginCallback callback) {
        String url = RetrofitHelper.imUrl() + "/confirm_pc";

        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        params.put("token", token);
        OKHttpHelper.post(url, params, new SimpleCallback<PCSession>() {
            @Override
            public void onUiSuccess(PCSession pcSession) {
                if (pcSession.getStatus() == 2) {
                    callback.onUiSuccess();
                } else {
                    callback.onUiFailure(pcSession.getStatus(), "");
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(-1, msg);
            }
        });
    }


    @Override
    public void getGroupAnnouncement(String groupId, AppServiceProvider.GetGroupAnnouncementCallback callback) {
        //从SP中获取到历史数据callback回去，然后再从网络刷新
        String url = RetrofitHelper.imUrl() + "/get-group-announcement";

        Map<String, Object> params = new HashMap<>(2);
        params.put("groupId", groupId);
        OKHttpHelper.post(url, params, new SimpleCallback<GroupAnnouncement>() {
            @Override
            public void onUiSuccess(GroupAnnouncement announcement) {
                callback.onUiSuccess(announcement);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(-1, msg);
            }
        });
    }


    @Override
    public void updateGroupAnnouncement(String groupId, String announcement, AppServiceProvider.UpdateGroupAnnouncementCallback callback) {
        //更新到应用服务，再保存到本地SP中
        String url = RetrofitHelper.imUrl() + "/put-group-announcement";

        Map<String, Object> params = new HashMap<>(2);
        params.put("groupId", groupId);
        params.put("author", ChatManagerHolder.gChatManager.getUserId());
        params.put("text", announcement);
        OKHttpHelper.post(url, params, new SimpleCallback<GroupAnnouncement>() {
            @Override
            public void onUiSuccess(GroupAnnouncement announcement) {
                callback.onUiSuccess(announcement);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(-1, msg);
            }
        });
    }
}
