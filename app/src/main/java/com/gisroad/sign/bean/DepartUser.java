package com.gisroad.sign.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 用户
 * Author: ngh
 * date: 2016/9/28
 */

@Entity
public class DepartUser {
    @Id
    private Long id;

    private Long d_id;
    private String name;
    private String url;

    @Generated(hash = 106098595)
    public DepartUser(Long id, Long d_id, String name, String url) {
        this.id = id;
        this.d_id = d_id;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 336980638)
    public DepartUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DepartUser{" +
                "id=" + id +
                ", d_id=" + d_id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
