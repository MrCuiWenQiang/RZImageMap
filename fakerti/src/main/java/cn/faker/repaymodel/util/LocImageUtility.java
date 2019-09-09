package cn.faker.repaymodel.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;


/**
 * @author FLT
 * @function
 * @motto For The Future
 */
public class LocImageUtility {
    static Context mContext;

    static LruCache<String, Bitmap> lurBit;


    public static void setImageUtility(Context context) {
        mContext = context;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int pictureMemory = maxMemory / 4;
        lurBit = new LruCache<String, Bitmap>(pictureMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /***
     * 通知手机图片库更新
     *
     * @param context
     * @param picturePath
     */
    @Deprecated
    public static void NotifyPhonePicture(Context context, String picturePath) {
        context.sendBroadcast(new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                + picturePath)));
    }
    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /***
     * 通知手机图片库更新
     *
     * @param context
     * @param picturePath
     */
    public static void NotifyPhonePicture(Context context, File picturePath) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(picturePath);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /***
     * @return
     */
    public static File getCameraLocalPath() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String sTime = formatter.format(curDate);
        File fileAll = new File(Environment.getExternalStorageDirectory(),
                "mkb");
        String path = Environment.getExternalStorageDirectory() + "/" + "mkb";
        if (!fileAll.exists()) {
            fileAll.mkdirs();
        }
        String fileName = "mkb" + "_" + sTime + ".jpg";
        File file = new File(fileAll, fileName);
        return file;
    }

    /***
     * 设置本地图片
     *
     * @param path
     * @param imageView
     */
    public static void ShowLocImage(String path, ImageView imageView) {
        imageView.setTag(path);
        Bitmap bitmap = getLruCacheBitmap(path);
        ImageCacheBean cacheBean = new ImageCacheBean();
        if (bitmap != null) {
            cacheBean.setPath(path);
            cacheBean.setImageView(imageView);
            cacheBean.setBitMap(bitmap);
            Message message = new Message();
            message.obj = cacheBean;
            ImageSetHandler.sendMessage(message);
        } else {
            ImageSize imageSize = getImageViewWidth(imageView);
            int reqWidth = imageSize.width;
            int reqHeight = imageSize.height;
            Bitmap bm = decodeSampledBitmapFromResourceSize(path, reqWidth,
                    reqHeight);
            addBitmapToLruCache(path, bm);
            cacheBean.setBitMap(getBitmapFromLruCache(path));
            ;
            cacheBean.setImageView(imageView);
            ;
            cacheBean.setPath(path);
            Message message = Message.obtain();
            message.obj = cacheBean;
            ImageSetHandler.sendMessage(message);
        }
    }

    /**
     * 根据计算的inSampleSize，得到压缩后图片
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static Bitmap decodeSampledBitmapFromResourceSize(String pathName,
                                                              int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        // 进行缩放比例控制，如果原图片过大進行比例縮小
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 200);
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, options);
        // bitmap.recycle();
        // System.gc();
        return bitmap;
    }


    /***
     * 压缩图片
     *
     * @param picturePath
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String picturePath, int coefficient) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        File file = new File(picturePath);
        Bitmap bitmap = null;
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(picturePath, newOpts);
        }
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        newOpts.inJustDecodeBounds = false;
        float hh = coefficient * 1000f;//
        float ww = coefficient * 1000f * h / w;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(picturePath, newOpts);
        return bitmap;
    }

    /***
     * 另开线程压缩图片
     * @param picturePath
     * @return
     */
    public static void getCompressBitmapPath(final String picturePath, final int coefficient, final PictureCompress pictureCompress) {

        new AsyncTask<Integer, Integer, String>() {
            @Override
            protected String doInBackground(Integer... params) {
                String filePath = "";
                Bitmap bit = decodeSampledBitmapFromResource(picturePath, coefficient);
                if (bit != null) {
                    File file = BitmapToFile(bit);
                    if (file.exists()) {
                        filePath = file.getAbsolutePath();
                    }
                }
                bit.recycle();
                return filePath;
            }

            protected void onPostExecute(String result) {
                pictureCompress.CompressPath(result);
            }

        }.executeOnExecutor(Executors.newCachedThreadPool());
    }
    /***
     * 另开线程压缩图片
     * @param lspicturePath
     * @param pictureCompress
     * @return
     */
    public static void getCompressBitmapPath(final ArrayList<String> lspicturePath, final int coefficient, final PictureCompressLs pictureCompress) {

        new AsyncTask<Integer, Integer, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Integer... params) {
                ArrayList<String> ls_filePath=new ArrayList<String>();
                for (String picturePath:lspicturePath){
                String filePath = "";
                Bitmap bit = decodeSampledBitmapFromResource(picturePath, coefficient);
                if (bit != null) {
                    File file = BitmapToFile(bit);
                    if (file.exists()) {
                        filePath = file.getAbsolutePath();
                        ls_filePath.add(filePath);
                    }
                }
                bit.recycle();
                }
                return ls_filePath;
            }

            protected void onPostExecute(ArrayList<String> result) {
                pictureCompress.CompressPath(result);
            }

        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    /**
     * 图片转换为file
     */
    public static File BitmapToFile(Bitmap bm) {
        String name = UUID.randomUUID().toString();
        FileOutputStream fos = null;
        File out = null;
        String folderPath = Environment.getExternalStorageDirectory().toString() + "/" + "xar";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = folderPath + "/" + name + ".png";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtility.showToast("内存卡未加载");
            return null;
        }
        try {
            out = new File(fileName);
            if (out.exists()) {
                out.delete();
            }
            fos = new FileOutputStream(out);
            bm.compress(Bitmap.CompressFormat.PNG, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String getFilePath(File file) {
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            return "";
        }

    }

    /***
     * 获取保存文件路径
     *
     * @param bitmap
     * @return
     */
    public static void getBitmapPath(final Bitmap bitmap, final PicturePath picturePath) {
        new AsyncTask<Integer, Integer, String>() {
            @Override
            protected String doInBackground(Integer... params) {
                String path = "";
                if (bitmap != null) {
                    File file = BitmapToFile(bitmap);
                    if (file.exists()) {
                        path = file.getAbsolutePath();
                    }
                    bitmap.recycle();
                }
                return path;
            }

            @Override
            protected void onPostExecute(String result) {
                picturePath.getBitPath(result);
            }
        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /***
     * 从缓存中获取图片
     *
     * @param path
     * @return
     */
    static Bitmap getLruCacheBitmap(String path) {
        return lurBit.get(path);
    }

    /**
     * 往LruCache中添加一张图片
     *
     * @param key
     * @param bitmap
     */
    static void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFromLruCache(key) == null) {
            if (bitmap != null)
                lurBit.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     */
    static Bitmap getBitmapFromLruCache(String key) {
        return lurBit.get(key);
    }


    /***
     * 获取图片角度
     *
     * @param picturePath
     * @return
     */
    public static int getBitmapAngle(String picturePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(picturePath);
        } catch (IOException e) {
            e.toString();
        }
        int angele = 0;
        if (exif != null) {
            int direction = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (direction) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angele = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angele = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angele = 270;
                    break;
            }
        }
        return angele;
    }

    /***
     * 将图片扶正
     *
     * @param bitPath
     * @param bitmapShow
     * @return
     */
    public static Bitmap ResetBitmapAngle(String bitPath, Bitmap bitmapShow) {
        int angle = getBitmapAngle(bitPath);
        if (bitmapShow == null || bitmapShow.isRecycled()) {
            return null;
        }
        if (angle != 0) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmapShow = Bitmap.createBitmap(bitmapShow, 0, 0, bitmapShow.getWidth(), bitmapShow.getHeight(), m, true);
        }
        return bitmapShow;
    }

    /**
     * 根据ImageView获得适当的压缩的宽和高
     *
     * @param imageView
     * @return
     */
    private static ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0)
            width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        // maxWidth
        // parameter
        if (width <= 0)
            width = displayMetrics.widthPixels;
        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getHeight(); // Get actual image height
        if (height <= 0)
            height = params.height; // Get layout height parameter
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        // maxHeight
        // parameter
        if (height <= 0)
            height = displayMetrics.heightPixels;
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;

    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
        }
        return value;
    }


    static Handler ImageSetHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImageCacheBean cacheBean = (ImageCacheBean) msg.obj;
            if (cacheBean != null) {
                if (cacheBean.getImageView().getTag().equals(cacheBean.getPath())) {
                    Bitmap bitmap = ResetBitmapAngle(cacheBean.getPath(), cacheBean.getBitMap());
                    cacheBean.getImageView().setImageBitmap(bitmap);
                }
            }
        }
    };


    static class ImageCacheBean {
        ImageView imageView;

        Bitmap bitMap;

        String path;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public Bitmap getBitMap() {
            return bitMap;
        }

        public void setBitMap(Bitmap bitMap) {
            this.bitMap = bitMap;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    private static class ImageSize {
        int width;
        int height;
    }

    public interface PicturePath {
        void getBitPath(String url);
    }

    public interface PictureCompress {
        void CompressPath(String path);
    }
    public interface PictureCompressLs {
        void CompressPath(ArrayList<String> ls_path);
    }
}
