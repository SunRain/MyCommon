package android.app;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
    private static final String TAG = "MainService";
    public static final String ACTION_MYDEMOS = "com.nbtstatx.mydemos";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() intent == " + intent);
        Log.e(TAG, "onStartCommand() flags == " + flags);
        Log.e(TAG, "onStartCommand() startId == " + startId);

        String action = ACTION_MYDEMOS;
        if (null == intent) {
            if (startId % 4 == 0) {
                startService(new Intent(ACTION_MYDEMOS));
                return START_NOT_STICKY;
            }
        } else {
            action = intent.getAction();
        }

        super.onStartCommand(intent, flags, startId);
        return processAction(action);
    }

    private int processAction(String action) {
        if (ACTION_MYDEMOS.equals(action)) {
            // startBootTimer();
            // MainApplication.getApplication().sendMessage(
            // IMessageControl.START_SERVICE, null);
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");
    }
}
