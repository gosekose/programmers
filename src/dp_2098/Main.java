package dp_2098;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    static int n;
    static int answer;
    static int[][] fromTo;
    static int [][] dp;
    static int INF = 987654321;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = parseInt(br.readLine());
        fromTo = new int[n][n];
        dp = new int[1 << n][n]; // 디피 설정

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) fromTo[i][j] = parseInt(st.nextToken());
        }

        answer = tsp(1, 0); // 방문, 위치
        System.out.println(answer);
    }

    static int tsp(int visited, int loc) {
        if (visited == (1 << n) - 1) {
            if (fromTo[loc][0] > 0) return fromTo[loc][0]; // 값이 유효함
            return INF; // 간선이 없어서 최대값으로 설정
        }

        if (dp[visited][loc] > 0) return dp[visited][loc]; // 현재 방문한 상태가 최신 상태로 업데이트 된 경우

        dp[visited][loc] = INF; // 아직 방문하지 않았음을 의미함

        for (int dest = 0; dest < n; dest++) {
            if (isValid(visited, loc, dest))
                dp[visited][loc] = Math.min(dp[visited][loc],
                        tsp(visited | (1 << dest), dest) + fromTo[loc][dest]);
        }

        return dp[visited][loc];
    }

    static boolean isValid(int visited, int loc, int dest) {
        return (visited & (1 << dest)) == 0 && fromTo[loc][dest] > 0;
    }
}