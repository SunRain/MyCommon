package me.android.common.cache.core;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import wd.android.util.util.ByteUtil;
import wd.android.util.util.MyLog;

public class GuavaCache<V> {
	// 基本上可以通过两种方式来创建cache：
	// cacheLoader
	// callable callback
	// 通过这两种方法创建的cache，和通常用map来缓存的做法比，不同在于，这两种方法都实现了一种逻辑——从缓存中取key
	// X的值，如果该值已经缓存过了，则返回缓存中的值，如果没有缓存过，可以通过某个方法来获取这个值。
	// 但不同的在于cacheloader的定义比较宽泛，是针对整个cache定义的，可以认为是统一的根据key值load value的方法。
	// 而callable的方式较为灵活，允许你在get的时候指定。

	// 　回收的参数：
	// 　　1. 大小的设置：CacheBuilder.maximumSize(long) CacheBuilder.weigher(Weigher)
	// CacheBuilder.maxumumWeigher(long)
	// 　　2. 时间：expireAfterAccess(long, TimeUnit) expireAfterWrite(long,
	// TimeUnit)
	// 　　3. 引用：CacheBuilder.weakKeys() CacheBuilder.weakValues()
	// CacheBuilder.softValues()
	// 　　4. 明确的删除：invalidate(key) invalidateAll(keys) invalidateAll()
	// 　　5. 删除监听器：CacheBuilder.removalListener(RemovalListener)

	private LoadingCache<String, V> cache;
	private ILocalCache<String, V> localCache;

	public GuavaCache(ILocalCache<String, V> localCache) {
		this.localCache = localCache;
		init();
	}

	public void release() {
	}

	private void init() {
		cache = createCache(new CacheLoader<String, V>() {
			public V load(String key) throws Exception {
				MyLog.i("key = " + key);
				// 从本地获取，DiskLruCache
				// key = toKey(key);
				V value = localCache.get(key);
				if (value != null) {
					return value;
				} else {
					throw new Exception("key = " + key + ",value = null");
				}
			}
		});
	}

	public LoadingCache<String, V> createCache(
			CacheLoader<String, V> cacheLoader) {
		RemovalListener<String, V> removalListener = new RemovalListener<String, V>() {
			@Override
			public void onRemoval(RemovalNotification<String, V> rn) {
				MyLog.i("rn.getKey() = " + rn.getKey());
				// 移除时删除本地内容
				if (localCache.isExpire(rn.getKey())) {
					localCache.delete(rn.getKey());
				}
			}
		};

		LoadingCache<String, V> cache = CacheBuilder.newBuilder()
				.maximumSize(50).weakKeys().softValues()
				.expireAfterWrite(60 * 60, TimeUnit.SECONDS)
				.removalListener(removalListener).build(cacheLoader);
		return cache;
	}

	public void clear() {
		cache.cleanUp();
		cache.invalidateAll();
	}

	public V get(String key) {
		key = toKey(key);
		try {
			V value = cache.get(key);
			MyLog.i("key = " + key + ",value = " + value);
			return value;
		} catch (ExecutionException e) {
			MyLog.i(e.getMessage());
			return null;
		}
	}

	public void put(String key, V value) {
		key = toKey(key);
		cache.put(key, value);
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
