package android.app.mysocketserverdemo.socket;

import android.app.mysocketserverdemo.model.RemoteObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import wd.android.util.util.MyLog;

public class ServerTask implements Runnable {
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private volatile boolean isStop = true;
    private ISocketTaskListener listener;

    public ServerTask(Socket socket, ISocketTaskListener listener) {
        this.socket = socket;
        this.listener = listener;
        try {
            inputStream = new ObjectInputStream(new BufferedInputStream(
                    socket.getInputStream()));
            outputStream = new ObjectOutputStream(new BufferedOutputStream(
                    socket.getOutputStream()));
            isStop = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                Object object = inputStream.readObject();
                if (object != null) {
                    RemoteObject remoteObj = (RemoteObject) object;
                    MyLog.i(remoteObj.code + "");
                    if (remoteObj.code == RemoteObject.CODE_EXIT) {
                        listener.onExit(remoteObj);
                        break;
                    } else {
                        listener.onActivate(remoteObj);
                    }
                } else {
                    MyLog.e("object = null");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MyLog.e(ex);
        } finally {
            isStop = true;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                MyLog.e(e);
            }
        }
    }

    /**
     * 发送消息给所有客户端
     */
    public void sendMsg(RemoteObject remoteObj) {
        if (isStop) {
            MyLog.e("isStop = " + isStop);
            return;
        }

        try {
            outputStream.writeObject(remoteObj);
        } catch (IOException e) {
            e.printStackTrace();
            MyLog.e(e);
        }
    }
}
