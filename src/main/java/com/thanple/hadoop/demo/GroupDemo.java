package com.thanple.hadoop.demo;

import com.thanple.hadoop.common.Constant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by root on 17-4-13.
 */
public class GroupDemo extends Configured implements Tool {


    public static class MyMapper extends MapReduceBase implements
            Mapper<LongWritable, Text, LongWritable, LongWritable> {

        @Override
        public void map(LongWritable longWritable, Text text, OutputCollector<LongWritable, LongWritable> outputCollector, Reporter reporter) throws IOException {
            String[] spilted = text.toString().split(",");
            long firstNum = Long.parseLong(spilted[0]);
            long secondNum = Long.parseLong(spilted[1]);
            outputCollector.collect(new LongWritable(firstNum), new LongWritable(secondNum));
        }
    }

    public static class MyReducer extends MapReduceBase implements
            Reducer<LongWritable, LongWritable, LongWritable, LongWritable> {
        @Override
        public void reduce(LongWritable myNewKey, Iterator<LongWritable> iterator, OutputCollector<LongWritable, LongWritable> outputCollector, Reporter reporter) throws IOException {

            long min = Long.MAX_VALUE;
            while(iterator.hasNext()){
                min = Math.min(min,iterator.next().get());
            }
            outputCollector.collect(myNewKey,new LongWritable(min));
        }
    }


    @Override
    public int run(String[] strings) throws Exception {
        JobConf conf = new JobConf(getConf(), SortDemo.class);
        conf.setJobName("GroupDemo");
        conf.setJar(Constant.Path.JAR_NAME);

        conf.setMapOutputKeyClass(LongWritable.class);;
        conf.setMapOutputValueClass(LongWritable.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(LongWritable.class);

        conf.setMapperClass(MyMapper.class);
        conf.setReducerClass(MyReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, Constant.getLocalPath("/groupdemo/input.txt"));
        FileOutputFormat.setOutputPath(conf, Constant.getLocalPath("/groupdemo/output"));

        JobClient.runJob(conf);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new GroupDemo(), args);
        System.exit(res);
    }
}
