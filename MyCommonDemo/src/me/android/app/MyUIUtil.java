package me.android.app;

import me.android.custom.MainApp;
import wd.android.util.util.UIUtils;

public class MyUIUtil {
	public static void showToast(CharSequence msg) {
		UIUtils.showToast(MainApp.getApp(), msg);
	}
}
