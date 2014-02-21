package me.android.app.global;

import java.util.HashMap;
import java.util.Map;

import me.android.custom.MainConfig;
import wd.android.util.util.MapUtil;

public class UrlData {
    /**
     * 获得地址列表
     */
    public static final String URL_INIT = MainConfig.SERVER_ROOT
            + "/ichina/resource/cltv1/loading.jsp";

    // public static final int xx = 0x01;
    private Map<String, String> urls = new HashMap<String, String>();

    public void initUrl(Map<String, String> urls) {
        if (null != urls) {
            this.urls.clear();
            this.urls.putAll(urls);
        }
    }

    /**
     * 从服务器下发的地址列表中获取接口地址
     */
    public String getUrl(String key) {
        String url = MapUtil.getString(urls, key);
        return url;
    }

    /**
     * 从map中获取接口地址
     */
    public String getUrl(Map<String, Object> map, String key) {
        return MapUtil.getString(map, key);
    }

    /**
     * 首页（热门推荐）
     */
    public static final String URL_MAIN_PAGE = "mainPage";
}
