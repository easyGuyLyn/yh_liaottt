//package com.nwf.user.ipc;
//
//import android.content.Intent;
//import android.os.SystemClock;
//import android.util.Log;
//
//import com.dawoo.coretool.util.packageref.PackageInfoUtil;
//import com.dawoo.ipc.event.Events;
//import com.dawoo.ipc.event.bean.Ping;
//import com.dawoo.ipc.server.IPCServerService;
//import com.dawoo.ipc.utl.FastJsonUtils;
//import com.dawoo.ipc.utl.GetBytesWithHeadInfo;
//import com.nwf.user.TestApplication;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * archar  天纵神武
// * 本地socket
// **/
//public class IPCSocketManager {
//
//    private static final String TAG = "IPCSocketManager: ";
//    private ConnectedThread mConnectedThread;
//    private Thread mConnectThread;
//    private Timer mTimer;
//    private TimerTask mTimerTask;
//    private static final long PING_DELAY = 3000;//心跳间隔
//    private static IPCSocketManager mInstance;
//
//    public static IPCSocketManager getInstance() {
//        if (mInstance == null) {
//            synchronized (IPCSocketManager.class) {
//                if (mInstance == null) {
//                    mInstance = new IPCSocketManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    /**
//     * 开启 远程
//     */
//    public void startServerService() {
//        if (!PackageInfoUtil.isServiceRunning(TestApplication.getContext(), "com.dawoo.ipc.server.IPCServerService")) {
//            Intent intent = new Intent(TestApplication.getContext(), IPCServerService.class);
//            TestApplication.getContext().startService(intent);
//        }
//    }
//
//    /**
//     * 连接 远程
//     */
//    public void connectTcpService() {
//        if (mConnectThread != null && mConnectThread.isAlive()) {
//            mConnectThread.interrupt();
//        }
//        mConnectThread = new Thread(new TcpClient());
//        mConnectThread.start();
//    }
//
//    /**
//     * 异常重连
//     */
//    public void reconnect() {
//        cancelPingTask();
//        connectTcpService();
//    }
//
//    private class TcpClient implements Runnable {
//
//        @Override
//        public void run() {
//            Socket socket = null;
//            // 不停重试直到连接成功为止
//            while (socket == null) {
//                try {
//                    socket = new Socket("localhost", IPCServerService.PORT);
//                    Log.e(TAG, "IPCServerService 连接成功");
//                } catch (IOException e) {
//                    SystemClock.sleep(1000);
//                    Log.e(TAG, "连接TCP服务失败, 重试...");
//                    if (!PackageInfoUtil.isServiceRunning(TestApplication.getContext(), "com.dawoo.ipc.server.IPCServerService")) {
//                        Log.e(TAG, "远程服务:  " + IPCServerService.class.getSimpleName() + "  未运行或即将运行");
//                       // startServerService();
//                    }
//                }
//            }
//
//            if (mConnectedThread != null) {
//                mConnectedThread.cancel();
//            }
//            mConnectedThread = new ConnectedThread(socket);
//            mConnectedThread.start();
//            startPingTask();
//        }
//
//    }
//
//    /**
//     * 心跳包，不停地发送消息给 server
//     */
//    private void initTimerTask() {
//        mTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                sendPing();
//                Log.e(TAG, "******ipc socket**** ping");
//            }
//        };
//    }
//
//    /**
//     * 开启心跳包，每一秒发送一次空消息
//     */
//    public void startPingTask() {
//        cancelPingTask();
//        mTimer = new Timer();
//        initTimerTask();
//        mTimer.schedule(mTimerTask, 1500, PING_DELAY);
//    }
//
//    /**
//     * 关闭心跳包
//     */
//    public void cancelPingTask() {
//        if (mTimer != null) {
//            mTimer.cancel();
//            mTimer = null;
//        }
//        if (mTimerTask != null) {
//            mTimerTask.cancel();
//            mTimerTask = null;
//        }
//    }
//
//    private void sendPing() {
//        Ping ping = new Ping();
//        ping.setType(Events.PING);
//        ping.setPing("ipc socket 心跳");
//        sendBytes(GetBytesWithHeadInfo.getByteArry(FastJsonUtils.toJSONString(ping)));
//    }
//
//
//    /**
//     * 传输数据
//     */
//    public synchronized void sendBytes(byte[] bytes) {
//        if (null == mConnectedThread || mConnectedThread.isInterrupted()) {
//            return;
//        }
//        mConnectedThread.write(bytes);
//    }
//
//    /**
//     * 销毁
//     */
//    public void destroy() {
//        cancelPingTask();
//        if (mConnectedThread != null) {
//            mConnectedThread.cancel();
//        }
//        if (mConnectThread != null && mConnectThread.isAlive()) {
//            mConnectThread.interrupt();
//        }
//    }
//
//    private class ConnectedThread extends Thread {
//        private final Socket mmSocket;
//        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;
//
//        public ConnectedThread(Socket socket) {
//            mmSocket = socket;
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//
//            try {
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) {
//                Log.e(TAG, " socket not created", e);
//            }
//
//            mmInStream = tmpIn;
//            mmOutStream = tmpOut;
//        }
//
//        /**
//         * 读
//         */
//        public void run() {
//            Log.e(TAG, "BEGIN mConnectedThread");
//            int headLength;
//            byte[] headBytes_ = new byte[GetBytesWithHeadInfo.HEADLENGTH];//报文头部的缓冲
//            try {
//                while ((headLength = mmInStream.read(headBytes_)) != -1) {
//                    int contentLength = Integer.parseInt(new String(headBytes_, 0, headLength).trim());
//                    byte[] contentBytes_ = new byte[contentLength];
//                    int temp = mmInStream.read(contentBytes_);
//                    while (temp < contentLength) {
//                        temp += mmInStream.read(contentBytes_, temp, contentLength - temp);
//                    }
//                    IPCMessageManager.getInstance().dispatch(contentBytes_, contentLength);
//                }
//            } catch (IOException e) {
//                Log.e(TAG, " 本地socket 读取数据异常 : " + e.getLocalizedMessage());
//                e.printStackTrace();
//            }
//        }
//
//        /**
//         * 写
//         */
//        public void write(byte[] buffer) {
//            try {
//                if (this != null && this.isAlive()) {
//                    mmOutStream.write(buffer);
//                    mmOutStream.flush();
//                } else {
//                    Log.e(TAG, "向服务端写 -- 异常  线程死亡");
//                    reconnect();
//                }
//            } catch (IOException e) {
//                Log.e(TAG, "向服务端写 -- 异常", e);
//                cancel();
//                reconnect();
//            }
//        }
//
//        public void cancel() {
//            try {
//                if (this.isAlive()) {
//                    this.interrupt();
//                }
//                mmInStream.close();
//                mmSocket.close();
//                mmOutStream.close();
//            } catch (IOException e) {
//                Log.e(TAG, " socket 关闭异常", e);
//            }
//        }
//    }
//
//
//}
