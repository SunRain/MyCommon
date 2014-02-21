package android.app.floatviewdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.nbtstatx.mydemos.R;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatViewFactory {
    public static FloatView createView(Context context) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        FloatView myFV = new FloatView(context, wmParams);
        myFV.setImageResource(R.drawable.icon);
        myFV.setFocusable(true);
        myFV.setActivated(true);
        myFV.setClickable(true);
        // 获取WindowManager
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService("window");
        // 设置LayoutParams(全局变量）相关参数
        // wmParams = wmParams.getMywmParams();
        wmParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
        // 设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        // wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
        // | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        // wmParams.flags = 32;
        // 下面的flags属性效果形同“锁定”，即不可触摸，不接受任何事件，同时不影响后面的事件响应。
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_NOT_TOUCHABLE;

        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 100;
        wmParams.y = 100;

        // 设置悬浮窗口长宽数据
        wmParams.width = 40;
        wmParams.height = 40;

        // 显示myFloatView图像
        wm.addView(myFV, wmParams);
        return myFV;
    }
}
