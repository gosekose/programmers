package sw_12100.success;

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
        board.dfs(0);
        System.out.println(board.getAnswer());
    }
}

class Board {

    static final char[] DIR = {'U', 'D', 'L', 'R'}; // up, down, left, right

    int n;
    int answer;
    int[][] map;

    Board (int n) {
        this.n = n;
        map = new int[n][n];
    }

    void setBoard(int x, int y, int v) {
        map[x][y] = v;
    }

    int getAnswer() {
        return this.answer;
    }

    // dfs로 최대 5번 반복되는 과정에서 최대값 찾기
    void dfs(int step) {
        if (step == 5) { // count가 5라면 최대값 찾기
            for (int[] mapRow : map) {
                for (int num : mapRow) {
                    answer = Math.max(answer, num);
                }
            }
            return;
        }

        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            clone[i] = map[i].clone();
        }

        for (int i = 0; i < DIR.length; i++) { // 상 하 좌 우
            moveBlock(DIR[i]); // 블록 움직이기
            dfs(step + 1); // step 증가시켜서 다음 step 진행

            for (int j = 0; j < n; j++) map[j] = clone[j].clone();
        }
    }

    // block의 역할 --> 미는 블록이 두번 합쳐지더라도 다음 block을 0으로 만들어 세번 겹치지 않도록 보호
    void moveBlock(char dir) {
        if (dir == 'U') { // 블록을 위로 몰기 (낮은 행으로 몰기)
            for (int c = 0; c < n; c++) { // 선택된 맵의 열 (col)
                int row = 0;
                int block = 0;
                for (int r = 0; r < n; r++) { //선택된 맵의 행 (row)
                    if (map[r][c] != 0) { // 0이 아니라면 블록이 있음
                        if (map[r][c] == block) { // 블록과 선택한 블록이 같다면
                            map[row - 1][c] = block * 2; // 낮은 행으로 합치기
                            map[r][c] = 0; // 선택된 맵 위치 블록 0
                            block = 0; // 그 위치에 있는 블록 0
                        }

                        // 만약 r = 1, c = 1, row = 0 인 경우, 앞에 블록이 없으므로
                        // r = 0인 경우 map[r][c] != 0 통과, block은 0이 유지되므로
                        // 자연스럽게 블록이 없는 공간에 밀어넣게 됨 이후 row++
                        else {
                            block = map[r][c]; // 현재위치 블록 설정
                            map[r][c] = 0; // r, c 위치 0으로 설정하기
                            map[row][c] = block; // 위로 밀어서 블록 값을 위치 시키기 (이 경우 제자리에 위치할 수 있으므로 map[r][c]보다 뒤에 있어야 함!!!
                            row++; // 다음 밀 곳은 한 칸 아래로 내린 곳
                        }
                    }
                }
            }
        }

        else if (dir == 'D') { // 블록을 아래로 몰기
            for (int c = 0; c < n; c++) {
                int row = n - 1; // 처음으로 옮겨져야 하는 도착 위치는 마지막 행
                int block = 0;
                for (int r = n - 1; r >= 0; r--) {
                    if (map[r][c] != 0) {
                        if (map[r][c] == block) {
                            map[row + 1][c] = block * 2;
                            map[r][c] = 0;
                            block = 0;
                        }

                        else {
                            block = map[r][c];
                            map[r][c] = 0;
                            map[row][c] = block;
                            row--; // 다음 번 블록은 맨 아래 위치에서 한 칸 올린 곳
                        }
                    }
                }
            }
        }

        else if (dir == 'L') { // 블록을 왼쪽으로 몰기
            for (int r = 0; r < n; r++) {
                int col = 0; // 옮겨질 위치의 열
                int block = 0;
                for (int c = 0; c < n; c++) {
                    if (map[r][c] != 0) {
                        if (map[r][c] == block) {
                            map[r][col - 1] = block * 2;
                            map[r][c] = 0;
                            block = 0;
                        }
                        else {
                            block = map[r][c];
                            map[r][c] = 0;
                            map[r][col] = block;
                            col++;
                        }
                    }
                }
            }
        }

        else if (dir == 'R') { // 블록을 오른쪽으로 몰기
            for (int r = 0; r < n; r++) {
                int col = n - 1;
                int block = 0;
                for (int c = n - 1; c >= 0; c--) {
                    if (map[r][c] != 0) {
                        if (map[r][c] == block) {
                            map[r][col + 1] = block * 2;
                            map[r][c] = 0;
                            block = 0;
                        }

                        else {
                            block = map[r][c];
                            map[r][c] = 0;
                            map[r][col] = block;
                            col--;
                        }
                    }
                }
            }
        }
    }
}

