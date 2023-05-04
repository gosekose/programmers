package sw_3190;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = parseInt(br.readLine());
        int K = parseInt(br.readLine());

        Game game = new Game(N); // game 인스턴스 선언

        StringTokenizer st;
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            game.setApple(parseInt(st.nextToken()), parseInt(st.nextToken())); // 사과 등록
        }

        int L = parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            game.addDirection(parseInt(st.nextToken()), st.nextToken()); // 시간에 맞춰 변경해야하는 시간과 방향 설정
        }

        System.out.println(game.run());
    }
}

class Game {

    int n;
    int time; // 출력해야 하는 시간
    boolean[][] nowVisited; // 현재 뱀의 몸(머리, 꼬리 포함)이 걸쳐 있는지 판단
    boolean[][] map; // 사과 있는지 판단
    char nowDir = 'R'; // 현재 방향 (계속 바뀜)
    List<Tail> tails = new LinkedList<>(); // 꼬리 추가 및 삭제를 위한 링크드 리스트
    Queue<Direction> dirs = new ArrayDeque<>(); // 시간에 맞춰 방향 변경을 위한 큐

    Game (int n) {
        this.n = n;
        map = new boolean[n + 1][n + 1]; // n by n 이므로 n + 1로 초기화
        nowVisited = new boolean[n + 1][n + 1];
    }

    void setApple(int row, int col) {
        map[row][col] = true; // apple 설정
    }

    void addDirection(int ttl, String dir) {
        dirs.add(new Direction(ttl, dir)); // 시간에 맞는 방향 설정
    }

    int run() {

        int r = 1; // 행
        int c = 1; // 열
        tails.add(new Tail(r, c)); // 시작은 머리와 꼬리가 동일 하므로 꼬리에 추가

        while (true) { // 반복

            if (!dirs.isEmpty() && time == dirs.peek().ttl) { //비어 있지 않고 ttl와 같음
                Direction nextDir = dirs.poll(); // 다음 방향을 위한 Direction 인스턴스 큐에서 빼기

                if (nowDir == 'U') { // 각 방향에 맞춰 회전 후 방향 재조정
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

            // 회전을 마친 경우 혹은 회전이 없는 경우 nowDir에 방향에 맞춰 움직이기 시작
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

            if (!isValid(r, c)) { // 이동하려는 곳이 인덱스에 벗어나거나, 몸통이 있는 곳이라면 종료
                time++; // 종료시간은 마지막 움직이는 경우도 포함이므로
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

            time++; // 모든 움직임이 끝났으므로 시간++
        }

        return time;
    }

    // 이미 몸체의 일부가 방문하고 있는 경우 종료
    boolean isValid(int r, int c) { // 맵의 유효한 범위에 아직 방문하지 않은 곳이여야
        return r > 0 && r <= n && c > 0 && c <= n && !nowVisited[r][c];
    }

    class Tail {
        int r; // row
        int c; // col

        Tail (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    class Direction {
        int ttl; // time to live
        String dir; // 방향성

        Direction (int ttl, String dir) {
            this.ttl = ttl;
            this.dir = dir;
        }
    }
}
