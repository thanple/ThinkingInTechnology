package com.thanple.thinking.springboot.task;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Thanple on 2017/2/19.
 */

@Component
public class ScheduledTasksDemo1 {
    private static final Logger log = Logger.getLogger(ScheduledTasksDemo1.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info(String.format("The time is now %s", dateFormat.format(new Date())));
    }
}
