package wd.android.util.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadUtil {
	/**
	 * 休眠一段时间
	 * 
	 * @param time
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ThreadPoolExecutor newFixedThreadPool(int nThreads) {
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
	}

	public ThreadPoolExecutor newCachedThreadPool() {
		return (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}

	public ThreadPoolExecutor newSingleThreadExecutor() {
		return (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
	}
}
