package sum_16139;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Question> qs = new ArrayList<>(); // question을 저장할 리스트
        
        String str = br.readLine();
        int n = str.length();
        int q = parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            qs.add(new Question(st.nextToken().charAt(0), parseInt(st.nextToken()), parseInt(st.nextToken())));
        }
        
        int[][] dp = new int[n + 1][26]; // dp 2차원 배열 (누적합)

        for (int i = 1; i <= n; i++) { // 인덱스 에러를 방지하기 위해 1부터 시작
            int num = str.charAt(i - 1) - 'a'; // char - 'a'로 정수로 변환
            for (int j = 0; j < 26; j++) {
                if (j == num) dp[i][num] = dp[i - 1][num] + 1; // 만약 해당 문자에 해당하는 정수라면 +1
                else dp[i][j] = dp[i - 1][j]; // 아니라면 그대로
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Question question : qs) {
            int num = question.c - 'a';
            sb.append(dp[question.e][num] - dp[question.s][num]).append("\n"); // 끝을 포함해야하므로 e는 1을 더해주고, s는 그대로
        }
        
        System.out.print(sb);
    }
    
    static class Question {
        char c;
        int s;
        int e;
        
        Question (char c, int s, int e) {
            this.c = c;
            this.s = s;
            this.e = e + 1; // 끝점 포함
        }
    }
}