package str_17609;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        List<String> strs = new ArrayList<>(); // 문자열 담을 리스트

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) strs.add(br.readLine()); // 문자열 리스트에 추가

        for (String str : strs) {
            int cnt = 0; // 회문, 유사회문, 그 외 판단
            int left = 0; // 포인터 왼쪽
            int right = str.length() - 1; // 포인터 오른쪽

            while (left < right) { // left 포인터가 right 포인터보다 작을 때 까지 , 만약 홀수길이이의 문자열이라면 중앙을 제외한 나머지가 모두 맞으면 가능 abkba

            if (str.charAt(left) != str.charAt(right)) { // 만약 양쪽 포인터가 가리키는 문자열이 같지 않다면
                    if (str.charAt(left + 1) == str.charAt(right)) { // 만약 두 포인터가 같지 않다면 유사회문 판단을 위해 left를 한칸 옮겨서 판단
                        cnt = move(str, left + 1, right);
                        if (cnt == 1) break; // 만약 왼쪽 포인터를 한번 더 옮긴 것이 유사회문이라면 종료
                    }
                    if (str.charAt(left) == str.charAt(right - 1)) { // 유사회문이 아닌 경우 right를 한칸 옮기고 진행
                        cnt = move(str, left, right - 1);
                        if (cnt == 1) break; // 유사회문 종료
                    }
                    cnt = 2; // 둘 다 아니거나, cnt = 2라면,
                    break;
                } else { // 유사회문 없이 돌아간다면, 하나씩 포인터 양쪽에서 옮겨주기
                    left++;
                    right--;
                }
            }

            if (cnt == 0) sb.append(0).append("\n");
            else if (cnt == 1) sb.append(1).append("\n");
            else sb.append(2).append("\n");
        }

        System.out.print(sb);
    }

    static int move(String str, int left, int right) { // 유사회문 판단을 위한 포인터 이동
        while (left < right) {
            if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else return 2; // 유사회문도 아니므로 2 리턴
        }
        return 1; // 유사회문
    }
}