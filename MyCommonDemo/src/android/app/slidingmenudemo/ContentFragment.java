package android.app.slidingmenudemo;

import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @version 1.0.0
 * @since Mar 12, 2013
 */
public class ContentFragment extends Fragment {
    String text = null;

    public ContentFragment() {
    }

    public ContentFragment(String text) {
        Log.e("Krislq", text);
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("Krislq", "onCreate:" + text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Krislq", "onCreateView:" + text);
        // inflater the layout
        View view = inflater.inflate(R.layout.slidingmenudemo_frame_text, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        Log.e("Krislq", "onDestroy:" + text);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("Krislq", "onDetach:" + text);
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.e("Krislq", "onPause:" + text);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e("Krislq", "onResume:" + text);
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.e("Krislq", "onStart:" + text);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e("Krislq", "onStop:" + text);
        super.onStop();
    }

}
