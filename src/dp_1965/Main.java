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
    int[] map;
    int[] dp;
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
            for (int j = i; j >= 0; j--) {
                if (map[j] < map[i]) {
                    dp[i] = Math.max(dp[j], dp[i]);
                }
            }
            dp[i] += 1;
            result = Math.max(dp[i], result);
        }
    }
}
