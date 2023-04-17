package pro_138475;

import java.util.*;

class Solution {
    
    class Node {
        int x;
        int c;
        
        Node (int x, int c) {
            this.x = x;
            this.c = c;
        }
    }
    
    Node[] dp;
    
    void calDp(int e) {
        dp = new Node[e + 1];
        
        for (int i = 0; i <= e; i++) {
            dp[i] = new Node(i, 0);
        }
        
        
        for (int i = 1; i <= e; i++) 
            for (int j = i; j <= e; j += i)
                dp[j].c++;
        
        Arrays.sort(dp, Comparator.comparing((Node n) -> n.c)
                    .reversed()
                    .thenComparing(n -> n.x));        
    
    }
    
    
    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        
        calDp(e);
        
        for (int i = 0; i < starts.length; i++) {
            int s = starts[i];
            for (int j = 0; j <= e; j++) {
                
                if (dp[j].x >= s) {
                    answer[i] = dp[j].x;
                    break;
                }
            }
        }
        
        return answer;
    }
}