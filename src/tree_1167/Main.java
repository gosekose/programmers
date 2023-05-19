package tree_1167;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = parseInt(br.readLine());
        Graph graph = new Graph(n); // 그래프 생성
        
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v1 = parseInt(st.nextToken()); // 첫 번째 문자 정점
            while (true) {
                int v2 = parseInt(st.nextToken()); // 연결할 정점 -1이면 종료
                if (v2 != -1) {
                    graph.addEdge(v1, v2, parseInt(st.nextToken())); // 간선 길이
                } else break;                 
            }            
        }
        
        graph.run(1,  0); // 임의의 정점에서 가장 긴 정점 구하기
        graph.run(graph.getNext(), 0); // 다음 정점에서 가장 긴 정점 구하기
        
        System.out.println(graph.getMax());
    }
}

class Graph {
    int n;
    int max; // 정점 최장 거리
    int next; // 다음에 dfs를 계산할 next 노드
    Node[] nodes; // 노드 배열
    boolean[] visited; // 방문 설정
    
    Graph(int n) {
        this.n = n;
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i); // 초기화
    }
    
    class Node {
        int x;
        List<Edge> edges = new ArrayList<>(); // 노드에 연결될 간선 설정
        
        Node (int x) {
            this.x = x;
        }
    }
    
    class Edge { // edge
        int y; // x 노드와 연결되는 간선 y
        int dist; // x 노드와 y 노드의 간선 길이
        
        Edge (int y, int dist) {
            this.y = y;
            this.dist = dist;
        }
    }
    
    void addEdge(int x, int y, int dist) {
        Node n1 = nodes[x];      // 단방향 간선
        n1.edges.add(new Edge(y, dist));
    }

    void run(int x, int dist) {
        visited = new boolean[n + 1]; // visited 초기화
        dfs(x, dist); // dfs 실행
    }

    void dfs(int x, int dist) {
        Node n1 = nodes[x];
        visited[n1.x] = true;

        for (Edge e : n1.edges) { // 간선 탐색
            if (!visited[e.y]) { // 간선에 연결된 노드에 방문하지 않은 경우
                dfs(e.y, dist + e.dist); // 다음 노드, 현재 거리에 간선 길이 더하기
            }
        }

        if (max < dist) { // 길이가 더 크면
            max = dist; // 길이 업데이트
            next = x; // 다음 노드 업데이트
        }
    }
    
    int getMax() {
        return max;
    }
    int getNext() {return next;}
    
}