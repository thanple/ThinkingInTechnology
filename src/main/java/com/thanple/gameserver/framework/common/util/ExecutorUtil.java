package com.thanple.gameserver.framework.common.util;

import java.util.concurrent.*;

/**
 * Created by Thanple on 2017/2/6.
 *
 * 线程池工具
 */
public class ExecutorUtil {

    //普通缓存线程池
    private static ExecutorService executorThreadPool = Executors.newCachedThreadPool();

    //定时任务线程池
    private static ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(50);


    public static void executeInThreadPool(Runnable runnable){
        executorThreadPool.execute(runnable);
    }

    public static <T> Future<T> executeFuture (Callable<T> callable){
        return executorThreadPool.submit(callable);
    }


    public static ScheduledFuture<?> scheduleThreadPool(Runnable runnable, long delay, TimeUnit timeUnit){
        return scheduleThreadPool.schedule(runnable,delay,timeUnit);
    }

}
