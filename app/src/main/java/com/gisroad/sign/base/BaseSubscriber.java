package com.gisroad.sign.base;

import android.util.Log;

import com.orhanobut.logger.Logger;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Author: ngh
 * date: 2016/9/26
 * 中间过度数据服务类
 */

public abstract class BaseSubscriber extends Subscriber<ResponseBody> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
