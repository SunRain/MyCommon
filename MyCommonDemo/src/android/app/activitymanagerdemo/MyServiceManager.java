package android.app.activitymanagerdemo;

import android.app.activitymanagerdemo.android.app.IMyActivityManager;
import android.app.activitymanagerdemo.android.os.IMyBinder;
import android.app.activitymanagerdemo.android.os.IMyInterface;
import android.app.activitymanagerdemo.android.os.MyBinder;

import wd.android.util.util.MyLog;

public class MyServiceManager {
    public static IMyBinder getService(String service) {
        MyLog.e("getService(" + service + ")");
        if ("MyActivityManagerService".equals(service)) {
            return createBMyActivityManagerService();
        }
        return null;
    }

    private static IMyBinder createBMyActivityManagerService() {
        try {
            Object obj = Class
                    .forName(
                            "com.nbtstatx.mydemos.activitymanagerdemo.MyActivityManagerService")
                    .newInstance();
            MyBinder binder = (MyBinder) obj;
            binder.attachInterface((IMyInterface) obj,
                    IMyActivityManager.descriptor);
            return (IMyBinder) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
