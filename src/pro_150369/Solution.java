package pro_150369;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        int deliver = 0, pickup = 0;
        long answer = 0;
        
        for (int i = n - 1; i >= 0; i--) {
            if (deliveries[i] != 0 || pickups[i] != 0) {
                int cnt = 0;
                while (deliver < deliveries[i] || pickup < pickups[i]) {
                    cnt++;
                    deliver += cap;
                    pickup += cap;
                }
                
                deliver -= deliveries[i];
                pickup -= pickups[i];
                
                answer += (i + 1) * cnt * 2; 
            }
        }
        return answer;
    }
}