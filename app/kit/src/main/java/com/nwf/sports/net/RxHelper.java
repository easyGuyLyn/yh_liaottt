package com.nwf.sports.net;

import com.dawoo.coretool.util.NetworkUtils;
import com.nwf.sports.utils.Constant;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Nereus on 2017/5/19.
 */

public class RxHelper {

    private RxHelper(){}
    @Override
    protected Object clone()
    {
        throw new RuntimeException("i cannot be cloned,as you know");
    }
    /**
     * 在这里给{@code Observable}统一-例如防止重复执行、子线程订阅、主线程通知、没有网络等等
     * @return
     */
    public static <T> Subscription toSubscribe(Observable<T> o, Subscriber<T> s) {
        Subscription subscription = o.subscribeOn(Schedulers.io())
                .throttleFirst(Constant.throttleWindowTime, TimeUnit.SECONDS)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Timber.d("doOnSubscribe");
                        if(!NetworkUtils.isConnected())
                        {
                            throw new CustomHttpException(-1);
                        }
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);

        return subscription;
    }
}
