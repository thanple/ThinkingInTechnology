
#!/usr/bin/env python

#sudo chmod -R 777 ~/IdeaProjects/ThinkingInTechnology/src/main/java/com/thanple/hadoop/streaming/wordcount_mapper.py
#sudo chmod -R 777 ~/IdeaProjects/ThinkingInTechnology/src/main/java/com/thanple/hadoop/streaming/wordcount_reducer.py
#hadoop jar /usr/local/hadoop-2.8.1/share/hadoop/tools/lib/hadoop-streaming-2.8.1.jar -input /input/input.txt -output /output -mapper 'python ~/IdeaProjects/ThinkingInTechnology/src/main/java/com/thanple/hadoop/streaming/wordcount_mapper.py' -reducer 'python ~/IdeaProjects/ThinkingInTechnology/src/main/java/com/thanple/hadoop/streaming/wordcount_reducer.py' -jobconf mapred.map.capacity.per.tasktracker=3 -jobconf stream.memory.limit=2000

#cat ./datagen/small-earthquake.data | ./stream/mapper.py | sort | ./stream/reducer.py

import sys

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # increase counters
    for word in words:
        # write the results to STDOUT (standard output);
        # what we output here will be the input for the
        # Reduce step, i.e. the input for reducer.py
        #
        # tab-delimited; the trivial word count is 1
        print '%s\t%s' % (word, 1)