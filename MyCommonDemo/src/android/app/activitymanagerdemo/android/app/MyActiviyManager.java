package android.app.activitymanagerdemo.android.app;

public class MyActiviyManager {
    /**
     *
     */
    public int start(int i, String str) {
        return MyActivityManagerNative.getDefault().start(i, str);
    }

    public int stop(int i) {
        return MyActivityManagerNative.getDefault().stop(i);
    }
}
