/*
 * @lc app=leetcode.cn id=147 lang=java
 *
 * [147] 对链表进行插入排序
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode sortList = null;
        head = new ListNode(Integer.MIN_VALUE, head);
        ListNode max = null;
        ListNode nodeParent = null;

        for (sortList = null; sortList != head && head.next != null; sortList = max) {
            nodeParent = head;
            for (ListNode cur = head.next; cur.next != null; cur = cur.next) {
                if (cur.next.val > nodeParent.next.val) {
                    nodeParent = cur;
                }
            }

            max = nodeParent.next;
            nodeParent.next = max.next;
            max.next = sortList;
        }

        return sortList;
    }

    private ListNode findLastNode(ListNode head) {
        return head.next == null ? head : this.findLastNode(head.next);
    }
}
// @lc code=end
