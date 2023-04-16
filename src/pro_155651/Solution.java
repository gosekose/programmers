package pro_155651;

import java.util.*;

class Solution {
    
    public int solution(String[][] bookTime) {
        PriorityQueue<Integer> in = new PriorityQueue<>();
        PriorityQueue<Integer> out = new PriorityQueue<>();
        int max = 0, cnt = 1;
        
        for (String[] time : bookTime) {
            in.offer(getTime(time[0]));
            out.offer(getTime(time[1]) + 10);
        }
        
        while(!out.isEmpty() && !in.isEmpty()) {
            int o = out.poll();
            cnt--;
            
            while(!in.isEmpty() && in.peek() < o) {
                in.poll();
                cnt++;
            }
            max = Math.max(max, cnt);
        }
        
        return max;        
    }
    
    private int getTime(String str) {
        String[] t = str.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }
}
