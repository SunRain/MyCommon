package wd.android.framework.manager;

import java.util.HashMap;

import wd.android.util.util.MyLog;
import android.content.Context;

/**
 * Manager基类
 */
public abstract class BaseManager implements IManager {
	private volatile boolean isStart = false;
	private static HashMap<String, Object> mServices = new HashMap<String, Object>();

	@Override
	public final synchronized void create(Context context) {
		MyLog.i("isStart = " + isStart);
		if (isStart) {
			return;
		}
		isStart = true;
		mServices.clear();
		onCreate(context);
	}

	@Override
	public final synchronized void destroy() {
		MyLog.i("isStart = " + isStart);
		if (!isStart) {
			return;
		}
		isStart = false;
		onDestroy();
		mServices.clear();
	}

	/**
	 * 初始化资源
	 * 
	 * @param context
	 */
	protected abstract void onCreate(Context context);

	/**
	 * 与onCreate对应，释放资源
	 */
	protected abstract void onDestroy();

	/**
	 * 初始化管理模块
	 * 
	 * @param manager
	 */
	protected static void addService(Object manager) {
		mServices.put(manager.getClass().getSimpleName(), manager);
	}

	// public Object getService(Class<?> clazz) {
	// return mServices.get(clazz.getSimpleName());
	// }

	/**
	 * 获取单例服务对象
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> clazz) {
		return (T) mServices.get(clazz.getSimpleName());
	}
}
