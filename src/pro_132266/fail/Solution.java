package pro_132266.fail;

import java.util.*;

// 18:13 ~ 18:35 testcase 11 부터 시간 초과
class Solution {
    
    boolean[] visited;
    
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        int[] answer = new int[sources.length];
        
        visited = new boolean[n + 1];
        Graph graph = new Graph(n + 1);
        for (int i = 0; i < roads.length; i++) graph.addEdge(roads[i][0], roads[i][1]); // edge 연결
        
        for (int i = 0; i < sources.length; i++) {
            boolean refresh = true;
            if(i == 0) refresh = false;
            answer[i] = graph.bfs(sources[i], destination, visited, refresh);
        }
        
        return answer;
    }

    
    class Graph {
        class Node {
            int x;
            int step;
            List<Node> adjacent = new ArrayList<>();
        
            Node(int x, int step) {
                this.x = x;
                this.step = step;
            }
        
        }
        
        Node[] nodes;
        
        Graph(int n) {
            nodes = new Node[n];
            for (int i = 0; i < n; i++) nodes[i] = new Node(i, 0);
        }
        
        void addEdge(int x, int y) {
            Node n1 = nodes[x];
            Node n2 = nodes[y];
            
            n1.adjacent.add(n2);
            n2.adjacent.add(n1);         
        }
        
        int bfs(int n, int destination, boolean[] visited, boolean refresh) {
            if (refresh) Arrays.fill(visited, false);
        
            Queue<Node> queue = new ArrayDeque<>();
            queue.add(nodes[n]);
          
            while(!queue.isEmpty()) {
                Node n1 = queue.poll();
                if (n1.x == destination) return n1.step;
                
                if (visited[n1.x]) continue;
                visited[n1.x] = true;
                
                for (Node n2 : nodes[n1.x].adjacent) {
                    Node newN = new Node(n2.x, n1.step + 1);
                    queue.add(newN);
                }
            }
            
            return -1;
        }
    }
    
}

