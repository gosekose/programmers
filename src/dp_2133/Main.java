package dp_2133;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
        int n = Integer.parseInt(br.readLine());
        if (n % 2 == 1) {
            System.out.println(0);
            return;
        }

        int[] dp = new int[Math.max(n / 2, 2)];
        dp[0] = 3;
        dp[1] = 11;

        int tmp = 0;
        for (int i = 2; i < n / 2; i++) {
            tmp+= dp[i - 2] * 2;
            dp[i] = dp[i - 1] * 3 + 2 + tmp;
        }
        System.out.println(dp[n / 2 - 1]);

    }    
}