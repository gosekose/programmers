package pro_86052;

import java.util.*;

class Solution {
    
    static int row, col; // row, col
    static int[] dRow = {-1, 0, 1, 0}; // 도착점 기준으로 왼, 아래, 오른, 위 
    static int[] dCol = {0, -1, 0, 1};
    static boolean[][][] isVisited;
    
    public int[] solution(String[] grid) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        row = grid.length;
        col = grid[0].length();
        
        isVisited = new boolean[row][col][4];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int d = 0; d < 4; d++) {
                    if (!isVisited[i][j][d]) 
                        list.add(light(grid, i, j, d));
                }
            }
        }
        list.sort(Comparator.comparingInt((Integer n) -> n));
        
        int[] answer = new int[list.size()];
        for (int i = 0; i < answer.length; i++) answer[i] = list.get(i);
        
        return answer;
    }
    
    private static int light(String[] grid, int r, int c, int d) {
        int cnt = 0;
        
        while (true) {
            if (isVisited[r][c][d]) break;
            
            cnt++;
            isVisited[r][c][d] = true;
            
            if (grid[r].charAt(c) == 'L')
                d = d == 0 ? 3 : d - 1;
            else if (grid[r].charAt(c) == 'R')
                d = d == 3 ? 0 : d + 1;

            r = (r + dRow[d] + row) % row;
            c = (c + dCol[d] + col) % col;
            
        }
        
        return cnt;
    }
    
}