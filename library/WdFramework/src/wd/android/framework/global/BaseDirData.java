package wd.android.framework.global;

import java.io.File;
import java.util.HashMap;

import wd.android.util.util.EnvironmentInfo;
import wd.android.util.util.IoUtil;
import wd.android.util.util.MyLog;
import wd.android.util.util.ObjectUtil;
import android.content.Context;

/**
 * 目录管理基类
 */
public abstract class BaseDirData {
	public static final String WORK_PATH = "workpath";
	private File workDir = null;

	private HashMap<String, File> mDirs = ObjectUtil.newHashMap();

	public BaseDirData(Context context) {
		initWorkDir(context);
	}

	/**
	 * 初始化存储路径
	 */
	public void initWorkDir(Context context) {
		workDir = getRootDir(context);
		MyLog.i("workDir = " + workDir.getAbsolutePath());
		initDirs(context);
	}

	private File getRootDir(Context context) {
		File rootDir = new File(context.getFilesDir(), WORK_PATH);
		if (EnvironmentInfo.isExternalStorageUsable()) {
			// 删除files目录下文件
			IoUtil.deleteFiles(rootDir);
			// rootPath = EnvironmentInfo.getExternalStorageDirectory();
			rootDir = EnvironmentInfo.getExternalFilesDir(context, WORK_PATH);
		}
		return rootDir;
	}

	// /**
	// * 创建本地存储文件路径
	// */
	// private void createFolder(Context context) {
	// // Utils.createFolder(workPath);
	// for (String dir : mDirs.keySet()) {
	// // IoUtil.createDirector(mDirs.get(dir));
	// EnvironmentInfo.getExternalFilesDir(context, mDirs.get(dir));
	// }
	// }

	/**
	 * 获取目录
	 * 
	 * @param dir
	 * @return
	 */
	public File getDir(String dir) {
		// return new File(workDir, mDirs.get(dir)).getAbsolutePath()
		// + File.separator;
		return mDirs.get(dir);
	}

	protected void initDirs(Context context) {
		String[] dirs = getDirStrings();
		for (String dir : dirs) {
			// StringBuilder sb = new StringBuilder();
			// sb.append(dir);
			// if (!dir.endsWith(File.separator)) {
			// sb.append(File.separator);
			// }
			// mDirs.put(dir, dir);
			File file = new File(workDir, dir);
			file.mkdirs();
			mDirs.put(dir, file);
		}
	}

	/**
	 * 获取目录数组
	 * 
	 * @return
	 */
	public abstract String[] getDirStrings();
}
