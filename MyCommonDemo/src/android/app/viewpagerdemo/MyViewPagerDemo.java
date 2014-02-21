package android.app.viewpagerdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

import wd.android.common.ui.view.tabviewpager.PageViewConfig;
import wd.android.common.ui.view.tabviewpager.PageViewManager;

public class MyViewPagerDemo extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initPageView();
    }

    private void initPageView() {
        PageViewConfig pageViewConfig = new MyPageViewConfig(this);

        PageViewManager pageViewManager = new PageViewManager(null);
        pageViewManager.initPageView(pageViewConfig);

        addContentView(pageViewConfig.getRootView(), new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }
}