package android.app.telephonydemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.widget.TextView;

public class TelephonyDemoActivity extends Activity {
    private TextView txtTelephonyDemo = null;
    private MyPhoneManager myPhoneManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telephonydemo_main);
        initViews();

        int mcc = this.getResources().getConfiguration().mcc;
        int mnc = this.getResources().getConfiguration().mnc;
        txtTelephonyDemo.setText("mcc == " + mcc + ",mnc == " + mnc);
        myPhoneManager = new MyPhoneManager(this);
        myPhoneManager.startTelephony();
    }

    private void initViews() {
        txtTelephonyDemo = (TextView) findViewById(R.id.txtTelephonyDemo);
    }

    @Override
    protected void onDestroy() {
        myPhoneManager.stopTelephony();
        myPhoneManager.release();
        super.onDestroy();
    }
}
