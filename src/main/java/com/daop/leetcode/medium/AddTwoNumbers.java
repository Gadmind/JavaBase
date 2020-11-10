package com.daop.leetcode.medium;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.leetcode.medium
 * @Description: LeetCode 两数相加
 * @DATE: 2020-11-10 22:59
 * @AUTHOR: Daop
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 **/
public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1, new ListNode(2));
        ListNode node2 = new ListNode(1, new ListNode(2, new ListNode(3)));
        System.out.println(Integer.parseInt(getVal(node1))+Integer.parseInt(getVal(node2)));
    }

    public static String getVal(ListNode node) {
        String val;
        val = String.valueOf(node.val);
        if (node.next != null) {
            val += getVal(node.next);
        }
        return val;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
