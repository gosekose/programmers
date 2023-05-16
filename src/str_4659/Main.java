package str_4659;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String str = "";
        
        boolean isPossible; // 가능한 경우 판단 
        boolean flag; // 모음 중 하나는 반드시 포함 
        boolean isVowelSeq; // 모음이 연속인지 ?
        int seq; // 모음 연속 
        char pre; // 이전 문자열 
        
        while ((str = br.readLine()) != null && !str.equals("end")) {
            pre = str.charAt(0);
            seq = 0;
            flag = isVowelSeq = false;
            isPossible = true;
            
            if (str.length() >= 1) { // 문자열이 1개 이상인 경우 pre 설정, flag = true
                if (pre == 'a' || pre == 'e' || pre == 'i' || pre == 'o' || pre =='u') {
                    flag = true;
                    isVowelSeq = true; // 모음인 경우 true, 자음인 경우 false
                } 
                seq++; // seq 증가                    
            }
            
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);
                
                if (!flag && (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c =='u')) flag = true; // 아직 모음 판단 안한 경우
                
                if (pre == c) { // 3번 같은 글자 연속 두번 불가
                    if (!(pre == 'e' || pre == 'o')) {
                        isPossible = false; // 불가능한 경우 종료
                        break;
                    }
                }
                
                // 2번 판단 
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c =='u') {
                    if (isVowelSeq) seq++; // 모음 seq 증가 
                    else { // 이전이 자음이었다면
                        isVowelSeq = true;
                        seq = 1;
                    }
                } else { // 자음이라면
                    if (isVowelSeq) { // 이전이 모음이었다면
                        isVowelSeq = false;
                        seq = 1;
                    } else seq++; // 이전에도 자음이었으므로 seq 증가
                }
                
                if (seq >= 3) { 
                    isPossible = false;
                    break;
                }

                pre = c;
            }
            
            if (flag && isPossible) {
                sb.append("<").append(str).append("> is acceptable.").append("\n");
            } else {
                sb.append("<").append(str).append("> is not acceptable.").append("\n");
            }
        }
        
        System.out.print(sb);
    }
}