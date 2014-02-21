package android.app.alarmservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * Activity,是应用的眼睛，其展示给用户，可以用来交互；而BroadcastReceiver是耳朵，其接收发生的Intent；Service则相对于手，其吧事情做完。
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, NotifyService.class));
    }

}
