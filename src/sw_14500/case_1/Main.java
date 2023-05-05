package sw_14500.case_1;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        Tetris tetris = new Tetris(n, m);
        
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++)
                tetris.setMap(row, col, parseInt(st.nextToken()));
        }
        
        tetris.run();
        System.out.println(tetris.getMaxSum());
    }
}

class Tetris {
     
    int n;
    int m;
    int maxSum; // 자연수의 합이므로 초기화 0
    List<Integer> sums = new ArrayList<>();
    int[][] map;
    
    Tetris(int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m]; // row, col
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    void run() { 

        // 모든 블록 좌상단 기준
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {

                if (m - col >= 4) { // 파랑 일자 (가로)
                    sums.add(map[row][col] + map[row][col + 1] + map[row][col + 2] + map[row][col + 3]);
                }

                if (n - row >= 4) { // 파랑 일자 (세로)
                    sums.add(map[row][col] + map[row + 1][col] + map[row + 2][col] + map[row + 3][col]);
                }

                if (n - row >= 2 && m - col >= 2) { // 노랑 사각형
                    sums.add(map[row][col] + map[row + 1][col] + map[row][col + 1] + map[row + 1][col + 1]);
                }


                if (n - row >= 2 && m - col >= 3) { // 주황
                    int upRow = map[row][col] + map[row][col + 1] + map[row][col + 2];
                    int downRow = map[row + 1][col] + map[row + 1][col + 1] + map[row + 1][col + 2];

                    sums.add(upRow + map[row + 1][col]);
                    sums.add(upRow + map[row + 1][col + 2]);
                    sums.add(downRow + map[row][col]);
                    sums.add(downRow + map[row][col + 2]);
                }

                if (n - row >= 3 && m - col >= 2) {
                    int leftCol = map[row][col] + map[row + 1][col] + map[row + 2][col];
                    int rightCol = map[row][col + 1] + map[row + 1][col + 1] + map[row + 2][col + 1];

                    sums.add(leftCol + map[row + 2][col + 1]);
                    sums.add(leftCol + map[row][col + 1]);
                    sums.add(rightCol + map[row][col]);
                    sums.add(rightCol + map[row + 2][col]);
                }

                if (n - row >= 3 && m - col >= 2) {
                    int middleRow = map[row + 1][col] + map[row + 1][col + 1];
                    sums.add(middleRow + map[row][col] + map[row + 2][col + 1]);
                    sums.add(middleRow + map[row + 2][col] + map[row][col + 1]);
                }

                if (n - row >= 2 && m - col >= 3) {
                    int middleCol = map[row][col + 1] + map[row + 1][col + 1];
                    sums.add(middleCol + map[row][col] + map[row + 1][col + 2]);
                    sums.add(middleCol + map[row + 1][col] + map[row][col + 2]);
                }

                if (n - row >= 3 && m - col >= 2) {
                    int middleRow = map[row + 1][col] + map[row + 1][col + 1];
                    sums.add(middleRow + map[row][col] + map[row + 2][col]);
                    sums.add(middleRow + map[row][col + 1] + map[row + 2][col + 1]);
                }

                if (n - row >= 2 && m - col >= 3) {
                    int middleCol = map[row][col + 1] + map[row + 1][col + 1];
                    sums.add(middleCol + map[row + 1][col] + map[row + 1][col + 2]);
                    sums.add(middleCol + map[row][col] + map[row][col +2]);
                }
            }
        }
        
    }
    
    int getMaxSum() {    
        for (int i = 0; i < sums.size(); i++) {
            maxSum = Math.max(sums.get(i), maxSum);
        }
        
        return maxSum;
    }
  
}


//   ***  *     *  ***   **  **   *    * 
//   *    *** ***    *    *  *    *    *
//                        *  *    **  **

// *    *  **    **  
// **  **   **  **   
//  *  *       

//    *    *   *  ***
//    **  **  ***  *
//    *    *