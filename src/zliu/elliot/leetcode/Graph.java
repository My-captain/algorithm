package zliu.elliot.leetcode;

import java.util.*;

public class Graph {

    int[] visited = null;
    /**
     * 207. 课程表
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        visited = new int[numCourses];
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            next.add(new ArrayList<>());
        }
        for (int[] dep: prerequisites){
            next.get(dep[1]).add(dep[0]);
        }
        boolean dag = true;
        for (int i = 0; i < numCourses; ++i) {
            dag = dfsTopology(i, next);
            if (!dag) {
                break;
            }
        }
        return dag;
    }

    private boolean dfsTopology(int vertex, List<List<Integer>> dependencies) {
        List<Integer> next = dependencies.get(vertex);
        if (next.size() > 0) {
            visited[vertex] = 1;
            for (int ne : next) {
                // 邻接结点已经被DFS扫完过, 没有环, 则直接剪枝
                if (visited[ne] == 2) {
                    continue;
                } else if (visited[ne] == 1) {
                    // 邻接结点在本轮DFS中正在访问, 则有环
                    return false;
                }
                if (!dfsTopology(ne, dependencies)){
                    return false;
                }
            }
            // 已经访问过的置为2
            visited[vertex] = 2;
        }
        return true;
    }

    int[] topologyRes = null;
    int[] inEdge = null;
    int cursor = 0;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        inEdge = new int[numCourses];
        topologyRes = new int[numCourses];
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            next.add(new ArrayList<>());
        }
        for (int[] dep : prerequisites) {
            next.get(dep[1]).add(dep[0]);
            ++inEdge[dep[0]];
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; ++i) {
            if (inEdge[i] == 0) {
                queue.offer(i);
            }
        }
        bfsTopology(next, queue);
        if (cursor == numCourses) {
            return topologyRes;
        }
        return new int[]{};
    }

    private void bfsTopology(List<List<Integer>> next, Queue<Integer> queue) {
        while (queue.size() > 0) {
            topologyRes[cursor++] = queue.poll();
            for (int ne : next.get(topologyRes[cursor - 1])) {
                --inEdge[ne];
                if (inEdge[ne] == 0) {
                    queue.offer(ne);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.findOrder(2, new int[][]{{0,1},{1,0}});
    }

}
