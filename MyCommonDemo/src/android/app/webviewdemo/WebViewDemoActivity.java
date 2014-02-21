package android.app.webviewdemo;

import android.app.Activity;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import wd.android.util.util.MyLog;

public class WebViewDemoActivity extends Activity {
    private WebView webView = null;
    public Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewdemo_main);

        webView = (WebView) this.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);// JS利用OK
        // 设置字符集编码
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.addJavascriptInterface(new MyObject(webView, handler),
                "myObject");
        webView.setFocusable(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // activity.setProgress(progress * 1000);
                MyLog.e("onProgressChanged()");
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                MyLog.e("event = " + event);
                super.shouldOverrideKeyEvent(view, event);
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                MyLog.e("url = " + url);
                webView.loadUrl(url);
                return true;
            }
        });
        // 加载assets目录下的文件
        String url = "file:///android_asset/webviewdemo_index.html";
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MyLog.e("event = " + event);
        // return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }
}
