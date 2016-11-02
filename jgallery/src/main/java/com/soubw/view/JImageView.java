package com.soubw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.soubw.jgallery.R;
import com.soubw.jgallery.config.DataType;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JImageView extends JView {

    public JImageView(Context context) {
        super(context);
    }

    public JImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected int layoutId() {
        return R.layout.jimageview;
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    protected void onViewLongClick(View v) {

    }

    @Override
    protected void loadFileSuccess(String path, String name) {

    }

    @Override
    protected void loadError(String error) {

    }


    @Override
    protected void preDownLoad() {

    }

    @Override
    protected void refreshStatus() {
        ivImage.setVisibility(View.INVISIBLE);
        jRoundProgressBar.setVisibility(View.INVISIBLE);
        layoutOver.setVisibility(View.INVISIBLE);
        if(dataType.equals(DataType.NORMAL_IMAGE) || dataType.equals(DataType.GIF_IMAGE)){
            ivImage.setVisibility(View.VISIBLE);
        }else if(dataType.equals(DataType.OVER_IMAGE)){
            layoutOver.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void showImage(){
/*        if (dataType.equals(DataType.NORMAL_IMAGE) ){
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().into(ivImage);
            else
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().placeholder(defaultImage).into(ivImage);
        }else if(dataType.equals(DataType.GIF_IMAGE)){
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivImage);
            else
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImage).into(ivImage);
        }else if(dataType.equals(DataType.OVER_IMAGE)){
            layoutOver.setVisibility(View.VISIBLE);
        }
        if (thumbnail != null && defaultImage != -1)
            Glide.with(context).load(thumbnail).centerCrop().crossFade().placeholder(defaultImage).into(ivImage);
        else if (thumbnail != null)
            Glide.with(context).load(thumbnail).centerCrop().crossFade().into(ivImage);*/

    }
    
}
