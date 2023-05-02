package pro_77886;

import java.util.*;

class Solution {
    public String[] solution(String[] s) {
        String[] answer = new String[s.length]; // 정답 배열 설정
        StringBuilder sb; 
        
        for (int i = 0; i < s.length; i++) {
            String str = s[i]; 
            Stack<Character> stack = new Stack<>(); 
            
            int cnt = 0; // 연속된 캐릭터 타입의 값이 110인 것의 개수 
            
            for (int j = 0; j < str.length(); j++) {
                char z = str.charAt(j); // 스택이 2 이상 && z가 0이라면 스택에서 두개를 빼서  110 비교
                
                if(z == '0' && stack.size() >= 2) {
                    char y = stack.pop(); 
                    char x = stack.pop();  
                    
                    if (x == '1' && y == '1' && z == '0') {
                        cnt++; 
                    } else {
                        stack.push(x);  // 110이 아니라면 다시 후입 선출을 위해 x y z 순으로 스택에 넣기
                        stack.push(y);
                        stack.push(z);
                    }
                } else {
                    stack.push(z); // 스택 사이즈가 2보다 작다면 z가 1이더라도 스택에서 빼서 110을 만들 수 없으므로
                }     
            }
            
            int idx = stack.size(); // idx는 stack 사이즈 (연속 수가 110이 아닌 경우 스택에 다 넣음)
            
            sb = new StringBuilder();
            
            // 참조1
            boolean flag = false; // 맨 처음 0이 위치하는 지점 전까지 idx를 감소하기 위함 하단에 예시
            
            while(!stack.isEmpty()) {
                if (!flag && stack.peek() == '1') idx--;
                
                if (!flag && stack.peek() == '0') flag = true;  // 스택에서 뺀 값이 0이라면 0 자리에 위치
                
                sb.insert(0, stack.pop()); // sb에 정순으로 입력함으로써 원래 문자열에서 110만 뺀 상태
            }
            
            if (cnt > 0) { // 110 개수가 양수라면
                while (cnt > 0) {
                    sb.insert(idx, "110"); // 스택에서 뺄 때 (역순이므로 나중에 나오는 값이 앞쪽) 0이 나오는 다음의 인덱스
                    idx += 3; // 3개를 추가했으므로 +3
                    cnt --; // cnt--
                }
                answer[i] = sb.toString();
            } else {
                answer[i] = s[i]; // 만약 추가할 110이 없다면 문자열 그대로 리턴
            }
        }
        return answer;
    }

}

// 기존 배열이
// 1011010101111 
// stack : 1010101111
// cnt : 1

// stack size = 10
// 1111 0 
// 10 - 4
// idx = 6
// sb = 1010101111 
// cnt = 1
// 101010110111
// idx += 3
// cnt = 0

// 1011111
// 110 (x)
// 1011011011111
// 0이 위치한 지점 뒤로 옮길 수 있음
// 10 > 110