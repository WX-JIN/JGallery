package com.soubw.jgallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.listener.OnJGalleryClickListener;
import com.soubw.jgallery.listener.OnJGalleryLongClickListener;
import com.soubw.jgallery.listener.OnJGalleryPageSelectedListener;
import com.soubw.photoview.PhotoView;
import com.soubw.photoview.PhotoViewAttacher;

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
    public void onBindViewHolder(JGalleryHolder viewHolder, int position) {
        if (getItemType(position).equals(DataType.NORMAL_IMAGE) || getItemType(position).equals(DataType.VIDEO)){
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().into(viewHolder.photoView);
            else
                Glide.with(context).load(listData.get(position)).centerCrop().crossFade().placeholder(defaultImage).into(viewHolder.photoView);
        }else if(getItemType(position).equals(DataType.GIF_IMAGE)){
            if (defaultImage == -1)
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.photoView);
            else
                Glide.with(context).load(listData.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImage).into(viewHolder.photoView);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        if (weakViewMap != null && weakViewMap.containsKey(position)) {
            onJGalleryPageSelectedListener.onJGalleryPageSelected(position);
            final JGalleryHolder holder = weakViewMap.get(position);
                holder.photoView.setVisibility(View.VISIBLE);
                holder.photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float v, float v1) {
                        if (onJGalleryClickListener != null)
                            onJGalleryClickListener.OnClick(holder.photoView,getCurrentDataPos(position));
                    }

                    @Override
                    public void onOutsidePhotoTap() {
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
        }
    }

    public void setDefaultImage(int di){
        this.defaultImage = di;
    }

    public static class JGalleryHolder extends JGalleryRecycleAdapter.ViewHolder {

        private PhotoView photoView;

        public JGalleryHolder(View view) {
            super(view);
            photoView = (PhotoView) view.findViewById(R.id.photoView);
        }
    }

    public void setJGalleryClickListener(OnJGalleryClickListener listener) {
        this.onJGalleryClickListener = listener;
    }

    public void setJGalleryLongClickListener(OnJGalleryLongClickListener listener) {
        this.onJGalleryLongClickListener = listener;
    }

    public void setJGalleryPageSelectedListener(OnJGalleryPageSelectedListener listener) {
        this.onJGalleryPageSelectedListener = listener;
    }

}
