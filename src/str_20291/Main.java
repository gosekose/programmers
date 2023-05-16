package str_20291;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            String type = br.readLine().split("\\.")[1];

            if (!set.contains(type)) {
                set.add(type);
                map.put(type, 1);
            } else map.put(type, map.get(type) + 1);
        }

        List<String> sortType = new ArrayList<>(set);
        Collections.sort(sortType);
        
        StringBuilder sb = new StringBuilder();
        for (String key : sortType) sb.append(key).append(" ").append(map.get(key)).append("\n");
        
        System.out.print(sb);
    }
}