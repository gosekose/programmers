package sw_3190;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = parseInt(br.readLine());
        int K = parseInt(br.readLine());

        Game game = new Game(N);

        StringTokenizer st;
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            game.setApple(parseInt(st.nextToken()), parseInt(st.nextToken()));
        }

        int L = parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            game.addDirection(parseInt(st.nextToken()), st.nextToken());
        }

        System.out.println(game.run());
    }
}

class Game {

    int n;
    int time;
    boolean[][] nowVisited;
    boolean[][] map;
    char nowDir = 'R';
    List<Tail> tails = new LinkedList<>(); // 꼬리 추가 및 삭제
    Queue<Direction> dirs = new ArrayDeque<>();

    Game (int n) {
        this.n = n;
        map = new boolean[n + 1][n + 1];
        nowVisited = new boolean[n + 1][n + 1];
    }

    void setApple(int row, int col) {
        map[row][col] = true; // apple
    }

    void addDirection(int ttl, String dir) {
        dirs.add(new Direction(ttl, dir));
    }

    int run() {

        int r = 1; // 행
        int c = 1; // 열
        tails.add(new Tail(r, c));

        while (true) { // 반복

            if (!dirs.isEmpty() && time == dirs.peek().ttl) { //비어 있지 않고 ttl와 같음
                Direction nextDir = dirs.poll();

                if (nowDir == 'U') { // 위
                    if (nextDir.dir.equals("L")) nowDir = 'L';
                    else nowDir = 'R';
                }

                else if(nowDir == 'D') {
                    if (nextDir.dir.equals("L")) nowDir = 'R';
                    else nowDir = 'L';
                }

                else if (nowDir == 'L') {
                    if (nextDir.dir.equals("L"))nowDir = 'D';
                    else nowDir = 'U';
                }

                else if (nowDir == 'R') {
                    if (nextDir.dir.equals("L")) nowDir = 'U';
                    else nowDir = 'D';
                }
            }

            // move
            switch (nowDir) {
                case 'U':
                    r--;
                    break;
                case 'D':
                    r++;
                    break;
                case 'L':
                    c--;
                    break;
                case 'R':
                    c++;
                    break;
            }

            if (!isValid(r, c)) {
                time++;
                break;
            }

            nowVisited[r][c] = true; // 머리가 방문한 곳 방문 추가

            // 위치한 곳에 사과가 있는지 파악하기
            if (!map[r][c]) { // 없다면 머리 증가 후 꼬리 제거
                tails.add(new Tail(r, c)); // 머리 추가

                Tail tail = tails.get(0); // 맨 마지막 꼬리 가져오기
                nowVisited[tail.r][tail.c] = false; // 꼬리가 사라지므로 false
                tails.remove(0); // 꼬리 제거
            }

            else {
                tails.add(new Tail(r, c)); // 사과가 있다면 머리 위치 추가
                map[r][c] = false; // 사과 제거하기
            }

            time++;
        }

        return time;
    }

    // 이미 몸체의 일부가 방문하고 있는 경우 종료
    boolean isValid(int r, int c) { // 맵의 유효한 범위에 아직 방문하지 않은 곳이여야
        return r > 0 && r <= n && c > 0 && c <= n && !nowVisited[r][c];
    }

    boolean isNotAppleAndAtTail(int r, int c, Tail tail) {
        return !map[r][c] && tail.r == r && tail.c == c; // 사과가 없고 꼬리와 위치가 같은 경우
    }

    class Tail {
        int r;
        int c;

        Tail (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    class Direction {
        int ttl;
        String dir;

        Direction (int ttl, String dir) {
            this.ttl = ttl;
            this.dir = dir;
        }
    }
}

// 0 0 0 0 0 0 -> 움직이니까 + 1

// 7 7 7 7 0 0
// 0 7 0 7
// 0 7 7 7