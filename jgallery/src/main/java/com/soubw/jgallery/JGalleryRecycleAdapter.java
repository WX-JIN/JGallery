package com.soubw.jgallery;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubw.salvage.RecyclingPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author WX_JIN
 * @email wangxiaojin@soubw.com
 * @link http://soubw.com
 */
public abstract class JGalleryRecycleAdapter<VH extends JGalleryRecycleAdapter.ViewHolder> extends RecyclingPagerAdapter {

    protected Context context;
    private final LayoutInflater inflater;
    protected List listData;

    protected WeakHashMap<Integer, VH> weakViewMap = new WeakHashMap<>();
    private boolean isFirstView = true;

    public JGalleryRecycleAdapter(Context cx, List<?> data) {
        this.context = cx;
        this.listData = data;
        inflater = LayoutInflater.from(cx);
    }

    public View getView(int position, View convertView, ViewGroup container) {
        VH holder;
        if (convertView != null && convertView.getTag(getItemType(position)) != null) {
            holder = (VH) convertView.getTag(getItemType(position));
        } else {
            holder = this.onCreateViewHolder(container, getItemType(position));
            holder.view.setTag(getItemType(position), holder);
        }
        this.onBindViewHolder(holder, position);
        weakViewMap.put(position, holder);
        if (isFirstView) {
            onPageSelected(position);
            isFirstView = false;
        }
        return holder.view;
    }

    public void addRefreshData(List<?> data) {
        if (data == null || data.isEmpty())
            return;
        if (listData == null){
            listData = new ArrayList<>();
        }else {
            listData.clear();
        }
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void addMoreData(List<?> data) {
        if (data == null || data.isEmpty()  || listData == null)
            return;
        listData.addAll(getCount(), data);
        notifyDataSetChanged();
    }

    public void addBeforeData(List<?> data) {
        if (data == null || data.isEmpty() || listData == null)
            return;
        listData.addAll(0, data);
        notifyDataSetChanged();
    }

    @Override
    public void addItemView(int position, View view) {

    }

    @Override
    public void removeItemView(int position) {
        if (weakViewMap != null && weakViewMap.containsKey(position))
            weakViewMap.remove(position);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH viewHolder, int position);

    public abstract int getItemType(int position);

    public abstract void onPageSelected(int position);

    protected View loadLayout(@LayoutRes int resource, ViewGroup  parent){
        return inflater.inflate(resource, parent, false);
    }

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
