package zliu.elliot.newcoder;

import zliu.elliot.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.Collections;

public class Tree {


    /**
     * JZ34 二叉树中和为某一值的路径(二)
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int expectNumber) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        ArrayList<Integer> pathMem = new ArrayList<>();
        PreOrderFindPath(root, 0, expectNumber, pathMem, res);
        return res;
    }

    public void PreOrderFindPath(TreeNode currentNode, int currentSum, int expectNumber, ArrayList<Integer> mem, ArrayList<ArrayList<Integer>> res) {
        if (currentNode.left ==null && currentNode.right == null) {
            // 叶结点
            if (currentSum + currentNode.val == expectNumber) {
                mem.add(currentNode.val);
                ArrayList<Integer> list = new ArrayList<>(mem);
                res.add(list);
                mem.remove(mem.size()-1);
            }
        } else {
            // 非叶结点
            mem.add(currentNode.val);
            if (currentNode.left != null) {
                PreOrderFindPath(currentNode.left, currentSum + currentNode.val, expectNumber, mem, res);
            }
            if (currentNode.right != null){
                PreOrderFindPath(currentNode.right, currentSum + currentNode.val, expectNumber, mem, res);
            }
            mem.remove(mem.size()-1);
        }
    }

    public static void main(String[] args) {
    }

}
