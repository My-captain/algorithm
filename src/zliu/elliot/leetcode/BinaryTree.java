package zliu.elliot.leetcode;

import java.util.*;

public class BinaryTree {

    /**
     * 103. 二叉树的锯齿形层序遍历
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) {
            return result;
        }
        queue.add(root);
        byte order = 0x11;
        while (queue.size() > 0) {
            ArrayList<Integer> line = new ArrayList<>();
            result.add(line);
            int size = queue.size();
            for (int j = 0; j < size; ++j) {
                TreeNode node = queue.poll();
                line.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (order == 0x00) {
                Collections.reverse(line);
                order = 0x11;
            } else {
                order = 0x00;
            }
        }
        return result;
    }

    /**
     * 56. 合并区间
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int num = intervals.length;
        if (num < 2) {
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });
        ArrayList<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for (int i = 1; i < num; ++i) {
            int[] last = res.get(res.size() - 1);
            int[] current = intervals[i];
            if (last[1] >= current[0]) {
                last[1] = Math.max(current[1], last[1]);
            } else {
                res.add(current);
            }
        }
        return res.toArray(new int[0][]);
    }

    public static void main(String[] args) {

        String s = UUID.randomUUID().toString().replace('-', ' ');
        System.out.printf(s);

    }




















}
