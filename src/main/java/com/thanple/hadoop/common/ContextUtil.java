package com.thanple.hadoop.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * Created by root on 17-4-10.
 */
public class ContextUtil {

    public static Job getInitJob(String name) throws IOException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, name);
        job.setJar(Constant.Path.JAR_NAME); //设定jar的目录

        return job;
    }


}
