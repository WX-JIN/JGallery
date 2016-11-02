package com.soubw.utils;

import android.content.Context;
import android.os.Environment;

import com.soubw.config.JConfig;

import java.io.File;

/**
 * author: WX_JIN
 * email: wangxiaojin@soubw.com
 */
public class JFile {

    public static String initDirectory(Context context) {
        String baseDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseDir = Environment.getExternalStorageDirectory() + File.separator+ JConfig.baseName+ File.separator;
        }else {
            baseDir = context.getCacheDir().getAbsolutePath() + File.separator+ JConfig.baseName+ File.separator;
        }

        File basedir = new File(baseDir);
        if(!basedir.exists()) {
            basedir.mkdirs();
        }
        JConfig.basePath = baseDir;
        return baseDir;
    }

    public static boolean fileIsExist(String url){
        File dir = new File(url);
        if(dir.exists())
            return true;
        return false;
    }

    public static File getCacheFile(){
        File cachedir = new File(JConfig.basePath+ File.separator+ JConfig.cacheName+ File.separator);
        if(!cachedir.exists()) {
            cachedir.mkdirs();
        }
        return cachedir;
    }

    public static String getDownPath(){
        String path = JConfig.basePath+ File.separator+ JConfig.downPath+ File.separator;
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
