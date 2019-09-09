package cn.faker.repaymodel.net.okhttp3.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 文件下载专用调度（不支持文件断点续传）
 * Created by Mr.C on 2017/9/1 0001.
 */

public abstract class DownLoadFileCallBack implements Callback {

    private Handler handler;
    private String filePath;

    private InputStream inputStream;
    private FileOutputStream fos = null;
    private String  fileName;

    public DownLoadFileCallBack(String filePath, String fileName){
        this.filePath = filePath;
        this.fileName = fileName;
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onDownloadFail(DownloadBitmapCallback.download_code_error);
            }
        });
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        byte[] buf = new byte[2048];
        long sum = 0;
        int len = 0;

        inputStream = response.body().byteStream();
        long total = response.body().contentLength();
        final File file = new File(filePath,fileName);
        fos = new FileOutputStream(file);
        try {
            while ((len = inputStream.read(buf))!=-1){
                fos .write(buf,0,len);
                sum +=len;
                final int progress = (int)(sum * 1.0f / total * 100);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDownloading(progress);
                    }
                });
            }
            fos.flush();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onComplete(file);
                }
            });

        }catch (Exception e){
            onDownloadFail(DownloadBitmapCallback.download_code_dlrror);
        }finally {
            try{
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (Exception e){}

            try {
                if (fos !=null){
                    fos.close();
                }
            }catch (Exception e){

            }
        }


    }

    //下载完成
    public abstract void onComplete(File file);
    //下载中
    public abstract void onDownloading(int progress);
    //下载失败
    public abstract void onDownloadFail(int code);
}
