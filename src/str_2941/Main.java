package str_2941;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<String> set = new HashSet<>(List.of("c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z="));
        
        int result = 0; // 결과 출력
        String str = br.readLine();
        String tmp = ""; // 문자열
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);            
            tmp += c; // 문자열 추가 하기
            
            if (tmp.length() == 3) { // 3개인 경우 dz 이후 문자
                if (tmp.equals("dz=")) {
                    result++; // 하나의 문자
                    tmp = ""; // 문자를 모두 소모한 경우이므로
                } else {
                    result += 2; // 앞의 두개가 불가한 문자이므로
                    tmp = String.valueOf(c); // 새로운 c 설정
                }
            }
            
            if (tmp.length() == 2) {
                if (tmp.equals("dz")) continue; // dz=일수 있으므로 넘기기
                if (set.contains(tmp)) tmp = "";
                else tmp = String.valueOf(c);
                result++; // 만약 set에 있다면 하나로 쳐서 ++, 만약 아니라면, 이전 길이에 대한 ++,                
            }
        }
        
        if (tmp.length() >= 1) { // 한개 혹은 두개에서 끝난 경우
            result += tmp.length(); // 문자열이 있다는 것은 크로아티아 알파벳 만족 불가
        }
        
        System.out.print(result);
    }
}