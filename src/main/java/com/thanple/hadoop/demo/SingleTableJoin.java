package com.thanple.hadoop.demo;

import com.thanple.hadoop.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Create by Thanple at 2017/10/30 下午9:40
 * 单表连接Demo：根据child-parent找出grandchild-grandparent
 * <p>
 * input.txt:
 * child parent
 * Tom Lucy
 * Tom Jack
 * Jone Lucy
 * Jone Jack
 * ...
 * <p>
 * <p>
 * output:
 * Tom Alice
 * Tom Jesse
 * ...
 */
public class SingleTableJoin extends Configured implements Tool {

    public enum RelationShip {
        CHILD, PARENT;
    }


    /**
     * 自定义的类型一定要实现Writeable接口
     * */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RelationShipBean implements Writable {
        private RelationShip relationShip;
        private String name;

        @Override
        public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeUTF(this.relationShip.name());
            dataOutput.writeUTF(this.name);
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
            this.relationShip = RelationShip.valueOf(dataInput.readUTF());
            this.name = dataInput.readUTF();
        }
    }

    /**
     * Mapper中左表为child->parent,右表为parent->child，
     * 当将所有key收集在一块的时候，即可在Reduce中得到value，左表为grandparent，右表为grandchild
     * */
    public static class MyMapper extends MapReduceBase implements Mapper<Object, Text, Text, RelationShipBean> {
        @Override
        public void map(Object o, Text text, OutputCollector<Text, RelationShipBean> outputCollector, Reporter reporter) throws IOException {
            String[] strs = text.toString().split("\\s+");

            outputCollector.collect(new Text(strs[0]), new RelationShipBean(RelationShip.PARENT, strs[1]));
            outputCollector.collect(new Text(strs[1]), new RelationShipBean(RelationShip.CHILD, strs[0]));
        }
    }


    /**
     * 已经是将parent作为key，value分别为child(右表)和grandparent(左表)
     * 选择右表作为集合A，左表作为B
     * 则A*B即为所有child->grandparent的数组对
     * */
    public static class MyReducer extends MapReduceBase implements Reducer<Text, RelationShipBean, Text, Text> {

        @Override
        public void reduce(Text text, Iterator<RelationShipBean> iterator, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {
            List<String> grandParents = new ArrayList<>();
            List<String> grandChildren = new ArrayList<>();

            //先放到两个集合里面
            while (iterator.hasNext()) {
                RelationShipBean relationShipBean = iterator.next();
                if(relationShipBean.getRelationShip() == RelationShip.CHILD) {
                    grandChildren.add(relationShipBean.getName());
                }else {
                    grandParents.add(relationShipBean.getName());
                }
            }

            //再求笛卡尔积即可
            for(int i=0 , leni = grandChildren.size();  i<leni ;i++) {
                String grandChild = grandChildren.get(i);
                for(int j=0 , lenj = grandParents.size(); j<lenj ; j++) {
                    outputCollector.collect(new Text(grandChild), new Text(grandParents.get(j)));
                }
            }
        }
    }


    @Override
    public int run(String[] strings) throws Exception {
        JobConf conf = new JobConf(getConf(), SortDemo.class);
        conf.setJobName("SingleTableJoin");
        conf.setJar(Constant.Path.JAR_NAME);

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(RelationShipBean.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(SingleTableJoin.MyMapper.class);
        conf.setReducerClass(SingleTableJoin.MyReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, Constant.getLocalPath("/singletablejoin/input.txt"));
        FileOutputFormat.setOutputPath(conf, Constant.getLocalPath("/singletablejoin/output"));

        JobClient.runJob(conf);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new SingleTableJoin(), args);
        System.exit(res);
    }
}
