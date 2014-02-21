package android.app.alarmservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/*
 * 当这个Service启动，会使用btEvent改变标题，然后使用Toast显示一句提示语
 */
public class NotifyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        AlarmServiceActivity app = AlarmServiceActivity.getApp();
        app.btEvent("from NotifyService");
        Toast.makeText(this, "hello my iceskysl", Toast.LENGTH_SHORT).show();
    }

}
