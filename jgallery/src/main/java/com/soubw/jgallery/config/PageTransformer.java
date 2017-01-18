package com.soubw.jgallery.config;

import android.support.v4.view.ViewPager;


import com.soubw.jgallery.transformer.AccordionTransformer;
import com.soubw.jgallery.transformer.BackgroundToForegroundTransformer;
import com.soubw.jgallery.transformer.CubeInTransformer;
import com.soubw.jgallery.transformer.CubeOutTransformer;
import com.soubw.jgallery.transformer.DefaultTransformer;
import com.soubw.jgallery.transformer.DepthPageTransformer;
import com.soubw.jgallery.transformer.FlipHorizontalTransformer;
import com.soubw.jgallery.transformer.FlipVerticalTransformer;
import com.soubw.jgallery.transformer.ForegroundToBackgroundTransformer;
import com.soubw.jgallery.transformer.RotateDownTransformer;
import com.soubw.jgallery.transformer.RotateUpTransformer;
import com.soubw.jgallery.transformer.ScaleInOutTransformer;
import com.soubw.jgallery.transformer.StackTransformer;
import com.soubw.jgallery.transformer.TabletTransformer;
import com.soubw.jgallery.transformer.ZoomInTransformer;
import com.soubw.jgallery.transformer.ZoomOutSlideTransformer;
import com.soubw.jgallery.transformer.ZoomOutTranformer;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.co
 */
public class PageTransformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
