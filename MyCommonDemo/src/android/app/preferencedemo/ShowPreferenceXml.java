package android.app.preferencedemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.Map;

public class ShowPreferenceXml extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencedemo_showpreferencexml);

        TextView txtPreferencexml = (TextView) findViewById(R.id.preferencexmlTestPreference);
        txtPreferencexml.setText(showPreferenceXmlContent());
    }

    @SuppressWarnings("unchecked")
    private String showPreferenceXmlContent() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        Map<String, Object> myMap = (Map<String, Object>) sp.getAll();
        String str = "";
        for (Map.Entry<String, Object> entry : myMap.entrySet()) {
            str += (entry.getKey() + "  :  " + entry.getValue() + System
                    .getProperty("line.separator"));
        }
        return str;
    }

}
