package android.app.gpsdemo;

interface IMessageControl {
    public static final int LOCATION_CHANGED = 0;
    public static final int PROVIDER = 1;
    public static final int STATUS_CHANGED = 2;
    public static final int GPS_STATUS_CHANGED = 3;

    /**
     * 发送消息
     *
     * @param msg
     */
    public abstract void sendMessage(int what, Object obj);
}
