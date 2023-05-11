package sw_17144;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = parseInt(st.nextToken());
        int c = parseInt(st.nextToken());
        int t = parseInt(st.nextToken());

        AirCleaner airCleaner = new AirCleaner(r, c, t); // 공기 청정기 설정

        for (int row = 0; row < r; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < c; col++) {
                airCleaner.setMap(row, col, parseInt(st.nextToken()));
            }
        }

        airCleaner.on();
        System.out.println(airCleaner.getDusty());
    }
}

class AirCleaner {

    static final int[] DR = {-1, 0, 1, 0};
    static final int[] DC = {0, -1, 0, 1};

    int n; // 공기 청정기 행 길이
    int m; // 공기 청정기 열 길이
    int time; // 작동해야 하는 시간
    int[][][] map; // 0: 현재 값, 1: 업데이트에 적용될 확산 저장
    List<Point> cleaner = new ArrayList<>(); // 공기청정기는 2n (n은 1 이상)

    AirCleaner(int n, int m, int time) {
        this.n = n;
        this.m = m;
        this.time = time;

        map = new int[2][n][m];
    }

    void setMap(int row, int col, int value) {
        map[0][row][col] = value;
        if (value == -1) cleaner.add(new Point(row, col)); // 클리너 포인트 저장하기
    }

    void on() {
        int localTime = 0; // 지역변수 설정

        while (localTime < time) {

            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {

                    if (map[0][row][col] == 0) continue;

                    int count = 0; // 확산 가능한 개수 정하기
                    for (int k = 0; k < 4; k++) {
                        int nextR = row + DR[k];
                        int nextC = col + DC[k];
                        if (!isValid(nextR, nextC)) continue;
                        count++;
                    }

                    int diffusion = map[0][row][col] / 5; // 문제 공식에 의한 확산량 설정
                    map[0][row][col] -= diffusion * count; // 문제 공식에 의한 확산하게 한 먼지 업데이트

                    for (int k = 0; k < 4; k++) {
                        int nextR = row + DR[k];
                        int nextC = col + DC[k];
                        if (!isValid(nextR, nextC)) continue;
                        map[1][nextR][nextC] += diffusion; // 확산된 먼지를 받은 곳 업데이트
                    }
                }
            } // 확산시키기

            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    map[0][row][col] += map[1][row][col];
                    map[1][row][col] = 0;
                }
            } // 확산한 값 추가하기 (모두 한 번에 확산되기 때문에 1차원 1인덱스 배열에 추가한 값 업데이트 실시

            // 청정기 작동
            for (int i = 0; i < cleaner.size(); i++) {
                Point p = cleaner.get(i);
                int pRow = p.row; // pRow = 0 아님
                int pCol = p.col; // pCol = 0 임

                if (i % 2 == 0) { // 짝수번은 위로
                    pRow -= 1; // 한칸 위로 먼저 올리기

                    while(pRow > 0) { // pRow가 0보다 클 때까지 한칸 내리기
                        map[0][pRow][pCol] = map[0][pRow - 1][pCol]; // 한칸씩 이동
                        pRow--; // 한칸 이동 시킨 후 위로 다시 올리기
                    }

                    while (pCol < m - 1) { // pCol이 m - 1보다 작을 때까지 --> pCol이 m - 1에 도달 종료
                        map[0][pRow][pCol] = map[0][pRow][pCol + 1];
                        pCol++;
                    }

                    while (pRow < p.row) { // pRow가 p.row보다 작을 때까지 내리면서 업데이트
                        map[0][pRow][pCol] = map[0][pRow + 1][pCol];
                        pRow++;
                    }

                    while(pCol > 1) { // pRow == 0, pCol > 0일 때까지 감소
                        map[0][pRow][pCol] = map[0][pRow][pCol - 1];
                        pCol--;
                    }
                    map[0][p.row][p.col + 1] = 0;
                }

                else { // 홀수번은 아래로 순회
                    pRow += 1; // 한칸 아래로 내리기

                    while(pRow < n - 1) { // pRow가 n - 1보다 작을 때까지 한칸 내리기
                        map[0][pRow][pCol] = map[0][pRow + 1][pCol]; // 한칸씩 이동
                        pRow++; // 한칸 이동 시킨 후 위로 다시 올리기
                    }

                    while (pCol < m - 1) { // pCol이 m - 1보다 작을 때까지 --> pCol이 m - 1에 도달 종료
                        map[0][pRow][pCol] = map[0][pRow][pCol + 1];
                        pCol++;
                    }

                    while (pRow > p.row) { // pRow가 p.row보다 작을 때까지 내리면서 업데이트
                        map[0][pRow][pCol] = map[0][pRow - 1][pCol];
                        pRow--;
                    }

                    while(pCol > 1) { // pRow == 0, pCol > 0일 때까지 감소
                        map[0][pRow][pCol] = map[0][pRow][pCol - 1];
                        pCol--;
                    }
                    map[0][p.row][p.col + 1] = 0;
                }
            }
            localTime++;
        }
    }

    boolean isValid(int row, int col) { // 유효한 인덱스에 공기 청정기가 아니여야 함
        return row >= 0 && row < n && col >= 0 && col < m &&
                map[0][row][col] != -1;
    }

    int getDusty() {
        int amount = 0; // 공기청정기 및 0을 제외한 양수값 추가
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map[0][row][col] > 0) amount += map[0][row][col];
            }
        }
        return amount;
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


// o(v + e) -> 50 * 50 = 2500 + 2500 * 4 --> 12500
// time => 1000
// 12,500,000




