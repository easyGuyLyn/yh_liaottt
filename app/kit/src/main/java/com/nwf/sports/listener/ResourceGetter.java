package com.nwf.sports.listener;

import androidx.annotation.StringRes;

/**
 * Created by Nereus on 2017/5/16.
 */

public interface ResourceGetter
{
    public String getString(@StringRes int resid);
}
