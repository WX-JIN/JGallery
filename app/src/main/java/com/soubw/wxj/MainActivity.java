package com.soubw.wxj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void btnBanner(View view){
        startActivity(new Intent(this,BannerActivity.class));
    }

    public void btnFull(View view){
        startActivity(new Intent(this,FullActivity.class));
    }

}
