package com.conny.tv.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.conny.tv.bean.DaoMaster;
import com.conny.tv.bean.DaoSession;
import com.conny.tv.material.utils.ResourcesUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Desc:
 * Created by zhanghui on 2017/11/13.
 */

public class LocalApplication extends Application {

    public static Application APP;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        ResourcesUtil.init(getResources());

        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
