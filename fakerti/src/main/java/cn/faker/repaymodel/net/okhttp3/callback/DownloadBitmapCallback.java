package cn.faker.repaymodel.net.okhttp3.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 加载图片回调
 * Created by Mr.C on 2017/7/12 0012.
 */

public abstract class DownloadBitmapCallback extends BasicCallback {

    //下载连接错误
    public static int download_code_error = 0;
    //连接超时
    public static int download_code_overtime = 1;
    //下载过程中失败错误
    public static int download_code_dlrror = 2;

    private int dstWidth = 400;
    private int dstHeight = 120;

    private Handler handler;
    private Bitmap bitmap;

    public DownloadBitmapCallback(){
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
    }

    public DownloadBitmapCallback(int dstWidth, int dstHeight){
        this();
        this.dstWidth=dstWidth;
        this.dstHeight=dstHeight;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        super.onFailure(call,e);
        handler.post(new Runnable() {
            @Override
            public void run() {
                onDownloadFail(download_code_error);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        super.onResponse(call,response);
            if (response.code() == 200){
                try {
                    InputStream inputStream = response.body().byteStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds =false;//此处直接返回BITMAP
                    bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                    inputStream.close();
                }catch (Exception e){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDownloadFail(download_code_dlrror);
                        }
                    });
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onComplete(bitmap);
                    }
                });
            }else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDownloadFail(download_code_overtime);
                    }
                });
            }
        handler = null;
    }
    //下载完成
    public abstract void onComplete(Bitmap bitmap);
    //下载失败
    public abstract void onDownloadFail(int code);
}
