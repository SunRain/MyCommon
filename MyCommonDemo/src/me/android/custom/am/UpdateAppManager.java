package me.android.custom.am;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wd.android.util.util.MapUtil;
import wd.android.util.util.ObjectUtil;
import wd.android.util.util.Utils;

public class UpdateAppManager {
    private static final long INIT_DELAY_TIME = 1 * 60 * 1000 / 2;
    private static final long PERIOD = 2 * 60 * 60 * 1000;
    private Handler taskHandler = new Handler();
    private Handler externalHandler;
    private final Set<IUpdateAppListener> listeners = ObjectUtil.newHashSet();

    private IAysnRequest aysnRequest;
    private ExecutorService threadPool;

    private List<Map<String, Object>> updateList = ObjectUtil.newArrayList();

    public void config(IAysnRequest aysnRequest) {
        this.aysnRequest = aysnRequest;
    }

    public List<Map<String, Object>> getUpdateList() {
        synchronized (updateList) {
            return new ArrayList<Map<String, Object>>(updateList);
        }
    }

    UpdateAppManager(Handler externalHandler) {
        this.externalHandler = externalHandler;
        threadPool = Executors.newCachedThreadPool();
    }

    void start(Handler externalHandler) {
        taskHandler.postDelayed(task, INIT_DELAY_TIME);
    }

    void stop() {
        taskHandler.removeCallbacks(task);
    }

    private Runnable task = new Runnable() {
        public void run() {
            taskHandler.postDelayed(this, PERIOD);
            //
            externalHandler.sendEmptyMessage(AppManager.REQUEST_UPDATE_LIST);
        }
    };

    // 第一次请求后，每两小时查询一次
    void request(List<Map<String, String>> list) {
        if (aysnRequest == null) {
            // MyLog.e("aysnRequest = null");
            aysnRequest = new DefaultAysnRequest();
        }

        aysnRequest.request(list, new IResult() {
            @Override
            public void onSuccess(List<Map<String, Object>> updateAppList) {
                synchronized (updateList) {
                    updateList.clear();
                    if (Utils.isEmpty(updateAppList)) {
                        updateList.addAll(updateAppList);
                    }
                }

                notifyUpdateAppListener();
            }
        });
    }

    public static class DefaultAysnRequest implements IAysnRequest {
        @Override
        public void request(List<Map<String, String>> list, final IResult result) {
            // String url = MyManager.getUrl(UrlData.APP_UPDATE_URL);
            // Map<String, String> paramMap = ObjectUtil.newHashMap();
            // String json = JSON.toJSONString(list);
            // paramMap.put(Tag.REQUEST_LIST, json);
            // HttpUtil.exec(url, paramMap, new BaseHttpListener() {
            // @Override
            // protected void onSuccess(Map<String, String> headers,
            // Map<String, Object> responseMap) {
            // super.onSuccess(headers, responseMap);
            // // externalHandler
            // List<Map<String, Object>> updateAppList = MapUtil.getList(
            // responseMap, Tag.UPDATE_APP_LIST);
            // result.onSuccess(updateAppList);
            // }
            // });
        }
    }

    public void registerUpdateAppListener(IUpdateAppListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    /**
     * 去注册IAppManager监听接口
     *
     * @param listener
     */
    public void unRegisterUpdateAppListener(IUpdateAppListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void notifyUpdateAppListener() {
        synchronized (listeners) {
            for (IUpdateAppListener listener : listeners) {
                listener.notifyUpdateAppListener();
            }
        }
    }

    public void onAddApp(final String packageName, final Map<String, String> app) {
        // 查询list中，如果versionCode>=list，则移除list中数据，并刷新UI
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (updateList) {
                    Map<String, Object> mapTmp = null;
                    for (Map<String, Object> map : updateList) {
                        String pkgName = MapUtil.getString(map,
                                AppTag.PACKAGE_NAME);
                        if (pkgName.equals(packageName)) {
                            int versionCode = MapUtil.getInt(map,
                                    AppTag.VERSION_CODE);
                            int installedVersion = MapUtil.getInt(app,
                                    AppTag.VERSION_CODE);
                            if (installedVersion >= versionCode) {
                                mapTmp = map;
                            }
                            break;
                        }
                    }
                    if (mapTmp != null) {
                        updateList.remove(mapTmp);
                        notifyUpdateAppListener();
                    }
                }
            }
        });
    }

    public void onRemoveApp(final String packageName) {
        // 移除list中数据，并刷新UI
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (updateList) {
                    Map<String, Object> mapTmp = null;
                    for (Map<String, Object> map : updateList) {
                        String pkgName = MapUtil.getString(map,
                                AppTag.PACKAGE_NAME);
                        if (pkgName.equals(packageName)) {
                            mapTmp = map;
                            break;
                        }
                    }
                    if (mapTmp != null) {
                        updateList.remove(mapTmp);
                        notifyUpdateAppListener();
                    }
                }
            }
        });
    }

    public static interface IAysnRequest {
        public void request(List<Map<String, String>> list, IResult iResult);
    }

    public static interface IResult {
        public void onSuccess(List<Map<String, Object>> updateAppList);
    }
}
