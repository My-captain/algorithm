package zliu.elliot.leetcode;

public class DivideConquer {

    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        long N = n;
        double res = quickPow(x, Math.abs(N));
        return n >0 ? res:1/res;
    }

    public double quickPow(double x, long n) {
        if (n == 1) {
            return x;
        } else {
            double halfPow = quickPow(x, n >> 1);
            return n%2==0?halfPow*halfPow:halfPow*halfPow*x;
        }
    }

    public double quickPowIter(double x, long n) {
        double res = 1;
        double weight = x;
        while (n > 0) {
            if ((n&1) == 1) {
                res *= weight;
            }
            weight *= weight;
            n >>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        DivideConquer divideConquer = new DivideConquer();
        divideConquer.myPow(1.000, -2147483648);
    }

}
