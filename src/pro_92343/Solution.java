package pro_92343;

import java.util.*;

class Solution {
    
    int maxSheepCnt; // 최대 양의 수
    int totalSheepCnt; // 총 양의 수
    ArrayList<Integer>[] nodes; // A 노드의 자식 노드(B, C, D)와 부모(R)를 포함한 조상 노드(X, Y)의 자식 노드 중 방문해야하는 노드들
    
    public int solution(int[] info, int[][] edges) {
        
        nodes = new ArrayList[info.length]; 
        
        for (int i = 0; i < nodes.length; i++) nodes[i] = new ArrayList<>();
        
        for (int i = 0; i < info.length; i++) {
            if (info[i] == 0) totalSheepCnt++; // 총 양의 수를 구하여, 만약 최대 양의 수가 총 양의 수와 같다면 무조건 리턴
        }
        
        if (totalSheepCnt == 0) return 0; // 만약 총 양의 수가 0 이라면 바로 종료
        
        for (int[] edge : edges) {
            int parent = edge[0]; 
            int child = edge[1];
            nodes[parent].add(child); // 단방향 간선을 설정 (부모 -> 자식으로만 이동)
        }
        
        List<Integer> others = new ArrayList<>(); // 남아 있는 여분의 노드 (특정 X가 부모 노드이고, 자식 노드 A, B 노드가 있을 때) 선택되지 않은 자식 노드들 (단 X는 리프 노드를 제외한 노드)
        dfs(0, 0, 0, others, info);
        
        return maxSheepCnt;
    }
    
    void dfs(int sheepCnt, int wolfCnt, int node, List others, int[] info) {
        if (maxSheepCnt == totalSheepCnt) return; // 총 양의 수와 최대 방문한 양의 개수가 같다면 dfs 종료
        
        if (info[node] == 0) sheepCnt++;
        else wolfCnt++;
        maxSheepCnt = Math.max(maxSheepCnt, sheepCnt);
        
        if (sheepCnt <= wolfCnt) return; // 문제에서 늑대가 양의 개수보다 더 크다면 현재 dfs 종료
        
        List<Integer> remains = new ArrayList<>();
        remains.addAll(others); // 앞 서 정의한 방문해야하는 노드 추가
        
        if (!nodes[node].isEmpty()) {
            remains.addAll(nodes[node]);
        }
        
        remains.remove(Integer.valueOf(node)); // remove에 레퍼타입을 적용하면 해당 값은 index가 아닌 value로 제거 가능
        
        for (int nextNode : remains) {
            dfs(sheepCnt, wolfCnt, nextNode, remains, info);
        }
    }
}