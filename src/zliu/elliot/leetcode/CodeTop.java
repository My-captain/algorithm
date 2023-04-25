package zliu.elliot.leetcode;

import java.util.*;

public class CodeTop {

    /**
     * 215. 数组中的第K个最大元素
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        return -1;
    }

    /**
     * 54. 螺旋矩阵
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int rowS = 0, rowE = matrix.length - 1, colS = 0, colE = matrix[0].length - 1;
        int sum = (rowE + 1) * (colE + 1);
        while (colE >= colS && rowE >= rowS) {
            for (int col = colS; col <= colE; ++col) {
                result.add(matrix[rowS][col]);
            }
            ++rowS;
            if (result.size() == sum) {
                break;
            }
            for (int row = rowS; row <= rowE; ++row) {
                result.add(matrix[row][colE]);

            }
            if (result.size() == sum) {
                break;
            }
            --colE;
            for (int col = colE; col >= colS; --col) {
                result.add(matrix[rowE][col]);
            }
            if (result.size() == sum) {
                break;
            }
            --rowE;
            for (int row = rowE; row >= rowS; --row) {
                result.add(matrix[row][colS]);

            }
            if (result.size() == sum) {
                break;
            }
            ++colS;
        }
        return result;
    }

    /**
     * 415. 字符串相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1, add = 0;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 || j >= 0 || add != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;
            int result = x + y + add;
            ans.append(result % 10);
            add = result / 10;
            i--;
            j--;
        }
        ans.reverse();
        return ans.toString();
    }

    public static void main(String[] args) {
        CodeTop codeTop = new CodeTop();
//        codeTop.spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}});
        System.out.println(codeTop.addStrings("123", "4577"));
    }

}
