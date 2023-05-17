package dp_2011;

import java.io.*;

public class Main {
    static final int MOD = 1000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String encode = br.readLine();

        if (encode.charAt(0) == '0') {
            System.out.println("0");
            return;
        }

        int len = encode.length();
        long[] dp = new long[len + 1];
        dp[0] = dp[1] = 1; // 하나로 초기화

        // dp는 1번 인덱스, 문자열은 0번
        for (int i = 2; i <= len; i++) {
            char now = encode.charAt(i - 1); // 현재 문자 판단
            char pre = encode.charAt(i - 2); // 이전 문자
            if (now == '0') {
                // 120 이 있는 경우, 20은 가능한 문자이므로 1이 있었던 위치의 dp 활용 (두개가 하나이므로)
                if (pre == '1' || pre == '2') dp[i] = dp[i - 2];
                else break;
            }
            else {
                // 앞 문자가 0인 경우 무조건 주어진 숫자 하나만 선택 가능하므로 그대로  ex) 03 -> 3
                if (pre == '0') dp[i] = dp[i - 1];
                else {
                    int num = (pre - '0') * 10 + (now - '0'); // 숫자로 만들기
                    if (1 <= num && num <= 26) dp[i] = (dp[i - 2] + dp[i - 1]) % MOD;
                    else dp[i] = dp[i - 1]; // 28의 경우 알파벳 변환 불가하므로 한개 선택 => 그대로
                }
            }
        }
        System.out.println(dp[len] % MOD);
    }
}