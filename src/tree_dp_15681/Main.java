package tree_dp_15681;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int r = parseInt(st.nextToken());
        int q = parseInt(st.nextToken());
        
        Graph graph = new Graph(n, r);
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            graph.addEdge(parseInt(st.nextToken()), parseInt(st.nextToken()));           
        }
        
        for (int i = 0; i < q; i++) graph.addQuery(parseInt(br.readLine()));
        
        graph.makeSubTree(r); // 서브 트리 만들며 dp 저장
        graph.getPrint(); // 결과 출력
    }
}

class Graph {
    int n; // n개의 노드
    int root; // 루트 노드
    Node[] nodes; // 노드 저장
    List<Integer> querys = new ArrayList<>(); // 출력해야하는 쿼리
    int[] dp; // 서브 트리 개수
    
    Graph (int n, int root) {
        this.n = n;
        this.root = root;
        nodes = new Node[n + 1];
        dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i);
    }
    
    class Node {
        int x;
        List<Node> adjacent = new ArrayList<>(); // 인접 노드
        
        Node (int x) {
            this.x = x;
        }
    }
    
    void addEdge(int x, int y) {
        Node n1 = nodes[x];
        Node n2 = nodes[y];
        
        n1.adjacent.add(n2);
        n2.adjacent.add(n1);
    }
    
    void addQuery(int x) {
        querys.add(x);
    }
    
    void makeSubTree(int root) {
        dp[root] = 1; // 서브 트리에 포함
        Node node = nodes[root]; // 노드 꺼낸 후
        
        for (Node ad : node.adjacent) {
            if (dp[ad.x] == 0) { // 처음 호출 되는 경우만 재귀적 실행  --> dp == 0
                makeSubTree(ad.x); // 인접한 노드 여기서는 자식 노드를 의미
                dp[root] += dp[ad.x]; // 자식 노드의 서브트리를 더함
            }
        }
    }
    
    void getPrint() {
        StringBuilder sb = new StringBuilder();
        
        for (Integer q : querys) {
            sb.append(dp[q]).append("\n");
        }
        
        System.out.print(sb);
    }
    
}