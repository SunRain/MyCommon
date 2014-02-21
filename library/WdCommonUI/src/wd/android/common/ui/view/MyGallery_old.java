package wd.android.common.ui.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery_old extends Gallery {
	private Timer timer;
	private boolean isAutoFling = true;
	private Context context;

	public MyGallery_old(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyGallery_old(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyGallery_old(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	class mTimerTask extends TimerTask {

		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

	};

	// TimerTask timerTask = new TimerTask() {
	// @Override
	// public void run() {
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);
	// }
	// };

	@Override
	protected void onAttachedToWindow() {
		timer = new Timer();
		timer.schedule(new mTimerTask(), 5000, 5000);
		super.onAttachedToWindow();
	}

	@Override
	public void onDetachedFromWindow() {
		timer.cancel();
		super.onDetachedFromWindow();
	}

	public void setAutoFling(boolean isAutoFling) {
		this.isAutoFling = isAutoFling;
	}

	public void cancelAutoFling() {
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			isAutoFling = false;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isAutoFling = true;
		}

		return super.onTouchEvent(event);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		isAutoFling = hasWindowFocus;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (isAutoFling) {
					onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}
			}
		};
	};
}
