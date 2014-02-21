package android.app.viewswitcherdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class ViewSwitcherDemoActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private MyViewSwitcher mSwitcher;
    private Button btn_prev, btn_next;
    private float startX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewswitcherdemo_main);

        mSwitcher = (MyViewSwitcher) findViewById(R.id.switcher);
        mSwitcher.setDisplayedChild(0);

        btn_next = (Button) findViewById(R.id.next);
        btn_prev = (Button) findViewById(R.id.prev);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitcher.showNextScreen(getTextView());
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitcher.showPreviousScreen(getTextView());
            }
        });
    }

    private int count = 1;

    private TextView getTextView() {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LayoutParams(60, 60));
        textView.setText("xxxxx:" + count++);
        return textView;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:

                if (event.getX() > startX) { // 向右滑动
                    View view = getView();
                    mSwitcher.showPreviousScreen(view);
                } else if (event.getX() < startX) { // 向左滑动
                    View view = getView();
                    mSwitcher.showNextScreen(view);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private View getView() {
        return LayoutInflater.from(this).inflate(R.layout.slip_item1, null);
    }
}
