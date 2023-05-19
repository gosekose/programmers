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
        
        graph.makeSubTree(r);
        graph.getPrint();
    }
}

class Graph {
    int n;
    int root;
    Node[] nodes;
    List<Integer> querys = new ArrayList<>();
    int[] dp;
    
    Graph (int n, int root) {
        this.n = n;
        this.root = root;
        nodes = new Node[n + 1];
        dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i);
    }
    
    class Node {
        int x;
        List<Node> adjacent = new ArrayList<>();
        
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
        Node node = nodes[root];
        
        for (Node ad : node.adjacent) {
            if (dp[ad.x] == 0) { // 처음 호출 되는 경우 --> dp == 0
                makeSubTree(ad.x);
                dp[root] += dp[ad.x];
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