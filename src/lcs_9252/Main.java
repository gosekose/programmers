package lcs_9252;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str1 = br.readLine(); // 문자열 1
        String str2 = br.readLine(); // 문자열 2
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 1; i <= str1.length(); i++) { // LCS 공식
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        if (dp[str1.length()][str2.length()] == 0) sb.append(0); // 만약 값이 0 -> LCS가 없음
        else {
            sb.append(dp[str1.length()][str2.length()]).append("\n");

            Stack<Character> stack = new Stack<>();
            int i = str1.length(), j = str2.length();
            while (i > 0 && j > 0) { // 역추적 --> LCS 공식에 따라, (행 - 1)과 같다면 행--, (열 - 1)과 같다면 열 --

                if (dp[i][j] == dp[i - 1][j]) i--;
                else if (dp[i][j] == dp[i][j - 1]) j--;
                else { // 행 혹은 열 하나씩 뺀 것과 같지 않다면, 나머지는 대각선
                    stack.push(str1.charAt(i - 1)); // 스택에 넣기
                    i--;
                    j--;
                }
            }

            while(!stack.isEmpty()) sb.append(stack.pop()); // 후입선출로 맨 마지막부터 출력
        }
        System.out.println(sb);
    }
}