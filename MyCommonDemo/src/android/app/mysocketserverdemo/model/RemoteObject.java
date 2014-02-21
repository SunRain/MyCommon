package android.app.mysocketserverdemo.model;

import java.io.Serializable;

public class RemoteObject implements Serializable {
    private static final long serialVersionUID = 6015958872913590800L;
    public static final int CODE_EXIT = -1;
    public static final int CODE_SUCCEED = 0;
    public int code = CODE_SUCCEED;
}
