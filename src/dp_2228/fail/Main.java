package dp_2228.fail;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    static int n;
    static int m;
    static int[] dp;
    static int max;
    static boolean[] open;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = parseInt(st.nextToken());
        m = parseInt(st.nextToken());

        dp = new int[n];
        open = new boolean[n];
        
        for (int i = 0; i < n; i++) dp[i] = parseInt(br.readLine());
        max = dp[0];

        if (n == 1) {
            System.out.println(max);
            return;
        }
        
        for (int i = 1; i < n; i++) dp[i] += dp[i - 1]; // i는 i를 포함한 이전의 수
        dfs(new ArrayList<>(), 0); //
        System.out.println(max);
    }
    
    static void dfs(List<Integer> combi, int idx) {
        if (combi.size() == m - 1) {

            int pre = 0;
            for (int i = 0; i < combi.size(); i++) {
                int now = combi.get(i);
                if (i == 0) max = Math.max(dp[now], max); // pre 존재 x;
                else max = Math.max(dp[now] - dp[pre], max);

                if (i == combi.size() - 1) {
                    if (now != combi.get(i))
                        max = Math.max(dp[n - 1] - dp[now], max);
                }
                pre = now;
            }
            return;
        }

        if (n <= idx) return;
        for (int k = idx; k < n; k++) {
//            if (!combi.isEmpty() && combi.get(combi.size() - 1) == k - 1) continue;
            combi.add(k);
            dfs(combi, k + 1);
            combi.remove(combi.size() - 1);
        }
    }
}
// - 300 100
//     차이 400