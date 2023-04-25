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

    /**
     * 394. 字符串解码
     * @param s
     * @return
     */
    public String decodeString(String s) {
        if (s.length() < 1) {
            return "";
        }
        StringBuffer result = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c <= '9' && c > '0') {
                i = parseZipStr(s, i, result);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 递归解析被压缩的子串
     * @param s 原串
     * @param cursor 子串的startIndex
     * @param sb 上层传来的StringBuffer
     * @return 解析完子串后的原串下标索引
     */
    public int parseZipStr(String s, int cursor, StringBuffer sb) {
        int iStart = s.indexOf('[', cursor + 1);
        int cnt = Integer.parseInt(s.substring(cursor, iStart));
        StringBuffer currentResult = new StringBuffer();
        int i = iStart+1;
        for (; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == ']') {
                // 此段匹配结束
                break;
            } else if (c <= '9' && c > '0') {
                // 嵌套
                i = parseZipStr(s, i, currentResult);
            } else {
                // 普通
                currentResult.append(s.charAt(i));
            }
        }
        for (int j = 0; j < cnt; ++j) {
            sb.append(currentResult);
        }
        return i;
    }

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        System.out.println(recursion.decodeString("z1[abc 2[ def3[ ghi]]]"));
        System.out.println(recursion.decodeString("1[]"));
    }

}
