package android.app.activitymanagerdemo.android.os;

import wd.android.util.util.MyLog;

public class MyBinder implements IMyBinder {
    private String mDescriptor;
    private IMyInterface mOwner;

    @Override
    public final boolean transact(int code, MyParcel data, MyParcel reply,
                                  int flags) {
        MyLog.e(
                "transact(" + code + "," + data + "," + reply + "," + flags
                        + ")");
        if (data != null) {
            data.setDataPosition(0);
        }
        boolean r = onTransact(code, data, reply, flags);
        if (reply != null) {
            reply.setDataPosition(0);
        }
        return r;
    }

    private boolean execTransact(int code, int dataObj, int replyObj, int flags) {
        MyParcel data = MyParcel.obtain(dataObj);
        MyParcel reply = MyParcel.obtain(replyObj);
        // theoretically, we should call transact, which will call onTransact,
        // but all that does is rewind it, and we just got these from an IPC,
        // so we'll just call it directly.
        boolean res;
        res = onTransact(code, data, reply, flags);
        reply.recycle();
        data.recycle();
        return res;
    }

    public void attachInterface(IMyInterface owner, String descriptor) {
        MyLog.e(
                "attachInterface(" + owner + "," + descriptor + ")");
        mOwner = owner;
        mDescriptor = descriptor;
    }

    @Override
    public IMyInterface queryLocalInterface(String descriptor) {
        MyLog.e(
                "queryLocalInterface(" + descriptor + "):mDescriptor == "
                        + mDescriptor);
        if (mDescriptor.equals(descriptor)) {
            return mOwner;
        }
        return null;
    }

    public String getInterfaceDescriptor() {
        MyLog.e("getInterfaceDescriptor()");
        return mDescriptor;
    }

    protected boolean onTransact(int code, MyParcel data, MyParcel reply,
                                 int flags) {
        MyLog.e(
                "onTransact(" + code + "," + data + "," + reply + "," + flags
                        + ")");
        if (code == INTERFACE_TRANSACTION) {
            reply.writeString(getInterfaceDescriptor());
            return true;
        }
        return false;
    }
}

// final class MyBinderProxy implements IMyBinder {
// private static final String TAG = "MyBinderProxy";
//
// @Override
// public final boolean transact(int code, MyParcel data, MyParcel reply,
// int flags) {
// return false;
// if (data != null) {
// data.setDataPosition(0);
// }
// boolean r = onTransact(code, data, reply, flags);
// if (reply != null) {
// reply.setDataPosition(0);
// }
// return r;
// }
//
// @Override
// public IMyInterface queryLocalInterface(String descriptor) {
// return null;
// }
//
// @Override
// public String getInterfaceDescriptor() {
// return null;
// }
// }
