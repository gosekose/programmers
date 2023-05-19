package dp_5582;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String str1 = br.readLine(); // 입력 받을 문자열
        String str2 = br.readLine(); // 입력 받을 문자열
        int[][] dp = new int[str1.length() + 1][str2.length() + 1]; // 길이 + 1 길이 + 1 로 선언
        
        int max = 0;

        // row, col 을 1부터 시작하는 이유는 인덱스 에러 방지
        for (int row = 1; row <= str1.length(); row++) {
            for (int col = 1; col <= str2.length(); col++) {
                if (str1.charAt(row - 1) == str2.charAt(col - 1)) { // 문자열은 0부터 시작하므로 실제 row = 1에 해당하는 문자는 0
                    dp[row][col] = dp[row - 1][col - 1] + 1; // 만약 두 문자가 같다면 row - 1, col - 1 인덱스의 dp + 1 (연속된 문자열)
                    max = Math.max(max, dp[row][col]);
                }
            }
        }
        System.out.println(max);
    }
}