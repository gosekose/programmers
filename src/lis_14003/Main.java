package lis_14003;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < n; i++) arr[i] = parseInt(st.nextToken());
        List<Integer> result = search(arr);
        
        StringBuilder sb = new StringBuilder();
        sb.append(result.size()).append("\n");
        for (Integer i : result) sb.append(i).append(" ");
        System.out.print(sb);
    }
    
    static List<Integer> search(int[] arr) {
        List<Integer> dp = new ArrayList<>();
        
        int[] idxs = new int[arr.length];
        int[] preIdxs = new int[arr.length];
        
        for (int i = 0; i < arr.length; i++) {
            if (dp.isEmpty() || arr[i] > dp.get(dp.size() - 1)) {
                if (!dp.isEmpty()) preIdxs[i] = idxs[dp.size() - 1];
                dp.add(arr[i]);
                idxs[dp.size() - 1] = i;
            } else {
                int left = 0, right = dp.size() - 1;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (dp.get(mid) < arr[i]) left = mid + 1;
                    else right = mid;
                }
                dp.set(right, arr[i]);
                idxs[right] = i;
                if (right > 0) preIdxs[i] = idxs[right - 1];
            }            
        }
        
        List<Integer> lis = new ArrayList<>();
        int idx = idxs[dp.size() - 1];
        while (lis.size() < dp.size()) {
            lis.add(arr[idx]);
            idx = preIdxs[idx];
        }
        Collections.reverse(lis);
        return lis;
    }
}