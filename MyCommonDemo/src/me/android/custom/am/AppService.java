package me.android.custom.am;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import wd.android.util.util.MyLog;

public class AppService extends Service {
    public static final String ACTION_ADD_APP = "android.app.action_add_app";
    public static final String ACTION_REMOVE_APP = "android.app.action_remove_app";
    public static final String PACKAGE_NAME = "packageName";

    private IBinder mServiceBinder = new ServiceBinder();
    private AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appManager = new AppManager(this);
        appManager.initInstalledList();
    }

    public class ServiceBinder extends Binder {
        public AppManager getService() {
            return appManager;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.i("intent = " + intent + ",flags = " + flags + ",startId = "
                + startId);
        super.onStartCommand(intent, flags, startId);
        if (null == intent) {
            if (startId % 4 == 0) {
                startService(new Intent(this, this.getClass()));
                return START_NOT_STICKY;
            }
            return START_STICKY;
        }
        return processAction(intent);
    }

    private int processAction(Intent intent) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        appManager.release();
        super.onDestroy();
    }

}
