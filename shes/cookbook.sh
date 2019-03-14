#!/bin/bash

rm -rf ./dir/* 
find . -iname 'test*' | xargs -i cp {} ./dir1/


#统计文件行数
#find -print0生成以'\0'为分隔符的输出, xargs -0以'\0'进行分割
echo "***** test 1 ******"
find ./ -type f -name "*.sh" -print0 | xargs -0 wc -l

#获取sample, %从右往左 #从左往右
echo "***** test 2 ******"
file_jpg="sample.jpg"
file_func_jpg="sample.func.easy.jpg"
echo ${file_jpg%.*}
echo ${file_func_jpg%.*}
echo ${file_func_jpg%%.*}
echo ${file_jpg#*.}
echo ${file_func_jpg##*.}

#echo -e代表转义, read -p交互式
echo -e "***** test 3 ******\n *********"
#read -p "What woudld you like?" likes
echo ${likes}


#并行命令&,  wait相当于Thread.join()，不过这里是并行进程
echo "***** test 4 ******"
PIDARRAY=()
for file in ./*
do
	md5sum $file &
	PIDARRAY+=("$!")
done
wait ${PIDARRAY[@]}

#awk '{pattern + action}' {filenames}
echo "***** test awk ******"
awk -F":" '{ print $1 }' /etc/passwd
awk '{if(NR>=20 && NR<=30) print $1}' test.txt
echo 'I am Poe,my qq is 33794712' | awk  -F '[, ]' '{print $3" "$7}'
echo 'I am Poe,my qq is 33794712' | awk 'BEGIN{FS="[, ]"} {print $3" "$7}'
ls -all |awk 'BEGIN {size=0;} {size=size+$5;} END{print "[end]size is ",size}'
