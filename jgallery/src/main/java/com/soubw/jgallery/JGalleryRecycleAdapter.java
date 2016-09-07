package com.soubw.jgallery;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubw.jgallery.config.DataType;
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
    protected List typeData;

    protected WeakHashMap<Integer, VH> weakViewMap = new WeakHashMap<>();
    private boolean isFirstView = true;

    public JGalleryRecycleAdapter(Context cx, List<?> data) {
        this.context = cx;
        this.listData = data;
        inflater = LayoutInflater.from(cx);
    }

    public View getView(int position, View convertView, ViewGroup container) {
        VH holder;
        if (convertView != null) {
            holder = (VH) convertView.getTag(getTagId());
        } else {
            holder = this.onCreateViewHolder(container, position);
            holder.view.setTag(getTagId(), holder);
        }
        this.onBindViewHolder(holder, position);
        weakViewMap.put(position, holder);
        if (isFirstView) {
            onPageSelected(position);
            isFirstView = false;
        }
        return holder.view;
    }

    public void addRefreshData(List<?> ld,List<?> td) {
        if (ld == null || ld.isEmpty())
            return;
        if (listData == null){
            listData = new ArrayList<>();
        }else {
            listData.clear();
        }
        listData.addAll(ld);
        if (td != null && !td.isEmpty()){
            if (typeData == null){
                typeData = new ArrayList<>();
            }else {
                typeData.clear();
            }
            typeData.addAll(td);
        }
        notifyDataSetChanged();
    }

    public void addMoreData(List<?> ld,List<?> td) {
        if (ld == null || ld.isEmpty()  || listData == null)
            return;
        listData.addAll(getCount(), ld);
        if (td != null && !td.isEmpty()){
            typeData.addAll(td);
        }
        notifyDataSetChanged();
    }

    public void addBeforeData(List<?> ld,List<?> td) {
        if (ld == null || ld.isEmpty() || listData == null)
            return;
        listData.addAll(0, ld);
        if (td != null && !td.isEmpty()){
            typeData.addAll(0, td);
        }
        notifyDataSetChanged();
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

    protected Object getItemType(int position){
        if (typeData == null || typeData.isEmpty() || position >= typeData.size() )
           return DataType.NORMAL_IMAGE;
        return typeData.get(position);
    }

    public abstract void onPageSelected(int position);

    protected View loadLayout(@LayoutRes int resource, ViewGroup  parent){
        return inflater.inflate(resource, parent, false);
    }

    private int getTagId(){
        return R.layout.jgallery;
    }

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
