package sw_15684;

import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        int h = parseInt(st.nextToken());

        Ladder ladder = new Ladder(n, h);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            ladder.setMap(parseInt(st.nextToken()), parseInt(st.nextToken()));
        }

        ladder.start();
        System.out.println(ladder.getMinAddEdge());
    }
}

class Ladder {
    int n; // 열 개수
    int h; // 행 개수
    int[][] map;
    int minAddEdgeCnt = 4; // 최소 추가 설치 사다리 개수 (만약 4가 바뀌지 않는다면 -1)

    Ladder (int n, int h) {
        this.n = n;
        this.h = h;
        map = new int[h + 2][n + 2]; // (1, 1)  ~ (h ~ )이므로 h + 2
    }

    void setMap(int row, int col) {
        map[row][col] = 1;
    }

    void start() {
        for (int i = 0; i <= 3; i++) dfs(0, i, 1); // dfs로 추가 사다리가 0인 것부터 0 ~ 3까지 탐색
    }

    void dfs(int cnt, int limit, int r) {
        if (cnt > limit || cnt > minAddEdgeCnt) return; // 만약 추가한 사다리가 한계치 (0 ~ 3)으로 설정한 값보다 크거나 최대치를 넘는다면 종료

        if (isPossible()) {
            minAddEdgeCnt = Math.min(minAddEdgeCnt, cnt);
            return;
        }

        for (int row = r; row <= h; row++) { // 각 몇번째 행에서부터
            for (int col = 1; col <= n; col++) { // 1번열 (1, 1) ~ n개까지 (총 n개의 열)
                if (map[row][col] == 0 && map[row][col - 1] == 0 && map[row][col + 1] == 0) { // 참조 #1
                    map[row][col] = 1; //  현재 열 선택 가능
                    dfs(cnt + 1, limit, row); // 사다리를 하나 추가하여 이어서 dfs 진행
                    map[row][col] = 0; // dfs 종료 후 다시 이전에 설정한 1을 초기화
                }
            }
        }
    }

    boolean isPossible() {
        for (int col = 1; col <= n; col++) {
            int nowC = col; // 현재 선택한 열 번호
            int row = 1; // 행의 시작은 1
            while (true) {
                if (row > h) break; // 만약 h의 길이보다 크다면
                if (map[row][nowC] == 1) nowC++; // 1이라면 오른쪽으로 연결
                else if (map[row][nowC - 1] == 1) nowC--;// 이전항이 1이라면 이전항에서 연결한 것임
                row++; // 한번 이동했다면 그 열에서는 연속적으로 다음 열로 이동할 수 없기에 바로 내려갈 수 있음
            }

            if (nowC != col) return false; // 만약 열을 이동하고 최종적으로 마지막 행을 지나 도착했을 때, 이전 열과 다르다면
        }
        return true;
    }


    int getMinAddEdge() {
        return minAddEdgeCnt == 4 ? -1 : minAddEdgeCnt;
    }
}