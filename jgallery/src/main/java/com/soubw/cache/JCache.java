package com.soubw.cache;

/**
 * author: WX_JIN
 * email: wangxiaojin@soubw.com
 */
public class JCache {

    private ACache aCache;

    private static class Inner{
        private static JCache INSTANCE = new JCache();
    }

    public static JCache getInstance(){
        return Inner.INSTANCE;
    }

    private JCache(){
    }


}
