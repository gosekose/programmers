package tree_11725;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        Graph graph = new Graph(n); // 그래프 생성
        
        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            graph.addEdge(parseInt(st.nextToken()), parseInt(st.nextToken())); // 간선 설정
        }
        graph.getParent();
    }
}

class Graph {
    int n; // n개 노드
    Node[] nodes; // 노드를 담을 배열
    
    Graph(int n) {
        this.n = n;
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i); // 노드 초기화
    }
    
    class Node {
        int x;
        List<Node> adjacent = new ArrayList<>(); // 인접한 노드
        
        Node (int x) {
            this.x = x;
        }
    }
    
    void addEdge(int x, int y) {
        Node n1 = nodes[x];
        Node n2 = nodes[y];
        
        n1.adjacent.add(n2); // 연결된 간선
        n2.adjacent.add(n1); // 양방향 연결
    }
    
    void getParent() {
        int[] parent = new int[n + 1]; // 부모의 노드 번호 저장
        boolean[] visited = new boolean[n + 1]; // 방문
        
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(nodes[1]);
        
        while(!queue.isEmpty()) {
            Node n1 = queue.poll();
            
            visited[n1.x] = true;
            
            for (Node ad : n1.adjacent) {
                if (!visited[ad.x]) { // 아직 방문하지 않았다면 큐의 성질로 부모의 자식 노드 반환
                    queue.add(ad); // 큐에 추가
                    parent[ad.x] = n1.x; // 자식 노드 번호에 부모 노드 번호 저장
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 2; i <= n; i++) sb.append(parent[i]).append("\n");
        
        System.out.print(sb);
    }
}