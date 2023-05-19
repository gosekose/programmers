package mst_9372;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = parseInt(st.nextToken());
            int m = parseInt(st.nextToken()); 
            
            Graph graph = new Graph(n);
            for (int j = 0; j < m; j++) {
                st = new StringTokenizer(br.readLine());
                graph.addEdge(parseInt(st.nextToken()), parseInt(st.nextToken()));
            }
            
            sb.append(graph.mst()).append("\n");
        }
        
        System.out.print(sb);
    }
}

class Graph {
    int n;
    Node[] nodes;
    
    Graph (int n){
        this.n = n;
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i);
    }
    
    class Node {
        int x;
        List<Node> adjacent = new ArrayList<>();
        
        Node (int x) {
            this.x = x;
        }
    }
    
    void addEdge(int v1, int v2) {
        Node n1 = nodes[v1];
        Node n2 = nodes[v2];
        
        n1.adjacent.add(n2);
        n2.adjacent.add(n1);
    }
    
    int mst() {
        boolean[] visited = new boolean[n + 1];
        int result = 0;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(nodes[1]);
        visited[nodes[1].x] = true;

        while(!queue.isEmpty()){
            Node n1 = queue.poll();

            for (Node ad : n1.adjacent) {
                if (!visited[ad.x]) {
                    visited[ad.x] = true;
                    queue.add(ad);
                    result++;
                }
            }
        }
        
        return result;
    }
}