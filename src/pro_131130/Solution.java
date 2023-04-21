package pro_131130;

import java.util.*;

class Solution {
    
    List<Integer> res = new ArrayList<>();
    
    public int solution(int[] cards) {
        boolean[] visited = new boolean[cards.length];
        
        for (int i = 0; i < cards.length; i++) {
            if (visited[i]) continue;
            int step = open(i, 0, cards, visited);
            res.add(step);
        }
        
        if (res.size() < 2) return 0;
        
        res.sort(Comparator.comparingInt((Integer n) -> n).reversed());

        return res.get(0) * res.get(1);
    }
    
    int open(int idx, int cnt, int[] cards, boolean[] visited) {
        if (visited[idx]) return cnt;
        visited[idx] = true;
        return open(cards[idx] - 1, ++cnt, cards, visited);
    }
}