package com.gisroad.sign.bean;

import java.io.Serializable;

/**
 * Created by zhanglin on 2016/11/9.
 */

public class MyData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public int type;
    public T data;//多种类型数据，一般是List集合，比如获取所有员工列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
