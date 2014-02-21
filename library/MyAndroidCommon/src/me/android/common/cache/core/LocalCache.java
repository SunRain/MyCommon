package me.android.common.cache.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import wd.android.util.util.ByteUtil;
import wd.android.util.util.MyLog;

public class LocalCache<V> {
	private ILocalCache<String, V> localCache;

	public LocalCache(ILocalCache<String, V> localCache) {
		this.localCache = localCache;
	}

	public void release() {
	}

	public void clear() {
		localCache.clear();
	}

	public V get(String key) {
		key = toKey(key);
		if (localCache.isExpire(key)) {
			localCache.delete(key);
			return null;
		} else {
			V value = localCache.get(key);
			MyLog.i("key = " + key + ",value = " + value);
			return value;
		}
	}

	public void put(String key, V value) {
		key = toKey(key);
		localCache.put(key, value);
		MyLog.i("key = " + key + ",value = " + value);
		// 写入本地
		localCache.put(key, value);
	}

	private String toKey(String uri) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] md5bytes = messageDigest.digest(uri.getBytes());
			return ByteUtil.bytesToHexString(md5bytes, false);
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}
