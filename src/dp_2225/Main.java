package dp_2225;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        
        SumDivision sd = new SumDivision(n, k);
        sd.run();
    }
}

class SumDivision {
    
    static final int MOD = 1000000000;
    int n;
    int k;
    int[][] dp;

    SumDivision(int n, int k) {
        this.n = n;
        this.k = k;
        dp = new int[k + 1][n + 1]; // i 개로 총 합이 j을 만드는 것        
    }
    
    void run() {

        Arrays.fill(dp[1], 1); // i = 1, 즉 1개로 j의 값을 만드는 경우는 한 개
        for (int i = 1; i <= k; i++) dp[i][0] = 1; // 총 합이 0이 되도록 만드는 경우는 각 개수 별로 한 개씩 
        // ex) i = 1, 합이 0 ==> 0 (한 개 존재)
        // ex) i = 2, 합이 0 ==> 0 + 0 (한 개 존재)

        // dp[i][j] = dp[i - 1][0] + dp[i - 1][1] + .... + dp[i - 1][j];
        //   j
        // = sum(dp[a - 1][b]) 
        //   b -> 0 

        //   j - 1
        // = sum(dp[a - 1][b]) + dp[i - 1][j]
        //   b -> 0

        // = dp[i][j - 1] + dp[i - 1][j]
        for (int i = 2; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                dp[i][j] %= MOD;
            }
        }

        System.out.println(dp[k][n]);
    }
}
