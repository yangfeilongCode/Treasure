package com.feicuiedu.treasure;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure.commons.ActivityUtils;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * 视频播放碎片
 */
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView textureView;// 用来播放视频的view
    private MediaPlayer mediaPlayer;  //媒体播放器
    private ActivityUtils activityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textureView = new TextureView(getContext());  //初始化View
        return textureView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils = new ActivityUtils(this);   //activity工具类
        textureView.setSurfaceTextureListener(this); //表面结构监听
    }

    // 的确准备好了
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        try {
            AssetFileDescriptor afd = getContext().getAssets().openFd("welcome.mp4"); //找到视频文件
            FileDescriptor fd = afd.getFileDescriptor(); //获取文件说明符
            mediaPlayer = new MediaPlayer();  //初始化媒体播放器
            mediaPlayer.setDataSource(fd, afd.getStartOffset(), afd.getLength()); //设置数据来源
            mediaPlayer.prepareAsync(); // 异步
            //准备监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) { //准备好了
                    Surface mpSurface = new Surface(surface);  //表面初始化
                    mediaPlayer.setSurface(mpSurface); //设置表面
                    mediaPlayer.setLooping(true);  //是否循环
                    mediaPlayer.seekTo(0);  //制定播放的位置
                    mediaPlayer.start();  //启动
                }
            });
        } catch (IOException e) {
            activityUtils.showToast("媒体播放失败"); //异常
        }
    }
    //表面结构大小变化
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    //表面结构毁坏
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }
    //表面结构更新
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
    //生命周期 结束--破坏
    @Override public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); //释放媒体
            mediaPlayer = null;  //清空
        }
    }
}