package android.app.preferencedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/*
 * getPreferences()获取到作用域是本activity的preference
 * getSharedPreferences()获取到作用域是本应用程序的preference
 * getDefaultSharedPreferences()获取到全局作用域的preference
 * 
 * 
 * SharedPreferences
 * get方法获取对应键的值
 * edit方法获取SharedPreferences.Editor对象
 * 通过SharedPreferences.Editor的put方法写入键值对
 * 保存需要commit方法
 */
public class PreferenceDemoActivity extends PreferenceActivity implements
        OnSharedPreferenceChangeListener {
    private static final String KEY_CHANNEL_CHANGE_PREFERENCE = "showPreferenceXml";

    private static final String KEY_LIST_PREFERENCE = "ListPreference";

    private static final String KEY_RINGTONE_PREFERENCE = "RingtonePreference";

    private Preference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencedemo_main);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

    }

    // preference有不同的格式，如EditTextPreference,ListPreference,CheckBoxPreference等类型
    // 当选择或是输入不同的值时，他们的值会自动保存到sharedpreference中，当值发生改变的时候会触发
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        // sharedPreference:句柄
        // key:改变键值
        if (key.equals("CheckBox1")) {
            Boolean isTrue = sharedPreferences.getBoolean("CheckBox1", false);
            String result = (isTrue ? "good" : "bad");
            Log.i(result, " " + key);
        }
        String str = "";
        if (("myName").equals(key) || ("myEmail").equals(key)
                || ("myPhone").equals(key)) {
            str = sharedPreferences.getString(key, key.substring(2));
            if (getMyPreference() != null) {
                getMyPreference().setTitle(str);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        Map myMap = sp.getAll();
        // ListPreference
        if (myMap.containsKey(KEY_LIST_PREFERENCE)) {
            ListPreference listPreference = (ListPreference) findPreference(KEY_LIST_PREFERENCE);
            listPreference.setSummary(getText(listPreference));
        }
        // EditTextPreference
        if (myMap.containsKey("EditTextPreference")) {
            EditTextPreference editTextPreference = (EditTextPreference) findPreference("EditTextPreference");
            editTextPreference.setSummary(getText(editTextPreference));
        }

        // 录入用户个人资料
        String[] myKey = {"myName", "myEmail", "myPhone"};
        int myCount = myKey.length;
        for (int i = 0; i < myCount; i++) {
            if (myMap.containsKey(myKey[i])) {
                EditTextPreference myPreference = (EditTextPreference) findPreference(myKey[i]);
                myPreference.setTitle(getText(myPreference));
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        if (preference.getKey() != null
                && preference.getKey().equals(KEY_CHANNEL_CHANGE_PREFERENCE)) {
            Intent intent = new Intent(this, ShowPreferenceXml.class);
            startActivity(intent);// 点击PreferenceScreen跳转至其它Activity
            SharedPreferences sp = this.getSharedPreferences(
                    "com.sony.prefsdemo_preferences.xml", MODE_WORLD_READABLE);
            // SharedPreferences sp =
            // PreferenceManager.getDefaultSharedPreferences(this);
            // String str = sp.getString("EditTextPreference",
            // "EditTextPreference has nothing");
            // preference.setSummary(str);
            // showToast(str);
        }
        setMyPreference(preference);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void setMyPreference(Preference preference) {
        this.myPreference = preference;
    }

    private Preference getMyPreference() {
        return this.myPreference;
    }

    // 获得preference的value
    private String getText(Preference p) {
        String value;
        try {
            if (p instanceof ListPreference) {
                value = ((ListPreference) p).getValue();
                CharSequence[] entries = ((ListPreference) p).getEntries();
                CharSequence[] entryValues = ((ListPreference) p)
                        .getEntryValues();
                for (int i = 0; i < entryValues.length; i++) {
                    if (value.equals((String) entryValues[i]))
                        return (String) entries[i];
                }
            } else if (p instanceof EditTextPreference) {
                return ((EditTextPreference) p).getText();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}
