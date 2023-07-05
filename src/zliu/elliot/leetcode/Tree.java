package zliu.elliot.leetcode;

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

    /**
     * 199. 二叉树的右视图
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (queue.size() > 0) {
            int size = queue.size();
            TreeNode current = null;
            for (int i = 0; i < size; ++i) {
                current = queue.pollFirst();
                if (current.left != null) {
                    queue.offerLast(current.left);
                }
                if (current.right != null) {
                    queue.offerLast(current.right);
                }
            }
            res.add(current.val);
        }
        return res;
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
//        postOrderLowestCommonAncestor(root, p, q);
//        return this.lowestCommonAncestor;
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * 235. 二叉搜索树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorII(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorII(root.right, p, q);
        }
        return root;
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

    /**
     * 112. 路径总和
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return dfsPathSum(root, 0, targetSum);
    }
    private boolean dfsPathSum(TreeNode node, int currentSum, int targetSum) {
        if (node.left == null && node.right == null) {
            return (currentSum+node.val ) == targetSum;
        }
        if (node.left != null && dfsPathSum(node.left, currentSum+node.val, targetSum)) {
            return true;
        }
        if (node.right != null && dfsPathSum(node.right, currentSum+node.val, targetSum)) {
            return true;
        }
        return false;
    }

    List<List<Integer>> res = new ArrayList<>();
    /**
     * 113. 路径总和 II
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ArrayList<Integer> pathMem = new ArrayList<>();
        if (root == null) {
            return res;
        }
        dfsPathSumSearch(root, 0, targetSum, pathMem);
        return res;
    }
    private void dfsPathSumSearch(TreeNode node, int currentSum, int targetSum, List<Integer> memory) {
        memory.add(node.val);
        if (node.left == null && node.right == null) {
            // leaf node
            if (currentSum + node.val == targetSum) {
                this.res.add(new ArrayList<>(memory));
            }
        } else {
            if (node.left != null) {
                dfsPathSumSearch(node.left, currentSum + node.val, targetSum, memory);
            }
            if (node.right != null ){
                dfsPathSumSearch(node.right, currentSum + node.val, targetSum, memory);
            }
        }
        memory.remove(memory.size()-1);
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return dfsValidateBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }
    }

    int prev = Integer.MIN_VALUE;
    public boolean isValidBSTII(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            if (!isValidBSTII(root.left)) {
                return false;
            }
            if (prev >= root.val) {
                return false;
            }
            prev = root.val;
            return isValidBSTII(root.right);
        }
    }

    private boolean dfsValidateBST(TreeNode node, long lower, long ceil) {
        if (node.val <= lower || node.val >= ceil) {
            return false;
        } else {
            if (node.left != null && !dfsValidateBST(node.left, lower, node.val)) {
                return false;
            }
            if (node.right != null && !dfsValidateBST(node.right, node.val, ceil)) {
                return false;
            }
            return true;
        }
    }

    /**
     * 226. 翻转二叉树
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        } else {
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            invertTree(root.left);
            invertTree(root.right);
            return root;
        }
    }

    /**
     * 100. 相同的树
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q;
        } else {
            return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    /**
     * 513. 找树左下角的值
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int result = -1;
        queue.offer(root);
        while (true) {
            result = queue.peek().val;
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            if (queue.size() < 1) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        a.left = b;a.right=c;
        Tree tree = new Tree();tree.pathSum(a, 4);
    }

}
