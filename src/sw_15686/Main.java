package sw_15686;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        ChickenDistance chickenDistance = new ChickenDistance(n, m);

        for (int row = 1; row <= n; row++) { // 1행 1열 이므로
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= n; col++)
                chickenDistance.setMap(row, col, parseInt(st.nextToken()));
        }

        chickenDistance.start();

        System.out.println(chickenDistance.getMinDistance());
    }
}

class ChickenDistance {
    int n; // 정사각형 행렬
    int m; // m개의 선택된 치킨집
    int[][] map;
    boolean[] open; // 열려있는 집
    int minDistance = Integer.MAX_VALUE; // 최소 치킨 거리
    List<Point> chickens = new ArrayList<>(); // 치킨집 좌표
    List<Point> homes = new ArrayList<>(); // 집 좌표

    ChickenDistance(int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n + 1][n + 1];
    }

    void setMap(int row, int col, int value) {
        map[row][col] = value;

        if (value == 1) homes.add(new Point(row, col)); // 1이라면 집에 추가
        else if (value == 2) chickens.add(new Point(row, col)); // 2라면 치킨집 추가
    }

    void start() {
        open = new boolean[chickens.size()]; // 열려 있는집 초기화하기

        dfs(0, 0); // dfs
    }

    void dfs(int start, int cnt) {
        if (cnt == m) {
            int minLocalDistance = 0; // 현재 dfs에서 지역변수 Distance 초기화  (모든 집이 거리 비교 끝나면 비교)

            for (Point home : homes) { // 집 리스트 순회
                int distance = Integer.MAX_VALUE; // 비교할 거리는 최대치로 초기화
                for (int i = 0; i < chickens.size(); i++) {
                    if (open[i]) { // 만약 열려 있다면
                        Point ch = chickens.get(i);
                        int nowD = Math.abs(home.row - ch.row) + Math.abs(home.col - ch.col);
                        distance = Math.min(nowD, distance); // 치킨집과 비교해서 거리 업데이트
                    }
                }
                minLocalDistance += distance;
            }

            minDistance = Math.min(minLocalDistance, minDistance);
            return;
        }

        for (int i = start; i < chickens.size(); i++) {
            open[i] = true; // 집 열려 있도록 설정
            dfs(i + 1, cnt + 1); // 다음 재귀로 조합 설정
            open[i] = false; // 백트래킹으로 다시 초기화함으로써, 다음 재귀에 영향 받지 않도록 설정
        }
    }

    int getMinDistance() {
        return minDistance;
    }

    static class Point {
        int row;
        int col;

        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

}