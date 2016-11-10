package com.gisroad.sign.bean;

import com.gisroad.sign.AppContext;
import com.gisroad.sign.db.DepartUserDao;

import java.util.List;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class DepartUserManager implements BaseManager<DepartUser> {

    private DepartUserDao departUserDao;
    private static DepartUserManager instance;

    public DepartUserManager() {
        this.departUserDao = AppContext.instances.getDaoSession().getDepartUserDao();
    }

    public static DepartUserManager getInstance() {
        if (instance == null) {
            instance = new DepartUserManager();
        }
        return instance;
    }


    @Override
    public void inster(DepartUser departUser) {
        departUserDao.insert(departUser);
    }

    @Override
    public void update(DepartUser departUser) {
        departUserDao.update(departUser);
    }

    @Override
    public void delete(DepartUser departUser) {
        departUserDao.delete(departUser);
    }

    /**
     * 查询所有人
     * @param departUser
     * @return
     */
    @Override
    public List<DepartUser> select(DepartUser departUser) {
        return departUserDao.queryBuilder().list();
    }

    /**
     * 查询所有人
     * @return
     */
    public List<DepartUser> select() {
        return departUserDao.queryBuilder().list();
    }


    /**
     * 查询部门人员信息
     * @param departBean 部门信息
     * @return
     */
    public List<DepartUser> select(DepartBean departBean) {
        return departUserDao.queryBuilder().where(DepartUserDao.Properties.D_id.eq(departBean.getD_id())).list();
    }


    /**
     * 根据部门名字查询人员信息
     * @param name
     * @return
     */
    public List<DepartUser> selectByName(String name) {
        return departUserDao.queryBuilder().where(DepartUserDao.Properties.Name.eq(name)).list();
    }

}
