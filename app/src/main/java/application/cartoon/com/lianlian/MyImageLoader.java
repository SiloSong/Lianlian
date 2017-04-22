package application.cartoon.com.lianlian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;


/**
 * Created by l on 2017-03-18.
 */

public class MyImageLoader {
    private static final int MEMORY_CACHE_SIZE=2*1024;
    public static final int DISK_CACHE_SIZE=50*1024*1024;
/**
 * 加载网络图片不处理
 */
private static final DisplayImageOptions OPTIONS_NORMAL=new DisplayImageOptions.Builder()
        .cacheOnDisk(true)
        .cacheInMemory(true)
        .imageScaleType(ImageScaleType.EXACTLY
        ).bitmapConfig(Bitmap.Config.RGB_565)
        .considerExifParams(true)

        .build();
    /**
     * 加载本地图片，不缓存，不存储
     */
private static final DisplayImageOptions PHOTO_SELECT=new DisplayImageOptions.Builder()
            .cacheOnDisk(false).cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)

            .considerExifParams(true).build();
    /**
     * 加载原型图片，圆形边框为白色，边框宽度为5
     */
    private static final DisplayImageOptions OPTIONS_CRICLE=new DisplayImageOptions.Builder()
            .cacheOnDisk(true).cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)

            .displayer(new CircleBitmapDisplayer(Color.WHITE,5))
            .considerExifParams(true).build();
    /**
     * 加载圆角图片，圆角弧度为8
     */
    private static final DisplayImageOptions OPTIONS_ROUND_RADIUS=new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(new RoundedBitmapDisplayer(0))
            .bitmapConfig(Bitmap.Config.RGB_565)

            .considerExifParams(true)
            .build();

    private static ImageLoader instance;
    /**
     * 获取缓存的图片路径
     *
     */
    public static ImageLoader getImageLoader(Context context){
        if (instance==null){
          init(context);
        }
        return instance;
    }
    private static void init(Context context){
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .imageDownloader(new BaseImageDownloader(context,10000,10000))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(DISK_CACHE_SIZE)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                .build();
        ImageLoader.getInstance().init(config)
        ;
        instance=ImageLoader.getInstance();


    }
    public  static void display(Context context,String url, ImageView img,DisplayImageOptions options){
        if (instance==null){
            init(context);
        }
       instance.displayImage(url,img,options);
    }
    public static void displayPhoto(Context context,String url,ImageView imageView){
        display(context ,url,imageView,PHOTO_SELECT);
    }
    public static void displayCircle(Context context,String url,ImageView imageView){
        display(context ,url,imageView,OPTIONS_CRICLE);
    }
    public static void displayRoundRadius(Context context,String url,ImageView imageView){
        display(context ,url,imageView,OPTIONS_ROUND_RADIUS);
    }
    public static void clearCache(Context context){
        if (instance!=null){
            instance.clearDiskCache();
            instance.clearMemoryCache();
             }
    }
    public static File getImageLoaderFile(Context context,String url){
        if (instance==null){
            init(context);
        }
        File file=instance.getDiskCache().get(url);
        return  file;

    }

}
