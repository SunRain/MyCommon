package android.app.activitymanagerdemo.android.app;

import android.app.activitymanagerdemo.android.os.IMyBinder;
import android.app.activitymanagerdemo.android.os.IMyInterface;

public interface IMyActivityManager extends IMyInterface {

    public abstract int start(int i, String str);

    public abstract int stop(int i);

    String descriptor = "com.nbtstatx.mydemos.activitymanagerdemo.IActivityManager";
    int TRANSACTION_start = IMyBinder.FIRST_CALL_TRANSACTION + 1;
    int TRANSACTION_stop = IMyBinder.FIRST_CALL_TRANSACTION + 2;
}
