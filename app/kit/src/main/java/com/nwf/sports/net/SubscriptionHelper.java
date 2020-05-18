package com.nwf.sports.net;

import java.util.ArrayList;

import rx.Subscription;

/**
 * 取消订阅的辅助类
 */

public class SubscriptionHelper {

    private ArrayList<Subscription> subscriptions = new ArrayList<>();
    public void add(Subscription subscription)
    {
        subscriptions.add(subscription);
    }

    public boolean isEmpty()
    {
        return subscriptions.isEmpty();
    }

    public void unsubscribe()
    {
        if(!subscriptions.isEmpty())
        {
            for(Subscription subscription:subscriptions)
            {
                if(null != subscription && !subscription.isUnsubscribed())
                {
                    subscription.unsubscribe();
                }
            }
        }
        subscriptions.clear();
    }
}
