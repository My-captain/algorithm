package zliu.elliot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class hw {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n / 16.0; ++i) {
            sb.append(scanner.next("0x....").replaceAll("0x", ""));
        }
        byte[] bytes = new BigInteger(sb.toString(), 16).toByteArray();
    }
}
