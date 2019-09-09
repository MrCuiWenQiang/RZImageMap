package cn.faker.repaymodel.util.error;

import cn.faker.repaymodel.util.LogUtil;

/**
 * Function :
 * Remarks  :
 * Created by Mr.C on 2018/8/28 0028.
 */
public class ErrorUtil {
    public static void showError(Exception e) {
        String sOut = "";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        LogUtil.e("System Error", "DBThreadHelper.class startThreadInPool" + sOut);
    }
}
