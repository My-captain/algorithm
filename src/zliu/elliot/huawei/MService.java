package zliu.elliot.huawei;

import java.util.Arrays;
import java.util.Scanner;

public class MService {

    int maxCnt = 0;
    int maxTime = 0;
    int maxTime2 = Integer.MAX_VALUE;
    boolean[] add = null;

    private void dfsSearch(int[][] dist, int current, boolean[] visited, int sum, boolean ifStart, int callDepth) {
        if (!add[current]) {
            add[current] = true;
            ++maxCnt;
        }
        int cost = sum;
        if (!ifStart) {
            cost+=dist[current][current];
        }
        maxTime = Math.max(maxTime, cost);
        if (callDepth == dist.length) {
            maxTime2 = Math.min(maxTime2, cost);
        }
        visited[current] = true;
        for (int i = 0; i < dist.length; ++i) {
            if (i == current || dist[current][i] < 0 || visited[i]) {
                // 跳过  当前node、不存在路径、当前DFS已访问(避免回路)
                continue;
            }
            dfsSearch(dist, i, visited, cost+dist[current][i], false, callDepth+1);
        }
        visited[current] = false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int cnt = scanner.nextInt();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dist[i], -1);
            dist[i][i] = 0;
        }
        for (int i = 0; i < cnt; ++i) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            int dis = scanner.nextInt();
            dist[src - 1][dest - 1] = dis;
        }
        int start = scanner.nextInt();
        boolean[] visited = new boolean[n];
        MService mService = new MService();
        mService.add = new boolean[n];
        mService.dfsSearch(dist, start - 1, visited, 0, true, 1);
        System.out.println(mService.maxCnt);
        System.out.println(Math.min(mService.maxTime, mService.maxTime2));
    }

}
