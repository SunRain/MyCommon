package android.app.screendemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ScreenDemoActivity extends Activity {
    private static final String TAG = "ScreenDemoActivity";
    private ScreenListener mScreenListener = null;
    private Button btnUnlock, btnLock;
    private ClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 屏幕一直亮，禁止休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.screendemo_main);

        initViews();

        // PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        // WakeLock mWakelock =
        // pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
        // | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        // mWakelock.acquire();

        // WakeLock wakeLock = ((PowerManager) getSystemService(POWER_SERVICE))
        // .newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
        // | PowerManager.ON_AFTER_RELEASE, "MyActivity");
        // wakeLock.acquire();

        Log.e("ggg", TAG + "ggg onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ggg", TAG + "ggg onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除禁止休眠
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (null != mScreenListener) {
            mScreenListener.release();
            mScreenListener = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ggg", TAG + "ggg onStart()");
    }

    private void initViews() {
        btnUnlock = (Button) findViewById(R.id.btnUnlockScreenDemo);
        btnLock = (Button) findViewById(R.id.btnLockScreenDemo);
        clickListener = new ClickListener();
        btnUnlock.setOnClickListener(clickListener);
        btnLock.setOnClickListener(clickListener);
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnUnlockScreenDemo:
                    mScreenListener = new ScreenListener(ScreenDemoActivity.this);
                    break;
                case R.id.btnLockScreenDemo:
                    // mScreenListener.unRegisterScreenListener();
                    // MyScreenLockManager.lockScreen(ScreenDemoActivity.this);
                    // mScreenListener.lockScreen();
                    break;
                default:
                    break;
            }
        }
    }
}
