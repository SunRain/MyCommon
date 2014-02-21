package me.android.app.manager;

import java.io.File;

import me.android.common.http.MyHttp;

import org.apache.http.Header;

import wd.android.util.util.MyLog;
import android.content.Context;
import android.os.Handler;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpDownloadHolder {
	public static enum Config {
		/**
		 * 根据时间
		 */
		CONFIG_TIME,
		/**
		 * 根据大小
		 */
		CONFIG_SIZE
	}

	private Config config = Config.CONFIG_TIME;

	private static int DELAY_MILLIS = 10 * 1000;
	private static int SIZE = 100 * 1024;

	private MyHttp myHttp = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 取消下载
			synchronized (HttpDownloadHolder.this) {
				MyLog.i("cancel myHttp = " + myHttp);
				if (null == myHttp) {
					return;
				}
				myHttp.cancel();
				myHttp = null;
			}
		};
	};

	public HttpDownloadHolder init() {
		myHttp = null;
		return this;
	}

	public void deInit() {
		cancel(0);
	}

	private void cancel(long delayMillis) {
		if (delayMillis > 0) {
			handler.sendEmptyMessageDelayed(0, delayMillis);
		} else {
			handler.obtainMessage(0).sendToTarget();
		}
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public synchronized void start(Context context, String url) {
		MyLog.i("start config = " + config);

		if (myHttp != null) {
			MyLog.e("myHttp != null");
			return;
		}

		myHttp = new MyHttp(context);

		if (config == Config.CONFIG_TIME) {
			startByTime(context, url);
		} else {
			startBySize(context, url);
		}
	}

	private class MyFileHandler extends FileAsyncHttpResponseHandler {
		public MyFileHandler(Context context) {
			super(context);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, File file) {
			cancel(0);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, File file) {
			cancel(0);
		}
	}

	private void startByTime(Context context, String url) {
		myHttp.exec(url, (RequestParams) null, new MyFileHandler(context) {
			@Override
			public void onStart() {
				super.onStart();
				cancel(DELAY_MILLIS);
			}
		});
	}

	private void startBySize(Context context, String url) {
		myHttp.exec(url, (RequestParams) null, new MyFileHandler(context) {
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				if (bytesWritten >= SIZE) {
					cancel(0);
				}
			}
		});
	}
}
