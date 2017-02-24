package com.thanple.thinking.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Thanple on 2017/2/24.
 * 场景分析:10个人去春游,规定达到一个地点后才能继续前行
 */
public class CyclicBarrierWorker implements Runnable {
    private int id;
    private CyclicBarrier barrier;

    public CyclicBarrierWorker(int id, final CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(id + " th people wait");
            barrier.await(); // 大家等待最后一个线程到达
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int num = 10;
        CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("go on together!");
            }
        });
        for (int i = 1; i <= num; i++) {
            new Thread(new CyclicBarrierWorker(i, barrier)).start();
        }
    }
}


