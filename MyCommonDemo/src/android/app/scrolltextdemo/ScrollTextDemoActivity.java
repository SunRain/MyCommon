package android.app.scrolltextdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;

public class ScrollTextDemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolltextdemo_main);

		/*
         * 这2种跑马灯稍微有点区别，需要掂量着使用。 第1种跑马灯不能设置速度，并且要超过一行才会滚动。
		 * 第2种跑马灯只能设置单一颜色，且需要在代码中设置，不能同时设置多种颜色。速度设的不要过大(<= 2.0f)，否则停顿现象比较明 显。
		 */
        AutoScrollTextView marquee = (AutoScrollTextView) findViewById(R.id.marqueeScrollTextDemo);
        //
        marquee.setText("上证指数3000.15 6.81(0.37%)深圳成指3000.15 6.81(0.37%)");
        marquee.setTextColor(0xffff0000);// 注意：颜色必须在这里设置，xml中设置无效！默认黑色。
        //
        // 如果想改变跑马灯的文字内容或者文字效果，则在调用完setText方法之后，需要再调用一下init(width)方法，重新进行初始化和相关参数的计算。
        marquee.setSpeed(1.5f);
        marquee.init(320f);// width通常就是屏幕宽！
        marquee.startScroll();
    }
}
