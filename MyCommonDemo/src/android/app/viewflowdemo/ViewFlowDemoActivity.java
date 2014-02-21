package android.app.viewflowdemo;

import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import wd.android.common.ui.view.MyAutoFlipView;
import wd.android.common.ui.view.viewflow.ViewFlow;
import wd.android.framework.ui.BaseActivity;

public class ViewFlowDemoActivity extends BaseActivity {
    private ViewFlow viewFlow;

    @Override
    public void initView(View rootView, Bundle bundle) {
        // viewFlow = (ViewFlow) rootView.findViewById(R.id.myViewFlow);
        // viewFlow.setAdapter(new ImageAdapter(this), Integer.MAX_VALUE / 2);

        mSwitcher = (MyAutoFlipView) rootView.findViewById(R.id.myViewFlow);
        mSwitcher.setFlipInterval(3000);
        mSwitcher.setAutoStart(true);
        mSwitcher.setAdapter(new ImageAdapter(this));
        // mSwitcher = (MyViewFlipper) rootView.findViewById(R.id.myViewFlow);
        // mSwitcher.setFlipInterval(3000);
        // mSwitcher.setAutoStart(true);
        // mSwitcher.setAdapter(new ImageAdapter(this));
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getRootViewId() {
        return R.layout.viewflowdemo_main;
    }

    private float startX;
    MyAutoFlipView mSwitcher;

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:

                if (event.getX() > startX) { // 向右滑动
                    // mSwitcher.showPrevious();
                } else if (event.getX() < startX) { // 向左滑动
                    // mSwitcher.showNext();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

}
