package pro_134239;// 16:03 ~ 16:35
import java.util.*;

class Solution {
    
    List<Integer> heights = new ArrayList<>();
    
    public double[] solution(int k, int[][] ranges) {
        double[] answer = new double[ranges.length];
        heights.add(k);
        
        while (k > 1) {
            if (k % 2 == 0) k /= 2;
            else {
                k *= 3;
                k++;
            }
            heights.add(k);
        } // k -> 6개
        
        double[] dp = new double[heights.size()];
        for (int i = 1; i < heights.size(); i++) {
            dp[i] = dp[i - 1] + (heights.get(i - 1) + heights.get(i)) * 1.0 / 2; 
        }
        
        int last = heights.size() - 1;
        for (int i = 0; i < ranges.length; i++) {
            int a = ranges[i][0];
            int b = ranges[i][1];
            
            if (a > heights.size() || (last + b) < a) {
                answer[i] = -1.0;
                continue;
            }
            
            double total =  dp[last];
            answer[i] = total - dp[a] - (dp[last] - dp[last + b]);
        } 
   
        return answer;
    }
}

// 11 * 3 + 1 = 34
// 17  * 3 * 1 = 52
// 26 
// 13
// 40
// 20
// 10
// 5
// 16
// 8
// 4
// 2
// 1
// 여기서 말하는 오프셋 [a, b] x = 0 에서 a 만큼: 마지막 인덱스에서 -b 만큼 