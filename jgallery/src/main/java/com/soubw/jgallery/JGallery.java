package com.soubw.jgallery;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubw.jgallery.config.IndicatorGravityConfig;
import com.soubw.jgallery.listener.JGalleryClickListener;
import com.soubw.jgallery.listener.JGalleryLongClickListener;
import com.soubw.jgallery.listener.JGalleryPageSelectedListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author WX_JIN
 * wangxiaojin@soubw.com
 * http://soubw.com
 */
public class JGallery extends FrameLayout implements ViewPager.OnPageChangeListener,JGalleryPageSelectedListener {

    private static final String TAG = "JGallery";

    private ViewPager viewPager;

    private JGalleryPagerAdapter jGalleryPagerAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private android.widget.TextView tvNumIndicator;
    private android.widget.LinearLayout indicator;
    private int indicatorGravity = IndicatorGravityConfig.RIGHT_BOTTOM;
    private int dataCount = 0;

    public JGallery(Context context) {
        this(context, null);
    }

    public JGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.jgallery, this, true);
        this.indicator = (LinearLayout) view.findViewById(R.id.indicator);
        this.tvNumIndicator = (TextView) view.findViewById(R.id.tvNumIndicator);
        this.viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initAdapter(context);
    }

    private void initAdapter(Context context) {
        if (jGalleryPagerAdapter == null) {
            jGalleryPagerAdapter = new JGalleryPagerAdapter(context, null);
            viewPager.setAdapter(jGalleryPagerAdapter);
            jGalleryPagerAdapter.setJGalleryPageSelectedListener(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null)
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (jGalleryPagerAdapter != null)
            jGalleryPagerAdapter.onPageSelected(position);
    }

    @Override
    public void onJGalleryPageSelected(int position) {
        if (onPageChangeListener != null)
            onPageChangeListener.onPageSelected(position);
        Log.d(TAG, "onPageSelected: "+position);
        tvNumIndicator.setText((position+1) + "/" + dataCount);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null)
            onPageChangeListener.onPageScrollStateChanged(state);
    }

    public void setData(Object[] data) {
        setData(Arrays.asList(data));
    }

    public void setData(List data) {
        if (data == null || data.size() <= 0) {
            return;
        }
        dataCount = data.size();
        jGalleryPagerAdapter.addRefreshData(data);
        viewPager.setFocusable(true);
        viewPager.addOnPageChangeListener(this);
    }

    public void setIndicatorGravity(int type) {
        switch (type) {
            case IndicatorGravityConfig.LEFT_TOP:
                this.indicatorGravity = Gravity.LEFT | Gravity.TOP;
                break;
            case IndicatorGravityConfig.LEFT_BOTTOM:
                this.indicatorGravity = Gravity.LEFT | Gravity.BOTTOM;
                break;
            case IndicatorGravityConfig.RIGHT_TOP:
                this.indicatorGravity = Gravity.RIGHT | Gravity.TOP;
                break;
            case IndicatorGravityConfig.RIGHT_BOTTOM:
                this.indicatorGravity = Gravity.RIGHT | Gravity.BOTTOM;
                break;
            case IndicatorGravityConfig.CENTER_TOP:
                this.indicatorGravity = Gravity.CENTER | Gravity.TOP;
                break;
            case IndicatorGravityConfig.CENTER_BOTTOM:
                this.indicatorGravity = Gravity.CENTER | Gravity.BOTTOM;
                break;
            case IndicatorGravityConfig.CENTER:
                this.indicatorGravity = Gravity.CENTER;
                break;
        }
        setIndicatorLayoutParams();
    }

    private void setIndicatorLayoutParams(){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = indicatorGravity;
        this.indicator.setLayoutParams(layoutParams);
    }

    public void addBeforeData(List data) {
        if (data == null || data.size() <= 0 || jGalleryPagerAdapter == null)
            return;
        dataCount += data.size();
        jGalleryPagerAdapter.addBeforeData(data);
    }

    public void addMoreData(List data) {
        if (data == null || data.size() <= 0 || jGalleryPagerAdapter == null)
            return;
        dataCount += data.size();
        jGalleryPagerAdapter.addMoreData(data);
    }

    public void setPageTransformer(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            viewPager.setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(TAG, "Please set the PageTransformer class");
        }
    }

    public void setCurrentItem(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }

    public void setJGalleryClickListener(JGalleryClickListener listener) {
        jGalleryPagerAdapter.setJGalleryClickListener(listener);
    }

    public void setJGalleryLongClickListener(JGalleryLongClickListener listener) {
        jGalleryPagerAdapter.setJGalleryLongClickListener(listener);
    }

    public void setPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.onPageChangeListener = listener;
    }


}
