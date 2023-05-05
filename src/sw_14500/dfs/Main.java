package sw_14500.dfs;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        Board board = new Board(n, m);
        
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++) {
                board.setMap(row, col, parseInt(st.nextToken()));
            }
        }
        
        board.run();
        
        System.out.println(board.getMaxSum());
    }
}

class Board{
    
    static final int[] DR = {-1, 0, 1, 0};
    static final int[] DC = {0, -1, 0, 1};
    
    int n;
    int m;
    int maxSum;
    int[][] map;
    boolean[][] visited;
    
    Board(int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m];
        visited = new boolean[n][m];
    }

    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }

    void run() {
        
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int count = 1;
                int sum = map[row][col]; // 합 구하기

                visited[row][col] = true; // 맨 처음 선택하는 좌상단 좌표는 방문처리
                dfs(row, col, sum, count);
                visited[row][col] = false; // dfs 종료 후 맨 처음 선택한 시작점 방문 해제
            }
        }
    }
    
    void dfs(int row, int col, int sum, int count) {
        
        if (count == 4) {
            maxSum = Math.max(maxSum, sum); // 최대 값 구하기
            return; // 재귀 종료
        }

        for (int i = 0; i < 4; i++) {
            int nextR = row + DR[i]; // 이동할 row  
            int nextC = col + DC[i]; // 이동할 col

            if (!isValid(nextR, nextC)) continue; // 유효한 범위가 아니거나 이미 방문한 경우

            if (count == 2) {
                visited[nextR][nextC] = true;
                dfs(row, col, sum + map[nextR][nextC], count + 1);
                visited[nextR][nextC] = false;
            }

            visited[nextR][nextC] = true; // 방문 여부 표시
            dfs(nextR, nextC, sum + map[nextR][nextC], count + 1); // dfs 재귀
            visited[nextR][nextC] = false; // 현재 방문한 곳 false 표시
        }
    }
    
    
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m && !visited[row][col];
    }
    
    int getMaxSum() {
        return this.maxSum;
    }
    
}