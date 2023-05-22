package sum_2559;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        int[] map = new int[n];
        for (int i = 0; i < n; i++) {
            map[i] = parseInt(st.nextToken());
        }
        
        int sum = 0;
        for (int i = 0; i < k; i++) sum += map[i];
        int max = sum;
        for (int i = k; i < n; i++) {
            sum += (map[i] - map[i - k]);
            max = Math.max(max, sum);            
        }
        
        System.out.println(max);
    }
}