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

    public JGalleryRecycleAdapter(Context cx, List<?> ld) {
        this.context = cx;
        addRefreshData(ld,null);
        inflater = LayoutInflater.from(cx);
    }

    public JGalleryRecycleAdapter(Context cx, List<?> ld,List<?> td) {
        this.context = cx;
        addRefreshData(ld,td);
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

    public void addRefreshData(List ld,List td) {
        if (ld == null || ld.isEmpty())
            return;
        loopListData(ld,false,false);
        if (td != null && !td.isEmpty()){
            loopTypeData(td,false,false);
        }
        notifyDataSetChanged();
    }

    public void addMoreData(List ld,List td) {
        if (ld == null || ld.isEmpty()  || listData == null)
            return;
        if (listData.size() > 0 ){
            loopListData(ld,true,false);
        }else{
            loopListData(ld,false,false);
        }
        if (td != null && !td.isEmpty()){
            if (typeData.size() > 0 ){
                loopTypeData(ld,true,false);
            }else{
                loopTypeData(ld,false,false);
            }
        }
        notifyDataSetChanged();
    }

    public void addBeforeData(List ld,List td) {
        if (ld == null || ld.isEmpty() || listData == null)
            return;
        if (listData.size() > 0 ){
            loopListData(ld,true,true);
        }else{
            loopListData(ld,false,false);
        }
        if (td != null && !td.isEmpty()){
            if (typeData.size() > 0 ){
                loopTypeData(ld,true,true);
            }else{
                loopTypeData(ld,false,false);
            }
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

    private void loopListData(List<?> ld,boolean isClear,boolean isStart){
        if (isClear){
            listData.remove(0);
            listData.remove(listData.size()-1);
        }
        List temp = new ArrayList();
        if (isStart){
            temp.addAll(listData.size(),ld);
        }else{
            temp.addAll(ld);
        }
        if (listData == null){
            listData = new ArrayList<>();
        }else {
            listData.clear();
        }
        listData.addAll(temp);
        if (temp.size() > 1){
            listData.add(0,temp.get(temp.size()-1));
            listData.add(listData.size(),temp.get(0));
        }
    }

    private void loopTypeData(List<?> td,boolean isClear,boolean isStart){
        if (isClear){
            typeData.remove(0);
            typeData.remove(typeData.size()-1);
        }
        List temp = new ArrayList();
        if (isStart){
            temp.addAll(typeData.size(),td);
        }else{
            temp.addAll(td);
        }
        if (typeData == null){
            typeData = new ArrayList<>();
        }else {
            typeData.clear();
        }
        typeData.addAll(temp);
        if (temp.size() > 1){
            typeData.add(0,temp.get(temp.size()-1));
            typeData.add(typeData.size(),temp.get(0));
        }
    }

    public int getCurrentDataPos(int pos){
        if (pos == 0){
            return getCurrentDataCount() - 1;
        }else if(pos == listData.size()-1){
            return 0;
        }else{
            return pos - 1;
        }
    }

    public int getCurrentDataCount(){
        if (getCount() > 2){
            return getCount() - 2;
        }else{
            return getCount();
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
