package com.zt.map.rzimagemap.util;

import android.graphics.Bitmap;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.concurrent.ExecutionException;

/**
 * Function :
 * Remarks  :
 * Created by Mr.C on 2019/9/10 0010.
 */
public class MapUtil {
    public static Bitmap getMapViewBitmap(MapView v) {

        v.clearFocus();
        v.setPressed(false);
        //能画缓存就返回false
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = null;
        while (cacheBitmap == null) {
            final ListenableFuture<Bitmap> export = v.exportImageAsync();
            try {
                cacheBitmap = export.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }
}
