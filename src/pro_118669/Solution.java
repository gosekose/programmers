package pro_118669;

import java.util.*;

class Solution {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        Graph graph = new Graph(n);
        graph.setGate(gates);
        graph.setSummit(summits);
        
        for (int[] path : paths) {
            int s = path[0];
            int d = path[1];
            int w = path[2];
            
            graph.addEdge(s, d, w);
        }

        return graph.dijkstra(n, gates, summits);
    }
}


class Graph {
    
    Set<Integer> gateSet = new HashSet<>();
    Set<Integer> summitSet = new HashSet<>();
    List<List<Node>> nodes = new ArrayList<>(); // nodes의 i번 인덱스에는 i번이 갈 수 있는 adjacent 
    
    class Node {
        int d; // 만약 새로 출발하는 노드라면 도착해야하는 노드의 주소, 이미 도착했다면 도착한 현재 노드 번호
        int w; // 도착지 노드로 가는데 필요한 가중치, 만약 이 노드가 출발 노드라면 이 노드로 도착하는데 필요했던 가중치
        
        Node (int d, int w) {
            this.d = d;
            this.w = w;
        }
    }
    
    Graph(int n) {
        for (int i = 0; i <= n; i++) nodes.add(new ArrayList<>());
    };
    
    void setGate(int[] gates) {
        for (int gate : gates) {
            gateSet.add(gate);
        }
    }
    
    void setSummit(int[] summits) {
        for (int summit : summits) {
            summitSet.add(summit);
        }
    }
    
    void addEdge(int s, int d, int w) {        
        if (gateSet.contains(s) || summitSet.contains(d)) {
            nodes.get(s).add(new Node(d, w)); // 만약 s가 게이트라면 출발 노드만 존재 or d가 정상이라면 도착 노드만 존재
        } else if (gateSet.contains(d) || summitSet.contains(s)) {
            nodes.get(d).add(new Node(s, w)); 
        } else {
            nodes.get(d).add(new Node(s, w)); 
            nodes.get(s).add(new Node(d, w));   
        }
    }
    
    int[] dijkstra(int n, int[] gates, int[] summits) {
        
        Queue<Node> queue = new ArrayDeque<>();
        int[] intensity = new int[n + 1]; 
        
        Arrays.fill(intensity, Integer.MAX_VALUE);
        
        for (int gate : gates) {
            queue.add(new Node(gate, 0)); // gate로 도착하는 가중치는 0 (출발지이므로)
            intensity[gate] = 0;
        }
        
        while(!queue.isEmpty()) {
            Node from = queue.poll(); // 현재 도착한 노드는 출발 노드로 변경

            for (int i = 0; i < nodes.get(from.d).size(); i++) {
                Node to = nodes.get(from.d).get(i);
                
                int distance = Math.max(intensity[from.d], to.w);
                if (intensity[to.d] > distance) {
                    intensity[to.d] = distance;
                    queue.add(new Node(to.d, distance));
                }
            }            
        }
        
        int summit = Integer.MAX_VALUE; 
        int intense = Integer.MAX_VALUE;
        
        Arrays.sort(summits); // 만약 intense가 같다면 가장 작은 정상 번호를 구해야함
        
        for (int sm : summits) {
            if (intensity[sm] < intense) {
                summit = sm;
                intense = intensity[summit];
            }
        }
        
        return new int[]{summit, intense};
        
        
        // from gate: 1, 0
        // edge => (1, 2, 3), (1, 3, 1) 
        // edge => (2, 4, 7), (3, 4, 1)
        
        // nodes.get(1) -> [2, 3]
        // if 2
        // Node to => d: 2, w: 3
        // intensity[1.d] => intensity[1] = 0;
        // to.w = 3
        // distance = 3;
        // intensity[2] > distance
        // intensity[2] = 3
        // queue (new Node(2, 3))
        
        
        // if 3
        // Node to => d: 3, w: 1
        // distance = 1;
        // intensity[3] = 1
        // queue (3, 1)
        
        // queue -> d: 2, w: 3
        // Node to = nodes.get(2) -> d: 4 w: 7
        // intensity[2] =  3
        // to.w = 7
        // distance = 7
        // intensity[4] = distance = 7
        // queue (4, 7) ----> graph.get(4).size() == 0 (도착 노드이므로) --> 종료
        
        
        // queue -> d: 3 w: 1
        // Node to = nodes.get(3) -> d: 4, w: 1
        // intensity[3] = 1
        // to.w = 1;
        // distance = 1
        // intensity[4] > distance (앞에서 7로 업데이트 되었으므로 )
        // intensity[4] = distacne = 1
        // queue (4, 1) ----> graph.get(4).size() == 0 ---> 종료
        
    }
    
}