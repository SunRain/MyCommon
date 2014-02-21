package android.app.alarmservicedemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Calendar;

/*
 * Service声明周期为onCreate，onStart，onDestory。
 * 1，通过startService启动
 * 		onCreate --> onStart,Service停止的时候会直接进入onDestory。而如果直接退出没有调用stopService,Service会一直在后台执行。
 * 2，通过bindService
 * 		只会运行onCreate，这个时候将TestServiceHolder和testService绑定在一起，如果testService退出了，Service就会调用onBind --> onDestory。
 * Service的onCreate方法只会被调用一次。如果先绑定（bind）了，那么请启动的时候就直接运行onStart，如果先是启动（start），那么绑定的时候就直接运行onBind。
 * 如果先绑定了，就停止不掉了，也就是stopService不能用了，只能先UnbindService再StopService，所以先启动（start）还是先绑定（bind）行为是有区别的。
 */

/*
 * 单击第一个按钮后，启动一个Service，然后等待15秒后自动停止，或者单击exit按钮停止。
 */
public class AlarmServiceActivity extends Activity implements OnClickListener {
    private static AlarmServiceActivity appRef = null;

    private Button b_call_Service, b_exit_Service;

    boolean k = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appRef = this;
        // 设定模板
        setContentView(R.layout.alarmservicedemo_main);
        b_call_Service = (Button) findViewById(R.id.callAlarmServiceDemo);
        b_call_Service.setOnClickListener(this);
        b_exit_Service = (Button) findViewById(R.id.exitAlarmServiceDemo);
        b_exit_Service.setOnClickListener(this);
    }

    public static AlarmServiceActivity getApp() {
        return appRef;
    }

    public void btEvent(String data) {
        setTitle(data);
    }

    // 单击响应方法
    public void onClick(View v) {
        // 如果是启动按钮被单击
        switch (v.getId()) {
            case R.id.callAlarmServiceDemo:
                setTitle("Waiting... Alarm=2");
                Intent intent = new Intent(AlarmServiceActivity.this,
                        AlarmReceiver.class);
                PendingIntent p_intent = PendingIntent.getBroadcast(
                        AlarmServiceActivity.this, 0, intent, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 2);
                // 得到一个定时的服务实例
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        p_intent);
                break;
            case R.id.exitAlarmServiceDemo:
                Intent intent2 = new Intent(AlarmServiceActivity.this,
                        AlarmReceiver.class);
                PendingIntent p_intent2 = PendingIntent.getBroadcast(
                        AlarmServiceActivity.this, 0, intent2, 0);
                AlarmManager am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                am2.cancel(p_intent2);
                finish();
                break;
        }
    }
}
