package zliu.elliot.leetcode;

public class Recursion {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {this.val = val;}
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    int maxPathSln = Integer.MIN_VALUE;
    /**
     * 124. 二叉树中的最大路径和
     * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
     * 同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     * 路径和 是路径中各节点值的总和。给你一个二叉树的根节点 root ，返回其 最大路径和 。
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxPathSln;
    }
    public int dfs(TreeNode root) {
        if (root == null) return 0;
        int l = Math.max(0, dfs(root.left)), r = Math.max(0, dfs(root.right));
        maxPathSln = Math.max(maxPathSln, l+root.val+r);
        return Math.max(l, r) + root.val;
    }

}
