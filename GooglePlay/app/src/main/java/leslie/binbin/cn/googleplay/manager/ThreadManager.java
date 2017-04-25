package leslie.binbin.cn.googleplay.manager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器
 */

public class ThreadManager {

    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {

        if (mThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (mThreadPool == null) {
                    int cupCount = Runtime.getRuntime().availableProcessors();
                    System.out.println("cpu个数:"+cupCount);
                    int threadCount = 10;
                    mThreadPool = new ThreadPool(threadCount,threadCount,1);

                }
            }
        }

        return mThreadPool;
    }

    //线程池
    public static class ThreadPool {

        private int corePoolSize;//核心线程数
        private int maximumPoolSize;//最大线程数
        private long keepAliveTime;//休息时间
        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r) {
            //参1 核心线程数
//参2 最大线程数
//参3 线程休眠时间
//参4 时间单位
//参5 时间队列
//参6 生产线程的工厂
//参7 线程异常处理策略

            if (executor == null) {
                executor = new ThreadPoolExecutor(
                        corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()
                        //参1 核心线程数cd
                        //参2 最大线程数
                        //参3 线程休眠时间
                        //参4 时间单位
                        //参5 时间队列
                        //参6 生产线程的工厂
                        //参7 线程异常处理策略
                );
            }

            //线程池也执行一个Runnable对象,具体运行时机线程池说了算
            executor.execute(r);
        }

        //取消任务
        public void cancel(Runnable r){
            //从线程队列中移除对象
            if(executor!=null) executor.getQueue().remove(r);


        }
    }
}
