package android.app.viewpagerdemo;

import android.content.Context;
import android.nbtstatx.mydemos.R;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import wd.android.common.ui.view.tabviewpager.PageViewConfig;

public class MyPageViewConfig extends PageViewConfig {

    private RadioButton tabBtn;

    public MyPageViewConfig(Context context) {
        super(context, 3);
        titles = new ArrayList<String>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");
        initTitles(titles);
    }

    @Override
    public View initTabView(int index) {
        LayoutParams layoutParams = new LayoutParams(200, 100);
        tabBtn = (RadioButton) LayoutInflater.from(mContext).inflate(
                R.layout.btn_tab, null);
        tabBtn.setVisibility(View.VISIBLE);
        tabBtn.setFocusable(true);
        tabBtn.setFocusableInTouchMode(false);
        tabBtn.setLayoutParams(layoutParams);

        tabBtn.setText(titles.get(index));

        return tabBtn;
    }

    @Override
    public View initViews() {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_viewpager, null);
        tabGroup = (RadioGroup) rootView.findViewById(R.id.tabGroup);
        mViewPager = (ViewPager) rootView.findViewById(R.id.vPager);

        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.viewpagerdemo_item, null);
            listViews.add(view);
        }
        return rootView;
    }
}
