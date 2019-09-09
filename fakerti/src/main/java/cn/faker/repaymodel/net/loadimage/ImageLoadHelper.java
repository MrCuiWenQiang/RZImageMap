package cn.faker.repaymodel.net.loadimage;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mr.C on 2017/11/4 0004.
 */

public class ImageLoadHelper {
    /**
     * 正常加载图片
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)){
            return;
        }
        Picasso.with(context).load(url).into(imageView);
    }

    /**
     *  设置宽高 减少内存开支
     * @param context
     * @param imageView
     * @param width
     * @param height
     * @param url
     */
    public static void loadImage(Context context, ImageView imageView,int width,int height, String url) {
        Picasso.with(context).load(url).resize(width,height).centerCrop().into(imageView);
    }


    /**
     * 设置图片加载中和加载失败的提示图片
     * @param context
     * @param imageView
     * @param url
     * @param placeimage 加载中
     * @param errorimage 加载失败
     */
    public static void loadImage(Context context, ImageView imageView, String url, int placeimage, int errorimage) {
        Picasso.with(context).load(url).placeholder(placeimage).error(errorimage).into(imageView);
    }

}
