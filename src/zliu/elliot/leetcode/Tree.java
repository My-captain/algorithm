package zliu.elliot.leetcode;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

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

    /**
     * 102. 二叉树的层序遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayList<Integer> currentLine;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentLine = new ArrayList<>();
            int lineLen = queue.size();
            for (int i = 0; i < lineLen; ++i) {
                TreeNode node = queue.poll();
                currentLine.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(currentLine);
        }
        return result;
    }

    public void preorderRecursive(TreeNode node, List<Integer> preorder) {
        if (node != null) {
            preorder.add(node.val);
            preorderRecursive(node.left, preorder);
            preorderRecursive(node.right, preorder);
        }
    }

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

    TreeNode lowestCommonAncestor = null;
    /**
     * 236. 二叉树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        postOrderLowestCommonAncestor(root, p, q);
        return this.lowestCommonAncestor;
    }

    public boolean postOrderLowestCommonAncestor(TreeNode currentNode, TreeNode p, TreeNode q) {
        if (this.lowestCommonAncestor != null) {
            // 剪枝
            return false;
        }
        boolean left = false, right = false;
        if (currentNode.left != null) {
            left = postOrderLowestCommonAncestor(currentNode.left, p, q);
        }
        if (this.lowestCommonAncestor != null) {
            // 剪枝
            return false;
        }
        if (currentNode.right != null) {
            right = postOrderLowestCommonAncestor(currentNode.right, p, q);
        }
        if (this.lowestCommonAncestor != null) {
            // 剪枝
            return false;
        }
        if ((left && right) || ((left || right) && (currentNode == p || currentNode == q))) {
            this.lowestCommonAncestor = currentNode;
        }
        if (this.lowestCommonAncestor != null) {
            // 剪枝
            return false;
        }
        return left || right || currentNode == p || currentNode == q;
    }

    /**
     * 94. 二叉树的中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root != null) {
            recursiveInorder(root, res);
        }
        return res;
    }

    public void recursiveInorder(TreeNode node, List<Integer> res) {
        if (node.left != null) {
            recursiveInorder(node.left, res);
        }
        res.add(node.val);
        if (node.right != null) {
            recursiveInorder(node.right, res);
        }
    }

    /**
     * 110. 平衡二叉树
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        int summary = dfsBalanced(root);
        return summary < 0;
    }

    public int dfsBalanced(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int l = dfsBalanced(root.left);
            if (l < 0) {
                return l;
            }
            int r = dfsBalanced(root.right);
            if (r < 0) {
                return r;
            }
            return Math.abs(l-r) <= 1 ? Math.max(l, r) +1 : -1;
        }
    }

    /**
     * 101. 对称二叉树
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkSymmetric(root.left, root.right);
    }

    public boolean checkSymmetric(TreeNode l, TreeNode r) {
        if (l == null || r == null) {
            return l == r;
        } else if (l.val != r.val) {
            return false;
        }
        return checkSymmetric(l.left, r.right) && checkSymmetric(l.right, r.left);
    }

    int sumTreeNumbers = 0;
    /**
     * 129. 求根节点到叶节点数字之和
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        if (root != null) {
            StringBuffer sb = new StringBuffer();
            recursiveSumTree(root, sb);
        }
        return sumTreeNumbers;
    }

    public void recursiveSumTree(TreeNode node, StringBuffer sb) {
        sb.append(node.val);
        if (node.left == null && node.right == null) {
            sumTreeNumbers += Integer.parseInt(sb.toString());
        } else {
            if (node.left != null) {
                recursiveSumTree(node.left, sb);
            }
            if (node.right != null ) {
                recursiveSumTree(node.right, sb);
            }
        }
        sb.deleteCharAt(sb.length()-1);
    }



    public static void main(String[] args) {
        TreeNode a = new TreeNode(3);
        TreeNode b = new TreeNode(9);
        TreeNode c = new TreeNode(20);
        TreeNode d = new TreeNode(15);
        TreeNode e = new TreeNode(7);
        a.left = b;a.right=c;c.left = d;c.right=e;
        Tree tree = new Tree();tree.isSymmetric(a);
    }

}
