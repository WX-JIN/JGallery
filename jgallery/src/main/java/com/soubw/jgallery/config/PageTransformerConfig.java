package com.soubw.jgallery.config;

import android.support.v4.view.ViewPager.PageTransformer;

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
 * @author WX_JIN
 * wangxiaojin@soubw.com
 * http://soubw.com
 */
public class PageTransformerConfig {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
