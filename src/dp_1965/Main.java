package dp_1965;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        Box box = new Box(n);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            box.setMap(i, parseInt(st.nextToken()));
        }

        box.run();
        System.out.println(box.getResult());
    }

}

class Box {
    int n;
    int[] map; // 값 저장
    int[] dp; // 최대 몇개를 넣을 수 있는지 저장하는 DP
    int result;
    public Box(int n) {
        this.n = n;
        map = new int[n];
        dp = new int[n];
    }

    public void setMap(int i, int value) {
        map[i] = value; // 0: 값
    }

    int getResult() {
        return result;
    }

    public void run() {
        dp[0] = 1; // 첫 번째 선택은 1

        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (map[j] < map[i]) { // 만약 현재 있는 상자의 값보다 적다면
                    dp[i] = Math.max(dp[j], dp[i]); // 그 적은 상자와 현재 dp를 비교하여 업데이트
                }
            }
            dp[i] += 1; // 자기 자신을 넣어야 하므로 +1
            result = Math.max(dp[i], result);
        }
    }
}
