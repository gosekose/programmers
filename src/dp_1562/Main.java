package dp_1562;

import java.io.*;

public class Main {
    static final int MOD = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        long[][][] dp = new long[n + 1][10][1 << 10]; // i 자리에서, k라는 숫자가 오는데, 0 ~ 9까지의 방문 여부를 담은 3차원 배열 ex) dp[10][6][10] 10번째 자리에서, 6이 올 수 있는 경우에 방문 상태는 0000000110
        for (int i = 1; i <= 9; i++) 
            dp[1][i][1 << i] = 1; // 1자리에서 k라는 수가 오는 경우 (0은 첫번째 자리에 올 수 없음)
        
        for (int t = 2; t <= n; t++) {
            for (int k = 0; k <= 9; k++) { // 2자리부터는 0 가능
                for (int visited = 0; visited < (1 << 10); visited++) { // 비트마스크에 의해 0 ~ 1024까지
                    
                    int nextVisited = visited | (1 << k); // 비트마스크로 방문 처리 (이진수 | 은 or 연산)
                    
                    if (k == 0) dp[t][k][nextVisited] += dp[t - 1][k + 1][visited] % MOD; // k == 0 인 경우 올라가는 연산만 존재
                    else if (k == 9) dp[t][k][nextVisited] += dp[t - 1][k - 1][visited] % MOD; // k == 9인경우 내려가는 연산만 존재
                    else dp[t][k][nextVisited] += dp[t - 1][k + 1][visited] % MOD + dp[t - 1][k - 1][visited] % MOD; // 둘 다 가능
                
                    dp[t][k][nextVisited] %= MOD; // 모든 경우를 구한 후 + 연산을 했으므로 한번 더 MOD 연산 진행
                }
            }
        }
        
        long sum = 0;
        for (int i = 0; i <= 9; i++) {
            sum += dp[n][i][(1 << 10) - 1] % MOD; // 비트마스크의 마지막인덱스 1 111 111 111 (모든 숫자 방문)
            sum %= MOD;
        }
        
        System.out.println(sum);
    }
}