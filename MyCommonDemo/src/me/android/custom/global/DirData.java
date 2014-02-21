package me.android.custom.global;

import android.content.Context;

import wd.android.framework.global.BaseDirData;

public class DirData extends BaseDirData {

    /**
     * 采用系统默认缓存目录，保存至android/data/目录下
     */
    // public static final String CACHE_PIC = ".cache_pic/";
    public static final String UPDATE_APK = "update_apk/";

    public DirData(Context context) {
        super(context);
    }

    @Override
    public String[] getDirStrings() {
        return new String[]{UPDATE_APK};
    }
}
