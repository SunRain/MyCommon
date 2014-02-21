package me.android.custom;

import me.android.common.db.BaseDao;
import me.android.common.db.MyDaoManager;
import me.android.common.image.ImageManager;
import me.android.custom.global.DirData;
import wd.android.framework.global.AccountData;
import wd.android.framework.global.CommonTag;
import wd.android.framework.global.GlobalData;
import wd.android.framework.manager.BaseManager;
import wd.android.util.global.MyPreference;
import wd.android.util.thread.ThreadPool;

public class MyManager {

    // public <T> T getService(Class<T> clazz) {
    // return BaseManager.getService(clazz);
    // }

    public static GlobalData getGlobalData() {
        return BaseManager.getService(GlobalData.class);
    }

    public static ImageManager getAsyncImageManager() {
        return BaseManager.getService(ImageManager.class);
    }

    public static DirData getDirData() {
        return BaseManager.getService(DirData.class);
    }

    public static MyPreference getMyPreference() {
        return BaseManager.getService(MyPreference.class);
    }

    public static <T extends BaseDao<?>> T getDao(Class<T> daoClazz) {
        return BaseManager.getService(MyDaoManager.class).getDao(daoClazz);
    }

    // public static <T, K> MyDao<T, K> getMyDao(Class<T> beanClazz) {
    // return BaseManager.getService(MyDaoManager.class).getMyDao(beanClazz);
    // }

    public static AccountData getAccountData() {
        return getData(CommonTag.ACCOUNT_DATA);
    }

    public static <T> T getData(String tag) {
        GlobalData globalData = BaseManager.getService(GlobalData.class);
        return globalData.getData(tag);
    }

    // public static String getUrl(String key) {
    // GlobalData globalData = BaseManager.getService(GlobalData.class);
    // UrlData urlData = (UrlData) globalData.getData(Tag.URL_DATA);
    // return urlData.getUrl(key);
    // }

    public static ThreadPool getThreadPool() {
        return BaseManager.getService(ThreadPool.class);
    }
}