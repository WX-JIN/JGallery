package com.soubw.jgallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.listener.OnJGalleryClickListener;
import com.soubw.jgallery.listener.OnJGalleryLoadListener;
import com.soubw.jgallery.listener.OnJGalleryLongClickListener;
import com.soubw.jgallery.listener.OnJGalleryPageSelectedListener;
import com.soubw.jvideoview.JVideoView;

import java.util.List;

/**
 * @author WX_JIN
 * wangxiaojin@soubw.com
 * http://soubw.com
 */
public class JGalleryPagerAdapter extends JGalleryRecycleAdapter<JGalleryPagerAdapter.JGalleryHolder> {

    private int defaultImage = -1;
    private OnJGalleryClickListener onJGalleryClickListener;
    private OnJGalleryLongClickListener onJGalleryLongClickListener;
    private OnJGalleryLoadListener onJGalleryLoadListener;
    private OnJGalleryPageSelectedListener onJGalleryPageSelectedListener;

    public JGalleryPagerAdapter(Context cx, List ld) {
        super(cx, ld);
    }

    public JGalleryPagerAdapter(Context cx, List ld,List td) {
        super(cx, ld, td);
    }

    @Override
    public JGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JGalleryHolder(loadLayout(R.layout.jgallery_item,parent));
    }

    @Override
    public void onBindViewHolder(JGalleryHolder holder, int position) {
        if (getItemType(position).equals(DataType.NORMAL_IMAGE) ){
            displayType(holder, false);
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().into(holder.photoView);
            else
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().placeholder(defaultImage).into(holder.photoView);
        }else if(getItemType(position).equals(DataType.GIF_IMAGE)){
            displayType(holder, false);
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.photoView);
            else
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImage).into(holder.photoView);
        }else if(getItemType(position).equals(DataType.NET_VIDEO)||
                getItemType(position).equals(DataType.LOCAL_VIDEO) ||
                getItemType(position).equals(DataType.OVER_VIDEO)){//todo wxj
            displayType(holder, true);
            holder.jVideoView.setData(listData.get(position),typeData.get(position),position);
            holder.jVideoView.setJGalleryClickListener(onJGalleryClickListener);
            holder.jVideoView.setJGalleryLongClickListener(onJGalleryLongClickListener);
            holder.jVideoView.setJGalleryLoadListener(new OnJGalleryLoadListener() {
                @Override
                public void onLoad(int position, String path, String name) {
                    changeListDataStatus(position,path);
                    changeTypeDataStatus(position,DataType.LOCAL_VIDEO);
                    if (onJGalleryLoadListener != null){
                        onJGalleryLoadListener.onLoad(position,path,name);
                    }
                }
            });
        }
    }

    @Override
    public void onPageSelected(final int position) {
        if (weakViewMap != null && weakViewMap.containsKey(position)) {
            onJGalleryPageSelectedListener.onJGalleryPageSelected(position);
            final JGalleryHolder holder = weakViewMap.get(position);
            if (getItemType(position).equals(DataType.NORMAL_IMAGE) || getItemType(position).equals(DataType.GIF_IMAGE)){
                displayType(holder, false);
                holder.photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onJGalleryClickListener != null)
                            onJGalleryClickListener.OnClick(holder.photoView,getCurrentDataPos(position));
                    }
                });

                holder.photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (onJGalleryLongClickListener != null)
                            onJGalleryLongClickListener.OnLongClick(holder.photoView,getCurrentDataPos(position));
                        return false;
                    }
                });
            }else if(getItemType(position).equals(DataType.NET_VIDEO)
                    ||getItemType(position).equals(DataType.LOCAL_VIDEO)||
                    getItemType(position).equals(DataType.OVER_VIDEO)){//todo wxj
                displayType(holder, true);

            }

        }
    }

    public void displayType(JGalleryHolder holder, boolean isVideo){
        if (isVideo){
            holder.photoView.setVisibility(View.INVISIBLE);
            holder.jVideoView.setVisibility(View.VISIBLE);
        }else{
            holder.photoView.setVisibility(View.VISIBLE);
            holder.jVideoView.setVisibility(View.INVISIBLE);
        }
    }

    public void setDefaultImage(int di){
        this.defaultImage = di;
    }

    public static class JGalleryHolder extends JGalleryRecycleAdapter.ViewHolder {

        private ImageView photoView;
        private JVideoView jVideoView;

        public JGalleryHolder(View view) {
            super(view);
            photoView = (ImageView) view.findViewById(R.id.photoView);
            jVideoView = (JVideoView) view.findViewById(R.id.jVideoView);
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

    public void setJGalleryPageSelectedListener(OnJGalleryPageSelectedListener listener) {
        this.onJGalleryPageSelectedListener = listener;
    }

}
