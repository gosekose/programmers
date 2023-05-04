package sw_12100.fail;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = parseInt(br.readLine());

        Board board = new Board(n);

        for (int i = 0 ; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board.setBoard(i, j, parseInt(st.nextToken()));
            }
        }
        System.out.println(board.moveBlock());
    }
}

class Board {

    static final int[] DX = {-1, 0, 1, 0}; // 좌 우
    static final int[] DY = {0, 1, 0, -1}; // 상 하

    int n;
    int[][] map;
    int answer;

    Board (int n) {
        this.n = n;
        map = new int[n][n];
    }

    void setBoard(int x, int y, int v) {
        map[x][y] = v;
    }

    int moveBlock() {

        for (int[] mapRow : map) {
            for(int num : mapRow) {
                answer = Math.max(answer, num); // 먼저 블록을 이동시키기 전 answer 초기화
            }
        }

        Queue<Block> queue = new ArrayDeque<>(); // block 큐에 넣기
        queue.add(new Block(0, map.clone(), new boolean[n][n])); // queue에 넣을 때, 복사해서 넣기

        while(!queue.isEmpty()) {
            Block now = queue.poll();
            if (now.step > 5) continue; // 블록이 5번 움직이면 종료하기
            answer = Math.max(answer, now.getMax()); // 블록이 5번 이하로 움직인 경우 answer 업데이트

            for (int k = 0; k < 4; k++) {
                int dx = DX[k];
                int dy = DY[k];

                if (dx == -1) { // 왼쪽으로 이동, x: -1
                    for (int i = 0; i < n; i++) {
                        for (int j = 1; j < n - 1; j++) {
                            if (now.map[i][j] == 0) continue; //  0이라면 패스

                            int bj = j - 1; // 합치려는 열
                            if (now.map[i][j - 1] == 0) { // 만약 왼쪽으로 가려는 열의 now 값이 0이라면
                                while(bj > 0) {
                                    if (now.map[i][bj] != 0) break;
                                    bj--; // 최대한 왼쪽으로 합치기 위해 bj--
                                }
                            }
                            // 합치려는 열과 현재 열이 같고, 이전 열이 합쳐지지 않았다면
                            if (now.map[i][bj] == now.map[i][j] && !now.isSum[i][bj]) {
                                now.map[i][bj] *= 2; // 합치기
                                now.map[i][j] = 0; // 합치면서 현재 열 0 초기화
                                now.isSum[i][bj] = true; // 합친 배열 true
                            }
                        }
                    }
                } else if (dx == 1) { // 오른쪽으로 이동 x: 1
                    for (int i = 0; i < n; i++) {
                        for (int j = n - 2; j > 0; j--) {
                            if (now.map[i][j] == 0) continue; //  0이라면 패스

                            int bj = j + 1; // 합치려는 열
                            if (now.map[i][j + 1] == 0) { // 다음 열이 0이라면
                                while(bj < n) {
                                    if (now.map[i][bj] != 0) break;
                                    bj++; // 만약 0번째 인덱스라면 최대한 내리기 위해 idx++
                                }
                            }

                            // 합치려는 열과 지금 열이 같고, 합쳐지기 전이라면
                            if (now.map[i][bj] == now.map[i][j] && !now.isSum[i][bj]) {
                                now.map[i][bj] *= 2; // 합치기
                                now.map[i][j] = 0; // 합치면서 로우를 올리므로 0
                                now.isSum[i][bj] = true; // 합친 배열 true
                            }
                        }
                    }
                } else if (dy == -1) { // y: -1 (위로 올리기)
                    for (int i = 1; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            if (now.map[i][j] == 0) continue; //  0이라면 패스

                            int bi = i - 1; // 끝까지 올릴 수 있는데까지 올리기
                            if (now.map[i - 1][j] == 0) { // 만약 올리려는 행의 now 값이 0이라면
                                while(bi > 0) { // 양수일 때 까지
                                    if (now.map[bi][j] != 0) break;
                                    bi--;
                                }
                            }

                            // 이전 행과 현재 행의 값이 같다면
                            if (now.map[bi][j] == now.map[i][j] && !now.isSum[bi][j]) {
                                now.map[bi][j] *= 2; // 합치기
                                now.map[i][j] = 0; // 합치면서 로우를 올리므로 0
                                now.isSum[bi][j] = true; // 합친 배열 true
                            }
                        }
                    }
                } else if (dy == 1) { // y: 1 아래로 내림
                    for (int i = n - 2; i > 0; i--) {
                        for (int j = 0; j < n; j++) {
                            if (now.map[i][j] == 0) continue; //  0이라면 패스

                            int bi = i + 1; // 끝까지 내릴 때까지 내리기
                            if (now.map[i + 1][j] == 0) { // 만약 내리려는 행의 now 값이 0이라면
                                while(bi < n - 1) {
                                    if (now.map[bi][j] != 0) break;
                                    bi++; // 만약 0번째 인덱스라면 최대한 내리기 위해 idx++
                                }
                            }

                            // 이전 행과 현재 행이 같고, 아직 합쳐지기 전이라면
                            if (now.map[bi][j] == now.map[i][j] && !now.isSum[bi][j]) {
                                now.map[bi][j] *= 2; // 합치기
                                now.map[i][j] = 0; // 합치면서 로우를 올리므로 0
                                now.isSum[bi][j] = true; // 합친 배열 true
                            }
                        }
                    }
                }

                int[][] next = now.map.clone();
                for (int i = 0; i < n; i++) {
                    System.out.println(" ");
                    for (int j = 0; j < n; j++)
                        System.out.print(next[i][j] + " ");
                }
                Block block = new Block(now.step + 1, next, new boolean[n][n]);
                queue.add(block);
            }
        }
        return answer;
    }

    class Block {
        int step;
        int[][] map;
        boolean[][] isSum;

        Block (int step, int[][] map, boolean[][] isSum) {
            this.step = step;
            this.map = map.clone();
            this.isSum = isSum;
        }

        int getMax() {
            int max = 0;

            for (int[] mapRow : map) {
                for (int num : mapRow) {
                    max = Math.max(max, num);
                }
            }

            return max;
        }
    }
}

// 4
// 2 0 0 0
// 2 2 0 0
// 2 0 0 0
// 0 0 0 0

// 4

// 4
// 2 0 2 8
// 0 0 2 2
// 0 0 0 0
// 0 0 0 0

// 2
// 4 4
// 4 4
// 16