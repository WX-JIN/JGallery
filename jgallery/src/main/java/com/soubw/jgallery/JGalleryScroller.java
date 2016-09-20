package com.soubw.jgallery;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JGalleryScroller extends Scroller {

    public static int DEFAULT_SCROLLER_TIME = 300;
    private int scrollerTime = DEFAULT_SCROLLER_TIME;

    public JGalleryScroller(Context context) {
        super(context);
    }

    public JGalleryScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, scrollerTime);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, scrollerTime);
    }

    public void setScrollerTime(int time) {
        this.scrollerTime = time;
    }
}
