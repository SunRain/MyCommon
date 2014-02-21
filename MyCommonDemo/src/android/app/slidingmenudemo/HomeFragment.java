package android.app.slidingmenudemo;

import java.util.Map;

import me.android.app.global.UrlData;
import me.android.common.http.CacheHttpListener;
import me.android.common.http.HttpLoader;
import me.android.common.http.HttpUtil;
import wd.android.framework.ui.BaseFragment;
import wd.android.util.util.MyLog;
import android.os.Bundle;
import android.view.View;

public class HomeFragment extends BaseFragment {

	@Override
	public void initView(View rootView, Bundle savedInstanceState) {

	}

	@Override
	public void initData(Bundle savedInstanceState) {

		HttpUtil.setCookieStore(getActivity());

		HttpLoader.init(this.getActivity());
		HttpLoader.load(UrlData.URL_INIT, new MyBaseHttpListener());

		String url = "http://211.136.104.111/ichina/resource/cltv1/home.jsp";
		HttpLoader.load(url, new MyBaseHttpListener());
	}

	@Override
	public int getRootViewId() {
		return 0;
	}

	public class MyBaseHttpListener extends CacheHttpListener {
		@Override
		public void onFinish() {
			super.onFinish();
			MyLog.e("gggg");
		}

		@Override
		public void onStart() {
			super.onStart();
			MyLog.e("gggg" + this.getClass().getName());
		}

		@Override
		protected void onFailure(Throwable error,
				Map<String, Object> responseMap) {
			super.onFailure(error, responseMap);
		}

		@Override
		protected void onSuccess(int statusCode, Map<String, String> headers,
				Map<String, Object> responseMap) {
		}
	}

	;

}
