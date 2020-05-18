package cn.wildfirechattest;

import android.content.Context;
import android.os.Handler;


import com.nwf.sports.IMApplication;

import androidx.multidex.MultiDex;


public class TestApplication extends IMApplication {

    private static Context context;
    public static Handler handler = new Handler();

    private static TestApplication mInstance;

    public static TestApplication getInstance() {
        return mInstance;
    }

    //兼容 4.5版本以下 添加MultiDex分包，但未初始化的问题
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }


}
