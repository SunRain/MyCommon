package android.app.xmlpullparsedemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class XmlPullParseDemoActivity extends Activity {
    private TextView txtValue = null;
    private String requestStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xmlpullparsedemo_main);
        initViews();
        parse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        txtValue = null;
        requestStr = null;
    }

    private void initViews() {
        txtValue = (TextView) findViewById(R.id.txtValueXmlPullParseDemo);
    }

    private void parse() {
        Map<String, String> values = new HashMap<String, String>();
        requestStr = getResponseStr();
        try {
            values = MyXmlPullparser.parse(requestStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String value = values.get(MyXmlPullparser.KEY_RESPONSE) + "\n"
                + values.get(MyXmlPullparser.KEY_RESULT);
        txtValue.setText(value);
    }

    private String getResponseStr() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><Server version=\"1.0\"><Response Type=\"Query\"><M2><Result>0</Result></M2></Response></Server>";
    }
}
