package cn.faker.repaymodel.net.okhttp3.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.util.IOUtils;

import java.io.IOException;
import java.text.ParseException;

import cn.faker.repaymodel.net.json.JsonUtil;
import cn.faker.repaymodel.net.okhttp3.Base64;
import cn.faker.repaymodel.net.okhttp3.BaseResultBean;
import cn.faker.repaymodel.net.okhttp3.EncryptUtil;
import cn.faker.repaymodel.net.okhttp3.RSAKeys;
import cn.faker.repaymodel.net.okhttp3.RSAUtils;
import cn.faker.repaymodel.util.LogUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 普通响应回调
 * created by Mr.C at 2017年4月1日 09:45:28
 **/
//public abstract class HttpResponseCallback implements Callback {
public abstract class HttpResponseCallback extends BasicCallback {
    public static Handler handler;
    private String result;





    public static final int REQUEST_CODE_ERROR = 0;//访问失败
    public static final int REQUEST_CODE_NULL_DATA = 1;//数据为null
    public static final int REQUEST_CODE_NULL_Response = 2;//Response为null
    public static final int REQUEST_CODE_NULL_BaseResultBean = 3;//原始数据解析异常
    public static final int REQUEST_CODE_NULL_REQUEST = 4;//网络获取到的失败信息
    public static final int REQUEST_CODE_ERROR_DATA = 5;//信息填写错误
    public static final int USER_STATUS_NO = 6;//帐号被锁定

    /**
     * 用于下层判断
     */
    public static final int STATE_NULL_DATA = 0x102;//暂无数据
    public static final int CARD_NULL_DATA = 900;//储蓄卡为null

    public <T> HttpResponseCallback() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void onFailure(Call arg0, final IOException arg1) {
        super.onFailure(arg0, arg1);
        handler.post(new Runnable() {

            @Override
            public void run() {
                // 连接失败
                onFailed(REQUEST_CODE_ERROR, "连接失败");
            }
        });
    }

    @Override
    public void onResponse(Call arg0, Response response) throws IOException {
        super.onResponse(arg0, response);

        if (response != null) {

            result = response.body().string();
            if (result != null) {
                //关闭RSA解密
             /*   if (!result.startsWith("{")) {
                    result = result.substring(1,result.length()-1);
                    StringBuilder json = new StringBuilder();
                    result = result.replaceAll(" ", "+");
                    if (!TextUtils.isEmpty(result)) {
                        String[] contents = result.split("\\|");
                        for (int k = 0; k < contents.length; k++) {
                            String value = contents[k];
                            try {
                                value = new String(RSAUtils.decryptByPublicKey(Base64.decode(value), RSAKeys.publicClientKey),"UTF-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            json.append(value);
                        }
                        result = json.toString();
                    }

                }*/
                if (result != null) {
                    LogUtil.e("test-data", result);
                    final BaseResultBean baseResultBean = JsonUtil.convertJsonToObject(result, BaseResultBean.class);
                    if (baseResultBean != null) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                if (baseResultBean.getCode()==200) {
                                    onSuccess(baseResultBean.getData());
                                    onMessage(baseResultBean.getMsg());
                                } else {
                                    //关闭错误拦截
                           /*         if (onFailedAll!=null){
                                        if (!onFailedAll.onFailed(baseResultBean.getCode(), baseResultBean.getMsg())){
                                            onFailed(baseResultBean.getCode(), baseResultBean.getMsg());
                                        }
                                    }else {
                                        onFailed(baseResultBean.getCode(), baseResultBean.getMsg());
                                    }*/
                                    if (onFailedAll!=null){
                                        onFailedAll.onFailed(baseResultBean.getCode(), baseResultBean.getMsg());
                                    }
                                    onFailed(baseResultBean.getCode(), baseResultBean.getMsg());
                                }
                            }
                        });
                    } else {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                onFailed(REQUEST_CODE_NULL_BaseResultBean, "数据解析异常");
                            }
                        });
                    }


                } else {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // 返回的结果字符串是null或空字符
                            onFailed(REQUEST_CODE_NULL_Response, "未获取到数据");
                        }
                    });
                }

            } else {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // 返回的结果字符串是null或空字符
                        onFailed(REQUEST_CODE_NULL_Response, "未获取到数据");
                    }
                });
            }
        } else {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // Response 为null
                    onFailed(REQUEST_CODE_NULL_DATA, "未获取到数据");
                }
            });
        }
    }


    public abstract void onSuccess(String data);
    public  void onMessage(String msg){

    }

    /**
     * 失败
     *
     * @param status
     */
    public abstract void onFailed(int status, String message);

    /**
     * 用于处理常见失败类型 比如登录超时
     */
    public interface OnFailedAll{
        /**
         * 返回true则为已处理了不继续下发
         * @param status
         * @param message
         * @return
         */
        boolean onFailed(int status, String message);
    }

    /*    *//**
     * 需要保存的Token
     *//*
    public abstract void onSacveToken(String Token);*/
}
