package cn.faker.repaymodel.util.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


/**
 * @author FLT
 * @function map参数输出
 * @motto For The Future
 */
public class PermissionUtility {
    /***
     * 检查数据存取权限
     *
     * @param activity
     * @return
           */
    public static boolean writeExternalStorage(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0xf1);
        }
        return permision;
    }

    /***
     * 检查相机权限
     *
     * @param activity
     * @return
     */
    public static boolean checkCamera(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.CAMERA");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 0xf1);
        }
        return permision;
    }

    /***
     * 检查拨号权限
     *
     * @param activity
     * @return
     */
    public static boolean callPhone(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.CALL_PHONE");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0xf1);
        }
        return permision;
    }

    /***
     * 检查存储
     *
     * @param activity
     * @return
     */
    public static boolean readExternalStorage(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 0xf1);
        }
        return permision;
    }

    /***
     * 读取联系人
     *
     * @param activity
     * @returnC
     */
    public static boolean readContacts(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.READ_CONTACTS");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, 0xf1);
        }
        return permision;
    }

    /***
     * 写入联系人
     *
     * @param activity
     * @return
     */
    public static boolean writeContacts(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.WRITE_CONTACTS");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS}, 0xf1);
        }
        return permision;
    }

    /***
     * 手机状态
     *
     * @param activity
     * @return
     */
    public static boolean readPhoneState(Activity activity) {
        int perm = activity.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE");
        boolean permision = (perm == PackageManager.PERMISSION_GRANTED);
        if (!permision) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 0xf1);
        }
        return permision;
    }
}
