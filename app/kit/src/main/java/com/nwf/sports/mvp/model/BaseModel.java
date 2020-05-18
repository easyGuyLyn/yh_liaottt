package com.nwf.sports.mvp.model;

import com.dawoo.coretool.util.NetworkUtils;
import com.nwf.sports.net.CustomHttpException;
import com.nwf.sports.net.HttpResult;
import com.nwf.sports.utils.Constant;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by benson on 17-12-21.
 */

public class BaseModel {
    protected <T> Subscription toSubscribe(Observable<T> o, Subscriber<T> s) {
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

    /**
     * 在这里给{@code Observable}统一加点糖-例如防止重复执行、子线程订阅、主线程通知、没有网络等等
     * @param observable
     * @return
     */
    public static <O>Observable<O> addSugar(Observable<O> observable)
    {
        return observable
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {

//            if (!httpResult.isSuccess()) {
//                throw new ApiException(httpResult.getCode(), httpResult.getMessage());
//            }
            return httpResult.getData();
        }
    }
}
