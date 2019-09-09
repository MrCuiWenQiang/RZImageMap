package cn.faker.repaymodel.util;

import android.content.Context;

/**
 * Created by Administrator on 2017/10/2 0002.
 */

public class DpUtil {

    /**
     * dp → px
     * @param context
     * @param dpValue
     * @return
     */
    public static float dip2px(Context context , float dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return dpValue*density+0.5f;
    }

    /***
     * 获取屏幕中的大小
     *
     * @param context 关联
     * @param sizeId  大小的id
     * @return 大小
     */
    public static int getPixelSize(Context context, int sizeId) {
        int size = 0;
        if (sizeId != -1) {
            size = context.getResources().getDimensionPixelSize(sizeId);
        }
        return size;
    }

    public static int getPixel(Context context, int sizeId) {
        int size = 0;
        if (sizeId != -1) {
            size = (int) context.getResources().getDimension(sizeId);
        }
        return size;
    }
}
