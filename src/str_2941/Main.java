package str_2941;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<String> set = new HashSet<>(List.of("c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z="));
        
        int result = 0;
        String str = br.readLine();
        String tmp = "";
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);            
            tmp += c;
            
            if (tmp.length() == 3) {
                if (tmp.equals("dz=")) {
                    result++;
                    tmp = "";    
                } else {
                    result += 2;
                    tmp = String.valueOf(c);
                }
            }
            
            if (tmp.length() == 2) {
                if (tmp.equals("dz")) continue;
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