package com.thanple.hadoop.demo;

import com.thanple.hadoop.common.ContextUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 将Hadoop的input文件上传到hdfs上运行步骤：
 * hadoop fs -mkdir /input 创建的文件在本地看不到，必须在Hadoop的Browse Directory中查看
 * hadoop fs -put -/wordcount/input.txt /input
 * hadoop jar -/Thanple.jar com.thanple.hadoop.demo.WordCountMain /input/input.txt /output/wordcount
 * */
public class WordCountMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = ContextUtil.getInitJob("WordCount");

        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCount.IntSumMapper.class);
        job.setCombinerClass(WordCount.IntSumReducer.class);
        job.setReducerClass(WordCount.IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
