package dp_9465;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(br.readLine());

        Sticker sticker = new Sticker();
        for (int i = 0; i < t; i++) {
            int n = parseInt(br.readLine());
            sticker.setInit(n);
            for (int row = 0; row < 2; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int col = 1 ; col <= n; col++)
                    sticker.setMap(row, col, parseInt(st.nextToken()));
            }
            sticker.run();
        }

        sticker.printResult();

    }    
}

class Sticker {
    int n;
    int[][] dp;
    int[][] map;
    List<Integer> result = new ArrayList<>();
    
    Sticker () {
    }

    void setInit(int n) {
        this.n = n;
        dp = new int[2][n + 1]; // dp를 담을 2차원 배열
        map = new int[2][n + 1]; // 각 스티커의 값
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value; 
    }
    
    void run() {
        dp[0][1] = map[0][1]; // i가 2부터이므로 1을 초기화
        dp[1][1] = map[1][1];
        
        for (int i = 2; i <= n; i++) {
            dp[0][i] = Math.max(dp[1][i - 1], dp[1][i - 2]) + map[0][i];
            dp[1][i] = Math.max(dp[0][i - 1], dp[0][i - 2]) + map[1][i];
        }
        result.add(Math.max(dp[0][n], dp[1][n]));
    }

    void printResult() {
        StringBuilder sb = new StringBuilder();

        for (Integer r : result) {
            sb.append(r).append("\n");
        }
        System.out.print(sb);
    }
}


