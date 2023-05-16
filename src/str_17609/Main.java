package str_17609;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        List<String> strs = new ArrayList<>();

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) strs.add(br.readLine());

        for (String str : strs) {
            int left = 0;
            int right = str.length() - 1;
            int cnt = 0;

            while (left < right) {
                if (str.charAt(left) != str.charAt(right)) {

                    if (str.charAt(left + 1) == str.charAt(right)) {
                        cnt = move(str, left + 1, right);
                        if (cnt == 1) break;
                    }

                    if (str.charAt(left) == str.charAt(right - 1)) {
                        cnt = move(str, left, right - 1);
                        if (cnt == 1) break;
                    }

                    cnt = 2;
                    break;
                }

                else {
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

    static int move(String str, int left, int right) {
        while (left < right) {
            if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else {
                return 2;
            }
        }
        return 1;
    }
}