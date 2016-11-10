package com.gisroad.sign.bean;

import com.gisroad.sign.AppContext;
import com.gisroad.sign.db.DepartBeanDao;

import java.util.List;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class DepartmentManager implements BaseManager<DepartBean> {
    private DepartBeanDao departmentDao;
    private static DepartmentManager instance;

    public DepartmentManager() {
        this.departmentDao = AppContext.instances.getDaoSession().getDepartBeanDao();
    }

    public static DepartmentManager getInstance() {
        if (instance == null) {
            instance = new DepartmentManager();
        }
        return instance;
    }


    @Override
    public void inster(DepartBean department) {
        departmentDao.insert(department);
    }

    @Override
    public void update(DepartBean department) {
        departmentDao.update(department);
    }

    @Override
    public void delete(DepartBean department) {
        departmentDao.delete(department);
    }

    @Override
    public List<DepartBean> select(DepartBean department) {

        return departmentDao.queryBuilder().list();
    }
    public List<DepartBean> select() {

        return departmentDao.queryBuilder().list();
    }


}
