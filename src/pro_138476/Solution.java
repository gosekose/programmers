package pro_138476;

import java.util.*;
class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < tangerine.length; i++) {
            int t = tangerine[i];
            if (map.containsKey(t)) map.put(t, map.get(t) + 1);
            else map.put(t, 1);
        }
        
        List<Integer> list = new ArrayList<>(map.keySet());
        
        list.sort(Comparator.comparing((Integer n) -> map.get(n)).reversed());
        
        for (Integer i : list) { 
            k -= map.get(i);
            answer++;
            if (k <= 0) break;
        }
                
        return answer;
    }
}