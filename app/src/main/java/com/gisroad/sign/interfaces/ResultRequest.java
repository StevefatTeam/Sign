package com.gisroad.sign.interfaces;

import java.util.Objects;

/**
 * Author: ngh
 * date: 2016/9/26
 * 数据转发
 */

public interface ResultRequest<T> {
    //返回结果
    void onSuccess(T t);
    //错误结果
    void onError(Throwable e);
    void onSuccess(Object o1,Object o2);
}
