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








    public static void main(String[] args) {

    }

}
