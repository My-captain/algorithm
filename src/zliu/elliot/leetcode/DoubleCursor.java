package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preNode = head, targetNode = head, tailNode = head;
        for (int i = 0; i < n-1; ++i) {
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
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int right = nums.length -1,left=nums.length-2;
        while (right > 0) {
            while (left>=0){
                if (nums[left] < nums[right]) {
                    int temp = nums[right];
                    for (int i = right-1; i >= left; --i) {
                        nums[i+1] = nums[i];
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
            left = right-1;
        }
        Arrays.sort(nums);
    }

    public static void main(String[] args) {
        DoubleCursor doubleCursor = new DoubleCursor();
//        doubleCursor.nextPermutation(new int[]{1,3,2});
        doubleCursor.nextPermutation(new int[]{2,3, 1});
        doubleCursor.nextPermutation(new int[]{1,2});

        System.out.printf("");
    }

}
