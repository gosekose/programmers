package bellman_11657;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    static ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
    static final int INF = Integer.MAX_VALUE;
    static long[] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken()); // 정점 개수
        int m = parseInt(st.nextToken()); // 간선 개수
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int e = parseInt(st.nextToken());
            int v = parseInt(st.nextToken());
            int w = parseInt(st.nextToken());
            
            graph.get(e).add(new Edge(v, w));
        }
        
        dist = new long[n + 1]; // 정점 개수로 dist 초기화 노드 1번에서 각 노드 출발
        Arrays.fill(dist, INF);
        dist[1] = 0; // 시작노드인 1번은 = 0

        for (int k = 0; k < n - 1; k++) {
            for (int i = 1; i <= n; i++) { // 정점 개수
                ArrayList<Edge> edges = graph.get(i);

                for (Edge edge : edges) {
                    if (dist[i] != INF && dist[edge.v] > dist[i] + edge.w) {
                        dist[edge.v] = dist[i] + edge.w; // 출발 정점에서 도착 정점까지의 거리가, 이전의 값보다 작다면 업데이트
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n; i++) { // 벨만포드 알고리즘
            ArrayList<Edge> edges = graph.get(i);

            for (Edge edge : edges) {
                if (dist[i] != INF && dist[edge.v] > dist[i] + edge.w) {
                    sb.append(-1).append("\n");
                    System.out.print(sb);
                    return;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (dist[i] == INF) sb.append(-1).append("\n");
            else sb.append(dist[i]).append("\n");
        }

        System.out.print(sb);
    }
    
    static class Edge {
        int v; // 도착 정점
        int w; // 가중치
        
        Edge (int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}