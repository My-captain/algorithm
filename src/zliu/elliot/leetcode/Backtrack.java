package zliu.elliot.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class Backtrack {

    /**
     * 46. 全排列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        backtrack(0, nums, results);
        return results;
    }

    public void backtrack(int i, int[] nums, List<List<Integer>> result) {
        if (i == nums.length - 1) {
            List<Integer> permute = Arrays.stream(nums).boxed().collect(Collectors.toList());
            result.add(permute);
            return;
        }
        for (int j = i; j < nums.length; ++j) {

            // 将被选中的j,交换到索引i位置
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;

            backtrack(i + 1, nums, result);

            // 将j位置和i位置换回
            temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * 51. N 皇后
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        int[] chooseColumn = new int[n];
        ArrayList<int[]> results = new ArrayList<>();
        backtrackQueens(chooseColumn, 0, n, results);
        List<List<String>> res = new ArrayList<>();

        char[] chars = new char[n];
        Arrays.fill(chars, '.');
        for (int i = 0; i < results.size(); ++i) {
            ArrayList<String> resi = new ArrayList<>();
            res.add(resi);
            int[] cols = results.get(i);
            for (int j = 0; j < cols.length; ++j) {
                chars[cols[j]] = 'Q';
                resi.add(new String(chars));
                chars[cols[j]] = '.';
            }
        }
        return res;
    }

    /**
     * @param chooseCol 已经被选中的column
     * @param current   当前待放置的queen index
     * @param n         queen个数
     * @param results
     */
    public void backtrackQueens(int[] chooseCol, int current, int n, ArrayList<int[]> results) {
        if (current == n) {
            results.add(Arrays.copyOf(chooseCol, chooseCol.length));
            return;
        }
        // 根据以往queen位置,生成当前被禁止的位置
        boolean[] banned = new boolean[n];
//        Arrays.fill(banned, false);
        for (int i = 0; i < current; ++i) {
            int queenLoc = chooseCol[i];
            banned[queenLoc] = true;
            int distance = current - i;
            if (queenLoc - distance >= 0) {
                banned[queenLoc - distance] = true;
            }
            if (queenLoc + distance < n) {
                banned[queenLoc + distance] = true;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (!banned[i]) {
                chooseCol[current] = i;
                backtrackQueens(chooseCol, current + 1, n, results);
            }
        }
    }

    /**
     * 52. N 皇后 II
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        int[] chooseColumn = new int[n];
        return backtrackQueenCnt(chooseColumn, 0, n);
    }

    public int backtrackQueenCnt(int[] chooseCol, int current, int n) {
        if (current == n) {
            return 1;
        }
        int solutions = 0;
        // 根据以往queen位置,生成当前被禁止的位置
        boolean[] banned = new boolean[n];
        for (int i = 0; i < current; ++i) {
            int queenLoc = chooseCol[i];
            banned[queenLoc] = true;
            int distance = current - i;
            if (queenLoc - distance >= 0) {
                banned[queenLoc - distance] = true;
            }
            if (queenLoc + distance < n) {
                banned[queenLoc + distance] = true;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (!banned[i]) {
                chooseCol[current] = i;
                solutions += backtrackQueenCnt(chooseCol, current + 1, n);
            }
        }
        return solutions;
    }

    /**
     * 93. 复原 IP 地址
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        char[] searchMemory = new char[2 * n];
        backtrackParenthesis(result, 0, 0, n, searchMemory);
        return result;
    }

    public void backtrackParenthesis(List<String> res, int push, int pop, int n, char[] currentResult) {
        if (push + pop == 2 * n) {
            res.add(new String(currentResult));
        } else {
            if (push > pop && push < n) {
                // 两种选择的场景
                currentResult[push + pop] = '(';
                backtrackParenthesis(res, push + 1, pop, n, currentResult);
                currentResult[push + pop] = ')';
                backtrackParenthesis(res, push, pop + 1, n, currentResult);
            } else if (push == pop) {
                // 只能左括号
                currentResult[push + pop] = '(';
                backtrackParenthesis(res, push + 1, pop, n, currentResult);
            } else if (push == n) {
                // 只能右括号
                currentResult[push + pop] = ')';
                backtrackParenthesis(res, push, pop + 1, n, currentResult);
            }
        }

    }

    int[] currentNum = null;
    List<List<Integer>> res = null;
    boolean[] choose = null;

    public List<List<Integer>> subsets(int[] nums) {
        this.currentNum = nums;
        this.res = new ArrayList<>();
        this.choose = new boolean[nums.length];
        backtrackSubset(0);
        return this.res;
    }

    public List<Integer> generateSubset() {
        ArrayList<Integer> subset = new ArrayList<>();
        for (int i = 0; i < this.choose.length; ++i) {
            if (this.choose[i]) {
                subset.add(this.currentNum[i]);
            }
        }
        return subset;
    }

    public void backtrackSubset(int current) {
        if (current == choose.length) {
            this.res.add(generateSubset());
        } else {
            choose[current] = true;
            backtrackSubset(current + 1);
            choose[current] = false;
            backtrackSubset(current + 1);
        }
    }

    List<String> ipAddr = new ArrayList<>();

    /**
     * 93. 复原 IP 地址
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        if (s.length() < 4) {
            return this.ipAddr;
        }
        int[] segmentLen = new int[4];
        backtrackRestoreIp(segmentLen, 0, 0, s);
        return this.ipAddr;
    }

    public void backtrackRestoreIp(int[] segmentLen, int current, int sumLen, String s) {
        if (current == 4) {
            if (sumLen != s.length()) {
                // 不合法方案
                return;
            } else {
                // 验证合法性
                int[] ipAddr = new int[4];
                int start = 0;
                for (int i = 0; i < 4; ++i) {
                    ipAddr[i] = Integer.parseInt(s.substring(start, start + segmentLen[i]));
                    if (ipAddr[i] > 255) {
                        return;
                    }
                    start += segmentLen[i];
                }
                this.ipAddr.add(String.format("%s.%s.%s.%s", ipAddr[0], ipAddr[1], ipAddr[2], ipAddr[3]));
            }
        } else {
            if (s.charAt(sumLen) == '0') {
                segmentLen[current] = 1;
                backtrackRestoreIp(segmentLen, current + 1, sumLen + 1, s);
            } else {
                for (int i = 1; i < 4; ++i) {
                    if (s.length() - sumLen - i < 3 - current) {
                        // 已经超过长度
                        break;
                    }
                    segmentLen[current] = i;
                    backtrackRestoreIp(segmentLen, current + 1, sumLen + i, s);
                }
            }
        }
    }

    public List<String> letterCombinations(String digits) {
//        new LinkedList<>()
        String[] strings = new String[]{"abc", "", ""};
        HashMap<Integer, String> kStr = new HashMap<Integer, String>();
        kStr.put(2, "abc");
        return null;
    }

    List<List<Integer>> sumCombination = null;

    /**
     * 39. 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 排序, 方便剪枝
        Arrays.sort(candidates);
        this.sumCombination = new ArrayList<>();
        int[] chosen = new int[target / candidates[0] + 1];
        dfsCombinationSum(candidates, target, 0, 0, 0, chosen);
        return this.sumCombination;
    }

    /**
     * @param candidates
     * @param target
     * @param sum           已被选择的数字,对其进行求和
     * @param current       当前需要考虑选择的candidates下标
     * @param currentChoose 当前已经选择的数字
     * @param chosen        当前方案
     */
    public void dfsCombinationSum(int[] candidates, int target, int sum, int current, int currentChoose, int[] chosen) {
        if (sum == target) {
            ArrayList<Integer> result = new ArrayList<>();
            this.sumCombination.add(result);
            for (int i = 0; i < currentChoose; ++i) {
                result.add(chosen[i]);
            }
            return;
        }
        if (current > candidates.length - 1 || sum > target || target - sum < candidates[current]) {
            // 当前考虑的项目已超出数组
            // 已超出target
            // 剩余的数字，凑不齐
            return;
        }
        int maxRepeat = (target - sum) / candidates[current] + 1;
        for (int i = 0; i <= maxRepeat; ++i) {
            if (i > 0) {
                chosen[currentChoose] = candidates[current];
                currentChoose += 1;
            }
            dfsCombinationSum(candidates, target, sum + i * candidates[current], current + 1, currentChoose, chosen);
        }
    }

    /**
     * 79. 单词搜索
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        byte[][] visited = new byte[board.length][board[0].length];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (dfsExist(board, word, 0, i, j, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfsExist(char[][] board, String word, int currentSearch, int xLoc, int yLoc, byte[][] visited) {
        System.out.printf("%s,%s\n", xLoc, yLoc);
        if (visited[xLoc][yLoc] == 0x00 && board[xLoc][yLoc] == word.charAt(currentSearch)) {
            // 未被访问过并且可以继续搜索
            if (currentSearch == word.length() - 1) {
                // 已经搜完
                return true;
            } else {
                visited[xLoc][yLoc] = 0x11;
                if (xLoc - 1 >= 0) {
                    if (dfsExist(board, word, currentSearch + 1, xLoc - 1, yLoc, visited)) {
                        return true;
                    }
                }
                if (xLoc + 1 < board.length) {
                    if (dfsExist(board, word, currentSearch + 1, xLoc + 1, yLoc, visited)) {
                        return true;
                    }
                }
                if (yLoc - 1 >= 0) {
                    if (dfsExist(board, word, currentSearch + 1, xLoc, yLoc - 1, visited)) {
                        return true;
                    }
                }
                if (yLoc + 1 < board[0].length) {
                    if (dfsExist(board, word, currentSearch + 1, xLoc, yLoc + 1, visited)) {
                        return true;
                    }
                }
                visited[xLoc][yLoc] = 0x00;
            }
        }
        return false;
    }

    /**
     * 47. 全排列 II
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        dfsPermuteUnique(nums, 0, result);
        return result;
    }

    public void dfsPermuteUnique(int[] nums, int current, List<List<Integer>> result){
        if (current == nums.length) {
            result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }
        for (int idx = current; idx < nums.length; ++idx) {
            if (idx > 0 && nums[idx] == nums[idx-1]) {
                dfsPermuteUnique(nums, current+1, result);
                continue;
            }

            int temp = nums[current];
            nums[current] = nums[idx];
            nums[idx] = temp;
            dfsPermuteUnique(nums, current+1, result);

            temp = nums[current];
            nums[current] = nums[idx];
            nums[idx] = temp;
        }
    }

    /**
     * 200. 岛屿数量
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int cnt = 0;
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[0].length; ++col) {
                if (grid[row][col] == '1') {
                    ++cnt;
                    dfsIslands(grid, row, col);
                }
            }
        }
        return cnt;
    }

    public void dfsIslands(char[][] grid, int xLoc, int yLoc) {
        if (grid[xLoc][yLoc] == '1') {
            grid[xLoc][yLoc] = '0';
            if (xLoc - 1 >= 0) {
                dfsIslands(grid, xLoc - 1, yLoc);
            }
            if (xLoc + 1 < grid.length) {
                dfsIslands(grid, xLoc + 1, yLoc);
            }
            if (yLoc - 1 >= 0) {
                dfsIslands(grid, xLoc, yLoc - 1);
            }
            if (yLoc + 1 < grid[0].length) {
                dfsIslands(grid, xLoc, yLoc + 1);
            }
        }
    }


    public static void main(String[] args) {
        Backtrack backtrack = new Backtrack();
//        backtrack.permute(new int[]{1, 2, 3});
//        backtrack.solveNQueens(4);
//        backtrack.totalNQueens(4);
//        backtrack.generateParenthesis(3);
//        backtrack.subsets(new int[]{1, 2, 3});
//        backtrack.restoreIpAddresses("101023");
//        backtrack.combinationSum(new int[]{2,3,6,7}, 7);
//        boolean exist = backtrack.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCB");
//        System.out.println(exist);
        backtrack.permuteUnique(new int[]{1,1,2});
    }

}
