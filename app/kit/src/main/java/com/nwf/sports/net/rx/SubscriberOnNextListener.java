package com.nwf.sports.net.rx;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(String e);
}
