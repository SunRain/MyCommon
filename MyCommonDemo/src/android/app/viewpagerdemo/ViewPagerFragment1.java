package android.app.viewpagerdemo;

import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewPagerFragment1 extends Fragment {

    private String text;
    private TextView tv = null;

    public ViewPagerFragment1() {
        super();
    }

    /**
     * 覆盖此函数，先通过inflater inflate函数得到view最后返回
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_pager_fragment_demo1,
                container, false);
        tv = (TextView) v.findViewById(R.id.viewPagerText);
        tv.setText("xxx");
        Log.e("ggggg", "ggggg onCreateView = " + text);
        return v;
    }
}
