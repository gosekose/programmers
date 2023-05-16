package str_20437;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(br.readLine());
        
        StringGame stringGame = new StringGame();
        for (int i = 0; i < t; i++) {
            stringGame.addStr(br.readLine());
            stringGame.addTarget(parseInt(br.readLine()));
        }
        stringGame.find();
    }
}

class StringGame {
    List<String> strs = new ArrayList<>(); // 문자열을 담을 리스트
    List<Integer> targets = new ArrayList<>(); // k를 담을 타겟 리스트
    Map<Character, List<Integer>> map = new HashMap<>(); // 해시맵
    
    void addStr(String str) {
        strs.add(str);
    }
    
    void addTarget(int k) {
        targets.add(k);
    }
    
    void init() {
        for (char c = 'a'; c <= 'z'; c++) map.put(c, new ArrayList<>());
    } // 미리 각 문자에 대한 어레이 리스트 생성하여 맵에 저장
    
    void find() {
        init();
        StringBuilder sb = new StringBuilder();

        // T: 100  W: 10000   idx: 10000 / 26 (k = 1) , 삭제 10000 / 26,  알파벳 26
        // O(T * W + T * 10000 / 26 * 2 * 26) = O(300만)
        for (int i = 0; i < strs.size(); i++) { // 문자열 순회
            String str = strs.get(i);
            int k = targets.get(i);
            int min = Integer.MAX_VALUE; // 먼저 최소값 맥스로 설정
            int max = -1; // max는 문자열 길이이므로 1 이상 나올 수 있으므로 -1로 최소화 설정
            
            for (int j = 0; j < str.length(); j++) { // 각 문자에 대한 charAt으로 탐색
                List<Integer> idxs = map.get(str.charAt(j));
                idxs.add(j); // 인덱스 추가하기
            }
            
            Set<Character> set = map.keySet(); // 맵에서 키셋을 가져오기
            
            for (Character ch : set) { // 키셋을 순회하며 ch 가져옴
                List<Integer> idxs = map.get(ch); // 각 맵에 저장되어 있는 인덱스를 저장한 리스트 가져오기
                
                if (idxs.size() >= k) { // k보다 같거나 크다면, k개 이상이 알파벳이 존재한다는 의미
                    for (int idx = 0; idx + (k - 1) < idxs.size(); idx++) { // 현재 인덱스부터 k - 1 큰 인덱스까지
                        min = Math.min(min, idxs.get(idx + k - 1) - idxs.get(idx) + 1); // 1 ~ 3까지의 길이는 3이므로 + 1
                        max = Math.max(max, idxs.get(idx + k - 1) - idxs.get(idx) + 1);
                    }
                } 
                idxs.clear(); // 저장된 인덱스 모두 제거하기
            }
            
            if (min == Integer.MAX_VALUE && max == -1) sb.append(-1).append("\n");
            else sb.append(min).append(" ").append(max).append("\n");
        }
        
        System.out.print(sb);
    }
}