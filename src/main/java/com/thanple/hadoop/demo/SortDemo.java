package com.thanple.hadoop.demo;


import com.thanple.hadoop.common.Constant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by root on 17-4-12.
 * 自定义排序
 */
public class SortDemo extends Configured implements Tool {

    private static class MyNewKey implements WritableComparable<MyNewKey> {
        long firstNum;
        long secondNum;
        public MyNewKey() {}
        public MyNewKey(long first, long second) {
            firstNum = first;
            secondNum = second;
        }
        @Override
        public void write(DataOutput out) throws IOException {
            out.writeLong(firstNum);
            out.writeLong(secondNum);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            firstNum = in.readLong();
            secondNum = in.readLong();
        }

        /*
         * 当key进行排序时会调用以下这个compreTo方法
         */
        @Override
        public int compareTo(MyNewKey anotherKey) {
            long min = firstNum - anotherKey.firstNum;
            if (min != 0) {
                // 说明第一列不相等，则返回两数之间小的数
                return (int) min;
            } else {
                return (int) (secondNum - anotherKey.secondNum);
            }
        }

    }


    public static class MyMapper extends MapReduceBase implements
            Mapper<LongWritable, Text, MyNewKey, LongWritable> {

        @Override
        public void map(LongWritable longWritable, Text text, OutputCollector<MyNewKey, LongWritable> outputCollector, Reporter reporter) throws IOException {
            String[] spilted = text.toString().split(",");
            long firstNum = Long.parseLong(spilted[0]);
            long secondNum = Long.parseLong(spilted[1]);
            // 使用新的类型作为key参与排序
            MyNewKey newKey = new MyNewKey(firstNum, secondNum);
            outputCollector.collect(newKey, new LongWritable(secondNum));
        }
    }

    public static class MyReducer extends MapReduceBase implements
            Reducer<MyNewKey, LongWritable, LongWritable, LongWritable> {
        @Override
        public void reduce(MyNewKey myNewKey, Iterator<LongWritable> iterator, OutputCollector<LongWritable, LongWritable> outputCollector, Reporter reporter) throws IOException {
            outputCollector.collect(new LongWritable(myNewKey.firstNum),
                    new LongWritable(myNewKey.secondNum));
        }
    }


    @Override
    public int run(String[] strings) throws Exception {
        JobConf conf = new JobConf(getConf(), SortDemo.class);
        conf.setJobName("SortDemo");
        conf.setJar(Constant.Path.JAR_NAME);

        conf.setMapOutputKeyClass(MyNewKey.class);;
        conf.setMapOutputValueClass(LongWritable.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(LongWritable.class);

        conf.setMapperClass(MyMapper.class);
        conf.setReducerClass(MyReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, Constant.getLocalPath("/sortdemo/input.txt"));
        FileOutputFormat.setOutputPath(conf, Constant.getLocalPath("/sortdemo/output"));

        JobClient.runJob(conf);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new SortDemo(), args);
        System.exit(res);
    }
}
