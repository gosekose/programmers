package sw_14891;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Gear gear = new Gear();
        for (int i = 0; i < 4; i++) {
            String gearStatus = br.readLine();
            for (int j = 0; j < 8; j++) {
                gear.addStatus(i, j, gearStatus.charAt(j));
            }
        }

        int k = parseInt(br.readLine());
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            gear.setRotate(parseInt(st.nextToken()), parseInt(st.nextToken()));
        }

        gear.run();

        System.out.println(gear.getScore());
    }
}

class Gear {

    int[][] map = new int[4][8];
    Queue<Rotate> rotates = new ArrayDeque<>();
    Gear(){}

    void addStatus(int row, int col, char status) {
        map[row][col] = Character.getNumericValue(status);
    }

    void setRotate(int number, int dir) {
        rotates.add(new Rotate(number - 1, dir)); // 번호가 1부터인데 인덱스는 0부터 설정
    }

    void run() { // 극 N = 0, S = 1  dir:  시계 = 1, 반시계 = -1
        while (!rotates.isEmpty()) {
            Rotate rt = rotates.poll();
            int number = rt.number;
            int dir = rt.dir;

            int two = map[number][2];
            int six = map[number][6];

            rotate(number, dir);

            if (number == 0) dfs(number + 1, two, dir, false); // 오른쪽으로 이동하면서 회전
            else if (number == 3) dfs(number - 1, six, dir, true); // 왼쪽으로 이동
            else { // 1, 2는 양쪽 방향으로 진행
                dfs(number + 1, two, dir, false);
                dfs(number - 1, six, dir, true);
            }
        }
    }

    void rotate(int number, int dir) { // 극 N = 0, S = 1  dir:  시계 = 1, 반시계 = -1
        int[] tmp = map[number].clone(); // 복사 배열
        if (dir == 1) { // 시계
            // 이전항이 자신의 항
            System.arraycopy(tmp, 0, map[number], 1, 7);
            map[number][0] = tmp[7];
        } else { // 반시계
            // 다음 항이 자신의 항
            System.arraycopy(tmp, 1, map[number], 0, 7);
            map[number][7] = tmp[0];
        }
    }

    // 극이 달라야 회전
    void dfs(int nowNum, int prePole, int preDir, boolean toLeft) {
        if (nowNum == -1 || nowNum == 4) return;
        int nowDir = preDir == 1 ? -1 : 1; // 역방향

        int two = map[nowNum][2]; // 회전 시키기 전 고정 값
        int six = map[nowNum][6];

        if (toLeft) { // 영향 주는 곳은 6, 받는 곳은 2
            if (prePole == two) return; // 극이 같기에 더 이상 진행 x

            rotate(nowNum, nowDir); // 극이 다르다면 방향과 다르게 진행 회전
            dfs(nowNum - 1, six, nowDir, true); // 번호를 낮춰서, 다음 재귀

        } else { // 오른쪽으로 이동시 영향 주는 곳은 2, 받는 곳 6
            if (prePole == six) return; // 극이 같기에 더 이상 진행 x

            rotate(nowNum, nowDir);
            dfs(nowNum + 1, two, nowDir, false);
        }
    }

    int getScore() {  // 극 N = 0, S = 1  dir:  시계 = 1, 반시계 = -1
        int score = 0;

        score += map[0][0] == 0 ? 0 : 1;
        score += map[1][0] == 0 ? 0 : 2;
        score += map[2][0] == 0 ? 0 : 4;
        score += map[3][0] == 0 ? 0 : 8;

        return score;
    }

    class Rotate {
        int number;
        int dir;

        Rotate (int number, int dir) {
            this.number = number;
            this.dir = dir;
        }
    }
}