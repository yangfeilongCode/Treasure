package com.feicuiedu.treasure;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.feicuiedu.treasure.user.UserPrefs;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 宝藏应用  用来对百度地图SDK初始化
 */
public class TreasureAppplication extends Application {

    @Override
    public void onCreate() { //创建
        super.onCreate();

        UserPrefs.init(this); //初始化用户控制台-（使用前必须先对其进行初始化 init(),否则就报错啦！！！）

        // 对百度地图SDK初始化
        SDKInitializer.initialize(this);

        initImageLoader(); //初始图像载入
    }

    //初始图像载入
    private void initImageLoader() {
        //显示视图选择设置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_icon) //显示加载时视图
                .showImageOnFail(R.drawable.user_icon)  // 展示失败时的视图
                .showImageForEmptyUri(R.drawable.user_icon)  //  显示空路径时的图像
                //.displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R.dimen.dp_10)))
                .cacheOnDisk(true)  // 打开disk   缓存到磁盘上
                .cacheInMemory(true) // 打开cache  在内存中缓存
                .build();  //构造
        //视图加载配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(5*1024*1024) // 设置内存缓存5MB
                .defaultDisplayImageOptions(options)// 设置默认的显示选项
                .build();

        ImageLoader.getInstance().init(config); //？？？？

    }
}
