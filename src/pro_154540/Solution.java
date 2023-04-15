package pro_154540;

import java.util.*;

class Solution {
    
    static final int[] D_X = {-1, 0, 1, 0};
    static final int[] D_Y = {0, -1, 0, 1};
    
    int n, m, cnt;
    List<Integer> resList = new ArrayList<>();
    
    public int[] solution(String[] maps) {
        n = maps.length;
        m = maps[0].length();
        boolean[][] visited = new boolean[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cnt = 0;
                dfs(i, j, maps, visited);
                if (cnt != 0) resList.add(cnt);    
            }
        }
        
        if (resList.size() == 0) return new int[]{-1};
        
        int[] res = new int[resList.size()];
        resList.sort(Comparator.comparingInt(n -> n));
        for (int i = 0; i < resList.size(); i++) res[i] = resList.get(i); 
        
        return res;
    }
    
    private void dfs(int x, int y, String[] maps, boolean[][] visited) {
        if (visited[x][y] || maps[x].charAt(y) == 'X') return;
        visited[x][y] = true;
        
        for (int k = 0; k < 4; k++) {
            int newX = x + D_X[k];
            int newY = y + D_Y[k];
            
            if (validRange(newX, newY, maps, visited))  
                dfs(newX, newY, maps, visited);
        } // 마지막 모두 방문한 경우 경우 재귀 종료
        
        cnt += Character.getNumericValue(maps[x].charAt(y)); 
        return;
    }
    
    private boolean validRange(int x, int y, String[] maps, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < n && y < m 
            && !visited[x][y] && maps[x].charAt(y) != 'X';
    }

}