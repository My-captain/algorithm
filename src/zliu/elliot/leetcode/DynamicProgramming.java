package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {

    /**
     * 322. 零钱兑换
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int subAmount = 1; subAmount <= amount; ++subAmount) {
            dp[subAmount] = -1;
            int currentMin = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (coin > subAmount){
                    break;
                }
                if (dp[subAmount-coin] == -1) {
                    continue;
                }
                currentMin = Math.min(currentMin, dp[subAmount-coin]+1);
                dp[subAmount] = currentMin;
            }

        }
        return dp[amount];
    }

    /**
     * 509. 斐波那契数
     * @param n
     * @return
     */
    public int fib(int n) {
        int[] prev = new int[]{0, 1};
        if (n < 2) {
            return prev[n];
        } else {
            int temp = 0;
            for (int i = 2; i <= n; ++i) {
                temp = prev[0] + prev[1];
                prev[0] = prev[1];
                prev[1] = temp;
            }
            return temp;
        }
    }

    /**
     * 64. 最小路径和
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
        for (int rowIdx = row - 1; rowIdx >= 0; rowIdx--) {
            for (int colIdx = col - 1; colIdx >= 0; colIdx--) {
                if (rowIdx == row - 1 && colIdx == col - 1) {
                    // 终点🏁
                    dp[rowIdx][colIdx] = grid[rowIdx][colIdx];
                    continue;
                }
                if (rowIdx == row - 1) {
                    // 最后一行
                    dp[rowIdx][colIdx] = grid[rowIdx][colIdx] + dp[rowIdx][colIdx + 1];
                    continue;
                }
                if (colIdx == col - 1) {
                    // 最后一列
                    dp[rowIdx][colIdx] = grid[rowIdx][colIdx] + dp[rowIdx + 1][colIdx];
                    continue;
                }
                dp[rowIdx][colIdx] = Math.min(dp[rowIdx + 1][colIdx], dp[rowIdx][colIdx + 1]);
                dp[rowIdx][colIdx] += grid[rowIdx][colIdx];
            }
        }
        return dp[0][0];
    }

    /**
     * 120. 三角形最小路径和
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int height = triangle.size();
        for (int rowIdx = height - 2; rowIdx >= 0; rowIdx--) {
            List<Integer> footRow = triangle.get(rowIdx + 1);
            List<Integer> row = triangle.get(rowIdx);
            for (int colIdx = 0; colIdx < row.size(); colIdx++) {
                row.set(colIdx, Math.min(footRow.get(colIdx), footRow.get(colIdx + 1)) + row.get(colIdx));
            }
        }
        return triangle.get(0).get(0);
    }

    /**
     * 一些恶魔抓住了公主（P）并将她关在了地下城的右下角。地下城是由M x N 个房间组成的二维网格。
     * 我们英勇的骑士（K）最初被安置在左上角的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。
     * 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
     * <p>
     * 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；
     * 其他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
     * <p>
     * 为了尽快到达公主，骑士决定每次只向右或向下移动一步。
     * 编写一个函数来计算确保骑士能够拯救到公主所需的最低初始健康点数。
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row][col];

        for (int rowIdx = row - 1; rowIdx >= 0; rowIdx--) {
            for (int colIdx = col - 1; colIdx >= 0; colIdx--) {
                if (rowIdx == row - 1 && colIdx == col - 1) {
                    // 终点🏁
                    dp[rowIdx][colIdx] = Math.max(1 - dungeon[rowIdx][colIdx], 1);
                    continue;
                }
                if (rowIdx == row - 1) {
                    // 最后一行
                    dp[rowIdx][colIdx] = Math.max(dp[rowIdx][colIdx + 1] - dungeon[rowIdx][colIdx], 1);
                    continue;
                }
                if (colIdx == col - 1) {
                    // 最后一列
                    dp[rowIdx][colIdx] = Math.max(dp[rowIdx + 1][colIdx] - dungeon[rowIdx][colIdx], 1);
                    continue;
                }
                dp[rowIdx][colIdx] = Math.min(dp[rowIdx + 1][colIdx], dp[rowIdx][colIdx + 1]);
                dp[rowIdx][colIdx] = Math.max(dp[rowIdx][colIdx] - dungeon[rowIdx][colIdx], 1);
            }
        }
        return dp[0][0];
    }

    /**
     * 221.在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        int maxEdge = 0;
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        for (int rowIdx = 0; rowIdx < row; rowIdx++) {
            for (int colIdx = 0; colIdx < col; colIdx++) {
                if (rowIdx == 0 || colIdx == 0) {
                    if (matrix[rowIdx][colIdx] == '1') {
                        maxEdge = Math.max(maxEdge, 1);
                        dp[rowIdx][colIdx] = 1;
                    } else {
                        dp[rowIdx][colIdx] = 0;
                    }
                } else if (matrix[rowIdx][colIdx] == '1') {
                    dp[rowIdx][colIdx] = Math.min(Math.min(dp[rowIdx - 1][colIdx - 1], dp[rowIdx - 1][colIdx]), dp[rowIdx][colIdx - 1]) + 1;
                    maxEdge = Math.max(maxEdge, dp[rowIdx][colIdx]);
                }
            }
        }
        return maxEdge * maxEdge;
    }

    /**
     * 给你一个 m * n 的矩阵，矩阵中的元素不是 0 就是 1，请你统计并返回其中完全由 1 组成的 正方形 子矩阵的个数。
     *
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        int squareCnt = 0;
        for (int rowIdx = 0; rowIdx < row; rowIdx++) {
            for (int colIdx = 0; colIdx < col; colIdx++) {
                if (rowIdx == 0 || colIdx == 0) {
                    dp[rowIdx][colIdx] = matrix[rowIdx][colIdx];
                    squareCnt += dp[rowIdx][colIdx];
                } else if (matrix[rowIdx][colIdx] == 1) {
                    int temp = dp[rowIdx][colIdx] = Math.min(Math.min(dp[rowIdx - 1][colIdx - 1], dp[rowIdx - 1][colIdx]), dp[rowIdx][colIdx - 1]) + 1;
                    if (temp > 0) {
                        squareCnt += temp == 1 ? 1 : 2;
                    }
                }
            }
        }
        return squareCnt;
    }

    /**
     * 279. 完全平方数
     * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                min = Math.min(min, dp[i - j * j]);
            }
            dp[i] = min+1;
        }
        return dp[n];
    }

    /**
     * 70. 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        if (n==1){
            return 1;
        }
        if (n==2){
            return 2;
        }
        int temp1 = 1, temp2=2, temp3=0;
        for (int i = 2; i < n; ++i) {
            temp3 = temp2+temp1;
            temp1 = temp2;
            temp2 = temp3;
        }
        return temp2;
    }

    public int rob(int[] nums) {
        int len = nums.length;
        int[] f = new int[len+2];
        f[0] = f[1] = 0;
        f[2] = nums[0];
        for (int i = 1; i < len; ++i) {
            f[i+2] = Math.max(f[i-1], f[i]) + nums[i];
        }
        return Math.max(f[len+1], f[len]);
    }

    /**
     * 118. 杨辉三角
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; ++i) {
            if (i <= 1) {
                ArrayList<Integer> row = new ArrayList<>();
//                row.add();
                res.add(row);
            } else {

            }
        }
        return null;
    }

    /**
     * 53. 最大子数组和
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; ++i) {
            if (i == 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = Math.max(dp[i-1]+nums[i], nums[i]);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 300. 最长递增子序列
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length < 1) {
            return 0;
        }
        // dp[i]表示当前最长递增子序列长度
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; ++i) {
            dp[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int profit = 0;
        if (prices.length == 0) {
            return profit;
        }

        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        for (int i = 1; i < prices.length; ++i) {
            dp[i] = Math.min(dp[i-1], prices[i]);
            profit = Math.max(prices[i] - dp[i-1], profit);
        }
        return profit;
    }

    /**
     * 1143. 最长公共子序列
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() < 1 || text2.length() < 1) {
            return 0;
        }
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1][len2];
        dp[0][0] = text1.charAt(0) == text2.charAt(0) ? 1 : 0;
        for (int row = 1; row < len1; ++row) {
            dp[row][0] = text1.charAt(row) == text2.charAt(0) ? 1 : dp[row-1][0];
        }
        for (int col = 1; col < len2; ++col) {
            dp[0][col] = text1.charAt(0) == text2.charAt(col) ? 1 : dp[0][col-1];
        }
        for (int row = 1; row < len1; ++row) {
            for (int col = 1; col < len2; ++col) {
                dp[row][col] = text1.charAt(row) == text2.charAt(col) ? (dp[row - 1][col - 1] + 1) : Math.max(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        return dp[len1 - 1][len2 - 1];
    }

    /**
     * 122. 买卖股票的最佳时机 II
     *
     * @param prices
     * @return
     */
    public int maxProfitII(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i][1]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * 123. 买卖股票的最佳时机 III
     *
     * @param prices
     * @return
     */
    public int maxProfitIII(int[] prices) {
        int[][] dp = new int[prices.length][3];
        return -1;
    }

    /**
     * 42. 接雨水
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int len = height.length;
        int[][] margin = new int[len][2];
        margin[0][0] = height[0];
        margin[len - 1][1] = height[len - 1];
        for (int i = 1; i < len; ++i) {
            margin[i][0] = Math.max(margin[i - 1][0], height[i]);
        }
        for (int i = len - 2; i >= 0; --i) {
            margin[i][1] = Math.max(margin[i + 1][1], height[i]);
        }
        int ans = 0;
        for (int i = 1; i < len - 1; ++i) {
            ans += Math.min(margin[i][0], margin[i][1]) - height[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        dp.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18});
    }

}
