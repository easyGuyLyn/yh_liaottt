//package com.nwf.user.ipc;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.dawoo.ipc.event.Events;
//import com.dawoo.ipc.event.bean.BaseIpcEvent;
//import com.dawoo.ipc.event.bean.GameApiEvent;
//import com.dawoo.ipc.utl.FastJsonUtils;
//import com.nwf.user.ConstantValue;
//import com.hwangjr.rxbus.RxBus;
//
///**
// * archar  天纵神武
// **/
//public class IPCMessageManager {
//    private static final String TAG = "IPCMessageManager: ";
//
//    private static IPCMessageManager mInstance;
//
//    public static IPCMessageManager getInstance() {
//        if (mInstance == null) {
//            synchronized (IPCSocketManager.class) {
//                if (mInstance == null) {
//                    mInstance = new IPCMessageManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//
//    //分发
//    public void dispatch(byte[] bytes, int contentLength) {
//        if (bytes.length == 0) {
//            return;
//        }
//        String json = new String(bytes, 0, contentLength);
//        Log.e(TAG, json);
//        BaseIpcEvent baseIpcEvent = FastJsonUtils.toBean(json, BaseIpcEvent.class);
//        if (!TextUtils.isEmpty(baseIpcEvent.getType())) {
//            switch (baseIpcEvent.getType()) {
//                case Events.EVENT_REFRSH_API:
//                    GameApiEvent gameApiEvent = FastJsonUtils.toBean(json, GameApiEvent.class);
//                    Log.e(TAG, "game api" + gameApiEvent.getGameApi() + "");
//                    RxBus.get().post(ConstantValue.EVENT_REFRSH_API, gameApiEvent.getGameApi() + "");
//                    break;
//            }
//        }
//
//    }
//
//}
