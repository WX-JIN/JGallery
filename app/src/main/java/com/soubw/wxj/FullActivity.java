package com.soubw.wxj;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.soubw.jgallery.JGallery;
import com.soubw.jgallery.config.PageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * author: WX_JIN
 * email: wangxiaojin@soubw.com
 */
public class FullActivity extends FragmentActivity {

    private JGallery jGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jGallery = new JGallery(this);
        setContentView(jGallery);
        List list = new ArrayList();
        list.add("http://img.hb.aicdn.com/2f9f2dbb8e4a3c5356d1a68d028200dafba6999c11ed5-y06Nn9_fw658");
        list.add("http://img2.ph.126.net/W_ARfKat8Kd980IaCadAfA==/6630180459815284013.jpg");
        list.add("http://img0.ph.126.net/KYhtqy2CjGE7PPqwsa4UjQ==/6630928127722322679.jpg");
        list.add("http://img.hb.aicdn.com/f95cd52b24c3cd5718900a8661e7d753ce44b37e179504-6TO3FP_fw658");
        list.add("http://ww2.sinaimg.cn/mw690/92077a4bgw1f5q72gjc6wj20qo140n7v.jpg");
        list.add("http://img.hb.aicdn.com/ea8ee7955be7e1ff706a4b72bb1c22012f2356461666d-yPnDC6_fw658");


        //
        /**
         * 添加数据
         * param1 图片或者视频url
         * param2 图片或者视频类型
         * param3 图片或者视频缩略图或者默认图
         */
        this.jGallery.setData(list);

        /**
         * 设置默认背景图
         */
        this.jGallery.setDefaultImage(R.mipmap.ic_launcher);

        /**
         * 设置切换图片风格
         */
        this.jGallery.setPageTransformer(PageTransformer.ScaleInOut);

    }
}
