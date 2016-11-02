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
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public abstract class JGalleryRecycleAdapter<VH extends JGalleryRecycleAdapter.ViewHolder> extends RecyclingPagerAdapter {

    protected Context context;
    private final LayoutInflater inflater;
    protected List listData;
    private List typeData;
    private List preData;

    protected WeakHashMap<Integer, VH> weakViewMap = new WeakHashMap<>();
    private boolean isFirstView = true;
    private boolean autoLoop;

    public JGalleryRecycleAdapter(Context cx, List<?> ld) {
        this.context = cx;
        addRefreshData(ld,null,null);
        inflater = LayoutInflater.from(cx);
    }

    public JGalleryRecycleAdapter(Context cx, List<?> ld,List<?> td) {
        this.context = cx;
        addRefreshData(ld,td,null);
        inflater = LayoutInflater.from(cx);
    }

    public JGalleryRecycleAdapter(Context cx, List<?> ld,List<?> td,List<?> pd) {
        this.context = cx;
        addRefreshData(ld,td,pd);
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

    public void addRefreshData(List ld,List td,List pd) {
        if (ld == null || ld.isEmpty())
            return;
        if (listData != null){
            listData.clear();
        }
        loopListData(ld,false,false);
        if (td != null && !td.isEmpty()){
            if (typeData != null){
                typeData.clear();
            }
            loopTypeData(td,false,false);
        }
        if (pd != null && !pd.isEmpty()){
            if (preData != null){
                preData.clear();
            }
            loopPreData(pd,false,false);
        }
        notifyDataSetChanged();
    }

    public void addMoreData(List ld,List td,List pd) {
        if (ld == null || ld.isEmpty()  || listData == null)
            return;
        if (listData.size() > 0){
            if (autoLoop){
                loopListData(ld,true,false);
            }else {
                loopListData(ld,false,false);
            }
        }else{
            loopListData(ld,false,false);
        }
        if (td != null && !td.isEmpty()){
            if (typeData.size() > 0){
                if (autoLoop){
                    loopTypeData(td,true,false);
                }else {
                    loopTypeData(td,false,false);
                }
            }else{
                loopTypeData(td,false,false);
            }
        }
        if (pd != null && !pd.isEmpty()){
            if (preData.size() > 0){
                if (autoLoop){
                    loopPreData(pd,true,false);
                }else {
                    loopPreData(pd,false,false);
                }
            }else{
                loopPreData(pd,false,false);
            }
        }
        notifyDataSetChanged();
    }

    public void addBeforeData(List ld,List td,List pd) {
        if (ld == null || ld.isEmpty() || listData == null)
            return;
        if (listData.size() > 0){
            if (autoLoop){
                loopListData(ld,true,true);
            }else {
                loopListData(ld,false,true);
            }
        }else{
            loopListData(ld,false,false);
        }
        if (td != null && !td.isEmpty()){
            if (typeData.size() > 0){
                if (autoLoop){
                    loopTypeData(td,true,true);
                }else {
                    loopTypeData(td,false,true);
                }
            }else{
                loopTypeData(td,false,false);
            }
        }
        if (pd != null && !pd.isEmpty()){
            if (preData.size() > 0){
                if (autoLoop){
                    loopPreData(pd,true,true);
                }else {
                    loopPreData(pd,false,true);
                }
            }else{
                loopPreData(pd,false,false);
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

    protected Object getItemPreImage(int position){
        if (preData == null || preData.isEmpty() || position >= preData.size() )
            return null;
        return preData.get(position);
    }

    public abstract void onPageSelected(int position);
    public abstract void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    protected View loadLayout(@LayoutRes int resource, ViewGroup  parent){
        return inflater.inflate(resource, parent, false);
    }

    private int getTagId(){
        return R.layout.jgallery;
    }

    private void loopListData(List<?> ld,boolean isClear,boolean isStart){
        List temp = new ArrayList();
        if (listData == null){
            listData = new ArrayList<>();
        }
        if (isClear){
            listData.remove(0);
            listData.remove(listData.size()-1);
        }
        if (isStart){
            temp.addAll(ld);
            temp.addAll(listData);
        }else{
            temp.addAll(listData);
            temp.addAll(ld);
        }
        listData.clear();
        listData.addAll(temp);
        if (temp.size() > 1 && autoLoop){
            listData.add(0,temp.get(temp.size()-1));
            listData.add(listData.size(),temp.get(0));
        }
    }

    private void loopTypeData(List<?> td,boolean isClear,boolean isStart){
        List temp = new ArrayList();
        if (typeData == null){
            typeData = new ArrayList<>();
        }
        if (isClear){
            typeData.remove(0);
            typeData.remove(typeData.size()-1);
        }
        if (isStart){
            temp.addAll(td);
            temp.addAll(typeData);
        }else{
            temp.addAll(typeData);
            temp.addAll(td);
        }
        typeData.clear();
        typeData.addAll(temp);
        if (temp.size() > 1  && autoLoop){
            typeData.add(0,temp.get(temp.size()-1));
            typeData.add(typeData.size(),temp.get(0));
        }
    }

    private void loopPreData(List<?> pd,boolean isClear,boolean isStart){
        List temp = new ArrayList();
        if (preData == null){
            preData = new ArrayList<>();
        }
        if (isClear){
            preData.remove(0);
            preData.remove(preData.size()-1);
        }
        if (isStart){
            temp.addAll(pd);
            temp.addAll(preData);
        }else{
            temp.addAll(preData);
            temp.addAll(pd);
        }
        preData.clear();
        preData.addAll(temp);
        if (temp.size() > 1  && autoLoop){
            preData.add(0,temp.get(temp.size()-1));
            preData.add(preData.size(),temp.get(0));
        }
    }

    public int getLoopDataPos(int currentPos){
        if (getCount() > 2  && autoLoop){
            return currentPos + 1;
        }else{
            return currentPos;
        }
    }

    public void changeListDataStatus(int pos,String path){
        listData.remove(pos);
        listData.add(pos,path);
    }

    public void changeTypeDataStatus(int pos,String date){
        typeData.remove(pos);
        typeData.add(pos,date);
    }



    public int getCurrentDataPos(int pos){
        if (!autoLoop)
            return pos;
        if (pos == 0 ){
            return getCurrentDataCount() - 1;
        }else if(pos == listData.size()-1){
            return 0;
        }else{
            return pos - 1;
        }
    }

    public int getCurrentDataCount(){
        if (getCount() > 2  && autoLoop){
            return getCount() - 2;
        }else{
            return getCount();
        }
    }

    public void setAutoLoop(boolean al) {
        this.autoLoop = al;
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
