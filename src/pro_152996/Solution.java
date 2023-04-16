package pro_152996;

import java.util.*;

class Solution {
    
    public long solution(int[] weights) {
        Map<Double, Integer> map = new HashMap<>();
        long res = 0;
        Arrays.sort(weights);
        
        for (int weight : weights) {
            res += isPartner(weight, map);
        }
        return res;
    }
    
    private long isPartner(int w, Map<Double, Integer> map) {
        long res = 0;
        double d1 = w * 1.0;
        double d2 = (w * 2.0) / 3.0;
        double d3 = (w * 1.0) / 2.0;
        double d4 = (w * 3.0) / 4.0;
        
        if (map.containsKey(d1)) res += map.get(d1);
        if (map.containsKey(d2)) res += map.get(d2);
        if (map.containsKey(d3)) res += map.get(d3);
        if (map.containsKey(d4)) res += map.get(d4);
        
        map.put(w * 1.0, map.getOrDefault(w * 1.0, 0) + 1);
        return res;
    }
    
}

