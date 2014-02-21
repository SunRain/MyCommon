package android.app.screendemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ScreenListener {
    private static final String TAG = "ScreenListener";
    private Context mContext = null;
    private MyScreenLockManager myScreenLockManager = null;

    /**
     * @param mContext
     */
    public ScreenListener(Context context) {
        super();
        this.mContext = context;
        myScreenLockManager = new MyScreenLockManager(mContext);
        mContext.registerReceiver(mBoradcastReceiver, new IntentFilter(
                Intent.ACTION_SCREEN_OFF));
        // mContext.registerReceiver(mBoradcastReceiver, new IntentFilter(
        // Intent.ACTION_SCREEN_ON));
    }

    public void release() {
        mContext.unregisterReceiver(mBoradcastReceiver);
        myScreenLockManager.release();
        myScreenLockManager = null;
        mBoradcastReceiver = null;
        mContext = null;
    }

    private BroadcastReceiver mBoradcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                Log.e("ggg", TAG + "gggg android.intent.action.SCREEN_ON");
                // myScreenLockManager.releaseLockScreen();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                Log.e("ggg", TAG + "gggg android.intent.action.SCREEN_OFF");
                myScreenLockManager.acquireWakeLock();
            } else {
                Log.e("ggg", TAG + "gggg");
            }
        }
    };
}
