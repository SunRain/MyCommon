package me.android.custom.am;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import me.android.custom.am.AppService.ServiceBinder;
import wd.android.util.util.MyLog;

public class AppBindHolder {
    private boolean isBind = false;
    private AppManager appService;
    private ServiceListener serviceListener;

    public AppBindHolder(ServiceListener serviceListener) {
        this.serviceListener = serviceListener;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceBinder binder = (ServiceBinder) service;
            appService = binder.getService();
            serviceListener.onServiceConnected(appService);
            // appService.notifyAppListener();
            // appService.getUpdateAppManager().notifyUpdateAppListener();
            MyLog.i("name = " + name);
        }

        public void onServiceDisconnected(ComponentName name) {
            MyLog.i("name = " + name);
            appService = null;
        }
    };

    public void bindService(Activity activity) {
        Intent intent = new Intent(activity, AppService.class);
        activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        isBind = true;
    }

    public void unbindService(Activity activity) {
        if (isBind) {
            activity.unbindService(mConnection);
            isBind = false;
        }
    }

    public AppManager getAppManager() {
        return appService;
    }

    public static interface ServiceListener {
        void onServiceConnected(AppManager appManager);
    }

}
