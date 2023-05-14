package dp_1890;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        
        Jump jump = new Jump(n);
        for (int row = 0; row < n; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++)
                jump.setMap(row, col, parseInt(st.nextToken()));
        }
        
        jump.run();
        System.out.println(jump.getPath());
    }
}

class Jump {
    int n;
    int[][] map; // 점프 값 저장
    long[][] dp; // 점프 후 dp 저장 2^63 - 1까지 저장 될 수 있으므로
    
    Jump (int n) {
        this.n = n;
        map = new int[n][n];
        dp = new long[n][n];
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    void run() {
        dp[0][0] = 1; // 초기 값 설정

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                int d = map[row][col];
                if (d == 0) continue; // 만약 이동 거리가 0이라면 중복 저장을 막기 위함 dp[row][col + 0] += dp[row][col] 방지

                if (isValid(row, col + d)) { // 값만큼 열 점프
                    dp[row][col + d] += dp[row][col];
                }

                if (isValid(row + d, col)) { // 값만큼 행 점프
                    dp[row + d][col] += dp[row][col];
                }
            }
        }
    }
    
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    long getPath() {
        return dp[n - 1][n - 1];
    }

}