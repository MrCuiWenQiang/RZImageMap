package cn.faker.repaymodel.util.proxy;

/**
 * Function : 线程池工厂 为了数据库（大量数据）增删改查操作不堵塞主线程 现成立线程池代理
 * Remarks  :
 * Created by Mr.C on 2018/6/8 0008.
 */

public class ThreadPoolProxyFactory {
    private static ThreadPoolProxy dBWhereThreadPool;
    private static ThreadPoolProxy dbLoadThreadPool;

    /**
     * 数据查询线程池
     * @return
     */
    public static ThreadPoolProxy getdBWhereThreadPool() {
        if (dBWhereThreadPool ==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (dBWhereThreadPool ==null){
                    dBWhereThreadPool = new ThreadPoolProxy(5,5);
                }
            }
        }
        return dBWhereThreadPool;
    }

    /**
     * 数据查询线程池
     * @return
     */
    public static ThreadPoolProxy getRFIDWhereThreadPool() {
        if (dBWhereThreadPool ==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (dBWhereThreadPool ==null){
                    dBWhereThreadPool = new ThreadPoolProxy(3,3);
                }
            }
        }
        return dBWhereThreadPool;
    }

    /**
     *数据下载线程池
     * @return
     */
    public static ThreadPoolProxy getdbLoadThreadPool() {
        if (dbLoadThreadPool ==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (dbLoadThreadPool ==null){
                    dbLoadThreadPool = new ThreadPoolProxy(3,3);
                }
            }
        }
        return dbLoadThreadPool;
    }


}
