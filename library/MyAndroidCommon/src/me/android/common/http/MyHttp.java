package me.android.common.http;

import java.lang.ref.WeakReference;

import org.apache.http.Header;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MyHttp {
	private final WeakReference<Context> context;

	// private Context context;

	public MyHttp(Context context) {
		// this.context = context;
		this.context = new WeakReference<Context>(context);
	}

	/**
	 * JSON数据请求
	 */
	public void cancel() {
		HttpUtil.cancel(context.get());
	}

	/**
	 * JSON数据请求
	 */
	public void exec(String url, HttpListener httpHandler) {
		exec(url, null, null, httpHandler);
	}

	/**
	 * JSON数据请求
	 */
	public void exec(String url, Header[] headers, HttpListener httpHandler) {
		exec(url, headers, null, httpHandler);
	}

	/**
	 * JSON数据请求
	 */
	public void exec(String url, RequestParams params, HttpListener httpHandler) {
		exec(url, null, params, httpHandler);
	}

	/**
	 * JSON数据请求
	 */
	public void exec(String url, Header[] headers, RequestParams params,
			HttpListener httpListener) {
		MyDataResponseHandler responseHandler = new MyDataResponseHandler(url,
				httpListener);
		exec(url, headers, params, responseHandler);
	}

	public void exec(String url, AsyncHttpResponseHandler responseHandler) {
		exec(url, null, (RequestParams) null, responseHandler);
	}

	public void exec(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		exec(url, null, params, responseHandler);
	}

	public void exec(String url, Header[] headers, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		HttpUtil.exec(context.get(), url, headers, params, responseHandler);
	}

	public void exec(String url, byte[] b,
			AsyncHttpResponseHandler responseHandler) {
		HttpUtil.exec(context.get(), url, null, b, responseHandler);
	}

	public void exec(String url, Header[] header, byte[] b,
			AsyncHttpResponseHandler responseHandler) {
		HttpUtil.exec(context.get(), url, header, b, responseHandler);
	}
}
