package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DoubleCursor {

    /**
     * 16. 最接近的三数之和
     *
     * @param nums   数组
     * @param target 目标
     * @return int
     */
    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        int closetSum = 0;
        int dis = Integer.MAX_VALUE;
        int sumTemp = 0, disTemp = 0;
        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                for (int k = j + 1; k < len; k++) {
                    sumTemp = nums[i] + nums[j] + nums[k];
                    disTemp = Math.abs(sumTemp - target);
                    if (disTemp < dis) {
                        dis = disTemp;
                        if (dis == 0) {
                            return target;
                        }
                        closetSum = sumTemp;
                    }
                }
            }
        }
        return closetSum;
    }

    public int threeSumClosest_DoubleCursor(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        int closetSum = 0;
        int dis = Integer.MAX_VALUE;
        int sumTemp = 0, disTemp = 0;
        for (int i = 0; i < len - 2; ++i) {
            for (int j = i + 1, k = len - 1; j < k; ) {
                /*
                // 去重处理
                if (nums[j] == nums[i]) {
                    ++j;
                    continue;
                }
                */
                // 更新距离值
                sumTemp = nums[i] + nums[j] + nums[k];
                disTemp = Math.abs(sumTemp - target);
                if (disTemp < dis) {
                    dis = disTemp;
                    if (dis == 0) {
                        return target;
                    }
                    closetSum = sumTemp;
                }
                // 更新游标
                if (sumTemp > target) {
                    --k;
                } else {
                    ++j;
                }
            }
        }
        return closetSum;
    }

    /**
     * 18. 四数之和
     *
     * @param nums   数组
     * @param target 目标
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
//        HashSet<String> duplicate = new HashSet<>();
        Arrays.sort(nums);
        int len = nums.length;
        long sumTemp = -1;
        for (int i = 0; i < len - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 更新距离值
                for (int k = j + 1, l = len - 1; k < l; ) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        ++k;
                        continue;
                    }
                    if (l > len - 1 && nums[l] == nums[l + 1]) {
                        --l;
                        continue;
                    }
                    sumTemp = (long) (nums[i]) + nums[j] + nums[k] + nums[l];
                    if (sumTemp == target) {
                        ArrayList<Integer> res = new ArrayList<>();
                        res.add(nums[i]);
                        res.add(nums[j]);
                        res.add(nums[k]);
                        res.add(nums[l]);
                        String resultCache = String.format("%o%o%o%o", nums[i], nums[j], nums[k], nums[l]);
//                        if (!duplicate.contains(resultCache)) {
//                            duplicate.add(resultCache);
                        result.add(res);
//                        }
                        ++k;
                    } else if (sumTemp > target) {
                        --l;
                    } else {
                        ++k;
                    }
                }
            }
        }
        return result;
    }

    public class ListNode {
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

    /**
     * 19. 删除链表的倒数第 N 个结点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preNode = head, targetNode = head, tailNode = head;
        for (int i = 0; i < n - 1; ++i) {
            tailNode = tailNode.next;
        }
        if (tailNode.next == null) {
            // 只有n个node
            return head.next;
        }
        targetNode = targetNode.next;
        tailNode = tailNode.next;
        while (tailNode.next != null) {
            preNode = preNode.next;
            targetNode = targetNode.next;
            tailNode = tailNode.next;
        }
        preNode.next = preNode.next.next;
        return head;
    }

    /**
     * 31. 下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int right = nums.length - 1, left = nums.length - 2;
        while (right > 0) {
            while (left >= 0) {
                if (nums[left] < nums[right]) {
                    int temp = nums[right];
                    for (int i = right - 1; i >= left; --i) {
                        nums[i + 1] = nums[i];
                    }
                    nums[left] = temp;
//                    int temp = nums[left];
//                    nums[left] = nums[right];
//                    nums[right] = temp;
                    return;
                }
                --left;
            }
            --right;
            left = right - 1;
        }
        Arrays.sort(nums);
    }

    /**
     * 61. 旋转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (k < 1 || head == null) {
            return head;
        }
        ListNode tail = head, newHead = head, newTail = head;
        // tail先往前探索k-1个
        for (int i = 0; i < k; ++i) {
            tail = tail.next;
            if (tail == null) {
                tail = head;
            }
        }
        newHead = newHead.next;
        if (newHead == null) {
            newHead = head;
        }
        while (tail.next != null) {
            tail = tail.next;
            newHead = newHead.next;
            newTail = newTail.next;
            if (newHead == null) {
                newHead = head;
            }
            if (newTail == null) {
                newTail = head;
            }
        }
        // 拼接
        tail.next = head;
        newTail.next = null;
        return newHead;
    }

    /**
     * 160. 相交链表
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == headB) {
            return headA;
        }
        if (headA == null || headB == null) {
            return null;
        }
        short switchCnt = 0;
        ListNode pA = headA, pB = headB;
        for (; switchCnt < 2; ) {
            pA = pA.next;
            pB = pB.next;
            if (pA == null) {
                pA = headB;
                ++switchCnt;
            }
            if (pB == null) {
                pB = headA;
                ++switchCnt;
            }
        }
        for (; pA != null; pA = pA.next, pB = pB.next) {
            if (pA == pB) {
                return pA;
            }
        }
        return null;
    }

    /**
     * 88. 合并两个有序数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2.length < 1) {
            return;
        }
        for (int cursor1 = 0, cursor2 = 0; cursor2 < n; ) {
            if (nums2[cursor2] <= nums1[cursor1]) {
                for (int i = m; i > cursor1; --i) {
                    nums1[i] = nums1[i - 1];
                }
                nums1[cursor1] = nums2[cursor2];
                ++m;
                ++cursor2;
            } else if (cursor1 >= m) {
                nums1[cursor1] = nums2[cursor2];
                ++m;
                ++cursor2;
            }
            ++cursor1;
        }
    }

    /**
     * 141. 环形链表
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head, fast = head;
        while (true) {
            fast = fast.next;
            if (fast == null) {
                return false;
            }
            fast = fast.next;
            if (fast == null) {
                return false;
            }
            slow = slow.next;
            if (slow == fast) {
                return true;
            }
        }
    }

    /**
     * 234. 回文链表
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> array = new ArrayList<>();
        int len = 0;
        while (head != null) {
            ++len;
            array.add(head.val);
        }
        for (int i = 0, j = len - 1; j >= i; ++i, --j) {
            if (array.get(i).equals(array.get(j))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 455. 分发饼干
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。
     * 如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     *
     * @param g 胃口
     * @param s 饼干
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int gCursor = g.length - 1, sCursor = s.length - 1;
        int satisfied = 0;
        while (gCursor > -1 && sCursor > -1) {
            if (s[sCursor] >= g[gCursor]) {
                ++satisfied;
                --sCursor;
            }
            --gCursor;
        }
        return satisfied;
    }

    /**
     * 283. 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int nonzeroCnt = 0;
        int readCursor = 0;
        int len = nums.length;
        while (readCursor < len) {
            if (nums[readCursor] != 0) {
                ++nonzeroCnt;
                if (readCursor >= nonzeroCnt) {
                    nums[nonzeroCnt - 1] = nums[readCursor];
                }
            }
            ++readCursor;
        }
        if (nonzeroCnt == len) {
            return;
        }
        Arrays.fill(nums, nonzeroCnt, len, 0);
    }

    /**
     * 344. 反转字符串
     * 就地修改
     *
     * @param s
     */
    public void reverseString(char[] s) {
        int tailIdx = s.length - 1;
        char temp;
        for (int i = 0; i < s.length / 2; ++i) {
            temp = s[i];
            s[i] = s[tailIdx - i];
            s[tailIdx - i] = temp;
        }
    }

    /**
     * 345. 反转字符串中的元音字母
     *
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        HashSet<Character> characters = new HashSet<>();
        characters.add('a');
        characters.add('e');
        characters.add('i');
        characters.add('o');
        characters.add('u');
        characters.add('A');
        characters.add('E');
        characters.add('I');
        characters.add('O');
        characters.add('U');
        char temp;
        int leftCursor = 0, rightCursor = s.length() - 1;
        for (; leftCursor < rightCursor; ) {
            if (!characters.contains(chars[leftCursor])) {
                ++leftCursor;
                continue;
            }
            if (!characters.contains(chars[rightCursor])) {
                --rightCursor;
                continue;
            }
            temp = chars[leftCursor];
            chars[leftCursor] = chars[rightCursor];
            chars[rightCursor] = temp;
            ++leftCursor;
            --rightCursor;
        }
        return new String(chars);
    }

    /**
     * 349. 两个数组的交集
     * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] integers = new int[len1+len2];
        int interCnt = 0;
        for (int cursor1 = 0, cursor2 = 0; cursor1 < len1 && cursor2 < len2; ) {
            if (nums1[cursor1] == nums2[cursor2]) {
                if (cursor1 == 0 || nums1[cursor1] != nums1[cursor1 - 1]) {
                    integers[interCnt++]=nums1[cursor1];
                }
                ++cursor1;
                ++cursor2;
            } else if (nums1[cursor1] < nums2[cursor2]) {
                ++cursor1;
            } else if (nums1[cursor1] > nums2[cursor2]) {
                ++cursor2;
            }
        }
        return Arrays.copyOfRange(integers, 0, interCnt);
    }

    /**
     * 350. 两个数组的交集 II
     * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。
     * 返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] integers = new int[len1+len2];
        int interCnt = 0;
        for (int cursor1 = 0, cursor2 = 0; cursor1 < len1 && cursor2 < len2; ) {
            if (nums1[cursor1] == nums2[cursor2]) {
                integers[interCnt++] = nums1[cursor1];
                ++cursor1;
                ++cursor2;
            } else if (nums1[cursor1] < nums2[cursor2]) {
                ++cursor1;
            } else if (nums1[cursor1] > nums2[cursor2]) {
                ++cursor2;
            }
        }
        return Arrays.copyOfRange(integers, 0, interCnt);
    }

    /**
     * 75. 颜色分类
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
     * @param nums
     */
    public void sortColors(int[] nums) {
        if (nums.length < 1) {
            return;
        }
        int[] colorCnt = new int[]{0,0,0};
        for (int i = 0; i < nums.length; ++i) {
            colorCnt[nums[i]]+=1;
        }
        Arrays.fill(nums, 0, colorCnt[0], 0);
        Arrays.fill(nums, colorCnt[0], colorCnt[0]+colorCnt[1], 1);
        Arrays.fill(nums, colorCnt[0]+colorCnt[1], colorCnt[0]+colorCnt[1]+colorCnt[2], 2);

    }

    public static void main(String[] args) {
        DoubleCursor doubleCursor = new DoubleCursor();
//        doubleCursor.nextPermutation(new int[]{1,3,2});
//        doubleCursor.nextPermutation(new int[]{2,3, 1});
//        doubleCursor.nextPermutation(new int[]{1,2});
//        doubleCursor.merge(new int[]{1,2,3,3,4, 0,0}, 5, new int[]{2,4}, 2);
//        doubleCursor.moveZeroes(new int[]{0, 1, 0, 3, 12});
        doubleCursor.intersection(new int[]{1, 2,2,1}, new int[]{2,2});

        System.out.printf("");
    }

}
