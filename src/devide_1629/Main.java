package devide_1629;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class  Main {

    static long c;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        long a = parseInt(st.nextToken());
        long b = parseInt(st.nextToken());
        c = parseInt(st.nextToken());

        System.out.println(pow(a, b));
    }

    static long pow(long a, long b) {

        if (b == 1) return a % c;
        long tmp = pow(a, b / 2);
        if (b % 2 == 1) return (tmp * tmp % c) * a % c;
        return tmp * tmp % c;
    }
}