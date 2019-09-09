package cn.faker.repaymodel.util.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function :线程池代理类
 * Remarks  :
 * Created by Mr.C on 2018/6/8 0008.
 */

public class ThreadPoolProxy {
    public ThreadPoolExecutor mThreadPoolExecutor;

    private int corePoolSize;
    private int maximumPoolSize;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
    }

    private final void init() {
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {
                    long keepAliveTime = 3000;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

                    mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                            threadFactory, handler);
                }
            }
        }
    }

    public Future submit(Runnable task) {
        init();
        return mThreadPoolExecutor.submit(task);
    }

    public void excute(Runnable task) {
        init();
        mThreadPoolExecutor.execute(task);
    }

    public void remove(Runnable task) {
        init();
        mThreadPoolExecutor.remove(task);
    }

}
