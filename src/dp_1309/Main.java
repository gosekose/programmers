package dp_1309;

import java.util.*;
import java.io.*;

public class Main {

    static final int MOD = 9901;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] dp = new int[n][3]; // [x][0]: 모두 선택 안함, [x][1]: 왼쪽만 넣음, [x][2]: 오른쪽만 넣음

        Arrays.fill(dp[0], 1); // 0번째에서는 모두 선택 안하거나, 왼쪽에만 넣거나 오른쪽에만 넣을 수 있음

        for (int i = 1; i < n; i++) {
            // 모두 선택안하고 시작한 경우, 모두 선택 X, 왼쪽만 넣은 경우, 오른쪽만 넣은 경우 모두 넣고, 이번에 선택 안함
            dp[i][0] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2]) % MOD;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD; // 이전에 모두 선택 안한 것, 오른쪽을 선택한 것을 선택하고 이번은 왼쪽을 선택
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD; // 이전에 모두 선택 안한 것, 왼쪽만 선택한 경우를 가져온 후 오른쪽만 선택
        }

        int sum = 0;
        for (int i = 0; i < 3; i++) sum += dp[n - 1][i];
        sum %= MOD;

        System.out.println(sum);

    }
}