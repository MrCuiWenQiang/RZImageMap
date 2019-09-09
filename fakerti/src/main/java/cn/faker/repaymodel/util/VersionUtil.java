package cn.faker.repaymodel.util;

import android.os.Build;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class VersionUtil {
    public static boolean isM(){
        return   (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M);
    }
}
