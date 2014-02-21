package android.app.tabdemo;

import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import wd.android.common.ui.view.tabview.TabFactory;
import wd.android.common.ui.view.tabview.normal.TabManager;
import wd.android.common.ui.view.tabview.normal.TabManager.TabHolder;

public class TabDemoActivity extends FragmentActivity {

    private TabManager mTabViewManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(this);
    }

    private void initViews(FragmentActivity activity) {
        // mTabViewManager = new TabManager(activity, 0);
        mTabViewManager = TabFactory.createTabManager(TabManager.class,
                activity, 0);
        //
        TabHolder tabHolder = new TabHolder();
        tabHolder.titleCharSequence = "tab1";
        tabHolder.iconRes = R.drawable.timer_tab;
        tabHolder.fragmentClazz = TestFragment1.class;
        mTabViewManager.addTabHolder(tabHolder);
        //
        TabHolder tabHolder2 = new TabHolder();
        tabHolder2.titleCharSequence = "tab2";
        tabHolder2.iconRes = R.drawable.timer_tab;
        tabHolder2.fragmentClazz = TestFragment1.class;
        mTabViewManager.addTabHolder(tabHolder2);
        //
        TabHolder tabHolder3 = new TabHolder();
        tabHolder3.titleCharSequence = "tab3";
        tabHolder3.iconRes = R.drawable.timer_tab;
        tabHolder3.fragmentClazz = TestFragment1.class;
        mTabViewManager.addTabHolder(tabHolder3);
        //
        TabHolder tabHolder4 = new TabHolder();
        tabHolder4.titleCharSequence = "tab4";
        tabHolder4.fragmentClazz = TestFragment1.class;
        tabHolder4.iconRes = R.drawable.timer_tab;
        mTabViewManager.addTabHolder(tabHolder4);

        setContentView(mTabViewManager.initView(null));
    }
}
