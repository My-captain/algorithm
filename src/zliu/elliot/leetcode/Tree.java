package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tree {

    /**
     * 剑指 Offer 55 - I. 二叉树的深度
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }

    int maxDiameter = 0;

    /**
     * 543. 二叉树的直径
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        depthFirst(root);
        return maxDiameter;
    }

    public int depthFirst(TreeNode node) {
        // DFS的过程中记录以当前node为root的最大路径, 避免遍历后再度搜索
        if (node == null) {
            return 0;
        } else {
            int left = depthFirst(node.left);
            int right = depthFirst(node.right);
            maxDiameter = Math.max(maxDiameter, left + right);
            return Math.max(left, right) + 1;
        }
    }

    /**
     * 144. 二叉树的前序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalRecurrent(TreeNode root) {

        ArrayList<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode current = null;
        while (!stack.empty() || current != null){
            if (current == null) {
                current = stack.pop();
            }
            res.add(current.val);
            if (current.right != null ){
                stack.push(current.right);
            }
            current = current.left;
        }
        return res;
    }

    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        ArrayList<Integer> preorder = new ArrayList<>();
        preorderRecursive(root, preorder);
        return preorder;
    }

    public void preorderRecursive(TreeNode node, List<Integer> preorder) {
        if (node != null) {
            preorder.add(node.val);
            preorderRecursive(node.left, preorder);
            preorderRecursive(node.right, preorder);
        }
    }

}