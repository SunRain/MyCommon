//package android.app.wifiicsdemo;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import android.nbtstatx.mydemos.R;
//
//public class WifiIcsDemoActivity extends Activity {
//	private TextView mInfo = null;
//	private Button mBtnSubmit;
//	private WifiConfigController mController = null;
//	private WifiManager mWifiManager;
//	private String ssid = "test_wlan";
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.wifiicsdemo_main);
//		initViews();
//		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		mController = new WifiConfigController();
//	}
//
//	private void initViews() {
//		mInfo = (TextView) findViewById(R.id.info);
//		mBtnSubmit = (Button) findViewById(R.id.submit);
//		mBtnSubmit.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				submit();
//				StringBuilder sb = new StringBuilder();
//				sb.append(mWifiManager.getConnectionInfo().getIpAddress());
//				mInfo.setText(sb.toString());
//			}
//		});
//	}
//
//	private void submit() {
//		WifiConfiguration config;
//		try {
//			config = mController.getConfig();
//			int netId = mWifiManager.addNetwork(config);
//			//
//			// mWifiManager.enableNetwork(netId, true);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//}