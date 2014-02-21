package me.android.common.db.wd;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.util.Map;

import wd.android.util.util.MyLog;
import wd.android.util.util.ObjectUtil;

/**
 * DAO管理类
 */
public class DaoManager {
    public final Map<Class<?>, BaseDao<?>> daoMap = ObjectUtil.newHashMap();
    private DatabaseUtil mDatabaseUtil;

    public DaoManager(Context context) {
        mDatabaseUtil = new DatabaseUtil(context);
    }

    /**
     * 根据bean class获取并实例化对应的dao接口
     *
     * @param beanClazz
     * @return
     */
    // @SuppressWarnings("unchecked")
    // public <T extends BaseDao<?>> T getDao(Class<?> beanClazz) {
    // T dao = (T) daoMap.get(beanClazz);
    // if (dao != null) {
    // return dao;
    // }
    //
    // Class<T> daoClazz = (Class<T>) DatabaseConfig.TABLE_MAP.get(beanClazz);
    // try {
    // // Constructor constructor = daoClazz.getDeclaredConstructor();
    // Constructor<T> constructor = daoClazz.getDeclaredConstructor(
    // DatabaseUtil.class, beanClazz.getClass()); // 构造函数参数列表的class类型
    // constructor.setAccessible(true);
    // dao = (T) constructor.newInstance(mDatabaseUtil);
    // // new DatabaseUtil(context);
    // } catch (Exception e) {
    // e.printStackTrace();
    // MyLog.e(e);
    // }
    // daoMap.put(beanClazz, (T) dao);
    // return dao;
    // }

    /**
     * 获取DAO接口
     *
     * @param beanClazz JavaBean类
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseDao<?>> T getDao(Class<T> daoClazz) {
        T dao = (T) daoMap.get(daoClazz);
        if (dao != null) {
            return dao;
        }

        // Class<T> daoClazz = (Class<T>)
        // DatabaseConfig.TABLE_MAP.get(beanClazz);
        try {
            // Constructor constructor = daoClazz.getDeclaredConstructor();
            Constructor<T> constructor = daoClazz
                    .getDeclaredConstructor(DatabaseUtil.class); // 构造函数参数列表的class类型
            constructor.setAccessible(true);
            dao = (T) constructor.newInstance(mDatabaseUtil);
            // new DatabaseUtil(context);
        } catch (Exception e) {
            MyLog.e(e);
        }
        daoMap.put(daoClazz, (T) dao);
        return dao;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mDatabaseUtil != null) {
            mDatabaseUtil.relese();
        }
    }
}
