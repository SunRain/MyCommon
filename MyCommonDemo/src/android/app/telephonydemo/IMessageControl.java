package android.app.telephonydemo;

interface IMessageControl {
    public static final int ON_DATA_CONNECTION_STATE_CHANGED = 0x001;

    /**
     * 发送消息
     *
     * @param msg
     */
    public abstract void sendMessage(int what, Object... obj);
}
