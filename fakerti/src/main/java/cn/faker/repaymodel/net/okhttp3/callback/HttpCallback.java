package cn.faker.repaymodel.net.okhttp3.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import cn.faker.repaymodel.net.json.JsonUtil;
import cn.faker.repaymodel.net.okhttp3.BaseResultBean;
import cn.faker.repaymodel.net.okhttp3.EncryptUtil;
import cn.faker.repaymodel.util.LogUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 普通响应回调 不顺应项目
 * created by Mr.C at 2017年4月1日 09:45:28
 **/
public abstract class HttpCallback implements Callback {
    public static Handler handler;
    private String result;

    public static final int REQUEST_CODE_ERROR = 0;//访问失败
    public static final int REQUEST_CODE_NULL_DATA = 1;//数据为null
    public static final int REQUEST_CODE_NULL_Response = 2;//Response为null
    public static final int REQUEST_CODE_NULL_BaseResultBean = 3;//原始数据解析异常
    public static final int REQUEST_CODE_NULL_REQUEST = 4;//网络获取到的失败信息
    public HttpCallback(){
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void onFailure(Call arg0, final IOException arg1) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // 连接失败
                onFailed(REQUEST_CODE_ERROR,"连接失败");
            }
        });
    }

    @Override
    public void onResponse(Call arg0, Response response) throws IOException {


        if (response != null) {

            result = response.body().string();
            if (result != null) {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                onSuccess(result);     }
                });
            } else {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // 返回的结果字符串是null或空字符
                        onFailed(REQUEST_CODE_NULL_Response,"未获取到数据");
                    }
                });
            }
        } else {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // Response 为null
                    onFailed(REQUEST_CODE_NULL_DATA,"未获取到数据");
                }
            });
        }
    }
    /**
     * 成功返回结果
     *
     * @param result
     */
    public abstract void onSuccess(String result);

    /**
     * 失败
     *
     * @param status
     */
    public abstract void onFailed(int status,String message);

/*    *//**
     * 需要保存的Token
     *//*
    public abstract void onSacveToken(String Token);*/
}
