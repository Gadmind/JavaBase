package com.daop.leetcode.easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.easy
 * @Description: LeetCode 136 只出现一次的数字
 * @DATE: 2020-12-11
 * @AUTHOR: Daop
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 说明：
 * <p>
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * 标签：位运算、哈希表
 * <p>
 * 示例 1:
 * 输入: [2,2,1]
 * 输出: 1
 *
 * 示例 2:
 * 输入: [4,1,2,1,2]
 * 输出: 4
 **/
public class SingleNumber {
    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2};
        System.out.println(singleNumber(nums));
        System.out.println(XOR(nums));
        System.out.println(hashSet(nums));
    }

    /**
     * 自己的想法：通过map将数组中的数字存放，
     * 如果存在多次就将次数+1
     * 最后对map遍历获取到 value = 1 的 key 并返回
     * @param nums 数组
     * @return 数组中只有一次的数字
     */
    private static int singleNumber(int[] nums) {
        int result = -1;
        Map<Integer, Integer> showTimes = new HashMap<>();
        for (int num : nums) {
            showTimes.merge(num, 1, Integer::sum);
        }
        for (Integer integer : showTimes.keySet()) {
            if (showTimes.get(integer) == 1) {
                result = integer;
            }
        }
        return result;
    }

    /**
     * 通过 hashSet 来存储
     * 将数组遍历放入 hashSet 中，如果数添加时已经存在 则会返回 false
     * 通过判断将 set 中的该数删除 最后返回
     * @return
     */
    private static int hashSet(int[] nums){
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            if (!numSet.add(num)) {
                numSet.remove(num);
            }
        }
        return numSet.iterator().next();
    }
    /**
     * 通过异或运算 通过两个数的二进制相比 同位相同则为 0 不同则为 1
     * @param nums
     * @return
     */
    private static int XOR(int[] nums){
        int res=0;
        for (int num : nums) {
            res=res^num;
        }
        return res;
    }

}
