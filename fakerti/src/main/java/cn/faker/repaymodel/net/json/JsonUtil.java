package cn.faker.repaymodel.net.json;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;

/**
 * Json工具类
 * 第三方库(FastJson)
 * created by Mr.C at2017年3月31日 17:30:19
 **/
public class JsonUtil {

    private static  SerializerFeature[] features;

    public static String convertObjectToJson(Object object){
           if (features == null){
               initSerializerFeature();
           }
            try {
                return JSON.toJSONString(object, features);
            } catch (Exception e) {
                return "";
            }
        }

    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
    /***
     * 直接解析为list
     *
     * @param jsonString
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> fromList(String jsonString, Class<T> t) {
        ArrayList<T> list = null;
        try {
            list = new ArrayList<>();
            if (jsonString != null && jsonString.length() > 0) {
                list.addAll(JSON.parseArray(jsonString, t));
            }
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e("error",e.toString());
            return null;
        }
        return list;
    }

    private static void initSerializerFeature(){
        features = new SerializerFeature[]{SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteSlashAsSpecial,
                SerializerFeature.BrowserCompatible,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat};
    }
}
