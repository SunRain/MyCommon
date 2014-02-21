package me.android.app.testdemo;

import me.android.app.MyUIUtil;
import me.android.app.manager.HttpDownloadHolder;
import wd.android.util.util.MyLog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import de.greenrobot.event.EventBus;

public class TestDemoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		controlBus = new EventBus();
		controlBus.register(this);

		controlBus.post(new MyTestEvent("gggg"));

		// DownloadHolder downloadHolder = new DownloadHolder().init();
		// downloadHolder.setConfig(2);
		// String url =
		// "http://dl.coolapk.com/dl2.php?dl=apk/4599/com.coolapk.market/%E9%85%B7%E5%B8%82%E5%9C%BA_CoolMarket/2.5.0/2013%2F0925%2Fcom.coolapk.market.apk&v=1wd2NJ";
		// downloadHolder.start(url);

		// HttpDownloadHolder downloadHolder = new HttpDownloadHolder().init();
		// downloadHolder.setConfig(1);
		// downloadHolder.start(url);
	}

	private EventBus controlBus;

	public void onEvent(MyTestEvent event) {
		MyLog.e("ggggg = " + event.getName());
		MyUIUtil.showToast("ggggg = " + event.getName());
	}

	@Override
	protected void onDestroy() {
		controlBus.unregister(this);
		super.onDestroy();
	}

}
