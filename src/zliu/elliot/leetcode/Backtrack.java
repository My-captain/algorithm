package zliu.elliot.leetcode;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;

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
        ArrayList<Integer> permutation = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        // 让相同的数字相邻, 才得以去重
        Arrays.sort(nums);
        dfsPermuteUnique(nums, 0, result, permutation, visited);
        return result;
    }

    public void dfsPermuteUnique(int[] nums, int current, List<List<Integer>> result, List<Integer> permutation, boolean[] visited){
        if (current == nums.length) {
            result.add(new ArrayList<>(permutation));
            return;
        }
        for (int idx = 0; idx < nums.length; ++idx) {
            if (visited[idx]) {
                continue;
            }
            /*
		    用于去重：
		    1.对于相同的数字规定了访问的顺序, 必须前者已被访问, 自己才能被访问
		    2.当条件符合时, 说明nums[idx-1]这棵子树已经DFS完毕, 此时属于同层剪枝
   		    */
            if (idx > 0 && nums[idx] == nums[idx-1] && !visited[idx-1]) {
                continue;
            }
            permutation.add(nums[idx]);
            visited[idx] = true;
            dfsPermuteUnique(nums, current+1, result, permutation, visited);
            visited[idx] = false;
            permutation.remove(permutation.size()-1);
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

    /**
     * 40. 组合总和 II
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> permutation = new ArrayList<>();
        dfsCombinationSumII(candidates, 0, 0, target, new boolean[candidates.length], result, permutation);
        System.out.println(result);
        return result;
    }

    private void dfsCombinationSumII(int[] nums, int currentSum, int current, int target, boolean[] visited, List<List<Integer>> result, List<Integer> candidates) {
        if (currentSum == target) {
            result.add(new ArrayList<>(candidates));
            return;
        }
        if (current > nums.length - 1|| currentSum > target || currentSum+nums[current] > target) {
            return;
        }
        if (current > 0 && nums[current] == nums[current-1] && !visited[current-1]) {
            // 同层剪枝, 1_a、1_b、2、5、6、7
            // 1_a没选,1_b一定不能选
            dfsCombinationSumII(nums, currentSum, current+1, target, visited, result, candidates);
        } else {
            dfsCombinationSumII(nums, currentSum, current+1, target, visited, result, candidates);
            visited[current] = true;
            candidates.add(nums[current]);
            dfsCombinationSumII(nums, currentSum+nums[current], current+1, target, visited, result, candidates);
            visited[current] = false;
            candidates.remove(candidates.size()-1);
        }
    }

    /**
     * 77. 组合
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfsCombine(n, 1, k, result, combine);
        System.out.println(result);
        return result;
    }

    public void dfsCombine(int n, int current, int k, List<List<Integer>> result, List<Integer> combine) {
        if (combine.size() == k) {
            result.add(new ArrayList<>(combine));
            return;
        }
        if (current > n || (n - current + 1 + combine.size()) < k) {
            return;
        }
        combine.add(current);
        dfsCombine(n, current + 1, k, result, combine);
        combine.remove(combine.size() - 1);
        if (n-current+1+combine.size() == k) {
            // 此项必须加入
            return;
        }
        dfsCombine(n, current + 1, k, result, combine);
    }

    String[] ch2Letter = new String[] {"abc", "def","ghi","jkl","mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.length() < 1) {
            return result;
        }
        StringBuffer sb = new StringBuffer();
        dfsLetterCombine(0, digits, sb, result);
        return result;
    }

    public void dfsLetterCombine(int current, String digits, StringBuffer sb, List<String> result) {
        if (current == digits.length()) {
            result.add(sb.toString());
            return;
        }
        String digitsLetters = getDigitsLetters(digits.charAt(current));
        for (int i = 0; i < digitsLetters.length(); ++i) {
            sb.append(digitsLetters.charAt(i));
            dfsLetterCombine(current+1, digits, sb, result);
            sb.deleteCharAt(sb.length()-1);
        }
    }
    private String getDigitsLetters(char c) {
        return ch2Letter[c-'2'];
    }

    /**
     * 131. 分割回文串
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        List<String> partition = new ArrayList<>();
        dfsPartition(s, 0, partition, result);
        return result;
    }

    public void dfsPartition(String s, int current, List<String> partition, List<List<String>> result) {
        if (current == s.length()) {
            result.add(new ArrayList<>(partition));
        }
        for (int i = current; i < s.length(); ++i) {
            if (tenet(s, current, i)) {
                partition.add(s.substring(current, i+1));
                dfsPartition(s, i+1, partition, result);
                partition.remove(partition.size()-1);
            }
        }
    }

    public boolean tenet(String s, int start, int end) {
        if (end - start > 0) {
            int mid = (end-start) >> 1;
            for (int i = 0; i <= mid; ++i) {
                if (s.charAt(start+i) != s.charAt(end-i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 306. 累加数
     * @param num
     * @return
     */
    public boolean isAdditiveNumber(String num) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfsAdditiveNumber(num, 0, path, result);
        return result.size()>0;
    }

    public void dfsAdditiveNumber(String num, int current, List<Integer> path, List<List<Integer>> result) {
        if (current == num.length()) {
            if (path.size()>2){
                result.add(new ArrayList<>(path));
            }
            return;
        }
        if (num.charAt(current) == '0') {
            if (path.size() > 1 && (path.get(path.size()-1) != 0 || path.get(path.size()-2) != 0)) {
                return;
            }
            path.add(0);
            dfsAdditiveNumber(num, current+1, path, result);
            path.remove(path.size()-1);
        } else {
            for (int i = current; i < num.length(); ++i) {
                if (path.size()+1+num.length()-1-i < 3) {
                    // 已有数字+当前数字+剩余数字必须>=3
                    return;
                }
                int cursor = Integer.parseInt(num.substring(current, i + 1));
                if (path.size() > 1 && path.get(path.size()-1)+path.get(path.size()-2) != cursor) {
                    continue;
                }
                path.add(cursor);
                dfsAdditiveNumber(num, i+1, path, result);
                path.remove(path.size()-1);
            }
        }
    }

    public String stringAdd(String s, int firstStart, int firstEnd, int secondStart, int secondEnd) {
        StringBuffer third = new StringBuffer();
        int carry = 0, cur = 0;
        while (firstEnd >= firstStart || secondEnd >= secondStart || carry != 0) {
            cur = carry;
            if (firstEnd >= firstStart) {
                cur += s.charAt(firstEnd) - '0';
                --firstEnd;
            }
            if (secondEnd >= secondStart) {
                cur += s.charAt(secondEnd) - '0';
                --secondEnd;
            }
            carry = cur / 10;
            cur %= 10;
            third.append((char) (cur + '0'));
        }
        third.reverse();
        return third.toString();
    }

    int maxGold = 0;
    /**
     * 1219. 黄金矿工
     * @param grid
     * @return
     */
    public int getMaximumGold(int[][] grid) {
        if (grid.length < 1) {
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[0].length; ++col) {
                if (grid[row][col] > 0) {
                    visited[row][col] = true;
                    dfsMaxGold(row, col, grid[row][col], grid, visited);
                    visited[row][col] = false;
                }
            }
        }
        return maxGold;
    }

    public void dfsMaxGold(int currentRow, int currentCol, int sum, int[][] grid, boolean[][] visited) {
        this.maxGold = Math.max(this.maxGold, sum);
        if (currentRow - 1 >= 0 && !visited[currentRow-1][currentCol] && grid[currentRow-1][currentCol]>0) {
            visited[currentRow-1][currentCol] = true;
            dfsMaxGold(currentRow-1, currentCol, sum+grid[currentRow-1][currentCol], grid, visited);
            visited[currentRow-1][currentCol] = false;
        }
        if (currentRow + 1 < grid.length && !visited[currentRow+1][currentCol] && grid[currentRow+1][currentCol]>0) {
            visited[currentRow+1][currentCol] = true;
            dfsMaxGold(currentRow+1, currentCol, sum+grid[currentRow+1][currentCol], grid, visited);
            visited[currentRow+1][currentCol] = false;
        }
        if (currentCol - 1 >= 0 && !visited[currentRow][currentCol-1] && grid[currentRow][currentCol-1]>0) {
            visited[currentRow][currentCol-1] = true;
            dfsMaxGold(currentRow,currentCol-1, sum+grid[currentRow][currentCol-1], grid, visited);
            visited[currentRow][currentCol-1] = false;
        }
        if (currentCol + 1 < grid[0].length && !visited[currentRow][currentCol+1] && grid[currentRow][currentCol+1]>0) {
            visited[currentRow][currentCol+1] = true;
            dfsMaxGold(currentRow, currentCol+1, sum+grid[currentRow][currentCol+1], grid, visited);
            visited[currentRow][currentCol+1] = false;
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
//        backtrack.permuteUnique(new int[]{1,1,2});
//        backtrack.combinationSum2(new int[]{10,1,1,2,7,6,1,5}, 8);
//        backtrack.combine(10, 2);
//        System.out.println(backtrack.tenet("a"));
//        System.out.println(backtrack.tenet("aab"));
//        System.out.println(backtrack.tenet("aba"));
//        backtrack.partition("aab");
//        backtrack.isAdditiveNumber("1023");
        backtrack.getMaximumGold(new int[][]{{0,6,0},{5,8,7},{0,9,0}});
    }

}
