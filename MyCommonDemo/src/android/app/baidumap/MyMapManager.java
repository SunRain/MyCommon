package android.app.baidumap;

import android.content.Context;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

public class MyMapManager {
    public static BMapManager bMapManager = null;
    private static final String strKey = "979dd7722f0c65bab6da9c33e0f5172e";

    public void initEngineManager(Context appContext) {
        if (bMapManager == null) {
            bMapManager = new BMapManager(appContext);
        }

        if (!bMapManager.init(strKey, new MyGeneralListener())) {
            // 初始化错误
        }
    } // 常用事件监听，用来处理通常的网络错误，授权验证错误等

    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                // "您的网络出错啦！"
            } else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                // "输入正确的检索条件！"
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
                // 授权Key错误：
                // "请在 DemoApplication.java文件输入正确的授权Key！",
                // DemoApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
}
