package android.app.gpsdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GpsDemoActivity extends Activity {
    private final String TAG = "TestGpsActivity";

    private Button btnOpenGpsTestGps = null;
    private Button btnCloseGpsTestGps = null;

    private TextView textLongitude = null;
    private TextView textLatitude = null;
    private TextView textProvider = null;
    private TextView textStatusChanged = null;
    private TextView textGpsStatusChanged = null;

    private MyGps mGps = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.gpsdemo_main);

        initViews();
        mGps = new MyGps(this, new MessageControl());
    }

    /**
     * 初始化views
     */
    private void initViews() {
        btnOpenGpsTestGps = (Button) findViewById(R.id.btnOpenGpsTestGps);
        btnCloseGpsTestGps = (Button) findViewById(R.id.btnCloseGpsTestGps);

        btnOpenGpsTestGps.setOnClickListener(clickListener);
        btnCloseGpsTestGps.setOnClickListener(clickListener);

        textLongitude = (TextView) findViewById(R.id.textLongitudeTestGps);
        textLatitude = (TextView) findViewById(R.id.textLatitudeTestGps);
        textProvider = (TextView) findViewById(R.id.textProviderTestGps);
        textStatusChanged = (TextView) findViewById(R.id.textStatusChangedTestGps);
        textGpsStatusChanged = (TextView) findViewById(R.id.textGpsStatusChangedTestGps);
    }

    private View.OnClickListener clickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnOpenGpsTestGps:
                    mGps.openGps();
                    break;
                case R.id.btnCloseGpsTestGps:
                    mGps.closeGps();
                    break;
                default:
                    break;
            }
        }
    };

    private void updateGpsInfo(MyGpsInfo gpsInfo) {
        Log.d(TAG, "gps...update gpdInfo");
        textLongitude.setText(String.valueOf(gpsInfo.getLongitude()));
        textLatitude.setText(String.valueOf(gpsInfo.getLatitude()));
    }

    private void updateProvider(String str) {
        textProvider.setText(str);
    }

    private void updateStatusChanged(String str) {
        textStatusChanged.setText(str);
    }

    private void updateGpsStatusChanged(String str) {
        textGpsStatusChanged.setText(str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
        Log.d(TAG, "onDestroy()");
    }

    /**
     * 释放资源
     */
    private void release() {
        if (null != mGps) {
            mGps.release();
            mGps = null;
        }
        mHandler = null;
        btnOpenGpsTestGps = null;
        btnCloseGpsTestGps = null;
        textLongitude = null;
        textLatitude = null;
        textProvider = null;
        textStatusChanged = null;
        textGpsStatusChanged = null;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMessageControl.LOCATION_CHANGED:
                    updateGpsInfo((MyGpsInfo) msg.obj);
                    break;
                case IMessageControl.PROVIDER:
                    updateProvider((String) msg.obj);
                    break;
                case IMessageControl.STATUS_CHANGED:
                    updateStatusChanged((String) msg.obj);
                    break;
                case IMessageControl.GPS_STATUS_CHANGED:
                    updateGpsStatusChanged((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressWarnings("unused")
    private void shoeToast(String str) {
        Toast.makeText(GpsDemoActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 消息处理器
     *
     * @author gKF38388
     */
    private class MessageControl implements IMessageControl {
        @Override
        public void sendMessage(int what, Object obj) {
            Message msg = mHandler.obtainMessage(what, obj);
            mHandler.sendMessage(msg);
        }

    }
}