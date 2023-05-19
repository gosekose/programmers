package mst_1197;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = parseInt(st.nextToken());
        int e = parseInt(st.nextToken());
        
        Graph graph = new Graph(v); // 그래프 설정
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            graph.addEdge(parseInt(st.nextToken()), parseInt(st.nextToken()), parseInt(st.nextToken()));
        }
        
        System.out.println(graph.mst());
    }
}

class Graph {
    int n; // n개의 노드
    int[] parent; // 유니온 파인드 루트 노드
    List<Edge> edges = new ArrayList<>(); // 간선
    
    Graph(int n) {
        this.n = n;
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;
    }
    
    class Edge {
        int x; // 연결된 노드
        int y; // 연결된 노드
        int w; // 가중치
        
        Edge (int x, int y, int w) {
            this.x = x;
            this.y = y;
            this.w = w;
        }
    }
    
    void addEdge(int x, int y, int w) {
        edges.add(new Edge(x, y, w));
    }
    
    int mst() {
        int result = 0;
        edges.sort(Comparator.comparing((Edge e) -> e.w)); // 엣지 오름차순 설정

        for (Edge e : edges) {
            int p1 = find(e.x); // x의 루트 노드
            int p2 = find(e.y); // y의 루트 노드

            if (p1 == p2) continue; // 만약 두 노드의 루트 노드가 같다면 패스
            result += e.w; // 가중치 업데이트
            union(p1, p2); // 두 노드 합치기
        }
        
        return result;
    }
    
    int find(int child) { // find 경로 압축
        if (child == parent[child]) return child;
        return parent[child] = find(parent[child]);
    }
    
    void union(int p1, int p2) { // union 합치기
        parent[p2] = p1;

    }
}