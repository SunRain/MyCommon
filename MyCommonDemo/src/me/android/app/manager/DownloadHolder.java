package me.android.app.manager;

import me.android.common.download.DownloadManagerPro.OnCompleteListener;
import me.android.common.download.MyDownloadManager;
import wd.android.framework.BaseApp;
import wd.android.util.util.MyLog;
import android.database.Cursor;
import android.os.Handler;

import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.DownloadManager.Query;

public class DownloadHolder {
	/**
	 * 根据时间
	 */
	private static final int CONFIG_TIME = 1;
	/**
	 * 根据大小
	 */
	private static final int CONFIG_SIZE = 2;

	private int config = CONFIG_TIME;

	private static int DELAY_MILLIS = 10 * 1000;
	private static int SIZE = 100 * 1024;

	private MyDownloadManager downloadManager = new MyDownloadManager();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 取消下载
			synchronized (DownloadHolder.this) {
				MyLog.i("cancel download,id = " + id);
				// if (id != -1) {
				// return;
				// }
				downloadManager.delete(id);
				downloadManager.deInit();
				id = -1;
			}
		};
	};

	private OnCompleteListener onCompleteListener = new OnCompleteListener() {
		@Override
		public void onDownloadSuccess(Query downloadQuery) {
			handler.obtainMessage(0).sendToTarget();
		}

		@Override
		public void onDownloadFailure(Query downloadQuery) {
			handler.obtainMessage(0).sendToTarget();
		}
	};

	private long id = -1;

	public DownloadHolder init() {
		downloadManager.addOnCompleteListener(onCompleteListener);
		return this;
	}

	public void deInit() {
		downloadManager.removeOnCompleteListener(onCompleteListener);
		handler.obtainMessage(0).sendToTarget();
	}

	public void setConfig(int config) {
		this.config = config;
	}

	public void start(String url) {
		MyLog.i("config = " + config);

		if (id != -1) {
			MyLog.e("downloadManager != null,id = " + id);
			return;
		}

		downloadManager.init(BaseApp.getApp());

		id = downloadManager.start(url);

		if (config == CONFIG_TIME) {
			startByTime();
		} else {
			startBySize();
		}
	}

	private synchronized void startByTime() {
		handler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
	}

	private synchronized void startBySize() {
		new Thread() {// 开启新的线程查询下载状态
			@Override
			public void run() {
				while (true) {
					try {
						DownloadManager.Query baseQuery = downloadManager
								.query(id).setOnlyIncludeVisibleInDownloadsUi(
										true);
						Cursor c = downloadManager.query(baseQuery);
						c.moveToFirst();
						int size = c
								.getInt(c
										.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
						if (size >= SIZE || id == -1) {
							MyLog.i("size = " + size);
							handler.obtainMessage(0).sendToTarget();
							break;
						}
						Thread.sleep(500);
					} catch (InterruptedException e) {
						MyLog.e(e);
					}
				}
			}
		}.start();
	}
}
