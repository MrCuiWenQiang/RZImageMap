package cn.faker.repaymodel.util;

import android.util.Log;

/**
 * Created by Mr.C on 2017/11/20 0020.
 */

public class LogUtil {
    public static boolean isShow = true;

    public static void e(String tag, String msg) {
        if (isShow) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isShow) {
            Log.w(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isShow) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isShow) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isShow) {
            Log.i(tag, msg);
        }
    }
}
