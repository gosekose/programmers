package str_6550;

import java.util.*;
import java.io.*;

public class Main {
    
    static Queue<Character> queue = new ArrayDeque<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        String str;
        String target;
        String seq;
        StringTokenizer st;
        
        while ((str = br.readLine()) != null && !str.equals("EOF")) { // 종료 조건은 문자열이 null이 아니고 EOF가 아니여야 함
            st = new StringTokenizer(str);
            target = st.nextToken();
            seq = st.nextToken();

            for (int i = 0; i < target.length(); i++) {
                queue.add(target.charAt(i)); // 큐에 target 문자열 넣기
            }
            
            for (int i = 0; i < seq.length(); i++) {
                if (queue.isEmpty()) break; // seq를 순회하며 target을 넣은 queue가 비면 종료
                if (queue.peek() == seq.charAt(i)) { // 만약 큐에 있는 문자열과 seq 문자열이 같다면 큐에서 빼기
                    queue.poll();
                }
            }

            if (queue.isEmpty()) { // 큐가 비어있다면 부분 문자열임을 의미
                sb.append("Yes").append("\n");
            } else {
                while(!queue.isEmpty()) { // 큐를 다시 비워주기
                    queue.poll();
                }
                sb.append("No").append("\n");
            }
        }
        
        System.out.print(sb);
    }
}
