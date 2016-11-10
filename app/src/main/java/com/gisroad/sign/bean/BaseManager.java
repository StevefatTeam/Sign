package com.gisroad.sign.bean;

import java.util.List;

/**
 * Author: ngh
 * date: 2016/9/28
 * bean 管理基类
 */

public interface BaseManager<T> {
    void inster(T t);

    void update(T t);

    void delete(T t);

    List<T> select(T t);
}
