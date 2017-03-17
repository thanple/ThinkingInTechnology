package com.thanple.thinking.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Thanple on 2017/3/17.
 */
public class TimerSchedule {

    public static void main(String[] args) {
        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {

            int count = 0;

            @Override
            public void run() {
                System.out.println(count++);
            }
        },500,1000);

    }
}
