package wd.android.custom.ui.view;

import wd.android.common.ui.R;
import wd.android.common.ui.view.pullrefreshview.PullToRefreshView.PullViewHolder;

public class PullToRefreshConfig {
	public static PullViewHolder getPullViewHolder() {
		PullViewHolder pullViewHolder = new PullViewHolder();
		pullViewHolder.layout_refresh_header = R.layout.pull_refresh_header;
		pullViewHolder.id_pull_refresh_image = R.id.pull_to_refresh_image;
		pullViewHolder.id_pull_refresh_text = R.id.pull_to_refresh_text;
		pullViewHolder.id_pull_refresh_updated_at = R.id.pull_to_refresh_updated_at;
		pullViewHolder.id_pull_refresh_progress = R.id.pull_to_refresh_progress;
		pullViewHolder.layout_refresh_footer = R.layout.pull_refresh_footer;
		pullViewHolder.id_pull_load_image = R.id.pull_to_load_image;
		pullViewHolder.id_pull_load_text = R.id.pull_to_load_text;
		pullViewHolder.id_pull_load_progress = R.id.pull_to_load_progress;
		//
		pullViewHolder.string_pull_refresh_loosen = R.string.pull_refresh_loosen;
		pullViewHolder.string_pull_refresh_header_pull = R.string.pull_refresh_header_pull;
		pullViewHolder.string_pull_refresh_footer_pull = R.string.pull_refresh_footer_pull;
		pullViewHolder.string_pull_refresh_loading = R.string.pull_refresh_loading;
		pullViewHolder.string_pull_refresh_refreshing = R.string.pull_refresh_refreshing;
		//
		pullViewHolder.drawable_pull_refresh_arrow_down = R.drawable.pull_refresh_arrow_down;
		pullViewHolder.drawable_pull_refresh_arrow_up = R.drawable.pull_refresh_arrow_up;
		return pullViewHolder;
	}

	// public static AdapterViewPullerHolder getListViewPullerHolder(
	// Activity activity) {
	// AdapterViewPullerHolder adapterViewPullerHolder = new
	// AdapterViewPullerHolder();
	// adapterViewPullerHolder.pull_refresh_view = (PullToRefreshView) activity
	// .findViewById(R.id.listview_pull_refresh_view);
	// adapterViewPullerHolder.adapterView = (AdapterView<Adapter>) activity
	// .findViewById(R.id.listView);
	// return adapterViewPullerHolder;
	// }

//	public static AdapterViewPullerHolder getAdapterViewPullerHolder(
//			PullToRefreshView pullToRefreshView,
//			AdapterView<Adapter> adapterView) {
//		AdapterViewPullerHolder adapterViewPullerHolder = new AdapterViewPullerHolder();
//		adapterViewPullerHolder.pull_refresh_view = pullToRefreshView;
//		adapterViewPullerHolder.adapterView = adapterView;
//		return adapterViewPullerHolder;
//	}
}
