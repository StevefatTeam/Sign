package com.gisroad.sign.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 部门
 * Author: ngh
 * date: 2016/9/28
 */
@Entity
public class DepartBean {
    @Id
    private Long d_id;
    private String d_name;

    @Generated(hash = 1839794757)
    public DepartBean(Long d_id, String d_name) {
        this.d_id = d_id;
        this.d_name = d_name;
    }

    @Generated(hash = 790678164)
    public DepartBean() {
    }

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }
}
