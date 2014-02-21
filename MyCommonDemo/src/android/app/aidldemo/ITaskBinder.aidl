package android.app.aidldemo;

import android.app.aidldemo.ITaskCallback;

interface ITaskBinder {
    boolean isTaskRunning();

    void stopRunningTask();

    void registerCallback(ITaskCallback cb);

    void unregisterCallback(ITaskCallback cb);
}
