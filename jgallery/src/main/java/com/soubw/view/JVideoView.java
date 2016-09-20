package com.soubw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.soubw.jgallery.R;
import com.soubw.jgallery.config.DataType;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JVideoView extends JView {

    private VideoView videoView;
    private ImageView ivPlayVideo;
    private ProgressBar progressBar;

    private int currentPosition = -1;
    private MediaMetadataRetriever mediaMetadataRetriever;

    public JVideoView(Context context) {
        super(context);
    }

    public JVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void loadView(View view) {
        mediaMetadataRetriever = new MediaMetadataRetriever();
        this.ivPlayVideo = (ImageView) view.findViewById(R.id.ivPlayVideo);
        this.ivPlayVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataType.equals(DataType.LOCAL_VIDEO)){
                    ivImage.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    if (currentPosition != -1){
                        videoView.seekTo(currentPosition);
                    }
                    videoView.start();
                    if (!videoView.isPlaying()){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    ivPlayVideo.setVisibility(View.INVISIBLE);
                } else if(dataType.equals(DataType.NET_VIDEO)){
                    downLoad();
                }
            }
        });
        this.videoView = (VideoView) view.findViewById(R.id.videoView);
        this.videoView.setZOrderOnTop(true);
        this.videoView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlayVideo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.jvideoview;
    }

    @Override
    protected void onViewClick(View v) {
        if(!pauseVideo()){
            if (onJGalleryClickListener != null){
                onJGalleryClickListener.OnClick(v,position);
            }
        }
    }

    @Override
    protected void onViewLongClick(View v) {
        if (onJGalleryLongClickListener != null){
            onJGalleryLongClickListener.OnLongClick(v,position);
        }
    }

    @Override
    protected void jFile(String path, String name) {
        dataType = DataType.LOCAL_VIDEO;
        url = path;
        refreshStatus();
        if(onJGalleryLoadListener !=null){
            onJGalleryLoadListener.onLoad(position,path, name);
        }
    }

    @Override
    protected void preDownLoad() {
        ivPlayVideo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void refreshStatus() {
        ivImage.setVisibility(View.INVISIBLE);
        jRoundProgressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        ivPlayVideo.setVisibility(View.INVISIBLE);
        layoutOver.setVisibility(View.INVISIBLE);
        if(dataType.equals(DataType.NET_VIDEO)){
            ivPlayVideo.setVisibility(View.VISIBLE);
            showImage();
        } else if(dataType.equals(DataType.LOCAL_VIDEO)){
            ivPlayVideo.setVisibility(View.VISIBLE);
            videoView.setVideoPath((String) url);
            mediaMetadataRetriever.setDataSource((String) url);
            showImage();
        }else if(dataType.equals(DataType.OVER_VIDEO)){
            layoutOver.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void showImage(){
        if (thumbnail != null && defaultImage != -1){
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(thumbnail).centerCrop().crossFade().placeholder(defaultImage).into(ivImage);
        }
        else if (thumbnail != null){
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(thumbnail).centerCrop().crossFade().into(ivImage);
        }
    }

    public boolean pauseVideo(){
        if (videoView.isPlaying()){
            currentPosition = videoView.getCurrentPosition();
            videoView.pause();
//            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000,
//                    MediaMetadataRetriever.OPTION_NEXT_SYNC);
            Bitmap bitmap = null;
            if (bitmap != null) {
                ivImage.setVisibility(View.VISIBLE);
                ivImage.setImageBitmap(bitmap);
            }else{
                showImage();
            }
            videoView.setVisibility(View.INVISIBLE);
            ivPlayVideo.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

}
