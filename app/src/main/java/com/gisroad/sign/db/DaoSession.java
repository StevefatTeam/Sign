package com.gisroad.sign.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;

import com.gisroad.sign.db.DepartBeanDao;
import com.gisroad.sign.db.DepartUserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig departBeanDaoConfig;
    private final DaoConfig departUserDaoConfig;

    private final DepartBeanDao departBeanDao;
    private final DepartUserDao departUserDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        departBeanDaoConfig = daoConfigMap.get(DepartBeanDao.class).clone();
        departBeanDaoConfig.initIdentityScope(type);

        departUserDaoConfig = daoConfigMap.get(DepartUserDao.class).clone();
        departUserDaoConfig.initIdentityScope(type);

        departBeanDao = new DepartBeanDao(departBeanDaoConfig, this);
        departUserDao = new DepartUserDao(departUserDaoConfig, this);

        registerDao(DepartBean.class, departBeanDao);
        registerDao(DepartUser.class, departUserDao);
    }
    
    public void clear() {
        departBeanDaoConfig.clearIdentityScope();
        departUserDaoConfig.clearIdentityScope();
    }

    public DepartBeanDao getDepartBeanDao() {
        return departBeanDao;
    }

    public DepartUserDao getDepartUserDao() {
        return departUserDao;
    }

}
