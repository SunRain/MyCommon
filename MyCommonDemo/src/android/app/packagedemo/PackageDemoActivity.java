package android.app.packagedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wd.android.util.applications.AppUtil;
import wd.android.util.util.MyFormatter;

public class PackageDemoActivity extends Activity implements
        OnItemClickListener {
    private ListView listview = null;
    private List<AppInfo> mlistAppInfo = null;
    LayoutInflater infater = null;
    // 全局变量，保存当前查询包得信息
    private long cachesize; // 缓存大小
    private long datasize; // 数据大小
    private long codesize; // 应用程序大小
    private long totalsize; // 总大小

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packagedemo_browse_app_list);
        listview = (ListView) findViewById(R.id.listviewApp);
        mlistAppInfo = new ArrayList<AppInfo>();
        // queryAppInfo(); // 查询所有应用程序信息
        getInstalledPackages();
        BrowseApplicationInfoAdapter browseAppAdapter = new BrowseApplicationInfoAdapter(
                this, mlistAppInfo);
        listview.setAdapter(browseAppAdapter);
        listview.setOnItemClickListener(this);
    }

    // 点击弹出对话框，显示该包得大小
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {

        // // 更新显示当前包得大小信息
        // try {
        // PackageStats packageStats = AppInfoHelper.queryPacakgeSize(this,
        // mlistAppInfo.get(position).getPkgName());
        // cachesize = packageStats.cacheSize; // 缓存大小
        // datasize = packageStats.dataSize; // 数据大小
        // codesize = packageStats.codeSize; // 应用程序大小
        // totalsize = cachesize + datasize + codesize;
        // Log.i(TAG, "cachesize--->" + cachesize + " datasize---->"
        // + datasize + " codeSize---->" + codesize);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        infater = (LayoutInflater) PackageDemoActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialog = infater.inflate(R.layout.packagedemo_dialog_app_size,
                null);
        TextView tvcachesize = (TextView) dialog.findViewById(R.id.tvcachesize); // 缓存大小
        TextView tvdatasize = (TextView) dialog.findViewById(R.id.tvdatasize); // 数据大小
        TextView tvcodesize = (TextView) dialog.findViewById(R.id.tvcodesize); // 应用程序大小
        TextView tvtotalsize = (TextView) dialog.findViewById(R.id.tvtotalsize); // 总大小
        // 类型转换并赋值
        tvcachesize.setText(MyFormatter.formateFileSize(this, cachesize));
        tvdatasize.setText(MyFormatter.formateFileSize(this, datasize));
        tvcodesize.setText(MyFormatter.formateFileSize(this, codesize));
        tvtotalsize.setText(MyFormatter.formateFileSize(this, totalsize));
        // 显示自定义对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(
                PackageDemoActivity.this);
        builder.setView(dialog);
        builder.setTitle(mlistAppInfo.get(position).getAppLabel() + "的大小信息为：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // 取消显示对话框
            }

        });
        builder.create().show();
    }

    private void getInstalledPackages() {
        List<PackageInfo> packages = AppUtil.getInstalledPackages(
                this.getPackageManager(), AppUtil.APP_USER);
        for (PackageInfo packageInfo : packages) {
            // 创建一个AppInfo对象，并赋值
            AppInfo appInfo = new AppInfo();
            appInfo.setAppLabel(packageInfo.applicationInfo.loadLabel(
                    getPackageManager()).toString());
            appInfo.setPkgName(packageInfo.packageName);
            appInfo.setAppIcon(packageInfo.applicationInfo
                    .loadIcon(getPackageManager()));
            // appInfo.setIntent(launchIntent);
            mlistAppInfo.add(appInfo); // 添加至列表中
        }
    }
}