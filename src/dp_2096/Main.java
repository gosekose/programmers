package dp_2096;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = parseInt(br.readLine());
        int[][] map = new int[n][3];
        
        int[][][] dp = new int[n][3][2];
        
        for (int row = 0; row < n; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < 3; col++) {
                map[row][col] = parseInt(st.nextToken());        
            }
        }
        
        dp[0][0][0] = dp[0][0][1] = map[0][0];
        dp[0][1][0] = dp[0][1][1] = map[0][1];
        dp[0][2][0] = dp[0][2][1] = map[0][2];
        
        for (int row = 1; row < n; row++) {
            dp[row][0][0] = Math.max(dp[row - 1][0][0], dp[row - 1][1][0]) + map[row][0];
            dp[row][1][0] = Math.max(Math.max(dp[row - 1][0][0], dp[row - 1][1][0]), dp[row - 1][2][0]) + map[row][1];
            dp[row][2][0] = Math.max(dp[row - 1][1][0], dp[row - 1][2][0]) + map[row][2];
        
            dp[row][0][1] = Math.min(dp[row - 1][0][1], dp[row - 1][1][1]) + map[row][0];
            dp[row][1][1] = Math.min(Math.min(dp[row - 1][0][1], dp[row - 1][1][1]), dp[row - 1][2][1]) + map[row][1];
            dp[row][2][1] = Math.min(dp[row - 1][1][1], dp[row - 1][2][1]) + map[row][2];    
        }
        
        int max = Math.max(Math.max(dp[n - 1][0][0], dp[n - 1][1][0]), dp[n - 1][2][0]);
        int min = Math.min(Math.min(dp[n - 1][0][1], dp[n - 1][1][1]), dp[n - 1][2][1]);
    
        System.out.println(max + " " + min);
    }
}