package android.app.wifidemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class WifiStateChanged {
    private Context mContext = null;
    private WifiStateListener mWifiStateListener = null;

    public WifiStateChanged(Context context, WifiStateListener wifiStateListener) {
        mContext = context;
        mWifiStateListener = wifiStateListener;
        mContext.registerReceiver(mWifiStateReceiver, new IntentFilter(
                WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    /**
     *
     */
    public void release() {
        mContext.unregisterReceiver(mWifiStateReceiver);
        mContext = null;
        mWifiStateListener = null;
        mWifiStateReceiver = null;
    }

    private BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("gggggggg", "ggggggg intent == " + intent);
            Bundle bundle = intent.getExtras();
            // int oldInt = bundle.getInt("previous_wifi_state");
            int newInt = bundle.getInt("wifi_state", 4);
            switch (newInt) {
                case WifiManager.WIFI_STATE_DISABLED:
                    mWifiStateListener.process("WIFI_STATE_DISABLED");
                    break;

                case WifiManager.WIFI_STATE_DISABLING:
                    mWifiStateListener.process("WIFI_STATE_DISABLING");
                    break;

                case WifiManager.WIFI_STATE_ENABLED:
                    mWifiStateListener.process("WIFI_STATE_ENABLED");
                    break;

                case WifiManager.WIFI_STATE_ENABLING:
                    mWifiStateListener.process("WIFI_STATE_ENABLING");
                    break;

                case WifiManager.WIFI_STATE_UNKNOWN:
                    mWifiStateListener.process("WIFI_STATE_UNKNOWN");
                    break;
            }
        }
    };

    /**
     * 电量监听接口
     */
    public interface WifiStateListener {
        public abstract void process(String str);
    }
}
