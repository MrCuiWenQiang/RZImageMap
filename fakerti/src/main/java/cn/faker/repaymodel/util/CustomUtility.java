package cn.faker.repaymodel.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author FLT
 * @function 共通基础工具类
 * @motto For The Future
 */
public class CustomUtility {

    /***
     * 检测网络可用状态
     *
     * @param context
     * @return
     */
    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 设置文字显示
     *
     * @param id
     * @param text
     */
    public static String setResString(Context context, int id, int text) {
        return String.format(context.getResources().getString(id), text);
    }

    /***
     * 设置文字显示
     *
     * @param id
     * @param text
     */
    public static String setResString(Context context, int id, String text) {
        return String.format(context.getResources().getString(id), text);
    }

    /*
     * 此方法是用来检测电话号码是否合法 如果合法，返回true 如果不合法，返回false
     */
    public static boolean isPhoneNumber(String PhoneNumber) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(PhoneNumber);
        return m.matches();
    }

    /**
     * MD5加密。
     *
     * @param str 字符串
     * @return MD5加密后字符串
     */
    public static String encryptMd5(String str) {
        try {
            // 加密
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            md5Digest.update(str.getBytes("utf-8"));
            byte[] bytes = md5Digest.digest();
            // 转换成16进制
            String hexString = toHexString(bytes);
            return hexString;
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 打电话
     *
     * @param phone
     */
    public static void makePhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 格式化金额。
     *
     * @param money 金额
     * @return 格式化的金额
     */
    public static String formatMoney(String money) {
        String moneyFormatted = money;
        if (money != null && !"".equals(money.trim())) {
            Double moneyDouble = Double.parseDouble(money);
            moneyFormatted = new DecimalFormat("¥#0.00").format(moneyDouble);
        }

        return moneyFormatted;
    }
    /**
     * 格式化金额。
     *
     * @return 格式化的金额
     */
    public static String formatRate(BigDecimal rate) {
        String moneyFormatted = rate.toString();
        if (moneyFormatted != null && !"".equals(moneyFormatted.trim())) {
            Double moneyDouble = Double.parseDouble(moneyFormatted);
            moneyFormatted = new DecimalFormat("#0.00%").format(moneyDouble);
        }

        return moneyFormatted;
    }
    /**
     * 格式化金额。
     *
     * @param money 金额
     * @return 格式化的金额
     */
    public static String formatMoneyNoDot(String money) {

        String moneyFormatted = money;

        if (money != null && !"".equals(money.trim())) {
            Double moneyDouble = Double.parseDouble(money);
            moneyFormatted = new DecimalFormat("¥#0").format(moneyDouble);
        }

        return moneyFormatted;
    }

    /***
     * 格式化金钱
     *
     * @param money
     * @return
     */
    public static String formatMoneyNo(String money) {

        String moneyFormatted = money;

        if (money != null && !"".equals(money.trim())) {
            Double moneyDouble = Double.parseDouble(money);
            moneyFormatted = new DecimalFormat("#0.00").format(moneyDouble);
        }

        return moneyFormatted;
    }

    /**
     * 获取资源图片
     *
     * @param id 资源图片id
     * @return
     */
    public static Drawable getResImage(Context context, int id) {
        Drawable image = context.getResources().getDrawable(id);
        return image;
    }

    /**
     * 格式化日期。
     *
     * @param format 格式
     * @param date   日期
     * @return 显示用格式的日期
     */
    public static String formatDate(String format, Date date) {
        String formattedDate = new SimpleDateFormat(format).format(date);
        return formattedDate;
    }


    /**
     * 格式化字符串
     *
     * @param format 格式
     * @param args   内容
     * @return 格式化的字符串
     */
    private static String formatString(String format, Object... args) {
        String formattedString = String.format(format, args);
        return formattedString;
    }

    /**
     * 格式化数量。
     *
     * @param count 数量
     * @return 格式化的数量
     */
    public static String formatCount(int count) {

        String countFormatted = new DecimalFormat("#,##0").format(count);
        return countFormatted;
    }

    /**
     * 格式化金额。
     *
     * @param money 金额
     * @return 格式化的金额
     */
    public static String formatMoney(BigDecimal money) {

        String moneyFormatted = new DecimalFormat("￥#0.00").format(money);
        return moneyFormatted;
    }

    /***
     * 格式化金額
     *
     * @param money
     * @return
     */
    public static String formatMoneyNoSymbol(BigDecimal money) {
        String moneyFormatted = new DecimalFormat("#0.00").format(money);
        return moneyFormatted;
    }

    /**
     * 格式化金额。
     *
     * @param money 金额
     * @return 格式化的金额
     */
    public static String formatMoney(double money) {

        String moneyFormatted = new DecimalFormat("￥#0.00").format(money);
        return moneyFormatted;
    }

    /**
     * 格式化金额。
     *
     * @param money 金额
     * @return 格式化的金额
     */
    public static String formatMoneyNo(double money) {

        String moneyFormatted = new DecimalFormat("#0.00").format(money);
        return moneyFormatted;
    }

    /**
     * 解析金额。
     *
     * @param moneyStr 金额字符串
     * @return 金额
     */
    public static double parseMoney(String moneyStr) {

        double money = Double.parseDouble(moneyStr.substring(1));
        return money;
    }


    /**
     * 将字符串转换成16进制字符串。
     *
     * @param bytes 加密后bytes数组
     * @return 16进制字符串
     */
    private static String toHexString(byte[] bytes) {

        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            // 转换为16进制
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexBuilder.append("0");
            }
            hexBuilder.append(hex);
        }

        // 转换为大写
        String hexString = hexBuilder.toString().toUpperCase();
        return hexString;
    }

    /***
     * 获取GridView 每一项宽度
     *
     * @param context  关联
     * @param margId   左右间距
     * @param margItem 项目间距
     * @param Column   列数
     * @return 列宽
     */
    public static int getItemWidth(Context context, int margId, int margItem,
                                   int Column) {
        // 获取左右边距
        int marg;
        if (margId == -1) {
            marg = 0;
        } else {
            marg = context.getResources().getDimensionPixelSize(margId);
        }
        int margm;
        // 获取项目间距离
        if (margItem == -1) {
            margm = 0;
        } else {
            margm = context.getResources().getDimensionPixelSize(margItem);
        }
        // 获取屏幕宽度
        int wWidth = getWindowWidth(context);
        // 获取项目总宽度
        int aWidth = wWidth - marg * 2 - margm * (Column - 1);
        // 获取每一项宽度
        int cWidth = aWidth / Column;
        return cWidth;

    }

    /***
     * 获取屏幕中的大小
     *
     * @param context 关联
     * @param sizeId  大小的id
     * @return 大小
     */
    public static int getPixelSize(Context context, int sizeId) {
        int size = 0;
        if (sizeId != -1) {
            size = context.getResources().getDimensionPixelSize(sizeId);
        }
        return size;
    }

    public static int getPixel(Context context, int sizeId) {
        int size = 0;
        if (sizeId != -1) {
            size = (int) context.getResources().getDimension(sizeId);
        }
        return size;
    }

    /***
     * 将sp转换为像素
     *
     * @param context 关联
     * @param spId    spid
     * @return
     */
    public static int getSpAsPx(Context context, int spId) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (context.getResources().getDimension(spId) * fontScale + 0.5f);
    }

    /***
     * 将dp转换为px
     *
     * @param context 关联
     * @param dpId    dpid
     * @return
     */
    public static int getDpAsPx(Context context, int dpId) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (context.getResources().getDimension(dpId) * scale + 0.5f);
    }

    /***
     * 获取版本号
     *
     * @return
     */
    public static String getPackageVersion(Context context) {
        String versionNo = "00";
        try {
            PackageManager pManager = context.getPackageManager();
            PackageInfo pInfo = pManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionNo = pInfo.versionName;
        } catch (Exception e) {
            Log.e("VersionUpdate", "getPackageVersion fill");
        }
        return versionNo;
    }

    /***
     * 隐藏键盘
     * @param activity
     */
    public static void hideKyBoard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null && inputManager != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /***
     * 生成设备唯一标识码
     */
    public static String getUUID(Context context) {
        UUID uuid = null;
        if (uuid == null) {
            final String androidId = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId
                            .getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE))
                            .getDeviceId();
                    uuid = deviceId != null ? UUID
                            .nameUUIDFromBytes(deviceId.getBytes("utf8"))
                            : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        String uuids[] = uuid.toString().split("-");
        String newUUid = "";
        for (int i = 0; i < uuids.length; i++) {
            newUUid += uuids[i];
        }
        return newUUid;
    }

    /***
     * 给TextView 设置下划线
     *
     * @param textView
     */
    public static void setTextViewUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
        textView.getPaint().setAntiAlias(true);// 抗锯齿
    }

    public static void setTextViewMiddleLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中间横线
        textView.getPaint().setAntiAlias(true);// 抗锯齿
    }

    /***
     * 设置EditText最大字符数量
     *
     * @param edInput 输入框
     * @param num     输入数目
     */
    public static void setInputCount(EditText edInput, int num) {
        edInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
    }

    public static void setInputCount(TextView vInput, int num) {
        vInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
    }


    /***
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionName;
    }

    /***
     * 获取屏幕高度（不包含状态栏）
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        /**可用屏幕高度*/
        int height = 0;
        /**状态栏高度*/
        int statusBarHeight = 0;
        Activity ac = (Activity) context;
        WindowManager wm = (WindowManager) ac
                .getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = CustomUtility.getPixelSize(context, resourceId);
        }
        height = height - statusBarHeight;
        return height;
    }
    /***
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getMaxWindowHeight(Context context) {
        /**可用屏幕高度*/
        int height = 0;
        /**状态栏高度*/
        int statusBarHeight = 0;
        Activity ac = (Activity) context;
        WindowManager wm = (WindowManager) ac
                .getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();

        return height;
    }

    /***
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        int width = 0;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getWindowmin(Context context){
        return Math.min(getWindowHeight(context),getWindowWidth(context));
    }
}
