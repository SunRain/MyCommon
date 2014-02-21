package wd.android.framework.manager;

import wd.android.framework.global.GlobalData;
import wd.android.util.global.MyPreference;
import android.content.Context;

public class CommonBaseManager extends BaseManager {

	@Override
	protected void onCreate(Context context) {
		addService(new GlobalData());
		addService(new MyPreference(context));
	}

	@Override
	protected void onDestroy() {
		getService(GlobalData.class).release();
		getService(MyPreference.class).commitTransaction();
	}
}
