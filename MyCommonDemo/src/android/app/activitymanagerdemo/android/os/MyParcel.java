package android.app.activitymanagerdemo.android.os;

import wd.android.util.util.MyLog;

public final class MyParcel {
    private static final String TAG = "MyParcel";
    private static final int POOL_SIZE = 6;
    private static final MyParcel[] sOwnedPool = new MyParcel[POOL_SIZE];
    private static final MyParcel[] sHolderPool = new MyParcel[POOL_SIZE];

    private int i = 0;
    private String str = null;

    private int mOwnObject; // used by native code

    private MyParcel(int obj) {
        init(obj);
    }

    private void init(int obj) {
        MyLog.e("init():obj == " + obj);
        i = obj;
    }

    private void freeBuffer() {
        MyLog.e("freeBuffer()");
    }

    public final void writeString(String val) {
        MyLog.e("writeString():val == " + val);
        str = val;
    }

    public final void writeInt(int val) {
        MyLog.e("writeInt():val == " + val);
        i = val;
    }

    public final int readInt() {
        MyLog.e("readInt()");
        return i;
    }

    public final String readString() {
        MyLog.e("readString()");
        return str;
    }

    static protected final MyParcel obtain(int obj) {
        final MyParcel[] pool = sHolderPool;
        synchronized (pool) {
            MyParcel p;
            for (int i = 0; i < POOL_SIZE; i++) {
                p = pool[i];
                if (p != null) {
                    pool[i] = null;
                    p.init(obj);
                    return p;
                }
            }
        }
        return new MyParcel(obj);
    }

    public static MyParcel obtain() {
        final MyParcel[] pool = sOwnedPool;
        synchronized (pool) {
            MyParcel p;
            for (int i = 0; i < POOL_SIZE; i++) {
                p = pool[i];
                if (p != null) {
                    pool[i] = null;
                    return p;
                }
            }
        }
        return new MyParcel(0);
    }

    public final void recycle() {
        freeBuffer();
        final MyParcel[] pool = mOwnObject != 0 ? sOwnedPool : sHolderPool;
        synchronized (pool) {
            for (int i = 0; i < POOL_SIZE; i++) {
                if (pool[i] == null) {
                    pool[i] = this;
                    return;
                }
            }
        }
    }

    public final void setDataPosition(int pos) {
        MyLog.e("setDataPosition(" + pos + ")");
    }

    public final void setDataCapacity(int size) {
        MyLog.e("setDataCapacity(" + size + ")");
    }

    public final void enforceInterface(String interfaceName) {
        MyLog.e("enforceInterface(" + interfaceName + ")");
    }

    public final IMyBinder readStrongBinder() {
        MyLog.e("readStrongBinder()");
        return null;
    }
}
