package cn.faker.repaymodel.util.error;

import android.content.Context;

/**
 * 捕捉全局崩溃错误
 * Created by Mr.C on 2018/1/16 0016.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mcontext;

    private Thread.UncaughtExceptionHandler mDefaultUncaught;

    public void initCrash(Context mcontext){
        this.mcontext = mcontext;
        mDefaultUncaught = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
