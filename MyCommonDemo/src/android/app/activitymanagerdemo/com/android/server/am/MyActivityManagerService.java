package android.app.activitymanagerdemo.com.android.server.am;

import android.app.activitymanagerdemo.android.app.MyActivityManagerNative;

import wd.android.util.util.MyLog;

public final class MyActivityManagerService extends MyActivityManagerNative {

    private static final String TAG = "MyActivityManagerService";
    private int i = 100;
    private String str = null;

    @Override
    public int start(int i, String str) {
        MyLog.e("i == " + i + ",str == " + str);
        this.i = i;
        return 0;
    }

    @Override
    public int stop(int i) {
        MyLog.e("i == " + i);
        return 0;
    }

}
