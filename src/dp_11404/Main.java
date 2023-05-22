package dp_11404;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    static final int INF = 100_000_001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        int m = parseInt(br.readLine());     
        int[][] graph = new int[n + 1][n + 1];

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (row == col) continue;
                graph[row][col] = INF;
            }
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int e = parseInt(st.nextToken());
            int v = parseInt(st.nextToken());
            int w = parseInt(st.nextToken());
            graph[e][v] = Math.min(graph[e][v], w);
        }

        
        for (int way = 1; way <= n; way++) { // 경유하려는 노드
            for (int row = 1; row <= n; row++) {
                if (row == way) continue; // 만약 출발지 노드와 경유하려는 노드가 같다면 패스

                for (int col = 1; col <= n; col++) {
                    // 출발지 노드와 도착지 노드가 같다면 패스
                    // 도착지 노드와 경유하려는 노드가 같다면 패스
                    if (row == col || col == way) continue;
                    graph[row][col] = Math.min(graph[row][way] + graph[way][col], graph[row][col]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (graph[row][col] == INF) sb.append(0).append(" ");
                else sb.append(graph[row][col]).append(" ");
            }
            sb.append("\n");
        }
        
        System.out.print(sb);
    }
}