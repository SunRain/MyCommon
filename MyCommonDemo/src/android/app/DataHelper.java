package android.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {
    static final String TITLE = "title";
    static final String ACTION = "action";
    static final String INTENT = "intent";

    private List<Map<String, String>> myData;
    private Context mContext = null;

    public DataHelper(Context context) {
        mContext = context;
    }

    /**
     * 获取数据
     *
     * @return
     */
    List<Map<String, String>> getData() {
        return myData;
    }

    private void addItem(String name, String action) {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put(TITLE, name);
        temp.put(ACTION, action);
        myData.add(temp);
    }

    // private void initData() {
    // addItem("ServiceDemo",
    // "com.nbtstatx.mydemos.servicedemo.ServiceDemoActivity");
    // addItem("GpsDemo", "com.nbtstatx.mydemos.gpsdemo.GpsDemoActivity");
    // addItem("DownloadDemo",
    // "com.nbtstatx.mydemos.downloaddemo.DownloadDemoActivity");
    // addItem("DomParserDemo",
    // "com.nbtstatx.mydemos.domparserdemo.DomParserDemoActivity");
    // addItem("ViewDemo", "com.nbtstatx.mydemos.viewdemo.ViewDemoActivity");
    // addItem("RoundRectLabel",
    // "com.nbtstatx.mydemos.roundrectlabel.RoundRectLabelActivity");
    // addItem("DbNote", "com.nbtstatx.mydemos.dbnote.LoginActivity");
    // addItem("PreferenceDemo",
    // "com.nbtstatx.mydemos.preferencedemo.PreferenceDemoActivity");
    // addItem("AidlDemo", "com.nbtstatx.mydemos.aidldemo.AidlDemoActivity");
    // addItem("AlarmServiceDemo",
    // "com.nbtstatx.mydemos.alarmservicedemo.AlarmServiceActivity");
    // addItem("MyEditText",
    // "com.nbtstatx.mydemos.myedittextdemo.MyEditTextActivity");
    // addItem("PackageManagerDemo",
    // "com.nbtstatx.mydemos.packagemanagerdemo.PackageManagerDemoActivity");
    // addItem("TestDemo", "com.nbtstatx.mydemos.testdemo.TestDemoActivity");
    // addItem("BroadcastDemo",
    // "com.nbtstatx.mydemos.broadcastdemo.BroadcastDemoActivity");
    // addItem("ListViewCursorDemo",
    // "com.nbtstatx.mydemos.listviewcursordemo.ListViewCursorDemoActivity");
    // addItem("BmiDemo", "com.nbtstatx.mydemos.bmidemo.BmiDemoActivity");
    // addItem("AbmiDemo", "com.nbtstatx.mydemos.abmidemo.AbmiDemoActivity");
    // addItem("ScreenDemo",
    // "com.nbtstatx.mydemos.screendemo.ScreenDemoActivity");
    // addItem("XmlPullParseDemo",
    // "com.nbtstatx.mydemos.xmlpullparsedemo.XmlPullParseDemoActivity");
    // addItem("HttpPostDemo",
    // "com.nbtstatx.mydemos.httppostdemo.HttpPostDemoActivity");
    // addItem("NtpDemo", "com.nbtstatx.mydemos.ntpdemo.NtpDemoActivity");
    // addItem("FloatViewDemo",
    // "com.nbtstatx.mydemos.floatviewdemo.FloatViewDemoActivity");
    // addItem("ScrollTextDemo",
    // "com.nbtstatx.mydemos.scrolltextdemo.ScrollTextDemoActivity");
    // addItem("BrightnessDemo",
    // "com.nbtstatx.mydemos.brightnessdemo.BrightnessDemoActivity");
    // }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);

        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefix.length() == 0 || label.startsWith(prefix)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0]
                        : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData,
                            nextLabel,
                            activityIntent(
                                    info.activityInfo.applicationInfo.packageName,
                                    info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel,
                                browseIntent(prefix.equals("") ? nextLabel
                                        : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }
        Collections.sort(myData, sDisplayNameComparator);
        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator = new Comparator<Map<String, Object>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map<String, Object> map1, Map<String, Object> map2) {
            return collator.compare(map1.get(TITLE), map2.get(TITLE));
        }
    };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(mContext, MainActivity.class);
        result.putExtra("com.nbtstatx.mydemos.Path", path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name,
                           Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put(TITLE, name);
        temp.put(INTENT, intent);
        data.add(temp);
    }
}
