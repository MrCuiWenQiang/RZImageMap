package cn.faker.repaymodel.net.okhttp3;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.faker.repaymodel.net.json.JsonUtil;
import cn.faker.repaymodel.net.okhttp3.callback.BasicCallback;
import cn.faker.repaymodel.net.okhttp3.callback.DownLoadFileCallBack;
import cn.faker.repaymodel.net.okhttp3.callback.DownloadBitmapCallback;
import cn.faker.repaymodel.net.okhttp3.callback.HttpResponseCallback;
import cn.faker.repaymodel.net.okhttp3.cookie.Cookie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Okhttp3网络访问类
 * 注意：init方法应当在应用启动的时候调用，只调用一次即可
 * Created by Mr.C on 2017/3/31 0031.
 */

public class HttpHelper {

    //默认请求时间
    protected static int httpConnectTimeOut = 120;
    private static String contentType = "application/json";

    private static HttpHelper httpHelper;
    public static OkHttpClient okHttpClient;


    protected static HttpResponseCallback.OnFailedAll onFailedAll;

    private static OnSettingClient onSettingClient;

    private HttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cookieJar(new Cookie());
        builder.connectTimeout(httpConnectTimeOut, TimeUnit.SECONDS);
        builder.readTimeout(httpConnectTimeOut, TimeUnit.SECONDS);
        if (onSettingClient != null) {
            onSettingClient.onInit(builder);
        }
        okHttpClient = builder.build();
    }

    private HttpHelper(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public static HttpHelper init(OnSettingClient onSetting) {
        onSettingClient = onSetting;
        return init();
    }

    public static HttpHelper init() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public static HttpHelper init(OkHttpClient okHttpClient) {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper(okHttpClient);
                }
            }
        }
        return httpHelper;
    }


    //cer文件设置方式
    public static void initCertificates(InputStream... certificates) {
        if (httpHelper != null) {
//            okHttpClient.setSslSocketFactory()
        }
    }


    /**
     * 检查是否初始化
     *
     * @return
     */
    public static boolean isInit() {
        return httpHelper != null;
    }

    /**
     * post方式提交数据
     *
     * @param path   地址
     * @param object bean类
     * @return
     */
    public static void post(String path, Object object, BasicCallback callback) {
        callback.setOnFailedAll(onFailedAll);
        Call call = getPostCall(path, object);
        callback.setUrl(path);
        HttpManager.addCall(path, call);
        call.enqueue(callback);
    }

    public static Call getPostCall(String path, Object object) {
        String json = JsonUtil.convertObjectToJson(object);
        byte[] data = null;
      /*  try {
            data = RSAUtils.encryptByPublicKey(json.getBytes("utf-8"), RSAKeys.publicClientKey);
            String result = Base64.encode(data);
            data = result.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        String base64data= Base64.encode(data);
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json.getBytes());

        Request request = new Request.Builder().url(path)
                .addHeader("Content-Length", String.valueOf(json.length()))
                .post(body).build();
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);
        return call;
    }

    public static void put(String path, Object object, BasicCallback callback) {
        callback.setOnFailedAll(onFailedAll);

        String json = JsonUtil.convertObjectToJson(object);
        byte[] data = null;
        try {
            data = RSAUtils.encryptByPublicKey(json.getBytes("utf-8"), RSAKeys.publicClientKey);
            String result = Base64.encode(data);
            data = result.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String base64data= Base64.encode(data);
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json.getBytes());

        Request request = new Request.Builder().url(path)
                .addHeader("Content-Length", String.valueOf(data.length))
                .put(body).build();
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);
        callback.setUrl(path);
        HttpManager.addCall(path, call);
        call.enqueue(callback);
    }

    /**
     * 文件下载
     *
     * @param url
     * @param callBack
     */
    public static void downloadFile(@NonNull String url, @NonNull DownLoadFileCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callBack);
    }


    /**
     * 带参数文件上传
     *
     * @param path
     * @param params
     * @param files
     * @param callback
     */
    public static void post_file(String path,
                                 HashMap<String, Object> params, HashMap<String, String> files, Callback callback) {

        MultipartBody.Builder multipartBuider = new MultipartBody.Builder();
        multipartBuider.setType(MultipartBody.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                multipartBuider.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        if (files != null && files.size() > 0) {
            for (Map.Entry<String, String> entry : files.entrySet()) {
                File file = new File(entry.getValue());
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                multipartBuider.addFormDataPart(String.valueOf(entry.getKey()), file.getName(), fileBody);
            }
        }

        Request request = new Request.Builder().url(path).post(multipartBuider.build()).header("Connection", "Keep-Alive")
                .build();

        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);

    }

    /**
     * 带参数文件上传  不加密
     *
     * @param path
     * @param params
     * @param files
     * @param fileType
     * @param callback
     */
    public static void post_file(String path,
                                 HashMap<String, Object> params, HashMap<String, String> files, String fileType, Callback callback) {
        MultipartBody.Builder multipartBuider = new MultipartBody.Builder();
        multipartBuider.setType(MultipartBody.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                multipartBuider.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        if (files != null && files.size() > 0) {
            for (Map.Entry<String, String> entry : files.entrySet()) {
                File file = new File(entry.getValue());
                RequestBody fileBody = RequestBody.create(MediaType.parse(fileType), file);
                multipartBuider.addFormDataPart(String.valueOf(entry.getKey()), file.getName(), fileBody);
            }
        }

        Request request = new Request.Builder().url(path).post(multipartBuider.build())
                .build();

        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);

    }

    public static void get(@NonNull String url, @NonNull Callback callback) {
        Request request = null;
        request = new Request.Builder().url(url).get().build();
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void get(@NonNull String url, HashMap<String, Object> params, @NonNull HttpResponseCallback callback) {
        if (params != null && params.size() > 0) {
            String paramData = getRequestData(params);
            if (!TextUtils.isEmpty(paramData)) {
                url = url + "?" + paramData;
            }
        }
        callback.setOnFailedAll(onFailedAll);
        Request request = null;
        request = new Request.Builder().url(url).get().build();
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * get方式下载图片文件
     */
    public static void downloadCodeBitmap(@NonNull String url, @NonNull DownloadBitmapCallback fileHttpResponseHanlder) {
        Request request = null;
        request = new Request.Builder().url(url).get().build();
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        Call call = okHttpClient.newCall(request);
        fileHttpResponseHanlder.setUrl(url);
        HttpManager.addCall(url, call);
        call.enqueue(fileHttpResponseHanlder);
    }

    /**
     * map集合数据转换
     *
     * @param params
     * @return
     */
    private static String getRequestData(HashMap<String, Object> params) {
        final HashMap<String, String> hasMap = new HashMap<String, String>();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                hasMap.put(key, value);
            }
        }

        StringBuilder data = null;
        if (hasMap != null && hasMap.size() > 0) {
            data = new StringBuilder();
            for (Map.Entry<String, String> entry : hasMap.entrySet()) {
                data.append(entry.getKey()).append("=");
                data.append(entry.getValue());
                data.append("&");
            }
            data.deleteCharAt(data.length() - 1);
            return data.toString();
        }
        return null;
    }


    public static void setOnFailedAll(HttpResponseCallback.OnFailedAll onFailedAll) {
        HttpHelper.onFailedAll = onFailedAll;
    }

    public interface OnSettingClient {
        void onInit(OkHttpClient.Builder builder);
    }
}
