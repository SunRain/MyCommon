package me.android.common.http;

import java.util.Map;

import org.apache.http.Header;

import wd.android.util.util.MyLog;
import wd.android.util.util.Utils;

public class MyDataResponseHandler extends
		HttpResponseHandler<Map<String, Object>> {
	private String url;
	private HttpListener httpListener;

	public MyDataResponseHandler(String url, HttpListener httpListener) {
		this.url = url;
		this.httpListener = httpListener;
	}

	@Override
	public void onFinish() {
		super.onFinish();
		httpListener.onFinish();
		if (MyLog.isDebug()) {
			MyLog.d("url = " + url);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		httpListener.onStart();
		if (MyLog.isDebug()) {
			MyLog.d("url = " + url);
		}
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			byte[] responseBytes, Map<String, Object> response) {
		MyLog.i("statusCode = " + statusCode);
		if (MyLog.isDebug()) {
			MyLog.d("header:----------------------------------------");
			for (Header header : headers) {
				MyLog.d(header.getName() + ":" + header.getValue());
			}
			MyLog.d("responseBody:----------------------------------------");
			String responseBody = Utils.getString(responseBytes, getCharset());
			MyLog.d(responseBody);
		}

		httpListener.onSuccess(statusCode, headers, responseBytes, response);
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, byte[] responseBytes,
			Map<String, Object> errorResponse) {
		httpListener.onFailure(throwable, errorResponse);
		String responseBody = Utils.getString(responseBytes, getCharset());
		MyLog.e(responseBody, throwable);
	}

	@Override
	protected Map<String, Object> parseResponse(Header[] headers,
			byte[] responseBytes, boolean isFailure) throws Throwable {
		return httpListener.parseResponse(headers, responseBytes);
	}
}
