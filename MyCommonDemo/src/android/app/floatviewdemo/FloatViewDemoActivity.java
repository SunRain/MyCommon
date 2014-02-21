package android.app.floatviewdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.WindowManager;

public class FloatViewDemoActivity extends Activity {
    private FloatView mFloatView;
    private WindowManager wm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // android:theme="@android:style/Theme.Holo.NoActionBar"
        getActionBar().hide();
        // 创建悬浮窗口
        wm = (WindowManager) this.getApplicationContext().getSystemService(
                "window");
        // mFloatView = FloatViewFactory.createView(MainApp.getApp());
        mFloatView = FloatViewFactory.createView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在程序退出(Activity销毁）时销毁悬浮窗口
        wm.removeView(mFloatView);
    }
}
