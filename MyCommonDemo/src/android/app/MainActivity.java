package android.app;

import android.content.Intent;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.Map;

import me.android.custom.MainApp;
import wd.android.framework.BaseApp;
import wd.android.util.util.EnvironmentInfo;
import wd.android.util.util.MyLog;

public class MainActivity extends ListActivity {
    private TextView txtMain = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        refreshListView();
        startService(new Intent(MainService.ACTION_MYDEMOS));
        MyLog.e("onCreate()");
        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
    }

    private void initViews() {
        txtMain = (TextView) findViewById(R.id.txtMain);
        txtMain.setText("/sdcard: "
                + EnvironmentInfo.getUsableSpace(EnvironmentInfo
                .getExternalStorageDirectory())
                + "\n"
                + "/data: "
                + EnvironmentInfo.getUsableSpace(new File(EnvironmentInfo
                .getDataStorageDirectory(BaseApp.getApp()) + "/Config")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.e("onDestroy()");
        MainApp.getApp().exitApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refreshListView();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // @SuppressWarnings("unchecked")
        // Map<String, String> map = (Map<String, String>) l
        // .getItemAtPosition(position);
        // Intent intent = new Intent(map.get(DataHelper.ACTION));
        // // Intent intent = new Intent();
        // // intent.setClass( this, TestGpsActivity.class );
        // startActivity(intent);

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) l
                .getItemAtPosition(position);
        Intent intent = (Intent) map.get(DataHelper.INTENT);
        startActivity(intent);
    }

    /**
     * 刷新ListView
     */
    private void refreshListView() {
        Intent intent = getIntent();
        String path = intent.getStringExtra("com.nbtstatx.mydemos.Path");
        if (path == null) {
            path = "";
        }
        setListAdapter(new SimpleAdapter(this, ((MyApp) MyApp.getApp())
                .getDataHelper().getData(path),
                android.R.layout.simple_list_item_1,
                new String[]{DataHelper.TITLE},
                new int[]{android.R.id.text1}));
        // setListAdapter(new SimpleAdapter(this,
        // ((DataHelper) getApplicationContext()).getData(),
        // android.R.layout.simple_list_item_1,
        // new String[] { DataHelper.TITLE },
        // new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }

    /**
     * Menu菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Menu菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_about:
                // ScreenShot.shoot(this);
                break;
        }
        return true;
    }
}