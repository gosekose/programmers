package pro_76503;

import java.util.*;

class Solution {
    public long solution(int[] a, int[][] edges) {
        Graph graph = new Graph(a);
        
        if (graph.isInitEnd() == 0 || graph.isInitEnd() == -1) {
            return graph.isInitEnd(); // 0 또는 -1 종료 조건
        }
        
        graph.addEdge(edges); // graph에 edge 설정
        graph.dfs(0);      
        
        return graph.getAnswer();
    }
}

class Graph {
    
    Node[] nodes; // 노드 배열
    boolean[] visited; // 방문 여부
    long answer; // 정답 long 타입!
    boolean isAllZero; // 모두 0인지 아니면 어떤 수는 0이 아닌지로 종료 여부 체크
    int isPossibleSumZero; // 합이 0이 가능한지 체크
    
    class Node {
        int x;
        long w;
        List<Node> adjacent = new ArrayList<>();
        
        Node (int x, long w) {
            this.x = x;
            this.w = w;
        }
    }
    
    Graph(int[] arr) {
        nodes = new Node[arr.length]; // 노드 배열
        visited = new boolean[arr.length]; // 방문 여부 체크
         
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i, arr[i]); // 노드 생성
            if (arr[i] != 0) this.isAllZero = false; // 하나라도 0이 아니라면 어떤 수는 0이 아니므로 false
            isPossibleSumZero += arr[i]; // 모든 수의 합이 zero가 될 수 있는지 체크
        }
        
    } 
    
    
    int isInitEnd() {
        if (isPossibleSumZero != 0) return -1; // 모든 수의 합이 0이 안된다면 조료
        if (this.isAllZero) return 0; // 이미 모든 수가 0이라면 종료
        return 1; 
    }
    
    void addEdge(int[][] edges) {
        
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0]; // edge 설정
            int v = edges[i][1];
            
            Node n1 = nodes[u];
            Node n2 = nodes[v];
            
            n1.adjacent.add(n2); // 양방향 연결관계
            n2.adjacent.add(n1);
        }
    }
    
    long dfs(int x) {
        
        visited[x] = true; // 방문 체크
        
        Node node = nodes[x]; // 노드 설정
        
        for (int i = 0; i < node.adjacent.size(); i++) {
            Node adjacent = node.adjacent.get(i); // 인접한 노드 가져옴
            
            if (!visited[adjacent.x]) {
                node.w += dfs(adjacent.x); // dfs로 인접한 노드로부터 가중치를 업데이트하여 가져옴 --> 중간 노드가 선택된다면 리프노드로 계속 방문하여 w 업데이트 
            }
        }
        
        this.answer += Math.abs(node.w); // 노드의 가중치의 절대값을 더함 (- 혹은 + 도 0으로 만들기 위해서는 정량적인 수가 필요하므로)
        
        return node.w; // 노드의 가중치를 리턴하여 dfs
    }
    
    
    long getAnswer() {
        return this.answer;
    }
    
}



// 모든 수의 합이 짝수라면 가능? - X
// 5 0 0 7 


// 건너 건너의 합이 짝수라면 - X
// 3 0 5 0
// 0 1 0 1

// 전부 합이 0이라면 --> 종료 조건 합이 0이 아니라면
// -2 0 0 2

// -5 0 2 1 2
//  
//  0 -5 2 1 2 // 5
//  0 0 -3 1 2 // 8

// -5  -- 1 -- 2
//  |     |
//  0     2



//        1 -- 1 
//        |
// -7  -- 1 -- 2
//  |     |
//  0     2
//  

// 7 + 2 + 4  = 14


//        1 -- 1 -- 1 -- 1
//        |
// -9  -- 1 -- 2 
//  |     |    
//  0     2


//  9 + 2 + 2 + 1 + 2 + 3 + 4  = 23


//                              -2
//                             /
//   2 -- 2 -- 2       -8 -- -7 -- -2 
//              \     /  \
//    3 -- 2 -- 8 -- 7    -2 -- -3
//                          \
//                           -2
// 92