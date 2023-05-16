package str_9342;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        Solution solution = new Solution();
        for (int i = 0; i < t; i++) {
            solution.setStr(br.readLine());
        }

        System.out.print(solution.getStr());
    }
}

class Solution {

    List<String> strs = new ArrayList<>(); // 문자열 저장 리스트

    void setStr(String str) {
        strs.add(str);
    }

    String getStr() {
        StringBuilder sb = new StringBuilder();
        boolean isInfected; // 조건에 맞는지 판단

        for (String str : strs) {
            isInfected = true;

            if (str == null || str.length() < 3) { // 문자가 null이거나 3개보다 작다면 조건 만족 불가능
                sb.append("Good!").append("\n");
                continue;
            }

            int flag = 0; // 0: A, 1: F, 2: C 3: C가 문자 한 개
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);

                if (flag == 3) { // 만약 flag == 3인 조건이 true라면 끝에 문자가 하나가 아니라는 것
                    isInfected = false; // 조건 만족 불가
                    break;
                }

                else if (flag == 0) { // A 문자가 유지되는 중
                    if (c == 'F') { // F가 나왔다면 flag++
                        flag++;
                    }
                    else if (c != 'A') { // F와 A가 아니라면 조건 불만족
                        isInfected = false;
                        break;
                    }
                } else if (flag == 1) { // F 문자 유지되는 중
                    if (c == 'C') { // C가 나왔다면 flag++
                        flag++;
                    }
                    else if (c != 'F') { // C와 F가 아니라면 조건 불만족
                        isInfected = false;
                        break;
                    }
                } else if (flag == 2) {  // C 문자 유지 중
                    if (c != 'C') {
                        if (c == 'A' || c == 'B' || c == 'D' || c == 'E' || c =='F') flag++; // 마지막 문자 한 개가 되어야 함
                        else {
                            isInfected = false;
                            break;
                        }
                    }
                }
            }
            if (isInfected && (flag == 2 || flag == 3)) {
                sb.append("Infected!").append("\n");
            } else {
                sb.append("Good").append("\n");
            }

        }

        return sb.toString();
    }
}