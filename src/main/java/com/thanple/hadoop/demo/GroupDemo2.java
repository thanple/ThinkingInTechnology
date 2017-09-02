package com.thanple.hadoop.demo;

import com.thanple.hadoop.common.Constant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by root on 17-4-13.
 * 自定义分组
 */
public class GroupDemo2 extends Configured implements Tool {

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

    public static class MyGroupingComparator implements
            RawComparator<MyNewKey> {

        /*
         * 基本分组规则：按第一列firstNum进行分组
         */
        @Override
        public int compare(MyNewKey key1, MyNewKey key2) {
            return (int) (key1.firstNum - key2.firstNum);
        }

        /*
         * @param b1 表示第一个参与比较的字节数组
         *
         * @param s1 表示第一个参与比较的字节数组的起始位置
         *
         * @param l1 表示第一个参与比较的字节数组的偏移量
         *
         * @param b2 表示第二个参与比较的字节数组
         *
         * @param s2 表示第二个参与比较的字节数组的起始位置
         *
         * @param l2 表示第二个参与比较的字节数组的偏移量
         */
        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            return WritableComparator.compareBytes(b1, s1, 8, b2, s2, 8);
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

            //按第一个数分组完之后，取第二个数的最大值
            long max = Long.MIN_VALUE;
            while(iterator.hasNext()){
                max = Math.max(max,iterator.next().get());
            }
            outputCollector.collect(new LongWritable(myNewKey.firstNum),
                    new LongWritable(max));
        }
    }



    @Override
    public int run(String[] strings) throws Exception {
        JobConf conf = new JobConf(getConf(), SortDemo.class);
        conf.setJobName("GroupDemo2");
        conf.setJar(Constant.Path.JAR_NAME);

        conf.setMapOutputKeyClass(MyNewKey.class);;
        conf.setMapOutputValueClass(LongWritable.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(LongWritable.class);

        conf.setMapperClass(MyMapper.class);
        conf.setReducerClass(MyReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        //设置分组
        conf.setOutputValueGroupingComparator(MyGroupingComparator.class);

        FileInputFormat.setInputPaths(conf, Constant.getLocalPath("/groupdemo/input.txt"));
        FileOutputFormat.setOutputPath(conf, Constant.getLocalPath("/groupdemo/output"));

        JobClient.runJob(conf);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new GroupDemo2(), args);
        System.exit(res);
    }
}
