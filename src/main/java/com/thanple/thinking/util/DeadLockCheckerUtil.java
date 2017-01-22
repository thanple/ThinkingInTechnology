package com.thanple.thinking.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thanple on 2017/1/22.
 * 死锁检测工具
 */

public class DeadLockCheckerUtil {

    public interface DeadlockHandler {
        void handleDeadlock(final ThreadInfo[] deadlockedThreads);
    }

    private final DeadlockHandler deadlockHandler;
    private final long period;
    private final TimeUnit unit;
    private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    final Runnable deadlockCheck = new Runnable() {
        @Override
        public void run() {
            long[] deadlockedThreadIds = DeadLockCheckerUtil.this.mbean.findDeadlockedThreads();

            if (deadlockedThreadIds != null) {
                ThreadInfo[] threadInfos =
                        DeadLockCheckerUtil.this.mbean.getThreadInfo(deadlockedThreadIds);

                DeadLockCheckerUtil.this.deadlockHandler.handleDeadlock(threadInfos);
            }
        }
    };

    /**
     * 使用默认的控制台Handler处理死锁结果
     * */
    private static DeadLockCheckerUtil getDefaultConsoleHandleDeadLockChecker(){
        return new DeadLockCheckerUtil(new DeadlockHandler() {
            @Override
            public void handleDeadlock(final ThreadInfo[] deadlockedThreads) {
                if (deadlockedThreads != null) {
                    System.err.println("Deadlock detected!");
                    Map<Thread, StackTraceElement[]> stackTraceMap = Thread.getAllStackTraces();
                    for (ThreadInfo threadInfo : deadlockedThreads) {
                        if (threadInfo != null) {
                            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                                if (thread.getId() == threadInfo.getThreadId()) {
                                    System.err.println(threadInfo.toString().trim());
                                    for (StackTraceElement ste : thread.getStackTrace()) {
                                        System.err.println("t" + ste.toString().trim());
                                    }


                                    //中断线程
                                    thread.interrupt();

                                }
                            }
                        }
                    }
                }
            }
        },5, TimeUnit.SECONDS);
    }
    public DeadLockCheckerUtil(final DeadlockHandler deadlockHandler,
                            final long period, final TimeUnit unit) {
        this.deadlockHandler = deadlockHandler;
        this.period = period;
        this.unit = unit;
    }

    public void start() {
        this.scheduler.scheduleAtFixedRate(
                this.deadlockCheck, this.period, this.period, this.unit);
    }

    public void close(){
        this.scheduler.shutdownNow();
    }

    public static void main(String[] args) {

        DeadLockCheckerUtil deadLockCheckerUtil = DeadLockCheckerUtil.getDefaultConsoleHandleDeadLockChecker();
        deadLockCheckerUtil.start();

        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        synchronized (lock1) {
                            System.out.println("Thread1 acquired lock1");
                            TimeUnit.MILLISECONDS.sleep(500);
                            synchronized (lock2) {
                                System.out.println("Thread1 acquired lock2");
                            }
                        }
                    }catch (InterruptedException ignore){
                        System.out.println("Thread 1 interrupted");
                    }
                }
            }

        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        synchronized (lock2) {
                            System.out.println("Thread2 acquired lock2");
                            TimeUnit.MILLISECONDS.sleep(500);
                            synchronized (lock1) {
                                System.out.println("Thread2 acquired lock1");
                            }
                        }
                    }catch (InterruptedException ignore){
                        System.out.println("Thread 2 interrupted");
                    }
                }
            }
        });
        thread2.start();

//        deadLockCheckerUtil.close();
    }

}
