package com.soubw.jgallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubw.jgallery.config.DataType;
import com.soubw.jgallery.config.IndicatorGravity;
import com.soubw.jgallery.config.IndicatorStyle;
import com.soubw.jgallery.listener.OnJGalleryClickListener;
import com.soubw.jgallery.listener.OnJGalleryLoadListener;
import com.soubw.jgallery.listener.OnJGalleryLongClickListener;
import com.soubw.jgallery.listener.OnJGalleryPageSelectedListener;
import com.soubw.utils.JFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JGallery extends FrameLayout implements ViewPager.OnPageChangeListener, OnJGalleryPageSelectedListener {

    private static final String TAG = "JGallery";

    private ViewPager viewPager;


    private JGalleryPagerAdapter jGalleryPagerAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private android.widget.TextView tvNumIndicator;
    private android.widget.LinearLayout indicator;

    private Handler handler = new Handler();
    private JGalleryScroller jGalleryScroller;


    private int indicatorGravity = IndicatorGravity.RIGHT_BOTTOM;
    private String dataType = DataType.NORMAL_IMAGE;
    private boolean autoPlay = false;
    private boolean autoLoop = false;
    private int defaultImage = -1;
    private int switchTime = 3000;
    private int currentPos = 0;


    public JGallery(Context context) {
        this(context, null);
    }

    public JGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        JFile.initDirectory(context);
        initView(context);
        initAttributeSet(context, attrs);
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
            try {
                Field mField = ViewPager.class.getDeclaredField("mScroller");
                mField.setAccessible(true);
                jGalleryScroller = new JGalleryScroller(viewPager.getContext(), new AccelerateInterpolator());
                mField.set(viewPager, jGalleryScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewPager.setFocusable(true);
        viewPager.addOnPageChangeListener(this);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JGallery);
        setIndicatorGravity(typedArray.getInt(R.styleable.JGallery_JGallery_indicator_gravity, IndicatorGravity.RIGHT_BOTTOM));
        setIndicatorStyle(typedArray.getInt(R.styleable.JGallery_JGallery_indicator_style, IndicatorStyle.NUMBER));
        setIndicatorNumberColor(typedArray.getColor(R.styleable.JGallery_JGallery_indicator_number_color, Color.WHITE));
        setDefaultImage(typedArray.getResourceId(R.styleable.JGallery_JGallery_default_image, defaultImage));
        setAutoPlay(typedArray.getBoolean(R.styleable.JGallery_JGallery_auto_play, autoPlay));
        setAutoLoop(typedArray.getBoolean(R.styleable.JGallery_JGallery_auto_loop, autoLoop));
        setSwitchTime(typedArray.getInt(R.styleable.JGallery_JGallery_switch_time, switchTime));
        setScrollerTime(typedArray.getInt(R.styleable.JGallery_JGallery_scroller_time, JGalleryScroller.DEFAULT_SCROLLER_TIME));
        typedArray.recycle();
        startAutoPlayTack(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null)
            onPageChangeListener.onPageScrolled(jGalleryPagerAdapter.getCurrentDataPos(position), positionOffset, positionOffsetPixels);
        if (jGalleryPagerAdapter != null)
            jGalleryPagerAdapter.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (jGalleryPagerAdapter != null)
            jGalleryPagerAdapter.onPageSelected(position);
    }

    @Override
    public void onJGalleryPageSelected(int position) {
        if (onPageChangeListener != null)
            onPageChangeListener.onPageSelected(jGalleryPagerAdapter.getCurrentDataPos(position));
        currentPos = position;
        showIndicator();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:
                startAutoPlayTack(false);

                break;
            case 2:
                startAutoPlayTack(true);
                break;
            case 0:
                if (autoLoop) {
                    if (viewPager.getCurrentItem() == 0) {
                        setCurrentItem(jGalleryPagerAdapter.getCurrentDataCount(), false);
                    } else if (viewPager.getCurrentItem() == jGalleryPagerAdapter.getCurrentDataCount() + 1) {
                        setCurrentItem(1, false);

                    }
                }
                startAutoPlayTack(true);
                break;
        }
        if (onPageChangeListener != null)
            onPageChangeListener.onPageScrollStateChanged(state);
    }

    private void startAutoPlayTack(boolean startAutoPlayTack) {
        handler.removeCallbacks(autoPlayTack);
        if (!startAutoPlayTack)
            return;
        handler.postDelayed(autoPlayTack, switchTime);
    }

    private final Runnable autoPlayTack = new Runnable() {

        @Override
        public void run() {
            if (autoPlay) {
                if (jGalleryPagerAdapter.getCount() > 1) {
                    currentPos = currentPos == (jGalleryPagerAdapter.getCount() - 1) ? autoLoop ? 0 : -1 : currentPos + 1;
                    if (currentPos == -1)
                        return;
                    if (currentPos == 0) {
                        setCurrentItem(currentPos, false);
                    } else {
                        setCurrentItem(currentPos, true);
                    }
                    handler.postDelayed(autoPlayTack, switchTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (jGalleryPagerAdapter.getCount() > 1) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startAutoPlayTack(false);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    startAutoPlayTack(true);
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setIndicatorGravity(int type) {
        switch (type) {
            case IndicatorGravity.LEFT_TOP:
                this.indicatorGravity = Gravity.LEFT | Gravity.TOP;
                break;
            case IndicatorGravity.LEFT_BOTTOM:
                this.indicatorGravity = Gravity.LEFT | Gravity.BOTTOM;
                break;
            case IndicatorGravity.RIGHT_TOP:
                this.indicatorGravity = Gravity.RIGHT | Gravity.TOP;
                break;
            case IndicatorGravity.RIGHT_BOTTOM:
                this.indicatorGravity = Gravity.RIGHT | Gravity.BOTTOM;
                break;
            case IndicatorGravity.CENTER_TOP:
                this.indicatorGravity = Gravity.CENTER | Gravity.TOP;
                break;
            case IndicatorGravity.CENTER_BOTTOM:
                this.indicatorGravity = Gravity.CENTER | Gravity.BOTTOM;
                break;
            case IndicatorGravity.CENTER:
                this.indicatorGravity = Gravity.CENTER;
                break;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = indicatorGravity;
        this.indicator.setLayoutParams(layoutParams);
    }

    public void setIndicatorStyle(int style) {
        this.indicator.setVisibility(View.VISIBLE);
        switch (style) {
            case IndicatorStyle.NUMBER:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    this.tvNumIndicator.setBackground(null);
                } else {
                    this.tvNumIndicator.setBackgroundDrawable(null);
                }
                break;
            case IndicatorStyle.CIRCLE_NUMBER:
                this.tvNumIndicator.setBackgroundResource(R.drawable.jgallery_num_indicator);
                break;
            case IndicatorStyle.GONE:
                this.indicator.setVisibility(View.GONE);
                break;
        }
    }

    public void setIndicatorNumberColor(int color) {
        this.tvNumIndicator.setTextColor(color);
    }

    public void setDefaultImage(int defaultImage) {
        this.jGalleryPagerAdapter.setDefaultImage(defaultImage);
    }

    public void setAutoPlay(boolean ap) {
        this.autoPlay = ap;
        startAutoPlayTack(autoPlay);
    }

    private void setAutoLoop(boolean al) {
        this.autoLoop = al;
        jGalleryPagerAdapter.setAutoLoop(al);
    }

    public void setSwitchTime(int time) {
        this.switchTime = time;
    }

    public void setScrollerTime(int time) {
        jGalleryScroller.setScrollerTime(time);
    }

    public void setPageTransformer(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            viewPager.setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void setDataType(String dt) {
        this.dataType = dt;
    }

    public void setCurrentItem(int position) {
        setCurrentItem(jGalleryPagerAdapter.getLoopDataPos(position), true);
    }

    private void setCurrentItem(int currentItem, boolean smoothScroll) {
        if (!smoothScroll)
            viewPager.setCurrentItem(currentItem, false);
        else
            viewPager.setCurrentItem(currentItem);
        jGalleryPagerAdapter.notifyDataSetChanged();
        jGalleryPagerAdapter.onPageSelected(currentPos);
    }

    public void setOnJGalleryClickListener(OnJGalleryClickListener listener) {
        jGalleryPagerAdapter.setJGalleryClickListener(listener);
    }

    public void setOnJGalleryLongClickListener(OnJGalleryLongClickListener listener) {
        jGalleryPagerAdapter.setJGalleryLongClickListener(listener);
    }

    public void setOnJGalleryLoadListener(OnJGalleryLoadListener listener) {
        jGalleryPagerAdapter.setJGalleryLoadListener(listener);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.onPageChangeListener = listener;
    }

    public void setData(Object[]... objects) {
        Object[] object0 = null,object1 = null,object2 = null;
        for (int i = 0; i < objects.length; i++) {
            if (i == 0) object0 = objects[0];
            if (i == 1) object1 = objects[1];
            if (i == 2) object2 = objects[2];
        }
        setData(object0 !=null? Arrays.asList(object0):null,object1 !=null? Arrays.asList(object1):null,object2 !=null? Arrays.asList(object2):null);
    }

    public void setData(List... lists) {
        List list0 = null,list1 = null,list2 = null;
        for (int i = 0; i < lists.length; i++) {
            if (i == 0) list0 = lists[0];
            if (i == 1) list1 = lists[1];
            if (i == 2) list2 = lists[2];
        }
        jGalleryPagerAdapter.addRefreshData(list0, list1, list2);
        setCurrentItem(jGalleryPagerAdapter.getLoopDataPos(0), true);
    }

    public void addBeforeData(Object[]... objects) {
        Object[] object0 = null,object1 = null,object2 = null;
        for (int i = 0; i < objects.length; i++) {
            if (i == 0) object0 = objects[0];
            if (i == 1) object1 = objects[1];
            if (i == 2) object2 = objects[2];
        }
        addBeforeData(object0 !=null? Arrays.asList(object0):null,object1 !=null? Arrays.asList(object1):null,object2 !=null? Arrays.asList(object2):null);
    }

    public void addBeforeData(List... lists) {
        List list0 = null,list1 = null,list2 = null;
        for (int i = 0; i < lists.length; i++) {
            if (i == 0) list0 = lists[0];
            if (i == 1) list1 = lists[1];
            if (i == 2) list2 = lists[2];
        }
        jGalleryPagerAdapter.addBeforeData(list0, list1, list2);
        setCurrentItem(autoLoop ? currentPos + list0.size() - 1 : currentPos + list0.size());
    }

    public void addMoreData(Object[]... objects) {
        Object[] object0 = null,object1 = null,object2 = null;
        for (int i = 0; i < objects.length; i++) {
            if (i == 0) object0 = objects[0];
            if (i == 1) object1 = objects[1];
            if (i == 2) object2 = objects[2];
        }
        addMoreData(object0 !=null? Arrays.asList(object0):null,object1 !=null? Arrays.asList(object1):null,object2 !=null? Arrays.asList(object2):null);
    }

    public void addMoreData(List... lists) {
        List list0 = null,list1 = null,list2 = null;
        for (int i = 0; i < lists.length; i++) {
            if (i == 0) list0 = lists[0];
            if (i == 1) list1 = lists[1];
            if (i == 2) list2 = lists[2];
        }
        jGalleryPagerAdapter.addMoreData(list0, list1, list2);
        jGalleryPagerAdapter.onPageSelected(currentPos);
    }

    private void showIndicator() {
        tvNumIndicator.setText((jGalleryPagerAdapter.getCurrentDataPos(currentPos) + 1) + "/" + jGalleryPagerAdapter.getCurrentDataCount());
    }

}
