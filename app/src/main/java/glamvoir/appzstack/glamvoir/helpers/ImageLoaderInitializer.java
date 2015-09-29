package glamvoir.appzstack.glamvoir.helpers;

import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Gajendran on 16-08-2015.
 */
public class ImageLoaderInitializer {
    private static DisplayImageOptions displayImageOptionWithFade = new DisplayImageOptions.Builder()
            .displayer(new FadeInBitmapDisplayer(250, true, false, false))
            .resetViewBeforeLoading(true)
            .cacheInMemory(Utility.shouldEnableCacheOnMemory())
            .cacheOnDisc(true)
            .build();

    public static void initImageLoaderIfNotInited(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            DisplayImageOptions imageDefaultOptions = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(true)
                    .cacheInMemory(Utility.shouldEnableCacheOnMemory())
                    .cacheOnDisk(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPoolSize(3)
                    .threadPriority(Thread.MIN_PRIORITY)
                    .defaultDisplayImageOptions(imageDefaultOptions)
                    .memoryCache(new LRULimitedMemoryCache(8 * 1024 * 1024))

//                    .diskCache(new LimitedAgeDiscCache(
//                                    new File(StorageUtils.getCacheDirectory(context), "icons"),
//                                    null,
//                                    // 30 days in secs: 30*24*60*60 = 2592000
//                                    2592000))
                    .build();

            ImageLoader.getInstance().init(config);
        }
    }

    public static DisplayImageOptions getDisplayImageOptionWithFade() {
        return displayImageOptionWithFade;
    }
}
