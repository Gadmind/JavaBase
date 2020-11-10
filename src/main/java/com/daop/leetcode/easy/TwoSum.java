package com.daop.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.algorithm
 * @Description: LeetCode 两数之和
 * @DATE: 2020-11-10 22:01
 * @AUTHOR: Daop
 * 给定一个整数数组 nums 和一个目标值 target，
 * 请你在该数组中找出和为目标值的那两个整数，
 * 并返回他们的数组下标。
 * =================
 * 解题思路：
 **/
public class TwoSum {


    public static void main(String[] args) {
        int[] nums = {10, 20, 52, 67, 12, 54, 75, 15, 12, 12, 44};
        int target = 30;
        int[] ints = twoSum(nums, target);
        for (int i : ints) {
            System.out.println(i);
        }
    }
    //中等

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {


            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No Two sum solution");
    }
}
