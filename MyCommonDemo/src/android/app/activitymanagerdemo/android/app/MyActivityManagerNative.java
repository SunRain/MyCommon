package android.app.activitymanagerdemo.android.app;

import android.app.activitymanagerdemo.MyServiceManager;
import android.app.activitymanagerdemo.android.os.IMyBinder;
import android.app.activitymanagerdemo.android.os.MyBinder;
import android.app.activitymanagerdemo.android.os.MyParcel;

import wd.android.util.util.MyLog;

public abstract class MyActivityManagerNative extends MyBinder implements
        IMyActivityManager {

    public IMyBinder asBinder() {
        MyLog.e("asBinder()");
        return this;
    }

    private static IMyActivityManager gDefault;

    static public IMyActivityManager asInterface(IMyBinder obj) {
        MyLog.e("asInterface(" + obj + ")");
        if (obj == null) {
            return null;
        }
        IMyActivityManager in = (IMyActivityManager) obj
                .queryLocalInterface(descriptor);
        if (in != null) {
            return in;
        }

        return new MyActivityManagerProxy(obj);
    }

    static public IMyActivityManager getDefault() {
        MyLog.e("getDefault()");
        if (gDefault != null) {
            return gDefault;
        }
        IMyBinder b = MyServiceManager.getService("MyActivityManagerService");
        gDefault = asInterface(b);
        return gDefault;
    }

    @Override
    public boolean onTransact(int code, MyParcel data, MyParcel reply, int flags) {
        MyLog.e(
                "onTransact(" + code + "," + data + "," + reply + "," + flags
                        + ")");
        switch (code) {
            case TRANSACTION_start: {
                data.enforceInterface(IMyActivityManager.descriptor);
                // IMyBinder b = data.readStrongBinder();
                int i = data.readInt();
                String str = data.readString();
                int result = start(i, str);
                reply.writeInt(result);
                return true;
            }

            case TRANSACTION_stop: {
                data.enforceInterface(IMyActivityManager.descriptor);
                // IMyBinder b = data.readStrongBinder();
                int i = data.readInt();
                int result = stop(i);
                reply.writeInt(result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}

class MyActivityManagerProxy implements IMyActivityManager {
    private static final String TAG = "MyActivityManagerProxy";
    private IMyBinder mRemote;

    public MyActivityManagerProxy(IMyBinder remote) {
        MyLog.e("MyActivityManagerProxy(" + remote + ")");
        mRemote = remote;
    }

    public IMyBinder asBinder() {
        MyLog.e("asBinder()");
        return mRemote;
    }

    @Override
    public int start(int i, String str) {
        MyLog.e("start(" + i + "," + str + ")");
        MyParcel data = MyParcel.obtain();
        MyParcel reply = MyParcel.obtain();
        data.writeInt(i);
        data.writeString(str);
        mRemote.transact(TRANSACTION_start, data, reply, 0);
        int result = reply.readInt();
        reply.recycle();
        data.recycle();
        return result;
    }

    @Override
    public int stop(int i) {
        MyLog.e("stop(" + i + ")");
        MyParcel data = MyParcel.obtain();
        MyParcel reply = MyParcel.obtain();
        data.writeInt(i);
        mRemote.transact(TRANSACTION_stop, data, reply, 0);
        int result = reply.readInt();
        reply.recycle();
        data.recycle();
        return result;
    }
}