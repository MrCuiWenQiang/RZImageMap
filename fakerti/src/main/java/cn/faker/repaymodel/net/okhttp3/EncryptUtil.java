package cn.faker.repaymodel.net.okhttp3;


import java.util.HashMap;

import cn.faker.repaymodel.net.json.JsonUtil;
import cn.faker.repaymodel.util.LogUtil;


/**
 * 加密工具类   为保证数据安全不应该放在JAVA层，
 * Created by Mr.c on 2017/7/11 0011.
 */

public class EncryptUtil {
    public static String KEY_INFO = "info";
    public static String KEY_TOKEN = "token";

    /***
     *数据加密
     * @param object
     */
    public static HashMap<String, Object> changeValue(Object object) {
        HashMap<String, Object> mapNormal = new HashMap<>();
        String info = JsonUtil.convertObjectToJson(object);
        LogUtil.e("test-post",info);
        String token = SecretUtility.getRandomString();
        String key = new StringBuffer(token.substring(0, 2)).append(token.substring(8, 10)).append(token.substring(16, 18)).append(token.substring(24, 26)).toString();
        try {
            mapNormal.put(KEY_INFO, java.net.URLEncoder.encode(SecretUtility.encodeString(info, key, key.getBytes())));
            mapNormal.put(KEY_TOKEN, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapNormal;
    }

    /**
     * 数据解密
     * @param result
     * @return
     */
    public static String callReqest(String result) {
        String info = null;
        try {
            ResultBean resultBean = JsonUtil.convertJsonToObject(result, ResultBean.class);
            String token = resultBean.getToken();
            String key = new StringBuffer(token.substring(0, 2)).append(token.substring(8, 10)).append(token.substring(16, 18)).append(token.substring(24, 26)).toString();
            info = SecretUtility.decodeString(resultBean.getInfo(), key, key.getBytes());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return info;
        }
    }



}
