package zliu.elliot;

import java.util.HashMap;

public class byteDance {
        public int findProphet(int n, int[][] trust) {
            HashMap<Integer, Integer> trustCnt = new HashMap<>();
            for (int i = 0; i < trust.length; ++i) {
                if (!trustCnt.containsKey(trust[i][1])){
                    trustCnt.put(trust[i][1], 1);
                } else {
                    Integer integer = trustCnt.get(trust[i][1]);
                    trustCnt.replace(trust[i][1], integer+1);
                }
            }
            for (Integer playerID :
                    trustCnt.keySet()) {
                if (trustCnt.get(playerID) == n - 1){
                    return playerID;
                }
            }
            return -1;
        }

    public static void main(String[] args) {
        new byteDance().findProphet(2, new int[][]{{1,2}});
    }

}
