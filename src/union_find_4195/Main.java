package union_find_4195;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Main {

    static int[] parent; // 부모 노드 설정
    static int[] cnt; // 부모에 있는 노드의 자식 노드 개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = parseInt(br.readLine());
        for (int i = 0; i < t; i++) {

            Map<String, Integer> map = new HashMap<>(); // 각 이름과 인덱스를 저장할 map
            int n = parseInt(br.readLine()); // 배열을 초기화할 개수

            // n개의 연결관계가 있으므로 n * 2가 최대
            parent = new int[n * 2];
            cnt = new int[n * 2];

            for (int j = 0; j < parent.length; j++) {
                parent[j] = j; // 부모 노드 자신으로 초기화
                cnt[j] = 1; // 개수는 모두 1로 초기화
            }

            int idx = 0; // 인덱스 0 설정
            for (int j = 0; j < n; j++) {
                String[] str = br.readLine().split(" ");

                if (!map.containsKey(str[0])) map.put(str[0], idx++);
                if (!map.containsKey(str[1])) map.put(str[1], idx++);

                union(map.get(str[0]), map.get(str[1])); // 두 친구 연결하기
                sb.append(cnt[find(map.get(str[1]))]).append("\n"); // 두번째 인원의 부모를 찾아서 네트워크 연결된 수 찾기
            }
        }

        System.out.print(sb);
    }

    static int find(int child) { // find
        if (parent[child] == child) return child;
        return parent[child] = find(parent[child]); // 경로 압축
    }

    static void union(int c1, int c2) { // 합치기
        int p1 = find(c1); // p1
        int p2 = find(c2); // p2

        if (p1 == p2) return;
        parent[p2] = p1;
        cnt[p1] += cnt[p2]; // p1에 p2 인원 합치기
        cnt[p2] = 0; // p2 인원 초기화
    }
}
