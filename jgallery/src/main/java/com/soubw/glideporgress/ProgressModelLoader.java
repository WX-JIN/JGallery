package com.soubw.glideporgress;

import android.os.Handler;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
public class ProgressModelLoader implements StreamModelLoader<String> {

    private final ModelCache<String, String> modelCache;
    private final Handler handler;

    public ProgressModelLoader(Handler handler) {
        this(null, handler);
    }

    public ProgressModelLoader(ModelCache<String, String> modelCache, Handler handler) {
        this.modelCache = modelCache;
        this.handler = handler;
    }


    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        String result = null;
        if (modelCache != null) {
            result = modelCache.get(model, width, height);
        }
        if (result == null) {
            result = model;
            if (modelCache != null) {
                modelCache.put(model, width, height, result);
            }
        }
        return new ProgressDataFetcher(result, handler);
    }

}
