package com.soubw.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public abstract class JView extends FrameLayout {

    private static final int MESSAGE_PROGRESS = 1;
    private static final int MESSAGE_FILE = 2;
    private static final int MESSAGE_ERROR = 3;

    protected OnJGalleryClickListener onJGalleryClickListener;
    protected OnJGalleryLongClickListener onJGalleryLongClickListener;
    protected OnJGalleryLoadListener onJGalleryLoadListener;
    protected JRoundProgressBar jRoundProgressBar;
    protected ImageView ivImage;
    protected LinearLayout layoutOver;

    protected Object url;
    protected Object dataType;
    protected Object thumbnail;
    protected int position;
    protected Context context;
    protected int defaultImage = -1;

    public JView(Context context) {
        this(context, null);
    }

    public JView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(layoutId(), this, true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClick(v);
            }
        });
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onViewLongClick(v);
                return false;
            }
        });
        this.layoutOver = (LinearLayout) view.findViewById(R.id.layoutOver);
        this.ivImage = (ImageView) view.findViewById(R.id.ivImage);
        this.jRoundProgressBar = (JRoundProgressBar) view.findViewById(R.id.jRoundProgressBar);
        loadView(view);
    }



    protected void downLoad(){
        preDownLoad();
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

            @Override
            public void onError(String errorMessage) {
                if (handler != null) {
                    Message message = handler.obtainMessage();
                    message.what = MESSAGE_ERROR;
                    Bundle bundle = new Bundle();
                    bundle.putString("error",errorMessage);
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

    protected Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle;
            switch (msg.what) {
                case MESSAGE_PROGRESS:
                    refreshProgress(msg.arg1*100/msg.arg2);
                    break;
                case MESSAGE_FILE:
                    bundle= msg.getData();
                    jFile(bundle.getString("path"), bundle.getString("name"));
                    break;
                case MESSAGE_ERROR:
                    bundle= msg.getData();
                    bundle.getString("error");
                    refreshStatus();
                    break;
                default:
                    break;
            }
        }
    };
    protected abstract int layoutId();
    protected abstract void loadView(View v);
    protected abstract void onViewClick(View v);
    protected abstract void onViewLongClick(View v);
    protected abstract void jFile(String path, String name);
    protected abstract void preDownLoad();
    protected abstract void refreshStatus();
    protected abstract void showImage();

    private void refreshProgress(int progress) {
        if(this.jRoundProgressBar.getVisibility() != View.VISIBLE && dataType.equals(DataType.NET_VIDEO))
            this.jRoundProgressBar.setVisibility(View.VISIBLE);
        this.jRoundProgressBar.setProgress(progress);
    }

    public void setData(Object o, Object dt,Object t, int pos){
        this.url = o;
        this.dataType = dt;
        this.thumbnail = t;
        this.position = pos;
        refreshStatus();
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
