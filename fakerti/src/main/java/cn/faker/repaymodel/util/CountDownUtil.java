package cn.faker.repaymodel.util;

import android.os.Handler;
import android.widget.TextView;

/**
 * 用于倒计时工具 支持TextView倒计时    格式是int不支持double  单位 s
 *
 * 这种模式必须要在ondestory 回调中调用 stop方法清零  否则下次使用将无法回调计时方法
 *  暂不支持暂停计时  恢复上次计时点
 * <p>
 * Created by Mr.C on 2017/11/18 0018.
 */

public class CountDownUtil {


    private static TimerRunble runnable;

    private static Handler handler;

    public static void countTextView(int totaltime, int difference, TimerListener listener) {
        listener.onCreate();
        initRunnable(totaltime * 1000, difference * 1000, listener);
    }


    private static void initRunnable(int totaltime, int difference, TimerListener listener) {
        if (handler ==null){
            handler = new Handler();
        }
        if (runnable == null) {
            runnable = new TimerRunble(totaltime, difference, listener, handler);
            handler.postDelayed(runnable, difference);
        }

    }

    //取消计时
    public static void stop() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
        }
    }



    private static class TimerRunble implements Runnable {
        private int totaltime;
        private int difference;
        private TimerListener listener;
        private int st;
        private Handler handler;


        TimerRunble(int totaltime, int difference, TimerListener listener, Handler handler) {
            this.totaltime = totaltime;
            this.difference = difference;
            this.listener = listener;
            this.handler = handler;
        }

        @Override
        public void run() {
                if (totaltime == 0) {
                    listener.onStart(totaltime / 1000, difference / 1000);
                    handler.postDelayed(runnable, difference);
                } else {
                    st = totaltime - difference;
                    if (st > 0) {
                        listener.onStart(totaltime / 1000, difference / 1000);
                        handler.postDelayed(runnable, difference);
                    } else {
                        listener.onEnd();
                    }
                    totaltime = st;
                }
        }
    }

    /**
     * 倒计时监听类
     */
    public interface TimerListener {
        /**
         * 可在此使控件失去点击事件
         */
        void onCreate();

        /**
         * @param newtime    现在时
         * @param difference 频率
         */
        void onStart(int newtime, int difference);

        void onEnd();
    }
}
