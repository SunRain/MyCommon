package me.android.common.db.wd;

import java.util.Set;

import me.android.common.db.BaseBean;
import wd.android.util.util.ObjectUtil;

public class DatabaseConfig {
	/**
	 * 数据库版本号
	 */
	static int DB_VERSION = 1;

	/**
	 * 数据库表集合
	 */
	static final Set<Class<? extends BaseBean>> TABLE_LIST = ObjectUtil
			.newHashSet();

	/**
	 * 初始化bean
	 * 
	 * @param beanClazz
	 */
	public static void addTable(Class<? extends BaseBean> beanClazz) {
		TABLE_LIST.add(beanClazz);
	}

	public static void initVersion(int version) {
		if (version > DB_VERSION) {
			DB_VERSION = version;
		}
	}
}
