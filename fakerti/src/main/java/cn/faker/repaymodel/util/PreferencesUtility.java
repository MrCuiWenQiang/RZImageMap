package cn.faker.repaymodel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author FLT
 * @function    共通preference工具
 * @motto For The Future
 */
public class PreferencesUtility {
    /**
     * 关联
     */
    private static Context context;
    /**
     * 文件名
     */
    private static String fileName;

    /***
     * 设置关联
     * <p>
     * 关联context
     */
    public static void setPreferencesUtility(Context mContext, String mFileName) {
        context = mContext;
        fileName = mFileName;
    }

    /***
     * /*** 设置相应的字段值
     *
     * @param fieldName  字段名称
     * @param fieldValue 布尔值
     */
    public static  void setPreferencesField(String fieldName, boolean fieldValue) {
        Editor editor = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .putBoolean(fieldName, fieldValue);
        editor.apply();
    }

    /***
     * 设置相应的字段值
     *
     * @param fieldName  字段名称
     * @param fieldValue String值
     */
    public static void setPreferencesField(String fieldName, String fieldValue) {
        Editor editor = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .putString(fieldName, fieldValue);
        editor.apply();
    }

    /***
     * 设置相应的字段值
     *
     * @param fieldName  字段名称
     * @param fieldValue 浮点型
     */
    public static void setPreferencesField(String fieldName, Float fieldValue) {
        Editor editor = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .putFloat(fieldName, fieldValue);
        editor.apply();
    }

    /***
     * 设置相应的字段值
     *
     * @param fieldName  字段名称
     * @param fieldValue int
     */
    public static void setPreferencesField(String fieldName, int fieldValue) {
        Editor editor = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .putInt(fieldName, fieldValue);
        editor.apply();
    }

    /***
     * 设置相应的字段值
     *
     * @param fieldName  字段名称
     * @param fieldValue 长整型
     */
    public static void setPreferencesField(String fieldName, Long fieldValue) {
        Editor editor = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .putLong(fieldName, fieldValue);
        editor.apply();
    }

    /***
     * 获取相应字段名下的字符串
     *
     * @param fieldName 字段名
     * @return 值
     */
    public static String getPreferencesAsString(String fieldName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        String value = preferences.getString(fieldName, "");
        return value;
    }

    /***
     * 获取相应字段名下的布尔值
     *
     * @param fieldName 字段名
     * @return 值
     */
    public static boolean getPreferencesAsBoolean(String fieldName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        boolean value = preferences.getBoolean(fieldName, false);
        return value;
    }

    /***
     * 获取相应字段名下的浮点型
     *
     * @param fieldName 字段名
     * @return 值
     */
    public static float getPreferencesAsFloat(String fieldName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        float value = preferences.getFloat(fieldName, -1);
        return value;
    }

    /***
     * 获取相应字段名下的整型
     *
     * @param fieldName 字段名
     * @return 值
     */
    public static int getPreferencesAsInt(String fieldName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        int value = preferences.getInt(fieldName, 0);
        return value;
    }

    /***
     * 获取相应字段名下的长整型
     *
     * @param fieldName 字段名
     * @return 值
     */

    public static long getPreferencesAsLong(String fieldName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        long value = preferences.getLong(fieldName, -1);
        return value;
    }

}
