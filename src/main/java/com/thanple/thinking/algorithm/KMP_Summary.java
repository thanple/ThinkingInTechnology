package com.thanple.thinking.algorithm;

/**
 * Created by Thanple on 2017/2/17.
 * KMP字符串匹配算法(Knuth-Morris-Pratt)的总结：
 * 时间复杂度O(m+n)
 */
public class KMP_Summary {

    /**
     * 计算前缀函数π
     * π[i] = j表示pattern匹配式的子字符串S(0,i)的最长前后缀公共长度为j
     * 最长前后缀公共长度：前缀字符串等于后缀字符串，其中公共长度最大，例如abaabamnaba的最长前后缀公共长度为3(aba)。
     * */
    private static int [] computePrefix(final char[] pattern){
        int[] π = new int[pattern.length];
        int k=0;

        for(int i = 1; i<pattern.length; i++) {
            while(k>0 && pattern[k] != pattern[i])
                k = π[k];
            if(pattern[k] == pattern[i])
                k++;

            //有点像动态规划里面的自底向上保存子字符串的最长前后缀公共长度
            π[i] = k;
        }

        return π;
    }

    /**
     * KMP匹配字符串
     *
     * 原理：计算匹配字符串pattern的最长前后缀长度函数π，str与pattern匹配失配时pattern位移为q，
     * 则子字符串pattern[0,q]中如果存在前缀和后缀相等，则可将前缀替代后缀位置，而str源字符串继续搜索前进
     * */
    public static int kmpMatcher(final char [] str, final char [] pattern) {
        //计算前缀函数
        int[] π = computePrefix(pattern);

        int q = 0;  //q表示在pattern中的位移
        for (int i = 0; i<str.length; i++){
            while (q > 0 && pattern[q] != str[i])
                q = π[q];   //下一个字符串不匹配
            if(pattern[q] == str[i])
                q++;

            //pattern字符串全部匹配完毕
            if(q == pattern.length)
                return i+1-q;

        }

        return -1;
    }

    public static void main(String[] args) {
        int index = kmpMatcher("abaabamnaba".toCharArray() , "abam".toCharArray());
        System.out.println("匹配完后的下标:"+ index);
    }
}
