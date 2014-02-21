package android.app.mysocketserverdemo.socket;

import android.app.mysocketserverdemo.model.RemoteObject;

public interface ISocketTaskListener {
    void onActivate(RemoteObject remoteObj);

    void onExit(RemoteObject remoteObj);
}
