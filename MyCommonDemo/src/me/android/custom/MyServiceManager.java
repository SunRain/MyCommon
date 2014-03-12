package me.android.custom;

import me.android.common.db.MyDaoManager;
import me.android.custom.global.DirData;
import wd.android.framework.manager.CommonBaseManager;
import wd.android.framework.manager.ServiceHolder;
import wd.android.util.sdk.ExternalStorageReceiver;
import wd.android.util.sdk.ExternalStorageReceiver.ExternalStorageListener;
import android.content.Context;

public class MyServiceManager extends CommonBaseManager {
	private Context mContext;

	MyServiceManager() {
	}

	private ExternalStorageListener externalStorageListener = new ExternalStorageListener() {

		@Override
		public void onMediaMounted() {
			ServiceHolder.getService(DirData.class).initWorkDir(mContext);
		}

		@Override
		public void onMediaRemoved() {
			ServiceHolder.getService(DirData.class).initWorkDir(mContext);
		}
	};

	@Override
	protected void onCreate(Context context) {
		super.onCreate(context);
		mContext = context;
		// // 初始化存储路径，该对象无须释放资源
		DirData dirData = new DirData(context);
		addService(dirData);

		addService(new ExternalStorageReceiver(context, externalStorageListener));

		addService(new MyDaoManager().init(context));
	}

	@Override
	protected void onDestroy() {
		ServiceHolder.getService(ExternalStorageReceiver.class).release();
		ServiceHolder.getService(MyDaoManager.class).deInit();
		super.onDestroy();
	}
}
