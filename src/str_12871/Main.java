package str_12871;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        String str1 = br.readLine();
        String str2 = br.readLine();
        
        int gcd = getGCD(str1.length(), str2.length()); // 최대 공약수 찾기
        if (gcd == 1) gcd = str1.length() * str2.length(); // 1이라면 최소 공배수는 두 문자열 길이의 곱
        else gcd = str1.length() * (str2.length() / gcd); // 1이 아니라면, 문자열 길이 * 문자열 길이 / 최대 공약수

        str1 = repeat(str1, gcd); // 문자열 반복
        str2 = repeat(str2, gcd);

        if (str1.equals(str2)) sb.append(1);
        else sb.append(0);
        
        System.out.print(sb);
    }

    // 유클리드 호재법
    static int getGCD(int x, int y) {
        if (y == 0) return x;
        return getGCD(y, x % y);
    }

    // 최소 공배수 개수만큼 문자열 증가
    static String repeat(String str, int lcm) {
        String repeat = str;
        while (str.length() < lcm) {
            str += repeat;
        }
        return str;
    }
}