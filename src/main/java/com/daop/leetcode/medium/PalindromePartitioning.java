package com.daop.leetcode.medium;

import sun.java2d.pipe.SpanIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.medium
 * @Description: Leetcode 131 分割回文串
 * @DATE: 2020-12-14
 * @AUTHOR: Administrator
 * <p>
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 * 返回 s 所有可能的分割方案。
 * <p>
 * 示例:
 * 输入: "aab"
 * 输出:
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 **/
public class PalindromePartitioning {
    public static void main(String[] args) {
        //TODO 待解决
        String s = "aab";
        List<List<String>> list = partition(s);
       /* for (List<String> strList : list) {
            for (String str : strList) {
                System.out.print(str+" ");
            }
            System.out.println();
        }*/

    }

    public static List<List<String>> partition(String s) {
        int length = s.length();
        List<List<String>> res = new ArrayList<>();
        part(s, 0, length, res);
        return null;
    }

    /**
     * @param s    传入的字符串
     * @param star 开始地址
     * @param len  字符串长度
     * @param res  结果集
     */
    private static void part(String s, int star, int len, List<List<String>> res) {
        System.out.println(star + "  " + ((s.substring(star, len).length()) == 0) + " " + s.substring(star, len));
        if (star == len - 1) {
            return;
        }
        for (int i = star; i < len; i++) {
            part(s, i + 1, len, res);
        }
    }

}
