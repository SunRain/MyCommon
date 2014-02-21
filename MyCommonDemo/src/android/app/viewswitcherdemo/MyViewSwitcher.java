package android.app.viewswitcherdemo;

import android.content.Context;
import android.nbtstatx.mydemos.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ViewSwitcher;

/**
 * 该部分是ViewSwitcher的重载，用该类实现两个屏的切换和切换的动画实现
 */
public class MyViewSwitcher extends ViewSwitcher {
    private Context mContext;

    public MyViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFactory(new SlideViewFactory(context));
        // setAnimateFirstView(false);
        mContext = context;
    }

    /**
     * 通过该方法将数据赋值进去，并且将初始的屏显示出来
     */
    private void setViewData(ViewGroup viewGroup, View view) {
        viewGroup.removeAllViews();
        viewGroup.addView(view);
    }

    /**
     * 该方法用于显示下一屏
     */
    public void showNextScreen(View view) {
        setOutAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_left));
        setInAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_right));
        showNext();
        setViewData((ViewGroup) getCurrentView(), view);
    }

    /**
     * 该方法用于显示上一屏
     */
    public void showPreviousScreen(View view) {
        // if (mCurrentScreen > 0) {
        // mCurrentScreen--;
        // setInAnimation(mContext, R.anim.slide_in_left);
        // setOutAnimation(mContext, R.anim.slide_out_right);
        // } else {
        // return;
        // }

        setOutAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_right));
        setInAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_left));

        showPrevious();
        setViewData((ViewGroup) getCurrentView(), view);
    }
}
