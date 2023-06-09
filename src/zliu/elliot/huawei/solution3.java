package zliu.elliot.huawei;

import java.util.Scanner;

public class solution3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 地图大小
        int n = scanner.nextInt();
        // 怪兽
        boolean[][] monster = new boolean[n][n];
        int k = scanner.nextInt();
        for (int i = 0; i < k; ++i) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            monster[r][c] = true;
        }
        // 公主位置
        int princessR = scanner.nextInt();
        int princessC = scanner.nextInt();
        // 王子
        int knightR = scanner.nextInt();
        int knightC = scanner.nextInt();
        String[][] carrier = new String[n][n];
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            carrier[i] = line.trim().split(" ");
        }

        solution3 solution3 = new solution3();
        solution3.carrier = carrier;
        solution3.monster = monster;
        solution3.princess = new int[]{princessR, princessC};
        solution3.dfsSave(knightR, knightC, 0, 1);
        System.out.println(solution3.minTime);
    }

    int minTime = Integer.MAX_VALUE;
    boolean[][] monster = null;
    String[][] carrier = null;
    int[] princess = null;

    /**
     *
     * @param r 当前行
     * @param c
     * @param timestamp 当前时间点
     */
    public void dfsSave(int r, int c, int timestamp, int wait) {
        if (timestamp>minTime) {
            // 超时剪枝
            return;
        }
        if (r == princess[0] && c == princess[1]) {
            // 到达目标
            minTime = Math.min(timestamp, minTime);
            return;
        }
        int nextCarrier = (timestamp+1)%3;
        if (r-1 >= 0 && !monster[r-1][c] && carrier[r-1][c].charAt(nextCarrier)=='0') {
            // 向上走
            dfsSave(r-1, c, timestamp+1, 1);
        }
        if (r+1 < monster.length && !monster[r+1][c] && carrier[r+1][c].charAt(nextCarrier)=='0') {
            // 向下走
            dfsSave(r+1, c, timestamp+1, 1);
        }
        if (c-1 >= 0 && !monster[r][c-1] && carrier[r][c-1].charAt(nextCarrier)=='0') {
            // 向左走
            dfsSave(r, c-1, timestamp+1, 1);
        }
        if (c+1 < monster.length && !monster[r][c+1] && carrier[r][c+1].charAt(nextCarrier)=='0') {
            // 向右走
            dfsSave(r, c+1, timestamp+1, 1);
        }
        if (carrier[r][c].charAt(nextCarrier)=='0' && wait <= 3) {
            // 原地不动
            dfsSave(r,c, timestamp+1, wait+1);
        }
    }

}
