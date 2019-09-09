package cn.faker.repaymodel.util.db;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.proxy.ThreadPoolProxyFactory;

/**
 * Function : 解决预防大量数据查询卡顿问题
 * Remarks  :
 * Created by Mr.C on 2018/6/8 0008.
 */

public class DBThreadHelper {

    private static Handler mainHandler;

    /**
     * 提供基本增删改查操作
     *
     * @param callback
     */
    public static final void startThreadInPool(final ThreadCallback callback) {
        initHandler();
        Thread job = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.t = callback.jobContent();
                } catch (Exception e) {
                    e.printStackTrace();
                    String sOut = "";
                    StackTraceElement[] trace = e.getStackTrace();
                    for (StackTraceElement s : trace) {
                        sOut += "\tat " + s + "\r\n";
                    }
                    LogUtil.e("System Error", "DBThreadHelper.class startThreadInPool" + sOut);
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.jobEnd(callback.t);
                    }
                });
            }
        });
        ThreadPoolProxyFactory.getdBWhereThreadPool().excute(job);
    }

    /**
     * 提供数据更新 满足大量数据不会出现oom问题
     *
     * @param callback
     */
    public static final void startDownLoadData(final ThreadCallback callback) {
        initHandler();
        Thread job = null;
        callback.thread = job;
        job = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.t = callback.jobContent();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("System Error", "DBThreadHelper.class startDownLoadData" + e.toString());
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.jobEnd(callback.t);
                    }
                });
            }
        });
        if (!TextUtils.isEmpty(callback.threadName())) {
            job.setName(callback.threadName());
        }
        ThreadPoolProxyFactory.getdbLoadThreadPool().excute(job);
    }


    public static abstract class ThreadCallback<T> {
        T t;
        protected static Thread thread;

        protected abstract T jobContent() throws Exception;

        protected abstract void jobEnd(T t);

        protected String threadName() {
            return null;
        }
    }

    private static void initHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
    }
}
