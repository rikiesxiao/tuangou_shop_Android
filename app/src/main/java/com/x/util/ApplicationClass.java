package com.x.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.robin.lazy.cache.CacheLoaderConfiguration;
import com.robin.lazy.cache.CacheLoaderManager;
import com.robin.lazy.cache.disk.naming.HashCodeFileNameGenerator;
import com.robin.lazy.cache.memory.MemoryCache;
import com.robin.lazy.cache.util.MemoryCacheUtils;
import com.x.net.ServicesAPI;
import com.x.net.XNetUtil;
import com.x.tuangou_shop.LoginVC;
import com.x.tuangou_shop.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends MultiDexApplication {

	public static int SW = 0;
	public static int SH = 0;

	public static Context context;

	public static Retrofit retrofit;

	public static ServicesAPI APPService;

	private List<WeakReference<Activity>> vcArrs = new ArrayList<>();


	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();

		MemoryCache memoryCache= MemoryCacheUtils.createLruMemoryCache(1024*1024*256);
		CacheLoaderConfiguration config = new CacheLoaderConfiguration(this, new HashCodeFileNameGenerator(), 1024 * 1024 * 1024*64, 200000, memoryCache,60*24*30*365*20);
		CacheLoaderManager.getInstance().init(config);

		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				XNetUtil.APPPrintln("onActivityCreated: "+activity);
				context = activity;
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {
				context = activity;
				WeakReference<Activity> item = new WeakReference<Activity>(activity);
				vcArrs.add(item);
			}

			@Override
			public void onActivityPaused(Activity activity) {

			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {

			}

		});

		initImageLoader();
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		SW = displayMetrics.widthPixels;
		SH = displayMetrics.heightPixels;

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

				Request request = chain.request().newBuilder()
						.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
						.addHeader("Accept","*/*")
						.build();

				XNetUtil.APPPrintln("URL: "+request.url().toString());
				if(request.body() != null)
				{
					XNetUtil.APPPrintln("Body: "+request.body().toString());
				}

				Response response = chain.proceed(request);

				return response;
			}
		}).build();


		retrofit = new Retrofit.Builder()
				.baseUrl(ServicesAPI.APPUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.callFactory(client)
				.build();

		APPService = retrofit.create(ServicesAPI.class);

		DataCache.getInstance().init();

		EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this);

		System.out.println("================init============");
	}

	//初始化网络图片缓存库
	private void initImageLoader() {
		//网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
				showImageForEmptyUri(R.drawable.app_default)
				.showImageOnFail(R.drawable.app_default)
				.cacheInMemory(true).cacheOnDisk(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);

	}


	@Subscribe
	public void getEventmsg(MyEventBus myEventBus) {

		if (myEventBus.getMsg().equals("UserLogouted")) {

			for(WeakReference<Activity> item : vcArrs)
			{
				if(item.get() != null)
				{
					if(item.get() instanceof LoginVC)
					{
						continue;
					}

					item.get().finish();
				}
			}

		}

	}



}
