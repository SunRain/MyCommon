package android.app.myedittextdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyEditTextActivity extends Activity {
    private LinearLayout mLinearLayout = null;
    private List<MyEditTextView> myEditTexts = null;
    private Map<String, String> map = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myedittextdemo_main);
        initialize();
    }

    private void initialize() {
        initLists();
        initViews();
    }

    private void initViews() {
        mLinearLayout = (LinearLayout) findViewById(R.id.testLinearLayoutMyEditTextDemo);
        myEditTexts = new ArrayList<MyEditTextView>();

        Set<String> keys = map.keySet();
        for (String key : keys) {
            MyEditTextView myEditText = new MyEditTextView(this, key,
                    (String) map.get(key), 20, false);
            myEditTexts.add(myEditText);
            mLinearLayout.addView(myEditText);
        }
    }

    private void initLists() {
        map = new HashMap<String, String>();
        map.put("zf1", "zf1");
        map.put("zf2", "zf2");
        map.put("zf3", "zf3");
        map.put("zf445", "zf43");
        map.put("zf4123", "zf41");
        map.put("zf423", "zf43");
        map.put("zf433", "zf44");
        map.put("zf45", "zf45");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map = null;
        myEditTexts = null;
        mLinearLayout = null;
    }
}