package com.gisroad.sign;

import android.app.Application;
import android.util.Log;

import com.gisroad.sign.db.DaoMaster;
import com.gisroad.sign.db.DaoSession;
import com.gisroad.sign.db.HMROpenHelper;
import com.pgyersdk.crash.PgyCrashManager;

import org.greenrobot.greendao.database.Database;


/**
 * Author: ngh
 * date: 2016/9/24
 */

public class AppContext extends Application {

    private HMROpenHelper mHelper;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static AppContext instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        PgyCrashManager.register(this);
        setDatabase();
    }

    public static AppContext getInstances() {
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new HMROpenHelper(this, "sign-db", null);
        db = mHelper.getWritableDb();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

        /******************************这里的更新要说明一点，版本放到配置文件中，这个的版本每次都是1，如果更新过需要加个标识，否则就不更新****************************/
        int dbVersion = mDaoMaster.getSchemaVersion();
        dbVersion+=1;
        Log.e("dbVersion----******",mDaoMaster.getSchemaVersion()+"------newVersion："+dbVersion);

        mHelper.onUpgrade(db,mDaoMaster.getSchemaVersion(),dbVersion);


    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public Database getDb() {
        return db;
    }



}
