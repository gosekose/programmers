package tree_dp_2213;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        
        Graph graph = new Graph(n);
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) graph.addWeight(i, parseInt(st.nextToken()));
        
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            graph.addEdge(parseInt(st.nextToken()), parseInt(st.nextToken()));
        }

        graph.run();
    }    
}

class Graph {
    int n;
    Node[] nodes; // 노드 배열
    Queue<Node> queue = new PriorityQueue<>(Comparator.comparing((Node n) -> n.x)); // 독립 집합
    int[] dp, arr, selected; // dp, arr(집합의 가중치), 선택된 배열
    
    Graph (int n) {
        this.n = n;
        nodes = new Node[n + 1]; // 노드 배열
        dp = new int[n + 1];
        arr = new int[n + 1];
        selected = new int[n + 1];
        
        for (int i = 1; i <= n; i++) nodes[i] = new Node(i);
    }
    
    class Node {
        int x; // 노드 번호
        List<Node> adjacent = new ArrayList<>(); // 인접 노드
        List<Node> tree = new ArrayList<>(); // 트리
        Node (int x) {
            this.x = x;
        }
    }
    
    void addWeight (int x, int w) {
        arr[x] = w;
    }
    
    void addEdge(int x, int y) {  // 양뱡향 연관 관계
        Node n1 = nodes[x];
        Node n2 = nodes[y];
        
        n1.adjacent.add(n2);
        n2.adjacent.add(n1);
    }
    
    void run() {
        buildTree(nodes[1], nodes[0]);

        StringBuilder sb = new StringBuilder();
        int t1 = dp(nodes[1], 0); // 루트 포함 X
        int t2 = dp(nodes[1], 1); // 루트 포함
        
        if (t1 < t2) selected[1] = 1; // 만약 포함이 더 크다면 1
        else selected[1] = 0; // 아니라면 0
        sb.append(Math.max(t1, t2)).append("\n");
        
        trace(nodes[1], selected[1]); // 1번 노드에 대한 선택 여부로 추적
        
        while(!queue.isEmpty()) {
            sb.append(queue.poll().x).append(" "); // 큐의 노드 번호를 sb에 추가
        }

        System.out.println(sb);
    }

    void buildTree(Node now, Node parent) { // 트리 만들기, 현재 노드, 부모 노드
        for (Node child : now.adjacent) { // 현재 노드의 인접 노드
            if (child != parent) { // 부모 노드라면 무한 루프이므로 제거
                now.tree.add(child); // 트리에 추가
                buildTree(child, now); // 재귀 호출
            }

        }
    }
    
    int dp(Node now, int include) {
        int ans = 0;

        if (include == 1) {
            for (Node next : now.tree) ans += dp(next, 0); // 다음 노드는 포함하지 않음
            return ans + arr[now.x];
        } else {
            for (Node next : now.tree) {
                int t1 = dp(next, 0); // 현재 포함 안한 경우 다음에도 포함 안할 수 있음
                int t2 = dp(next, 1); // 다음은 포함하도록 
                
                if (t1 < t2) selected[next.x] = 1; // 포함이 더크면 포함 처리
                else selected[next.x] = 0; // 미포함 처리
                ans += Math.max(t1, t2);
            }
            return ans;
        }
    }
    
    void trace(Node now, int include) {
        if (include == 1) { // 현재를 포함했다면
            queue.add(now); // 큐에 추가하기
            for (Node next : now.tree) trace(next, 0); // 다음은 무조건 불가
        }
        else if (include == 0) {
            for (Node next : now.tree) trace(next, selected[next.x]); // 다음은 dp의 더 큰 값에 따라 달라짐
        }
    }
}