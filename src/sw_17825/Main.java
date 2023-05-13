package sw_17825;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        Board board = new Board();
        
        for (int i = 0; i < 10; i++) {
            board.setDice(i, parseInt(st.nextToken()));
        }
        
        board.init();
        board.dfs(0, 0);
        System.out.println(board.getMaxValue());
    }
}

class Board {
    int[] dice = new int[10];
    int[][] map = new int[4][30];
    boolean[][] check = new boolean[4][30];
    int[] mapRow = new int[4];
    int[] mapCol = new int[4]; 
    int maxValue = -1;
    
    Board(){}
    
    void setDice(int i, int value) {
        dice[i] = value;
    }
    
    void init() {
        map[0] = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, -1, -1, -1, -1, -1, -1, -1 };
		map[1] = new int[] { 0, 0, 0, 0, 0, 10, 13, 16, 19, 25, 30, 35, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		map[2] = new int[] { 0, 0, 0, 0, 0,  0,  0,  0,  0,  0, 20, 22, 24, 25, 30, 35, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		map[3] = new int[] { 0, 0, 0, 0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 30, 28, 27, 26, 25, 30, 35, 40, -1, -1, -1, -1, -1 };
    }
    
    int getMaxValue() {
        return maxValue;
    }
    
    void dfs(int idx, int sum) {
        if (idx == 10) {
            maxValue = Math.max(maxValue, sum);
            return;
        }
        
        for (int i = 0; i < 4; i++) {
            int tmpRow = mapRow[i];
            int tmpCol = mapCol[i];
            
            if (map[tmpRow][tmpCol] == -1) continue;
            check[tmpRow][tmpCol] = false;
            
            if (tmpRow == 0) { // 현재 4개의 map[][] 배열에서 0번째 인덱스에 속한 경우
                switch (tmpCol) { // map[0] 의 배열에 위치하여 다음의 인덱스에 속한 경우 (블루 지점)
                    case 5: 
                        mapRow[i] += 1;
                        break;
                    case 10:
                        mapRow[i] += 2;
                        break;
                    case 15:
                        mapRow[i] += 3;
                        break;
                }
            }
            
            mapCol[i] += dice[idx]; // 던져진 주사위 값 만큼 idx 업데이트
            
            if (mapRow[i] != 0) { // 만약 현재 블루 지점에서 한 번 경로를 바꾼 경우
                switch (map[mapRow[i]][mapCol[i]]) {
                    case 40:
                        mapRow[i] = 0;
                        mapCol[i] = 20;
                        break;
                    case 25:
                        mapRow[i] = 1;
                        mapCol[i] = 9;
                        break;
                    case 30:
                        mapRow[i] = 1;
                        mapCol[i] = 10;
                        break;
                    case 35:
                        mapRow[i] = 1;
                        mapCol[i] = 11;
                }
            }
            
            if (!check[mapRow[i]][mapCol[i]]) {
                if (map[mapRow[i]][mapCol[i]] != -1) {
                    check[mapRow[i]][mapCol[i]] = true;
                    dfs(idx + 1, sum + map[mapRow[i]][mapCol[i]]);
                    check[mapRow[i]][mapCol[i]] = false;
                } else {
                    dfs(idx + 1, sum);
                }
            }
            mapRow[i] = tmpRow;
            mapCol[i] = tmpCol;
            check[mapRow[i]][mapCol[i]] = true;
        }
    }
}