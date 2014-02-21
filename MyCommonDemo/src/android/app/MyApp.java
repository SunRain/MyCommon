package android.app;

import me.android.custom.MainApp;

public class MyApp extends MainApp {
    private DataHelper mDataHelper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mDataHelper = new DataHelper(this);
        MainApp.getApp().initApp();
    }

    public DataHelper getDataHelper() {
        return mDataHelper;
    }

}
