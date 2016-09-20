package com.soubw.jvideoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.soubw.jgallery.R;
import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.listener.OnJGalleryClickListener;
import com.soubw.jgallery.listener.OnJGalleryLoadListener;
import com.soubw.jgallery.listener.OnJGalleryLongClickListener;
import com.soubw.jroundprogressbar.JRoundProgressBar;
import com.soubw.utils.OkHttpProgress;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JVideoView extends FrameLayout {

    private VideoView videoView;
    private JRoundProgressBar jRoundProgressBar;
    private ImageView ivPlayVideo;
    private ImageView ivImage;
    private LinearLayout layoutVideoOver;
    private ProgressBar progressBar;

    private OnJGalleryClickListener onJGalleryClickListener;
    private OnJGalleryLongClickListener onJGalleryLongClickListener;
    private OnJGalleryLoadListener onJGalleryLoadListener;

    private Object url;
    private Object dataType;
    private int position;
    private int currentPosition = -1;
    private int defaultImage = -1;

    private MediaMetadataRetriever mediaMetadataRetriever;

    public JVideoView(Context context) {
        this(context, null);
    }

    public JVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.jvideoview, this, true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    currentPosition = videoView.getCurrentPosition();
                    videoView.pause();
                    Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000,
                            MediaMetadataRetriever.OPTION_NEXT_SYNC);
                    if (bitmap != null) {
                        ivImage.setVisibility(View.VISIBLE);
                        ivImage.setImageBitmap(bitmap);
                    }else if(defaultImage != -1){
                        ivImage.setVisibility(View.VISIBLE);
                        ivImage.setImageResource(defaultImage);
                    }
                    videoView.setVisibility(View.INVISIBLE);
                    ivPlayVideo.setVisibility(View.VISIBLE);
                    return;
                }
                if (onJGalleryClickListener != null){
                    onJGalleryClickListener.OnClick(v,position);
                }
            }
        });
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onJGalleryLongClickListener != null){
                    onJGalleryLongClickListener.OnLongClick(v,position);
                }
                return false;
            }
        });
        this.layoutVideoOver = (LinearLayout) view.findViewById(R.id.layoutVideoOver);
        this.ivImage = (ImageView) view.findViewById(R.id.ivImage);
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
                    initLoadVideo();
                }
            }
        });
        this.jRoundProgressBar = (JRoundProgressBar) view.findViewById(R.id.jRoundProgressBar);
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

    private static final int MESSAGE_PROGRESS = 1;
    private static final int MESSAGE_FILE = 2;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PROGRESS:
                    int percent = msg.arg1*100/msg.arg2;
                    refreshProgress(percent);
                    break;
                case MESSAGE_FILE:
                    Bundle bundle= msg.getData();
                    dataType = DataType.LOCAL_VIDEO;
                    url = bundle.getString("path");
                    refreshStatus();
                    if(onJGalleryLoadListener !=null){
                        onJGalleryLoadListener.onLoad(position, bundle.getString("path"), bundle.getString("name"));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void initLoadVideo(){
        ivPlayVideo.setVisibility(View.INVISIBLE);
        jRoundProgressBar.setVisibility(View.VISIBLE);
        final OkHttpProgress.ProgressListener progressListener = new OkHttpProgress.ProgressListener() {
            @Override
            public void update(long bytesRead,long contentLength, boolean done) {
                if (handler != null) {
                    Message message = handler.obtainMessage();
                    message.what = MESSAGE_PROGRESS;
                    message.arg1 = (int) bytesRead;
                    message.arg2 = (int) contentLength;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onLoad(String path, String name) {
                if (handler != null) {
                    Message message = handler.obtainMessage();
                    message.what = MESSAGE_FILE;
                    Bundle bundle = new Bundle();
                    bundle.putString("path",path);
                    bundle.putString("name",name);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpProgress.startUrl((String) url,progressListener);
            }
        }).start();
    }

    public void setData(Object o, Object dt,int pos){
        this.url = o;
        this.dataType = dt;
        this.position = pos;
        refreshStatus();
    }

    private void refreshProgress(int progress) {
        if(this.jRoundProgressBar.getVisibility() != View.VISIBLE && dataType.equals(DataType.NET_VIDEO))
            this.jRoundProgressBar.setVisibility(View.VISIBLE);
        this.jRoundProgressBar.setProgress(progress);
    }

    private void refreshStatus() {
        ivImage.setVisibility(View.INVISIBLE);
        jRoundProgressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        ivPlayVideo.setVisibility(View.INVISIBLE);
        layoutVideoOver.setVisibility(View.INVISIBLE);
        if(dataType.equals(DataType.NET_VIDEO)){
            ivPlayVideo.setVisibility(View.VISIBLE);
        } else if(dataType.equals(DataType.LOCAL_VIDEO)){
            ivPlayVideo.setVisibility(View.VISIBLE);
            videoView.setVideoPath((String) url);
            mediaMetadataRetriever.setDataSource((String) url);
        }else if(dataType.equals(DataType.OVER_VIDEO)){
            layoutVideoOver.setVisibility(View.VISIBLE);
        }
    }

    private void setDefaultImage(int di){
        this.defaultImage = di;
        if (ivImage != null){
            ivImage.setImageResource(defaultImage);
        }
    }

    public void setJGalleryClickListener(OnJGalleryClickListener listener) {
        this.onJGalleryClickListener = listener;
    }

    public void setJGalleryLongClickListener(OnJGalleryLongClickListener listener) {
        this.onJGalleryLongClickListener = listener;
    }

    public void setJGalleryLoadListener(OnJGalleryLoadListener listener) {
        this.onJGalleryLoadListener = listener;
    }

}
