package sw_14499;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        int r = parseInt(st.nextToken());
        int c = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());

        Board board = new Board(n, m);
        board.setPoint(r, c);

        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++) {
                board.setMap(row, col, parseInt(st.nextToken()));
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            board.addCommand(parseInt(st.nextToken()));
        }

        board.run();
        System.out.print(board.printUpNumber());
    }
}

class Board {
    int n;
    int m;
    Point point;
    int[][] map;
    int[] dice;
    Queue<Character> cmds = new ArrayDeque<>();
    List<Integer> upNumbers = new ArrayList<>();

    Board (int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m]; // (0, 0) 부터 시작
        dice = new int[6]; // front, bottom, back, up, left, right
    }

    void setPoint(int row, int col) {
        point = new Point(row, col);
    }

    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }

    void addCommand(int cmd) {
        switch (cmd) {
            case 1:
                cmds.add('E');
                break;
            case 2:
                cmds.add('W');
                break;
            case 3:
                cmds.add('N');
                break;
            case 4:
                cmds.add('S');
                break;
        }
    }

    void run() {

        while(!cmds.isEmpty()) {
            char command = cmds.poll();
            int row = 0;
            int col = 0;

            switch (command) {
                case 'E': // 동쪽은 열++
                    col++;
                    break;
                case 'W': // 서쪽은 열--
                    col--;
                    break;
                case 'N': // 북쪽은 행 -- (위로 올리므로)
                    row--;
                    break;
                case 'S': // 남쪽은 행 ++ (아래로 내려가므로)
                    row++;
                    break;
            }

            int nextR = point.row + row;
            int nextC = point.col + col;

            if (!isValid(nextR, nextC)) continue; // 유효하지 않은 명령어 패스

            rollDice(command); // 주사위 굴리기

            point.row = nextR; // 주사위 위치 row 이동시키기
            point.col = nextC; // 주사위 위치 col 이동시키기

            if (map[point.row][point.col] == 0) {
                map[point.row][point.col] = dice[1]; // 바닥에 주사위 값 복사
            } else {
                dice[1] = map[point.row][point.col]; // 바닥에 있는 값을 주사위애 복사
                map[point.row][point.col] = 0; // 칸 값 0
            }

            upNumbers.add(dice[3]); // 상단에 있는 주사위 값 upNumbers에 추가
        }
    }

    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }

    void rollDice(char command) {
        // front(0), bottom(1), back(2), up(3), left(4), right(5) 회전 마침
        int up = dice[3];
        int bottom = dice[1];
        int left = dice[4];
        int right = dice[5];
        int front = dice[0];
        int back = dice[2];

        if (command == 'E') { // 왼쪽으로 회전
            dice[4] = up; // 왼 <- 위
            dice[1] = left; // 바 <- 왼
            dice[5] = bottom; // 오 <- 바
            dice[3] = right; // 위 <- 오
        }

        else if (command == 'W') { // 오른쪽으로 회전
            dice[5] = up; // 오 <- 위
            dice[1] = right; // 바 <- 오
            dice[4] = bottom; // 왼 <- 바
            dice[3] = left; // 위 <- 왼
        }

        else if (command == 'N') { // 북쪽으로
            dice[0] = bottom; // 앞 <- 바
            dice[3] = front; // 위 <- 앞
            dice[2] = up; // 뒤 <- 위
            dice[1] = back; // 바 <- 뒤
        }

        else if (command == 'S') {
            dice[0] = up; // 앞 <- 위
            dice[1] = front; // 바 <- 앞
            dice[2] = bottom; // 뒤 <- 바
            dice[3] = back; // 위 <- 뒤
        }
    }

    String printUpNumber() {
        StringBuilder sb = new StringBuilder();
        for (int num : upNumbers) sb.append(num).append("\n");
        return sb.toString();
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

