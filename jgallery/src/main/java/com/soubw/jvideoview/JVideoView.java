package com.soubw.jvideoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.soubw.jgallery.R;
import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.listener.OnJGalleryClickListener;
import com.soubw.jgallery.listener.OnJGalleryLongClickListener;
import com.soubw.jroundprogressbar.JRoundProgressBar;

/**
 * @author WX_JIN on 2016/9/13
 *         wangxiaojin@soubw.com
 *         http://soubw.com
 */
public class JVideoView extends FrameLayout {

    private android.widget.VideoView videoView;
    private com.soubw.jroundprogressbar.JRoundProgressBar jRoundProgressBar;
    private android.widget.ImageView ivPlayVideo;
    private android.widget.LinearLayout layoutVideoOver;

    private OnJGalleryClickListener onJGalleryClickListener;
    private OnJGalleryLongClickListener onJGalleryLongClickListener;

    private Object url;
    private DataType dataType;

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

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.jvideoview, this, true);
        this.layoutVideoOver = (LinearLayout) view.findViewById(R.id.layoutVideoOver);
        this.ivPlayVideo = (ImageView) view.findViewById(R.id.ivPlayVideo);
        this.jRoundProgressBar = (JRoundProgressBar) view.findViewById(R.id.jRoundProgressBar);
        this.videoView = (VideoView) view.findViewById(R.id.videoView);
    }


    public void setData(Object o, DataType dt){
        this.url = o;
        this.dataType = dt;
    }

    private void refreshProgress(int progress) {
        if(this.jRoundProgressBar.getVisibility() != View.VISIBLE)
            this.jRoundProgressBar.setVisibility(View.VISIBLE);
        this.jRoundProgressBar.setProgress(progress);
    }

/*    private void refreshStatus(JMixShowViewHolder holder, T t) {
        if(t.getType() == JMixShowType.JMIXSHOW_VIDEO){
            holder.ivPlayVideo.setVisibility(View.VISIBLE);
        }else{
            holder.ivPlayVideo.setVisibility(View.GONE);
        }
        if (t.getStatus() == JMixShowBean.NORMAL_STATUS ) {
            loadImage(holder,t.getDefaultPath());
        } else if (t.getStatus() == JMixShowBean.EXIST_STATUS ) {
            refreshLocalPath(holder,t);
        } else if (t.getStatus() == JMixShowBean.OVER_STATUS) {
            if (t.getType() == JMixShowType.JMIXSHOW_IMAGE){
                holder.rootPicOver.setVisibility(View.VISIBLE);
            }else if(t.getType() == JMixShowType.JMIXSHOW_VIDEO){
                holder.rootVideoOver.setVisibility(View.VISIBLE);
            }
            holder.ivPlayVideo.setVisibility(View.INVISIBLE);
            holder.jRoundProgressBar.setVisibility(View.INVISIBLE);
        }
    }*/


    public void setJGalleryClickListener(OnJGalleryClickListener listener) {
        this.onJGalleryClickListener = listener;
    }

    public void setJGalleryLongClickListener(OnJGalleryLongClickListener listener) {
        this.onJGalleryLongClickListener = listener;
    }


}
