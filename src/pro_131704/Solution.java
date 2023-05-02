package pro_131704;

import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0;
        
        // 스택으로 보조 컨베이서 설정
        Stack<Integer> stack = new Stack<>();
        
        int idx = 0; // 인덱스
        
        for (int i = 1; i <= order.length; i++) {
            boolean flag = false; 
            
            if (i == order[idx]) { // 주문 상자 번호가 정순일 때, 
                idx++; // 인덱스 추가
                answer++; // 정답 + 1
                flag = true; 
            }
            
            // 보조 컨베이어가 비어있지 않고 보조 컨베이어의 맨 위가 주문 상자와 동일 할 때,
            while (!stack.isEmpty() && stack.peek() == order[idx]) {
                stack.pop(); // 스텍에서 제외
                idx++; // 인덱스 증가
                answer++; // 정답 수 증가
                flag = true;
            }
            
            if (!flag) { // 만약 1, 2 둘 다 아닌 경우
                stack.add(i); // 스택에 추가 
            }
        }
        
        // 정순 인덱스로 정순 상자 배열 모두 처리 후, 스택에 있는 값으로 비교
        while(!stack.isEmpty() && stack.peek() == order[idx]) {
            stack.pop();
            idx++;
            answer++;
        }

        return answer;
    }
}