package com.thanple.thinking.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Thanple on 2017/4/5.
 *
 * 锁的类型总结
 *
 */

public class LockTypeSummary {

    interface Lock{
        void lock();
        void unlock();
    }
    private abstract static class NumLock implements Lock{
        public int num = 0;
    }

    private static class NoLock extends NumLock{
        @Override
        public void lock() {}
        @Override
        public void unlock() {}
    }
    /**
     * 自旋锁
     * */
    private static class SpinLock  extends NumLock {
        private AtomicReference<Thread> sign =new AtomicReference<>();

        public void lock(){
            Thread current = Thread.currentThread();
            while(!sign .compareAndSet(null, current)); //compareAndSet: 当前引用值等于${1}则返回true，并且更新为${2}
        }

        public void unlock (){
            Thread current = Thread.currentThread();
            sign .compareAndSet(current, null);
        }
    }


    public static void testLock(NumLock numLock){
        long start = System.currentTimeMillis();
        final int finalNum = 5000;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(finalNum, () -> {
            System.out.println( numLock.getClass().getSimpleName() + " : "+ numLock.num + " " + (System.currentTimeMillis()-start) + "ms");
        });

        for(int i=0;i<finalNum;i++){
            new Thread(()->{
                numLock.lock();
                numLock.num++;
                numLock.unlock();

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    public static void main(String[] args) {

        testLock(new NoLock());
        testLock(new SpinLock());


    }


}
