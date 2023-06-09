package zliu.elliot.leetcode;

public class Numeric {

    public int Erotasthenes(int n) {
        int count = 0;
        boolean[] prime = new boolean[n]; // false表示为素数
        for(int i=2; i<=n; ++i) { // 只需要筛选到平方根即可
            if (!prime[i-1]) {		// 如果是素数,则对其倍数进行标记
                ++count;
                for(int j=i*i; j<=n; j+=i) { // 举例i=5，则：
                    prime[j-1]=true;		   // i=2,3,4时必然已经枚举过2x5、3x5、4x5
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Numeric numeric = new Numeric();
        numeric.Erotasthenes(100);
    }

}
