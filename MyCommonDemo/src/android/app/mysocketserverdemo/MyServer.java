package android.app.mysocketserverdemo;

import android.app.mysocketserverdemo.model.RemoteObject;
import android.app.mysocketserverdemo.socket.ISocketTaskListener;
import android.app.mysocketserverdemo.socket.ServerTask;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import wd.android.util.thread.ThreadPool;

public class MyServer {

    private static final int PORT = 9999;// 端口监听
    private List<Socket> mList = new ArrayList<Socket>();// 存放客户端socket
    private ServerSocket mServer = null;
    private volatile boolean isBreak = true;
    private ThreadPool mThreadPool = null;

    public static void main(String[] args) {
        new MyServer().start();
    }

    public MyServer() {
    }

    public void start() {
        isBreak = false;
        try {
            mServer = new ServerSocket(PORT);
            System.out.println("Server Start...");
            Socket client = null;
            while (isBreak) {
                client = mServer.accept();
                mList.add(client);
                mThreadPool = new ThreadPool(1, 1);
                mThreadPool.execute(new ServerTask(client,
                        new ISocketTaskListener() {
                            @Override
                            public void onExit(RemoteObject remoteObj) {
                            }

                            @Override
                            public void onActivate(RemoteObject remoteObj) {

                            }
                        }));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        mThreadPool.release();
        isBreak = true;
    }

}
