package wd.android.framework.ui;

import java.util.Set;

import wd.android.util.util.ActivityStack;
import wd.android.util.util.MyLog;
import wd.android.util.util.ObjectUtil;
import wd.android.util.util.UIUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

/**
 * android.support.v4.app.FragmentActivity的包装类
 */
public abstract class BaseActivity extends FragmentActivity implements
		IActivity {

	/**
	 * 按键监听器
	 */
	public static interface KeyEventListener {
		/**
		 * key被按下时调用
		 * 
		 * @param keyCode
		 * @param event
		 * @return
		 */
		boolean onKeyDown(int keyCode, KeyEvent event);
	}

	protected FragmentHelper mFragmentHelper;
	private Set<KeyEventListener> mKeyEventListener = ObjectUtil.newHashSet();

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityStack.getInstance().addActivity(this);
		mFragmentHelper = new FragmentHelper(this.getSupportFragmentManager());
		mKeyEventListener.clear();
		onBeforeContentView(savedInstanceState);
		int layoutResID = getRootViewId();
		if (layoutResID > 0) {
			setContentView(layoutResID);
		}
		onCreateActivity(savedInstanceState);
	}

	@Override
	public void onDestroyActivity() {
	}

	@Override
	protected void onDestroy() {
		mKeyEventListener.clear();
		ActivityStack.getInstance().removeActivity(this);
		onDestroyActivity();
		mFragmentHelper = null;
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		MyLog.i("outState = " + outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		MyLog.i("bundle = " + bundle);
	}

	@Override
	public void onBeforeContentView(Bundle savedInstanceState) {
		// 使全屏
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setSoftInputMode(
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {
		View rootView = UIUtils.getContentView(this);
		initView(rootView, savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		initData(null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		dispatchKeyEvent(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 注册按键监听事件
	 * 
	 * @param listener
	 */
	public void registerKeyEventListener(KeyEventListener listener) {
		synchronized (mKeyEventListener) {
			mKeyEventListener.add(listener);
		}
	}

	/**
	 * 去除按键监听事件
	 * 
	 * @param listener
	 */
	public void unRegisterKeyEventListener(KeyEventListener listener) {
		synchronized (mKeyEventListener) {
			mKeyEventListener.remove(listener);
		}
	}

	private void dispatchKeyEvent(int keyCode, KeyEvent event) {
		synchronized (mKeyEventListener) {
			for (KeyEventListener listener : mKeyEventListener) {
				listener.onKeyDown(keyCode, event);
			}
		}
	}

	@Override
	public void onBackPressed() {
		// finish();
		super.onBackPressed();
	}

	@Override
	public void showDialog(Bundle arguments, DialogFragment dialogFragment) {
		mFragmentHelper.showDialog(arguments, dialogFragment);
	}

}
