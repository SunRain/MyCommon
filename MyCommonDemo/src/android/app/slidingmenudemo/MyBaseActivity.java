package android.app.slidingmenudemo;

import java.util.Map;

import me.android.common.http.CacheHttpListener;
import me.android.custom.MainApp;
import wd.android.framework.ui.BaseActivity;
import android.os.Bundle;
import android.view.Window;

public abstract class MyBaseActivity extends BaseActivity {

	public static final int MSG_SHOW_PROGRESS = 0x01;
	public static final int MSG_DISMISS_PROGRESS = 0x02;

	// private LoadingDialog mLoadingDialog;

	@Override
	public void onBeforeContentView(Bundle savedInstanceState) {
		super.onBeforeContentView(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			// 恢复时结束进程重新打开
			((MainApp) MainApp.getApp()).restartApp(this);
			finish();
			return;
		}
		super.onCreateActivity(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	public class MyBaseHttpListener extends CacheHttpListener {
		@Override
		public void onFinish() {
			super.onFinish();
		}

		@Override
		public void onStart() {
			super.onStart();
		}

		@Override
		protected void onFailure(Throwable error,
				Map<String, Object> responseMap) {
			super.onFailure(error, responseMap);
		}

		@Override
		protected void onSuccess(int statusCode, Map<String, String> headers,
				Map<String, Object> responseMap) {

		}

	}

	;
}
