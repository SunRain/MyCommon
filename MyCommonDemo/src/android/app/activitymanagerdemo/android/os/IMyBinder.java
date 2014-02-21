package android.app.activitymanagerdemo.android.os;

public interface IMyBinder {
    /**
     * The first transaction code available for user commands.
     */
    int FIRST_CALL_TRANSACTION = 0x00000001;

    int INTERFACE_TRANSACTION = ('_' << 24) | ('N' << 16) | ('T' << 8) | 'F';

    public abstract boolean transact(int code, MyParcel data, MyParcel reply,
                                     int flags);

    public IMyInterface queryLocalInterface(String descriptor);

    public String getInterfaceDescriptor();
}
