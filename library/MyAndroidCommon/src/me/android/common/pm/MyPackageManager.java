package me.android.common.pm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wd.android.util.applications.AppUtil;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.Uri;

/**
 * PackageManager适配器，添加了静默卸载安装功能（该功能需要系统权限签名）
 */
final class MyPackageManager {

	private Context context;

	private ExecutorService threadPool;

	MyPackageManager(Context context) {
		this.context = context;
		threadPool = Executors.newCachedThreadPool();
	}

	void installPackage(final Uri packageURI,
			final PackageInstallObserver observer) {
		threadPool.submit(new Runnable() {
			@Override
			public void run() {
				int returnCode = PackageUtil.install(context,
						packageURI.getPath());

				// 获取安装包信息
				String archiveFilePath = packageURI.getPath();
				PackageInfo packageInfo = AppUtil.getUninatllAppInfo(
						context.getPackageManager(), archiveFilePath);
				String packageName = "";
				if (packageInfo != null) {
					packageName = packageInfo.packageName;
				}
				observer.packageInstalled(packageName, returnCode);
			}
		});
	}

	void deletePackage(final String packageName,
			final PackageDeleteObserver observer) {
		threadPool.submit(new Runnable() {
			@Override
			public void run() {
				int returnCode = PackageUtil.uninstall(context, packageName);
				observer.packageDeleted(packageName, returnCode);
			}
		});
	}
}

abstract class PackageInstallObserver {
	public abstract void packageInstalled(String packageName, int returnCode);
}

abstract class PackageDeleteObserver {
	public abstract void packageDeleted(String packageName, int returnCode);
}
