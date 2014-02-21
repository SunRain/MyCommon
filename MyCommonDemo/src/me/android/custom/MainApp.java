package me.android.custom;

import android.app.Activity;

import wd.android.framework.AppConfig;
import wd.android.framework.BaseApp;
import wd.android.framework.manager.BaseManager;

public class MainApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected BaseManager initServiceManager() {
        return new MyServiceManager();
    }

    @Override
    protected AppConfig initConfig() {
        return new MainConfig();
    }

    public void exitApp(Activity activity) {
        // Intent intent = IntentUtil.getActivityIntent(this,
        // InitActivity.class,
        // null, false);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // activity.setResult(InitActivity.CODE_EXIT, intent);
    }

    public void restartApp(Activity activity) {
        // ActivityStack.getInstance().finishAll();
        // Intent intent = IntentUtil.getActivityIntent(this,
        // InitActivity.class,
        // null, false);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // activity.startActivity(intent);
    }
}
