package com.daop.leetcode.easy;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.easy
 * @Description: Leetcode 7 整数反转
 * @DATE: 2020-12-14
 * @AUTHOR: Administrator
 **/
public class ReverseInteger {
    public static void main(String[] args) {
        int x = -3240;
        System.out.println(reverse(x));
    }

    public static int reverse(int x) {
        String str = String.valueOf(x);
        StringBuilder sb;
        if (!str.contains("-")) {
            sb = new StringBuilder(str);
        } else {
            sb=new StringBuilder(str.substring(1)).append("-");
        }
        sb.reverse();
        return Integer.parseInt(sb.toString());
    }
}
