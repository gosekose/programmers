package union_find_1976;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    static int n; // n 개의 도시
    static int m; // 들려야 하는 m개 도시
    static int[] parent; // union find
    static List<Integer> nodes = new ArrayList<>(); // 도시 번호 저장
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = parseInt(br.readLine());
        m = parseInt(br.readLine());
        
        parent = new int[n + 1]; // 초기화
        for (int i = 0; i <= n; i++) parent[i] = i; // 루트 노드 설정하기
        
        for (int row = 1; row <= n; row++) {
            // 유니온 파인드를 위해 중복되는 경로를 제거하기 위해 대각선 기준 오른쪽 탐색
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            for (int col = 1; col <= n; col++) {
                int link = parseInt(st.nextToken());
            
                if (col > row) { // 링크가 연결되어 있다면 row의 도시 번호를 기준으로 합치기
                    if (link == 1) union(find(row), find(col));
                }
            }      
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) nodes.add(parseInt(st.nextToken()));
       
        boolean result = isSameRoot(); // 도시가 같은 루트를 공유하는지 판단
        System.out.println(result ? "YES" : "NO");
    }
    
    static boolean isSameRoot() {
        
        if (m == 0) return true; // 만약 들려야 하는 도시가 0이라면 종료
        boolean check = true; // 루트가 같은지 판단
        
        int root = find(nodes.get(0));
        for (Integer node : nodes) {
            if (!(root == find(node))) { // 루트가 다르다면 간선이 존재하지 않음
                check = false;
                break;
            }
        }
        
        return check;
    }
   
    
    static int find(int x) { // find 경로 압축
        if (x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }
    
    static void union(int v1, int v2) {
        parent[v1] = v2;
    } // v1을 루트로 v2 합치기
}