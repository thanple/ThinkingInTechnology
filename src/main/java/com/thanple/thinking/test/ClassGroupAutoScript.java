package com.thanple.thinking.test;

import com.thanple.thinking.root.Constant;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Create by Thanple at 2017/11/8 下午10:15
 */
public class ClassGroupAutoScript {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new FileInputStream(new File(Constant.Path.RESOURCE_ROOT_PATH+"/com/thanple/thinking/test/班级成绩.txt")));

        LinkedHashMap<Integer,List<String>> classLines = new LinkedHashMap<>();

        while ( input.hasNextLine()) {
            String tmp = input.nextLine();
            if(tmp.startsWith("序号")){
                continue;
            }
            String [] tmps = tmp.split("\\s+");
            if(StringUtils.isEmpty(tmps[0])){
                continue;
            }
            Integer xuhao = Integer.parseInt(tmps[0]);
            xuhao /= 100;
            List<String> lines = classLines.get(xuhao);
            if(lines == null) {
                lines = new ArrayList<>();
                classLines.put(xuhao,lines);
            }
            lines.add(tmp);
        }

        for(Map.Entry<Integer,List<String>> entry: classLines.entrySet()) {
            System.out.println("----"+entry.getKey()+"班-----");

            PrintWriter output = new PrintWriter(new FileOutputStream(new File(Constant.Path.RESOURCE_ROOT_PATH+"/com/thanple/thinking/test/"+ entry.getKey() +".txt")));
            output.write("序号\t姓名\t编号\t考号\t语文\t数学\t英语\t政治\t历史\t地理\t信息\t生物\n");
            double sum = 0;
            for(String str : entry.getValue()) {
                output.write(str);
                String strs[] = str.split("\\s+");
                if(strs.length==5) {
                    sum += Integer.parseInt(strs[4]);
                    System.out.print(strs[4]+" ");
                }else{
                    sum += Integer.parseInt(strs[5]);
                    System.out.print(strs[5]+" ");
                }

                //System.out.print(str.split("\\s+")[4]+" ");
                output.write("\n");
            }
            System.out.println();
            System.out.println("总成绩:"+sum+" ,人数: "+entry.getValue().size());
            sum /=  entry.getValue().size();
            System.out.println("平均分:"+String.valueOf(sum));
            output.write(String.valueOf(sum));
            output.close();
        }

        input.close();
    }
}
