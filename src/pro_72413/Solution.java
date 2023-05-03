package pro_72413;

import java.util.*;

class Solution {
    
    static final int MAX = Integer.MAX_VALUE;
    List<List<Edge>> graph = new ArrayList<>();
    
    // 다익스트라
    // 각 노드간 연결관계 및 가중치 설정
    // 각 간선의 가중치 최대로 초기화
    // 우선순위 큐로 간선의 최단거리로 업데이트 
    public int solution(int n, int s, int a, int b, int[][] fares) {        
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>()); // 이차원 리스트 생성
        
        for (int[] fare : fares) {
            graph.get(fare[0]).add(new Edge(fare[1], fare[2])); // 양방향 간선 설정
            graph.get(fare[1]).add(new Edge(fare[0], fare[2]));
        }
        
        int[] costS = new int[n + 1]; // 다익스트라 배열 초기화        
        int[] costA = new int[n + 1];
        int[] costB = new int[n + 1];
        
        Arrays.fill(costS, MAX); // 다익스트라 알고리즘을 적용하기 위해 값 MAX_VALUE 설정
        Arrays.fill(costA, MAX);
        Arrays.fill(costB, MAX); 
        
        costS = dijkstra(s, costS); // 시작점(s)을 기준으로 각 노드별 최단 거리 구하기 참조#1
        costA = dijkstra(a, costA); // 시작점(a)를 기준으로 각 노드별 최단 거리 구하기 
        costB = dijkstra(b, costB); // 시작점(b)를 기준으로 각 노드별 최단 거리 구하기
        
        int answer = MAX;
        for (int i = 1; i <= n; i++) answer = Math.min(answer, costS[i] + costA[i] + costB[i]);  // 참조#2 
        
        return answer;
    }
    
    int[] dijkstra(int start, int[] costs) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparing((Edge e) -> e.cost));
        
        queue.add(new Edge(start, 0)); // 시작점에서 시작점으로 출발하는 노드이므로 0으로 초기화
        costs[start] = 0; // 시작점의 비용은 0
        
        while(!queue.isEmpty()) {
            Edge now = queue.poll(); // 우선순위 큐에 의해 간선이 적은 순서대로 queue에서 poll
            
            if (now.cost > costs[now.e]) continue; // 만약 x 노드로 가려는 가중치(비용)이 x로 가는 최단거리 배열의 값보다 크다면 패스
            
            for (Edge next : graph.get(now.e)) { // 그래프에 있는 e에서 출발하는 간선을 꺼내 간선이 이동하는 위치에 대한 거리가 최단 거리 값보다 작으면 업데이트
                int cost = next.cost + costs[now.e]; // 다음 노드로 가는 간선의 비용 + 현재 노드로 오는데 걸린 최단거리 비용 => 다음 노드에 대한 최종 비용 
                
                if (costs[next.e] > cost) { // 웨에서 계산한 cost가 더 작다면
                    costs[next.e] = cost;
                    queue.add(next); // 현재 계산한 간선은 유효성이 있으므로 (더 작은 비용으로 다음 노드로 움직일 수 있으므로) queue에 입력
                }
            }
        }   
        return costs;
    }    
}

class Edge {
    
    int e; // 노드 번호
    int cost; // 비용
    
    Edge(int e, int cost) {
        this.e = e;
        this.cost = cost;
    }
}