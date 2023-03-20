package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    /**
     * 93. 复原 IP 地址
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> res = new ArrayList<>();
        int[] ipSegLen = new int[4];
        backtrackIp(res, ipSegLen, s);
        return res;
    }

    public void backtrackIp(List<String> res, int[] ipSegLen, String s) {
//        if (Arrays.stream(ipSegLen).sum() < s.length()) {
//            for (int i = 0; i < 4; ++i) {
//                if (ipSegLen[i] < 3) {
//                    ipSegLen
//                    backtrackIp(res, ++ipSegLen[i], s);
//                }
//            }
//        }
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
            if (this.choose[i]){
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
            backtrackSubset(current+1);
            choose[current] = false;
            backtrackSubset(current+1);
        }
    }



    public static void main(String[] args) {
        Backtrack backtrack = new Backtrack();
//        backtrack.permute(new int[]{1, 2, 3});
//        backtrack.solveNQueens(4);
//        backtrack.totalNQueens(4);
//        backtrack.generateParenthesis(3);
        backtrack.subsets(new int[]{1, 2,3});
    }

}
