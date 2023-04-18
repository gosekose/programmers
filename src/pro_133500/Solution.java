package pro_133500;

import java.util.*;

class Solution {
    
    int answer;
    List<List<Integer>> map = new ArrayList<>();
    
    public int solution(int n, int[][] lighthouse) {
        answer = 0;
        
        for (int i = 0; i <= n; i++) map.add(new ArrayList<>());
        
        for (int i = 0; i < lighthouse.length; i++) {
            map.get(lighthouse[i][0]).add(lighthouse[i][1]); // 양방향 연결
            map.get(lighthouse[i][1]).add(lighthouse[i][0]); // 양방향 연결
        }
        
        dfs(1, 0);
        
        return answer;
    }
    
    private int dfs(int current, int before) {
        // System.out.println("current = " + current + ", before = " + before);
        
        if (map.get(current).size() == 1 && map.get(current).get(0) == before) return 1;
        
        int t = 0;
        
        for (int i = 0; i < map.get(current).size(); i++) {
            int next = map.get(current).get(i);
            if (next == before) continue;
            t += dfs(next, current);
        }
        
        System.out.println("current = " + current + ", before = " + before);
        if (t == 0) return 1;
        
        answer++;
        return 0;
    }
}

//   1
//    \ 
//     2
//    /
//   3

// 1
//  \
//   2
//    \
//     3
//    / \
//   4   5
// -> 2, 3 선택 되어야

// current = 1, i = 0, size = 1, next = 2, before = 0
// next, current  = 2, 1
// current = 2, i = 0, size = 1, next = 3, before 1
// next, current = 3, 2
// current = 3, i = 0, size = 3, next = 2, before = 2 --> continue;
// current = 3, i = 1, size = 3, next = 4, before = 2
// next, current = 4, 3
// current = 4, i = 0, size = 3, next = 3, before = 3 --> return 1
// current = 3, i = 2, size = 3, next = 5, before = 2
// next, current = 5, 3
// current = 5, i = 0, size = 1, next = 3, before = 3 --> return 1
// current = 3일 때, t = 2
// answer ++;
// return 0
// next, current = 2, 1 다시 시작
// t = 1
// answer++
// return 0
// current, before = 1, 0 다시 시작
// t = 0
// return 1;
// 더이상 호출 x