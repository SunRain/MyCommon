package wd.android.common.ui.view.pullrefreshview;

import java.util.ArrayList;
import java.util.List;

import wd.android.common.ui.view.pullrefreshview.PullToRefreshView.OnFooterRefreshListener;
import wd.android.common.ui.view.pullrefreshview.PullToRefreshView.OnHeaderRefreshListener;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class AdapterViewPuller<T> {
	private PullToRefreshView mPullToRefreshView;
	private List<T> mDataList = new ArrayList<T>();
	private BaseAdapter adapter = null;

	public AdapterViewPuller() {
	}

	public List<T> getDataList() {
		return mDataList;
	}

	public void init(BaseAdapter adapter,
			AdapterViewPullerHolder listViewPullerHolder,
			OnHeaderRefreshListener onHeaderRefreshListener,
			OnFooterRefreshListener onFooterRefreshListener) {
		this.adapter = adapter;
		mPullToRefreshView = listViewPullerHolder.pull_refresh_view;
		AdapterView adapterView = listViewPullerHolder.adapterView;
		adapterView.setAdapter(adapter);
		mPullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		mPullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);

		// onHeaderRefreshListener.onHeaderRefresh(mPullToRefreshView);
	}

	/**
	 * 重新刷新数据
	 * 
	 * @param list
	 * @param lastUpdated
	 */
	public void loadInit(List<T> list, CharSequence lastUpdated) {
		// 刷新
		mDataList.clear();
		if (list != null) {
			mDataList.addAll(list);
		}
		adapter.notifyDataSetChanged();
		// 设置更新时间
		// mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
		mPullToRefreshView.onHeaderRefreshComplete(lastUpdated);
		mPullToRefreshView.onFooterRefreshComplete();
	}

	/**
	 * 加载更多
	 */
	public void loadMore(List<T> list) {
		if (list != null) {
			// 添加
			mDataList.addAll(list);
		}
		adapter.notifyDataSetChanged();
		mPullToRefreshView.onHeaderRefreshComplete();
		mPullToRefreshView.onFooterRefreshComplete();
	}

	public static class AdapterViewPullerHolder {
		public PullToRefreshView pull_refresh_view;
		public AbsListView adapterView;
	}

	public static AdapterViewPullerHolder getAdapterViewPullerHolder(
			PullToRefreshView pullToRefreshView, AbsListView adapterView) {
		AdapterViewPullerHolder adapterViewPullerHolder = new AdapterViewPullerHolder();
		adapterViewPullerHolder.pull_refresh_view = pullToRefreshView;
		adapterViewPullerHolder.adapterView = adapterView;
		return adapterViewPullerHolder;
	}
}
