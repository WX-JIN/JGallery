package com.soubw.wxj;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.soubw.jgallery.JGallery;
import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.config.PageTransformer;
import com.soubw.jgallery.listener.OnJGalleryClickListener;

/**
 * author: WX_JIN
 * email: wangxiaojin@soubw.com
 */
public class BannerActivity extends FragmentActivity {
    private com.soubw.jgallery.JGallery jGallery;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_activity);
        this.jGallery = (JGallery) findViewById(R.id.jGallery);
//        List list = new ArrayList();
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/2.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/3.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/4.jpg");


        final String[] list = new String[]{
                "http://img2.ph.126.net/S5yjVLz4t7k7fTl0D7hPLw==/6630852261420005987.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/2.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/3.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/4.jpg"
        };

        final String[] list1 = new String[]{
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/gif/list/1.gif",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/gif/list/2.gif",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/gif/list/3.gif",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/gif/list/4.gif",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/video/mp4/1.mp4"
                ,"http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/2.jpg",
                Environment.getExternalStorageDirectory().getPath()+"/test",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/3.jpg"
        };

        final Object[] type = new Object[]{
                DataType.NORMAL_IMAGE, DataType.NORMAL_IMAGE, DataType.NORMAL_IMAGE, DataType.NORMAL_IMAGE
        };

        final Object[] pre = new Object[]{
                R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher
        };

        final Object[] type1 = new Object[]{
                DataType.GIF_IMAGE, DataType.GIF_IMAGE, DataType.GIF_IMAGE,
                DataType.GIF_IMAGE,DataType.NET_VIDEO, DataType.NORMAL_IMAGE, DataType.NORMAL_IMAGE, DataType.LOCAL_VIDEO,DataType.NORMAL_IMAGE

        };


        /**
         * 可配置默认的默认的图片（视频或者图片的缩略图）
         */
        final Object[] pre1 = new Object[]{
                R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher

        };

        this.jGallery.setData(list1,type1,pre1);
        this.jGallery.setPageTransformer(PageTransformer.Default);
        this.jGallery.setOnJGalleryClickListener(new OnJGalleryClickListener() {
            @Override
            public void OnClick(View view, int position) {
                //todo
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jGallery.addBeforeData(list,type,pre);
            }
        },3000);

    }
}
