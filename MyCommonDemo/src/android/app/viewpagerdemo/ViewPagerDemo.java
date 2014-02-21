package android.app.viewpagerdemo;

import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import wd.android.common.ui.view.tabview.TabFactory;
import wd.android.common.ui.view.tabview.slidetabview.SlideTabManager;
import wd.android.common.ui.view.tabview.slidetabview.SlideTabManager.SlideTabHolder;

/**
 * ViewPager和Fragment混合使用的Demo
 */
public class ViewPagerDemo extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_demo);

        ViewPager vp = (ViewPager) findViewById(R.id.viewPager);

        SlideTabManager slideTabManager = TabFactory.createTabManager(
                SlideTabManager.class, this, 1);
        //
        SlideTabHolder tabHolder = new SlideTabHolder();
        tabHolder.fragmentClazz = ViewPagerFragment1.class;
        tabHolder.titleCharSequence = "tab1";
        slideTabManager.addTabHolder(tabHolder);
        //
        SlideTabHolder tabHolder2 = new SlideTabHolder();
        tabHolder2.fragmentClazz = ViewPagerFragment1.class;
        tabHolder2.titleCharSequence = "tab2";
        slideTabManager.addTabHolder(tabHolder2);
        //
        // SlideTabHolder tabHolder3 = new SlideTabHolder();
        // tabHolder3.fragmentClazz = ViewPagerFragment1.class;
        // tabHolder3.titleCharSequence = "tab3";
        // slideTabManager.addTabHolder(tabHolder3);
        slideTabManager.initView(vp, null);
    }
}
