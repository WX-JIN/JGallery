package com.soubw.utils;

import android.graphics.Bitmap;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class JViewShot {

	/**
	 * 截屏
	 * @param v 需要截屏的View
	 * @return 返回截屏的Bitmap
	 */
	public static Bitmap shotView(View v){
		    v.clearFocus();
	        v.setPressed(false);
	        boolean willNotCache = v.willNotCacheDrawing();
	        v.setWillNotCacheDrawing(false);

	        int color = v.getDrawingCacheBackgroundColor();
	        v.setDrawingCacheBackgroundColor(0);

	        if (color != 0) {
	            v.destroyDrawingCache();
	        }
	        v.buildDrawingCache();
	        Bitmap cacheBitmap = v.getDrawingCache();
	        if (cacheBitmap == null) {
	            return null;
	        }
	        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

	        v.destroyDrawingCache();
	        v.setWillNotCacheDrawing(willNotCache);
	        v.setDrawingCacheBackgroundColor(color);
	        return bitmap;
	/*	//View是你需要截图的View 
		v.setDrawingCacheEnabled(true); 
		v.buildDrawingCache(); 
		Bitmap b1 = v.getDrawingCache(); 

		//去掉标题栏 
		//Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455); 
		Bitmap b = Bitmap.createBitmap(b1); 
		v.destroyDrawingCache(); 
		v.setDrawingCacheEnabled(false); 
		return b; */
	} 


	//保存到sdcard 
	public static void savePic(Bitmap b,String strFileName){
		File f = new File("/sdcard/" + strFileName + ".png");
		 try {
			 f.createNewFile();
			 } catch (IOException e) {
		}
		FileOutputStream fos = null; 
		try {
			fos = new FileOutputStream(f);
			if (null != fos) 
			{ 
			b.compress(Bitmap.CompressFormat.PNG, 100, fos); 
			fos.flush(); 
			fos.close(); 
			} 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if(b != null) {
				b.recycle();
			}
		}
	} 
}