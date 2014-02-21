package wd.android.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SlideView extends LinearLayout {
	private VelocityTracker mVelocityTracker;

	public interface ScrollState {
		static final int SCROLL_STATE_LEFT = 1;
		static final int SCROLL_STATE_DEFAULT = 2;
		static final int SCROLL_STATE_RIGHT = 3;
	}

	private int mScrollState = ScrollState.SCROLL_STATE_DEFAULT;

	private float mLastMotionX = 0;
	private int mLeftLen;

	public SlideView(Context context) {
		super(context);
		init();
	}

	public SlideView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mLeftLen = 150;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float x = ev.getRawX();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaX > 0 右滑动； 反之，左滑动
			int deltaX = (int) (x - mLastMotionX);
			if (checkMoveX(deltaX)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		float x = event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (x - mLastMotionX);
			checkMoveX(deltaX);
			scrollX(deltaX);
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (mScrollState == ScrollState.SCROLL_STATE_RIGHT
					&& getLeftMargin() >= mLeftLen) {
				scrollToX(mLeftLen);
				mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
				break;
			} else if (mScrollState == ScrollState.SCROLL_STATE_LEFT
					&& getRightMargin() >= 0) {
				resetScrollX();
				mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
				break;
			}
			// 不需要右边显示菜单
			// else if (mScrollState == ScrollState.SCROLL_STATE_LEFT
			// && getRightMargin() >= mLeftLen) {
			// scrollToX(-mLeftLen);
			// mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
			// break;
			// }
			resetScrollX();
			break;
		}
		return true;
	}

	/**
	 * 移动x
	 * 
	 * @param deltaX
	 *            > 0 右滑动； 反之，左滑动
	 */
	private int scrollX(int deltaX) {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		if (deltaX < 0 && mScrollState == ScrollState.SCROLL_STATE_RIGHT
				&& params.leftMargin <= 0) {
			return 0;
		}
		if (deltaX > 0 && mScrollState == ScrollState.SCROLL_STATE_LEFT
				&& params.rightMargin <= 0) {
			return 0;
		}
		params.leftMargin = (int) (params.leftMargin + deltaX * 0.5);
		params.rightMargin = (int) (params.rightMargin - deltaX * 0.5);
		setLayoutParams(params);
		invalidate();
		return params.leftMargin;

	}

	private int scrollToX(int x) {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		params.leftMargin = x;
		params.rightMargin = -x;
		setLayoutParams(params);
		invalidate();
		return params.leftMargin;
	}

	private void resetScrollX() {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		params.leftMargin = 0;
		params.rightMargin = 0;
		setLayoutParams(params);
		mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
		invalidate();
	}

	public void openMennu() {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		params.leftMargin = mLeftLen;
		params.rightMargin = -mLeftLen;
		setLayoutParams(params);
		mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
		invalidate();
	}

	public void closeMenu() {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		params.leftMargin = 0;
		params.rightMargin = 0;
		setLayoutParams(params);
		mScrollState = ScrollState.SCROLL_STATE_DEFAULT;
		invalidate();
	}

	/**
	 * 返回当前left margin
	 * 
	 * @return
	 */
	private int getLeftMargin() {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		return params.leftMargin;
	}

	/**
	 * 返回当前 right margin
	 * 
	 * @return
	 */
	private int getRightMargin() {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		return params.rightMargin;
	}

	/**
	 * 检测是否达到移动条件
	 * 
	 * @param deltaX
	 *            > 0 右移，反之左移
	 * @return
	 */
	private boolean checkMoveX(int deltaX) {
		if (mScrollState == ScrollState.SCROLL_STATE_DEFAULT) {
			if (deltaX > 0) {
				mScrollState = ScrollState.SCROLL_STATE_RIGHT;
			} else if (deltaX < 0) {
				mScrollState = ScrollState.SCROLL_STATE_LEFT;
			} else {
				return false;
			}
			return true;
		}
		return false;
	}

}
