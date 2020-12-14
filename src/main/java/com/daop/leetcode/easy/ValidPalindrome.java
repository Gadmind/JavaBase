package com.daop.leetcode.easy;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.easy
 * @Description: Leetcode 125 验证回文串
 * @DATE: 2020-12-14
 * @AUTHOR: Administrator
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 * 示例 1:
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * a man a plan a c analpanama
 * a nalp a nam a
 * 示例 2:
 * 输入: "race a car"
 * 输出: false
 **/
public class ValidPalindrome {
    public static void main(String[] args) {
        String string = "race a car";
        System.out.println(validPalindrome(string));
    }

    /**
     * 通过双指针方式同时从字符串头部和尾部进行遍历
     * 第一步: 先将传入的字符串进行全小写转换并转换为字符串组
     * 第二步: 对字符组进行遍历和判断, 剔除符号和空格, 并将字符添加到 sb 中进行保存
     * 第三步: 获取 sb 的长度, 并同时从 sb 的头部下标和尾部下标进行遍历比对, 若两个标相遇 则这个字符串为回文字符串
     *
     * @param s
     * @return
     */
    private static boolean validPalindrome(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder(s.length());
        for (char aChar : chars) {
            if (aChar >= 'A' && aChar <= 'Z' || aChar >= 'a' && aChar <= 'z' || aChar >= '0' && aChar <= '9') {
                sb.append(aChar);
            }
        }
        int i = 0, j = sb.length() - 1;
        while (i < j) {
            if (sb.charAt(i++) != sb.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

}
