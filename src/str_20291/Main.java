package str_20291;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        HashMap<String, Integer> map = new HashMap<>(); // 개수를 저장할 map 선언
        HashSet<String> set = new HashSet<>(); // 셋으로 고유한 문자 저장
        
        for (int i = 0; i < n; i++) {
            String type = br.readLine().split("\\.")[1]; // 이스케이프로 . 처리

            if (!set.contains(type)) { // 만약 key가 없다면 map에 저장
                set.add(type);
                map.put(type, 1);
            } else map.put(type, map.get(type) + 1); // 이미 값이 있는 경우 + 1
        }

        List<String> sortType = new ArrayList<>(set); // set을 문자열에 addAll
        Collections.sort(sortType); // 오름차순 정렬

        StringBuilder sb = new StringBuilder();
        for (String key : sortType) sb.append(key).append(" ").append(map.get(key)).append("\n");

        System.out.print(sb);
    }
}