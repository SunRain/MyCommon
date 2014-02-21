package android.app.screendemo;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class MyScreenLockManager {
    private static final String TAG = "MyScreenLockManager";
    private Context mContext = null;
    private WakeLock mWakeLock = null;
    private PowerManager mPowerManager = null;
    private KeyguardManager mKeyguardManager = null;
    private KeyguardLock mKeyguardLock = null;
    private boolean isUnlockScreen = false;

    /**
     * @param mContext
     */
    public MyScreenLockManager(Context context) {
        mContext = context;
    }

    // public static void unlockScreen(Context context) {
    // // 获取PowerManager的实例
    // PowerManager mPowerManager = (PowerManager) context
    // .getSystemService(Context.POWER_SERVICE);
    // // 得到一个WakeLock唤醒锁
    // WakeLock mWakelock = mPowerManager.newWakeLock(
    // PowerManager.FULL_WAKE_LOCK
    // | PowerManager.ACQUIRE_CAUSES_WAKEUP
    // | PowerManager.ON_AFTER_RELEASE, TAG);
    // // if (!mWakelock.isHeld()) {
    // if (!mPowerManager.isScreenOn()) {
    // // 唤醒屏幕
    // mWakelock.acquire();
    // }
    //
    // // 获得一个KeyguardManager的实例
    // KeyguardManager mKeyguardManager = (KeyguardManager) context
    // .getSystemService(Context.KEYGUARD_SERVICE);
    // // 得到一个键盘锁KeyguardLock
    // KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock(TAG);
    // if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
    // // 解锁键盘
    // mKeyguardLock.disableKeyguard();
    // // isScreenLock = true;
    // } else {
    // // isScreenLock = false;
    // }
    // }

    /**
     * 唤醒屏幕
     */
    public void acquireWakeLock() {
        releaseWakeLock();

        // 获取PowerManager的实例
        mPowerManager = (PowerManager) mContext
                .getSystemService(Context.POWER_SERVICE);
        // 得到一个WakeLock唤醒锁
        mWakeLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, TAG);
        // 获得一个KeyguardManager的实例
        mKeyguardManager = (KeyguardManager) mContext
                .getSystemService(Context.KEYGUARD_SERVICE);
        // 得到一个键盘锁KeyguardLock
        mKeyguardLock = mKeyguardManager.newKeyguardLock(TAG);

        // if (!mWakelock.isHeld()) {
        if (!mPowerManager.isScreenOn()) {
            // 唤醒屏幕
            mWakeLock.acquire();
            isUnlockScreen = true;
        }

        if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
            // 解锁键盘
            mKeyguardLock.disableKeyguard();
        }
    }

    public void releaseWakeLock() {
        if (!isUnlockScreen) {
            return;
        }

        // 获取PowerManager的实例
        if (!mKeyguardManager.inKeyguardRestrictedInputMode()) {
            // 锁键盘
            mKeyguardLock.reenableKeyguard();
        }
        // 使屏幕休眠
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }

    public void release() {
        if (isUnlockScreen) {
            mWakeLock.release();
            isUnlockScreen = false;
        }
        mContext = null;
        mWakeLock = null;
        mPowerManager = null;
        mKeyguardManager = null;
        mKeyguardLock = null;
    }
    // public static void lockScreen(Context context) {
    // // 获取PowerManager的实例
    // PowerManager mPowerManager = (PowerManager) context
    // .getSystemService(Context.POWER_SERVICE);
    // // 得到一个WakeLock唤醒锁
    // WakeLock mWakelock = mPowerManager.newWakeLock(
    // PowerManager.FULL_WAKE_LOCK
    // | PowerManager.ACQUIRE_CAUSES_WAKEUP
    // | PowerManager.ON_AFTER_RELEASE, TAG);
    // // 获得一个KeyguardManager的实例
    // KeyguardManager mKeyguardManager = (KeyguardManager) context
    // .getSystemService(Context.KEYGUARD_SERVICE);
    // // 得到一个键盘锁KeyguardLock
    // KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock(TAG);
    //
    // // 获取PowerManager的实例
    // if (!mKeyguardManager.inKeyguardRestrictedInputMode()) {
    // // 锁键盘
    // mKeyguardLock.reenableKeyguard();
    // }
    // // 使屏幕休眠
    // if (mWakelock.isHeld()) {
    // mWakelock.release();
    // }
    // }
}