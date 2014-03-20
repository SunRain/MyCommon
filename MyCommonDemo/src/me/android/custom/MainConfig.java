package me.android.custom;

import wd.android.framework.AppConfig;
import wd.android.util.util.MyLog.MyLogManager.Level;
import wd.android.util.util.MyLog.MyLogManager.LogMode;

public class MainConfig extends AppConfig {
	@Override
	protected void initDatabase() {
		// DatabaseConfig.initDatabase(version, beanClazzs);
	}

	@Override
	protected boolean initCrashReportFlag() {
		return false;
	}

	@Override
	protected Level initLogLevel() {
		return Level.DEBUG;
	}

	@Override
	protected LogMode initLogMode() {
		return LogMode.LOGCAT;
	}

	private static final class StoreRoot {
		/**
		 * 测试
		 */
		private static final String WONDER_TEST_OUT = "http://211.136.104.111";
		/**
		 * 正式环境
		 */
		private static final String OFFICIAL = "http://211.136.104.111";
	}

	/**
	 * IP
	 */
	public static String SERVER_ROOT = StoreRoot.WONDER_TEST_OUT;

}
