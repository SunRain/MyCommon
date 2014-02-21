package android.app.aidldemo;

import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import wd.android.util.util.MyLog;


public class MainManager {

    private void printf(String str) {
        MyLog.i(str);
    }

    IBinder getBinder() {
        return mBinder;
    }

    void callback(int val) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).actionPerformed(val);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }

    private final ITaskBinder.Stub mBinder = new ITaskBinder.Stub() {
        public void unregisterCallback(ITaskCallback cb) throws RemoteException {
            printf("unregisterCallback()");
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }

        public void stopRunningTask() throws RemoteException {
            printf("stopRunningTask()");
        }

        public void registerCallback(ITaskCallback cb) throws RemoteException {
            printf("registerCallback()");
            if (cb != null) {
                mCallbacks.register(cb);
            }
        }

        public boolean isTaskRunning() throws RemoteException {
            printf("isTaskRunning()");
            return false;
        }
    };

    private final RemoteCallbackList<ITaskCallback> mCallbacks = new RemoteCallbackList<ITaskCallback>();
}
