package pro_134239;// 16:03 ~ 16:35
import java.util.*;

class Solution {
    
    List<Integer> heights = new ArrayList<>();
    
    public double[] solution(int k, int[][] ranges) {
        double[] answer = new double[ranges.length]; // 정답 배열
        heights.add(k); // k를 단계별로 처리한 값을 heights에 넣기 위한 arrayList
        
        while (k > 1) {
            if (k % 2 == 0) k /= 2;
            else {
                k *= 3;
                k++;
            }
            heights.add(k);
        }
        
        double[] dp = new double[heights.size()]; // dp 선언

        // 너비 단위는 1 (ranges가 int 이차원 배열이므로), 인덱스 i의 k 와 i - 1의 k로 사다리꼴 넓이 구하기
        for (int i = 1; i < heights.size(); i++) {
            dp[i] = dp[i - 1] + (heights.get(i - 1) + heights.get(i)) * 1.0 / 2; // 이전 dp를 더함으로써 누적합
        }

        int last = heights.size() - 1;
        for (int i = 0; i < ranges.length; i++) {
            int a = ranges[i][0]; // 범위 a
            int b = ranges[i][1]; // 범위 b
            
            if (a > heights.size() || (last + b) < a) {  // 정적분되는 실제 범위가 유효하지 않은 경우
                // ex) last : 6, last = -3, a = 4 ==> 양수 정적분 불가
                answer[i] = -1.0;
                continue;
            }
            
            double total =  dp[last];
            answer[i] = total - dp[a] - (dp[last] - dp[last + b]); // 전체에서 양측 범위에 해당하는 적분 결과 빼서 중간 범위 구하기
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