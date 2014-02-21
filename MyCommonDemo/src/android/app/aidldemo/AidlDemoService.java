package android.app.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import wd.android.util.util.MyLog;


public class AidlDemoService extends Service {
    private MainManager mainManager = null;

    @Override
    public IBinder onBind(Intent intent) {
        printf("onBind()");
        return mainManager.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainManager = new MainManager();
        printf("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printf("onStartCommand intent = " + intent);
        printf("onStartCommand flags = " + flags);
        printf("onStartCommand startId = " + startId);
        mainManager.callback(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        printf("onDestroy()");
        super.onDestroy();
        mainManager = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        printf("onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        printf("onRebind()");
        super.onRebind(intent);
    }

    private void printf(String str) {
        MyLog.i(str);
    }
}
