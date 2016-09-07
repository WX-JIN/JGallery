package com.soubw.wxj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.soubw.jgallery.JGallery;
import com.soubw.jgallery.config.PageTransformerConfig;

public class MainActivity extends AppCompatActivity {

    private com.soubw.jgallery.JGallery jGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        this.jGallery = (JGallery) findViewById(R.id.jGallery);
//        List list = new ArrayList();
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/2.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/3.jpg");
//        list.add("http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/4.jpg");


        String[] list= new String[]{"http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/1.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/2.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/3.jpg",
                "http://7xllxs.com1.z0.glb.clouddn.com/common/pic/banner/4.jpg"};

        this.jGallery.setData(list);
        this.jGallery.setPageTransformer(PageTransformerConfig.Tablet);
    }
}
