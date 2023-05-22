package dp_2410;

import java.io.*;

public class Main {
    static final int MOD = 1_000_000_000;
    public static void main(String[ ] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        int[] dp = new int[n + 1];
        
        dp[1] = 1;
        if (n == 1) {
            System.out.println(dp[1]);
            return;
        } else if (n == 2 || n == 3) {
            System.out.println(2);
            return;
        } else if (n == 4 || n == 5) {
            System.out.println(4);
            return;
        }

        dp[2] = 2;
        dp[3] = 2;
        dp[4] = 4;
        dp[5] = 4;

        for (int i = 6; i <= n; i++) {
            if (i % 2 == 1) dp[i] = dp[i - 1] % MOD;
            else dp[i] = dp[i - 1] + dp[i - 3] % MOD;
        }
        
        System.out.println(dp[n]);
    }
}