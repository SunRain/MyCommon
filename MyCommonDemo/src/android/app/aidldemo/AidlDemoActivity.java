package android.app.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import wd.android.util.util.MyLog;

public class AidlDemoActivity extends Activity {
    private Button btnOk;
    private Button btnCancel;
    private boolean isBind = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidldemo_main);

        btnOk = (Button) findViewById(R.id.btnOkAidlDemo);
        btnCancel = (Button) findViewById(R.id.btnCancelAidlDemo);

        btnOk.setText("Start service");
        btnCancel.setText("Stop service");

        btnOk.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onOkClick();
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onCancelClick();
            }
        });
    }

    void onOkClick() {
        printf("clicked ok");
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, AidlDemoService.class);
        intent.putExtras(bundle);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        isBind = true;
        startService(intent);
    }

    void onCancelClick() {
        printf("clicked cancel");
        if (isBind) {
            unbindService(mConnection);
            isBind = false;
        }
    }

    private void printf(String str) {
        MyLog.i(str);
    }

    private ITaskBinder mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ITaskBinder.Stub.asInterface(service);

            printf("onServiceConnected()");
            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            printf("onServiceDisconnected()");
            mService = null;
        }
    };

    private ITaskCallback mCallback = new ITaskCallback.Stub() {
        public void actionPerformed(int actionId) throws RemoteException {
            printf("callback id = " + actionId);
        }
    };
}
