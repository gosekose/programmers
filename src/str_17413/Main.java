package str_17413;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        
        StringBuilder sb = new StringBuilder();

        boolean flag = false; // '<' 의 시작여부

        int start = -1; // 단어를 뒤집어야할 때 역순으로 순회하기 위한 인덱스 (초기화  -1)
        int end = -1;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == '<') {
                if (start != -1) { // 만약 '<' 이전에 'apple<' 처럼 뒤집어야 하는 문자열이 있음
                    for (int j = end; j >= start; j--) sb.append(str.charAt(j));
                    start = -1; // 초기화
                    end = -1;
                }
                flag = true; // flag가 true라면 '>'로 닫히지전까지 유효
            }

            if (flag) sb.append(str.charAt(i)); // 현재 꺽쇠 안이므로 flag
            else {
                if (start == -1) start = i; // 시작 인덱스 설정
                end = i; // 끝 인덱스는 계속 업데이트 (단어 길이가 1일 수 있음)

                if (c == ' ') {
                    for (int j = end - 1; j >= start; j--) sb.append(str.charAt(j));
                    start = -1;
                    sb.append(c);
                }
            }

            if (c == '>') flag = false; // flag false로 설정하여 꺽쇠 해제
        }

        // 꺽쇠 혹은 공백이 없어서 sb에 추가되지 않은 문자열이 있을 수 있으므로
        if (start != -1) {
            for (int j = end; j >= start; j--) sb.append(str.charAt(j));
        }

        System.out.print(sb);
    }
}