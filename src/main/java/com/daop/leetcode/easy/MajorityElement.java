package com.daop.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.easy
 * @Description: LeetCode 169 多数元素
 * @DATE: 2020-12-11
 * @AUTHOR: Administrator
 * <p>
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * 标签：位运算、数组、分治算法
 * 示例 1:
 * 输入: [3,2,3]
 * 输出: 3
 * <p>
 * 示例 2:
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 **/
public class MajorityElement {
    public static void main(String[] args) {
        int[] nums = {2,2,1,1,1,2,2};
        System.out.println(majorityElement(nums));
        System.out.println(sort(nums));
        System.out.println(divideAndConquer(nums));
        System.out.println(moore(nums));
    }

    /**
     * 计数法
     *
     * @param nums
     * @return
     */
    private static int majorityElement(int[] nums) {
        Map<Integer, Integer> showTimes = new HashMap<>();
        for (int num : nums) {
            showTimes.merge(num, 1, Integer::sum);
        }
        int len = nums.length >> 1;
        for (Map.Entry<Integer, Integer> entry : showTimes.entrySet()) {
            if (entry.getValue() > len) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * 排序法
     *
     * @param nums
     * @return
     */
    private static int sort(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length >> 1];
    }

    /**
     * 分治
     *
     * @param nums
     * @return
     */
    private static int divideAndConquer(int[] nums) {
        return divideAndConquerRec(nums, 0, nums.length - 1);
    }

    private static int divideAndConquerRec(int[] nums, int lo, int hi) {
        if (lo == hi) {
            return nums[lo];
        }
        // 找到数组的1/2下标
        int mid = ((hi - lo) >> 1) + lo;

        int left = divideAndConquerRec(nums, lo, mid);

        int right = divideAndConquerRec(nums, mid + 1, hi);

        if (left == right) {
            return left;
        }
        // 找到出现次数最多的
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    private static int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }


    private static int moore(int[] nums) {
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}
