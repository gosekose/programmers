package union_find_1717;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    static int n;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) parent[i] = i;
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int cal = parseInt(st.nextToken());
            int a = parseInt(st.nextToken());
            int b = parseInt(st.nextToken());
            
            if (cal == 0) {
                int p1 = find(a); // p1 루트 노드 찾기
                int p2 = find(b); // p2 루트 노드 찾기
                
                union(p1, p2); // 합치기
            } else {
                boolean same = find(a) == find(b);
                sb.append(same ? "YES" : "NO").append("\n");
            }
        }
        
        System.out.print(sb);
    }
    
    static int find(int child) {

        if (child == parent[child]) return child; // 자신이 루트 노드라면
        return parent[child] = find(parent[child]); // 만약 루트 노드가 아니라면 루트 노드 찾기, 경로압축
    }
    
    static void union(int p1, int p2) {
        parent[p2] = p1; // p2의 부모를 p1으로 바꾸기
    }
}