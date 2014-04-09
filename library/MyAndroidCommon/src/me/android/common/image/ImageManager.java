package me.android.common.image;

import java.io.File;

import wd.android.util.util.Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiscCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ImageManager {
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	private int maxImageWidthForMemoryCache = 0;
	private int maxImageHeightForMemoryCache = 0;

	public void init(Context context, String cacheDirPath, int defRedIs) {
		options = getOptions(defRedIs);

		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration defaultConfig = getConfiguration(context,
				cacheDirPath);

		// Initialize ImageLoader with configuration
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(defaultConfig);
	}

	public void deInit() {
		imageLoader.destroy();
		options = null;
	}

	private ImageLoaderConfiguration getConfiguration(Context context,
			String cacheDirPath) {
		ImageLoaderConfiguration.Builder builder = getDefaultConfigurationBuilder(
				context, cacheDirPath);
		return builder.build();
	}

	public ImageLoaderConfiguration.Builder getDefaultConfigurationBuilder(
			Context context, String cacheDirPath) {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context);
		// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
		// builder.denyCacheImageMultipleSizesInMemory();
		builder.discCacheFileCount(100);
		File cacheDir = null;
		if (Utils.isEmpty(cacheDirPath)) {
			cacheDir = StorageUtils.getIndividualCacheDirectory(context);
		} else {
			cacheDir = new File(cacheDirPath);
		}
		// 缓存100M
		// builder.discCache(new TotalSizeLimitedDiscCache(cacheDir,
		// new Md5FileNameGenerator(), 100 * 1024 * 1024));
		// 缓存保存24小时
		builder.discCache(new LimitedAgeDiscCache(cacheDir,
				new Md5FileNameGenerator(), 24 * 60 * 60));
		builder.tasksProcessingOrder(QueueProcessingType.LIFO);
		builder.threadPriority(Thread.NORM_PRIORITY);
		builder.threadPoolSize(3);
		// builder.defaultDisplayImageOptions(options);
		// builder.memoryCache(memoryCache);
		builder.memoryCacheExtraOptions(maxImageWidthForMemoryCache,
				maxImageHeightForMemoryCache);
		return builder;
	}

	public static final DisplayImageOptions.Builder getDefaultOptionsBuilder(
			int defRedIs) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		// builder.postProcessor(postProcessor)
		if (defRedIs != -1) {
			// 设置图片在下载期间显示的图片
			builder.showImageOnLoading(defRedIs);
			// 设置图片Uri为空或是错误的时候显示的图片
			builder.showImageForEmptyUri(defRedIs);
			// 设置图片加载/解码过程中错误时候显示的图片
			builder.showImageOnFail(defRedIs);
		}
		// 设置下载的图片是否缓存在内存中
		builder.cacheInMemory(true);
		// 设置下载的图片是否缓存在SD卡中
		builder.cacheOnDisc(true);
		// builder.displayer(new RoundedBitmapDisplayer(20));
		// 设置图片的解码类型
		builder.bitmapConfig(Bitmap.Config.RGB_565);
		// 设置图片的解码配置
		// builder.decodingOptions(android.graphics.BitmapFactory.Options
		// decodingOptions)
		// 设置图片以如何的编码方式显示
		// imageScaleType(ImageScaleType imageScaleType)

		// builder.displayer(displayer);
		return builder;
	}

	public static DisplayImageOptions getOptions(int defRedIs) {
		DisplayImageOptions.Builder builder = getDefaultOptionsBuilder(defRedIs);
		return builder.build();
	}

	public static final int CLEAR_CACHE_ALL = 0x01;
	public static final int CLEAR_CACHE_MEMORY = CLEAR_CACHE_ALL + 1;
	public static final int CLEAR_CACHE_DISC = CLEAR_CACHE_ALL + 2;

	/**
	 * 清理缓存
	 * 
	 * @param flag
	 */
	public void clearCache(int flag) {
		if (flag == CLEAR_CACHE_MEMORY) {
			imageLoader.clearMemoryCache();
		} else if (flag == CLEAR_CACHE_DISC) {
			imageLoader.clearDiscCache();
		} else {
			imageLoader.clearMemoryCache();
			imageLoader.clearDiscCache();
		}
	}

	/**
	 * 清理指定缓存
	 * 
	 * @param uri
	 */
	public void clearCache(String uri) {
		MemoryCacheUtils.removeFromCache(uri, imageLoader.getMemoryCache());
		DiscCacheUtils.removeFromCache(uri, imageLoader.getDiscCache());
	}

	public void loadImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, options);
	}

	public void loadImage(String uri, ImageView imageView, int defResId) {
		imageLoader.displayImage(uri, imageView, getOptions(defResId));
	}

	public void loadImage(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		imageLoader.displayImage(uri, imageView, listener);
	}

	public void loadImage(String uri, ImageAware imageAware) {
		imageLoader.displayImage(uri, imageAware);
	}

	public void loadImage(String uri, ImageView imageView,
			DisplayImageOptions options) {
		// 默认是ScaleType.FIT_CENTER，点9图片需要设置成fit_XY
		imageLoader.displayImage(uri, imageView, options);
	}

}
