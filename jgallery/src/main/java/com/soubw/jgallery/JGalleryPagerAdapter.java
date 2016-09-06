package com.soubw.jgallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.soubw.jgallery.listener.JGalleryClickListener;
import com.soubw.jgallery.listener.JGalleryLongClickListener;
import com.soubw.jgallery.listener.JGalleryPageSelectedListener;
import com.soubw.photoview.PhotoView;
import com.soubw.photoview.PhotoViewAttacher;

import java.util.List;

/**
 * @author WX_JIN on 2016/9/5
 * @email wangxiaojin@soubw.com
 * @link http://soubw.com
 */
public class JGalleryPagerAdapter extends JGalleryRecycleAdapter<JGalleryPagerAdapter.JGalleryHolder> {

    private int defaultImage = -1;
    private JGalleryClickListener jGalleryClickListener;
    private JGalleryLongClickListener jGalleryLongClickListener;
    private JGalleryPageSelectedListener jGalleryPageSelectedListener;

    public JGalleryPagerAdapter(Context cx, List data) {
        super(cx, data);
    }

    @Override
    public JGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JGalleryHolder(loadLayout(R.layout.jgallery_item,parent));
    }

    @Override
    public void onBindViewHolder(JGalleryHolder viewHolder, int position) {
        if (defaultImage != -1)
            Glide.with(context).load(listData.get(position)).centerCrop().crossFade().into(viewHolder.photoView);
        else
            Glide.with(context).load(listData.get(position)).centerCrop().crossFade().placeholder(defaultImage).into(viewHolder.photoView);
    }

    @Override
    public int getItemType(int position) {
        return R.layout.jgallery;
    }

    @Override
    public void onPageSelected(final int position) {
        if (weakViewMap != null && weakViewMap.containsKey(position)) {
            jGalleryPageSelectedListener.onJGalleryPageSelected(position);
            final JGalleryHolder holder = weakViewMap.get(position);
                holder.photoView.setVisibility(View.VISIBLE);
                holder.photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float v, float v1) {
                        if (jGalleryClickListener != null)
                            jGalleryClickListener.OnClick(holder.photoView,position);
                    }

                    @Override
                    public void onOutsidePhotoTap() {
                        if (jGalleryClickListener != null)
                            jGalleryClickListener.OnClick(holder.photoView,position);
                    }
                });

                holder.photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (jGalleryLongClickListener != null)
                            jGalleryLongClickListener.OnLongClick(holder.photoView,position);
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

    public void setJGalleryClickListener(JGalleryClickListener listener) {
        this.jGalleryClickListener = listener;
    }

    public void setJGalleryLongClickListener(JGalleryLongClickListener listener) {
        this.jGalleryLongClickListener = listener;
    }

    public void setJGalleryPageSelectedListener(JGalleryPageSelectedListener listener) {
        this.jGalleryPageSelectedListener = listener;
    }

}
