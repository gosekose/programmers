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

    List<String> strs = new ArrayList<>();

    void setStr(String str) {
        strs.add(str);
    }

    String getStr() {
        StringBuilder sb = new StringBuilder();
        boolean isInfected;

        for (String str : strs) {
            isInfected = true;

            if (str == null || str.length() < 3) {
                sb.append("Good!").append("\n");
                continue;
            }

            int alpha = 0; // 0: A, 1: F, 2:C
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);

                if (alpha == 3) {
                    isInfected = false;
                    break;
                }

                else if (alpha == 0) {
                    if (c == 'F') {
                        alpha++;
                    }
                    else if (c != 'A') {
                        isInfected = false;
                        break;
                    }
                } else if (alpha == 1) {
                    if (c == 'C') {
                        alpha++;
                    }
                    else if (c != 'F') {
                        isInfected = false;
                        break;
                    }
                } else if (alpha == 2) {
                    if (c != 'C') {
                        if (c == 'A' || c == 'B' || c == 'D' || c == 'E' || c =='F') alpha++;
                        else {
                            isInfected = false;
                            break;
                        }
                    }
                }
            }
            if (isInfected && (alpha == 2 || alpha == 3)) {
                sb.append("Infected!").append("\n");
            } else {
                sb.append("Good").append("\n");
            }

        }

        return sb.toString();
    }
}