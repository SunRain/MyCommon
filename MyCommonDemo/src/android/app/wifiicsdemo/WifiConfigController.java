//package android.app.wifiicsdemo;
//
//import java.lang.reflect.Field;
//import java.net.InetAddress;
//
//import android.net.LinkAddress;
//import android.net.LinkProperties;
//import android.net.NetworkUtils;
//import android.net.RouteInfo;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiConfiguration.KeyMgmt;
//
//public class WifiConfigController {
//	private LinkProperties mLinkProperties = new LinkProperties();
//
//	private Field ipAssignment, linkProperties;
//	private Class IpAssignment_enum;
//	private String mSsid = "test_wlan";
//	private String mPassword = "mobilevideo*1234";
//	private String ipAddr = "192.168.99.189";
//	private String networkPrefixLengthStr = "24";
//	private String gateway = "192.168.99.1";
//	private String dns1 = "202.96.209.5";
//	private String dns2 = "202.96.209.133";
//
//	public WifiConfigController() {
//	}
//
//	private void initWifiConfiguration() {
//		try {
//			Class<?> klass = Class
//					.forName("android.net.wifi.WifiConfiguration");
//			IpAssignment_enum = Class
//					.forName("android.net.wifi.WifiConfiguration$IpAssignment");
//			ipAssignment = klass.getDeclaredField("ipAssignment");
//			ipAssignment.setAccessible(true);
//			linkProperties = klass.getDeclaredField("linkProperties");
//			linkProperties.setAccessible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	String convertToQuotedString(String string) {
//		return "\"" + string + "\"";
//	}
//
//	WifiConfiguration getConfig() throws IllegalArgumentException,
//			IllegalAccessException {
//		ipAndProxyFieldsAreValid();
//		WifiConfiguration config = new WifiConfiguration();
//
//		config.SSID = convertToQuotedString(mSsid);
//		// If the user adds a network manually, assume that it is hidden.
//		// config.hiddenSSID = true;
//
//		config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
//		if (mPassword.matches("[0-9A-Fa-f]{64}")) {
//			config.preSharedKey = mPassword;
//		} else {
//			config.preSharedKey = '"' + mPassword + '"';
//		}
//
//		initWifiConfiguration();
//		ipAssignment.set(config, Enum.valueOf(IpAssignment_enum, "STATIC"));
//		linkProperties.set(config, new LinkProperties(mLinkProperties));
//		return config;
//	}
//
//	private boolean ipAndProxyFieldsAreValid() {
//		mLinkProperties.clear();
//		int result = validateIpConfigFields(mLinkProperties);
//		if (result != 0) {
//			return false;
//		}
//		return true;
//	}
//
//	private int validateIpConfigFields(LinkProperties linkProperties) {
//		InetAddress inetAddr = null;
//		try {
//			inetAddr = NetworkUtils.numericToInetAddress(ipAddr);
//		} catch (IllegalArgumentException e) {
//			return -1;
//		}
//
//		int networkPrefixLength = -1;
//		try {
//			networkPrefixLength = Integer.parseInt(networkPrefixLengthStr);
//		} catch (NumberFormatException e) {
//			// Use -1
//		}
//		if (networkPrefixLength < 0 || networkPrefixLength > 32) {
//			return -1;
//		}
//		linkProperties.addLinkAddress(new LinkAddress(inetAddr,
//				networkPrefixLength));
//
//		InetAddress gatewayAddr = null;
//		try {
//			gatewayAddr = NetworkUtils.numericToInetAddress(gateway);
//		} catch (IllegalArgumentException e) {
//			return -1;
//		}
//		linkProperties.addRoute(new RouteInfo(gatewayAddr));
//
//		InetAddress dnsAddr = null;
//		try {
//			dnsAddr = NetworkUtils.numericToInetAddress(dns1);
//		} catch (IllegalArgumentException e) {
//			return -1;
//		}
//		linkProperties.addDns(dnsAddr);
//		try {
//			dnsAddr = NetworkUtils.numericToInetAddress(dns2);
//		} catch (IllegalArgumentException e) {
//			return -1;
//		}
//		linkProperties.addDns(dnsAddr);
//		return 0;
//	}
//}
