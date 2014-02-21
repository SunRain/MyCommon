package android.app.httppostdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HttpPostDemoActivity extends Activity {
    private TextView txtValue = null;
    private EditText editStr = null;
    private EditText editUri = null;
    private Button btnResetUri = null;
    private Button btnReset = null;
    private Button btnSend = null;
    private ClickListener mClickListener = new ClickListener();
    private ProgressDialog proDialog = null;
    private String requestStr = null;

    // 声明spinner
    private Spinner spnList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httppostdemo_main);

        initViews();

        resetUri();
        resetStr();
    }

    private void initViews() {
        editStr = (EditText) findViewById(R.id.editStr_HttpPostDemo);
        editUri = (EditText) findViewById(R.id.editUri_HttpPostDemo);
        btnResetUri = (Button) findViewById(R.id.btnResetUri_HttpPostDemo);
        btnReset = (Button) findViewById(R.id.btnReset_HttpPostDemo);
        btnSend = (Button) findViewById(R.id.btnSend_HttpPostDemo);
        txtValue = (TextView) findViewById(R.id.txtValue_HttpPostDemo);

        btnResetUri.setOnClickListener(mClickListener);
        btnReset.setOnClickListener(mClickListener);
        btnSend.setOnClickListener(mClickListener);

        txtValue.setText("null");

        spnList = (Spinner) findViewById(R.id.spnList_HttpPostDemo);
        ArrayAdapter adpUri = ArrayAdapter
                .createFromResource(this, R.array.list_HostPostDemo,
                        android.R.layout.simple_spinner_item);
        adpUri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnList.setAdapter(adpUri);
        spnList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                switch (position) {
                    case 0:
                        MyHttpPostUtil.state = StringUtil.STATE_HOMENISS;
                        break;
                    case 1:
                        MyHttpPostUtil.state = StringUtil.STATE_HUAWEI;
                        break;
                }
                resetUri();
                resetStr();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void resetUri() {
        editUri.setText(MyHttpPostUtil.getUri());
    }

    private void resetStr() {
        editStr.setText(MyHttpPostUtil.getRequestStr());
    }

    private class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnResetUri_HttpPostDemo:
                    resetUri();
                    break;
                case R.id.btnReset_HttpPostDemo:
                    resetStr();
                    break;
                case R.id.btnSend_HttpPostDemo:
                    showProgressDialog();
                    new Thread(new SendThread()).start();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 显示一个进度条
     */
    private void showProgressDialog() {
        releaseProDialog();
        proDialog = ProgressDialog.show(HttpPostDemoActivity.this, null,
                "waiting...", true, false);
    }

    /**
     * 关闭ProgressDialog
     *
     * @param proDialog
     */
    private void releaseProDialog() {
        if (null != proDialog) {
            proDialog.dismiss();
            proDialog = null;
        }
    }

    private class SendThread extends Thread {
        @Override
        public void run() {
            super.run();
            requestStr = MyHttpPostUtil.getResponseStr(editUri.getText()
                    .toString(), editStr.getText().toString());
            Message msg = new Message();
            if (null != requestStr) {
                msg.what = StringUtil.SUCCESS;
                msg.obj = requestStr;
            } else {
                msg.what = StringUtil.FAILED;
            }
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            releaseProDialog();
            switch (msg.what) {
                case StringUtil.SUCCESS:
                    showToast("success");
                    txtValue.setText((String) msg.obj);
                    break;
                case StringUtil.FAILED:
                    showToast("failed");
                    txtValue.setText("null");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 显示toast
     *
     * @param str
     */
    private void showToast(CharSequence str) {
        Toast.makeText(HttpPostDemoActivity.this, str, Toast.LENGTH_SHORT)
                .show();
    }
}
