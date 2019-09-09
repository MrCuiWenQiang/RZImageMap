package cn.faker.repaymodel.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * @author FLT
 * @function 提示工具類
 * @motto For The Future
 */
public class ToastUtility {
    private static Toast mToast;
    private static Context mContext;

    public static void setToast(Context context) {
        mContext = context;
    }

    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)){
            if (mToast != null) {
                mToast.cancel();
            }
            Message msg = new Message();
            msg.obj = text;
            handler.sendMessage(msg);
        }
    }
    public static void showToast(int resid){
        if ( resid > 0 ) {
            if ( null != mToast ) {
                mToast.cancel();
            }
            Message msg = new Message();
            msg.obj = mContext.getString(resid);
            handler.sendMessage(msg);
        }
    }
    /***
     * 显示
     *
     * @param text
     */
    private static void showText(String text) {
        mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showText(String.valueOf(msg.obj));
        }
    };

}
