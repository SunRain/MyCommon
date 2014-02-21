package android.app.telephonydemo;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import wd.android.util.util.MyLog;

public class MyPhoneManager {
    private Context mContext = null;
    private PhoneStateListener mPhoneStateListener = null;
    private TelephonyManager mTelephonyManager = null;

    /** */
    MyPhoneManager(Context context) {
        this.mContext = context;
        mPhoneStateListener = new PhoneListener(context, new MessageControl());
        mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     *
     */
    void release() {
        mContext = null;
        mPhoneStateListener = null;
        mTelephonyManager = null;
    }

    void startTelephony() {
        // 添加监听
        mTelephonyManager.listen(mPhoneStateListener,
                PhoneStateListener.LISTEN_CELL_LOCATION
                        | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | PhoneStateListener.LISTEN_CALL_STATE
                        | PhoneStateListener.LISTEN_DATA_ACTIVITY
                        | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    }

    void stopTelephony() {
        // 去监听
        mTelephonyManager.listen(null, PhoneStateListener.LISTEN_NONE);
    }

    private class MessageControl implements IMessageControl {
        @Override
        public void sendMessage(int what, Object... obj) {
            processMessage(what, obj);
        }
    }

    private void processMessage(int what, Object... obj) {
        if (what == IMessageControl.ON_DATA_CONNECTION_STATE_CHANGED) {
            String networkOperator = mTelephonyManager.getNetworkOperator();
            String simOperator = mTelephonyManager.getSimOperator();
            MyLog.i("networkOperator == " + networkOperator);
            MyLog.i("simOperator == " + simOperator);
            processonDataConnectionStateChanged(obj);
        }
    }

    private void processonDataConnectionStateChanged(Object... obj) {
        Object[] objs = obj;
        int state = (Integer) objs[0];
        switch (state) {
            case TelephonyManager.DATA_CONNECTED:
                break;
            case TelephonyManager.DATA_DISCONNECTED:
                break;
        }
    }
}
