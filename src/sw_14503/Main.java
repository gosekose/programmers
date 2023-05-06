package sw_14503;// 북: 0, 1: 동,  2: 남,  3: 서
import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int r = parseInt(st.nextToken());
        int c = parseInt(st.nextToken());
        int dir = parseInt(st.nextToken());

        RobotCleaner robot = new RobotCleaner(n, m);
        robot.setPoint(r, c, dir); // 처음 위치 및 방향 초기화

        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++) {
                robot.setMap(row, col, parseInt(st.nextToken()));
            }
        }

        robot.run(); // 작동 시작

        System.out.println(robot.getCleanRoom());

    }
}

class RobotCleaner {

    static final int[] DR = {-1, 0, 1, 0};
    static final int[] DC = {0, -1, 0, 1};

    int n;
    int m;
    int[][] map;
    Point point;
    int nowDir; // 북: 0, 동: 1, 남: 2,  서: 3
    int cleanRoom;
    boolean[][] visited;

    RobotCleaner (int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m];
        visited = new boolean[n][m];
    }

    void setPoint(int row, int col, int dir) {
        point = new Point(row, col);
        nowDir = dir;
    }

    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }

    void run() {
        while(true) {

            int row = point.row;
            int col = point.col;

            // 움직일 수 있고, 방문을 안했다면(청소가 가능하다면) <1>
            if (map[row][col] == 0 && !visited[row][col]) {
                cleanRoom++;
                visited[row][col] = true; // 방문 처리
            }

            boolean isNotCleanRoom = false; // 방문 가능한 곳 체크
            for (int i = 0; i < 4; i++) {
                int possibleR = row + DR[i];
                int possibleC = col + DC[i];

                if (!isValid(possibleR, possibleC)) continue; // 불가능한 곳

                isNotCleanRoom = true; // 청소할 곳 있음 <2> // isNotCleanRoom
                break;
            }

            if (isNotCleanRoom) {
                rotate(); // 회전하기

                //북: 0, 동: 1, 남: 2,  서: 3 이동하기
                if (nowDir == 0) row--; // 북 방향에서 한 칸 앞은 행 기준 --
                else if (nowDir == 1) col++; // 동 방향(오른쪽) 한 칸 앞
                else if (nowDir == 2) row++; // 남 기준 앞 row++
                else  col--; // 서 방향 기준 앞은 왼쪽

                if (!isValid(row, col)) continue;
                point.row = row;// 가능한 경우 업데이트
                point.col = col;
            }

            else { // 현재 칸의 주변 중 청소되지 않은 빈 칸이 없는 경우 <2>

                //북: 0, 동: 1, 남: 2,  서: 3
                if (nowDir == 0) row++; // 북 방향에서 한 칸 뒤는 남쪽으로
                else if (nowDir == 1) col--; // 동 방향(오른쪽) 한 칸 뒤 왼쪽
                else if (nowDir == 2) row--;
                else  col++;

                if (!isPossibleBackMove(row, col)) return; // 후진 불가능
                point.row = row; // 후진 가능하다면 위치 업데이트 후 while문 돌아가기
                point.col = col;
            }
        }
    }

    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m &&
                map[row][col] == 0 && !visited[row][col];
    }

    boolean isPossibleBackMove(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m &&
                map[row][col] == 0; // 후진하는 곳은 벽이 아니여야 함
    }

    void rotate() {
        switch (nowDir) { // 북: 0, 동: 1, 남: 2,  서: 3 
            case 0:
                nowDir = 3; // 북 -> 서
                break;
            case 1:
                nowDir = 0; // 동 -> 북
                break;
            case 2:
                nowDir = 1; // 남 -> 동
                break;
            case 3:
                nowDir = 2; // 서 -> 남
                break;
        }
    }

    int getCleanRoom() {
        return cleanRoom;
    }

    class Point {
        int row;
        int col;

        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}