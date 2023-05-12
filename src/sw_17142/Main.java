package sw_17142;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        Virus virus = new Virus(n, m);
        
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++)
                virus.setMap(row, col, parseInt(st.nextToken()));
        }
        
        virus.run();
        System.out.println(virus.getTime());
    }
}

class Virus {
    
    static final int[] DR = {-1, 0, 1, 0}; // 이동 row
    static final int[] DC = {0, -1, 0, 1}; // 이동 col
    
    int n; // n by n 행렬
    int m; // 선택해야 하는 조합 개수
    int time = Integer.MAX_VALUE; // 최소 시간을 위해 최대 시간 으로 초기화
    int brickCnt; // 벽 개수
    int[][] map; // 맵 설정
    int[][] visited; // 방문 여부 2차원 인트 배열
    int visitedCnt; // 방문 수
    int impossibleCnt; // 모두 불가능한 경우 판단
    List<Point> virus = new ArrayList<>(); // 바이러스 리스트
    boolean[] active; // 활성화 바이러스를 위한 백트래킹 불린 배열
    
    Virus (int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][n];
        visited = new int[n][n];
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
        if (value == 2) virus.add(new Point(row, col)); // 바이러스 포인트 리스트에 추가
        if (value == 1) brickCnt++; // 벽 개수 추가
    }
    
    void run() {
        active = new boolean[virus.size()]; // 활성화 벽 불린 배열 초기화
        combi(0, 0); // 재귀로 조합 설정하기
    }
    
    void combi(int idx, int k) {
        if (k == m) { // 확산 시키기
            visitedCnt++; // k == m 인 걍우의 조합
            int remainDiffusionCnt = n * n - brickCnt - virus.size(); // 남아있는 확산되어야 할 빈칸의 수
            Queue<Point> queue = new ArrayDeque<>(); // 큐에 넣기 위함 (bfs)
            for (int i = 0; i < active.length; i++) {
                if (active[i]) queue.add(virus.get(i)); // 활성바이러스 큐에 넣기
            } 
            
            int localTime = 0; // 지역 시간 설정
            while(!queue.isEmpty()) {
                Point p = queue.poll();

                if (visited[p.row][p.col] == visitedCnt) continue; // 방문한 경우 건너 뛰기
                visited[p.row][p.col] = visitedCnt; // 방문 설정
                if (map[p.row][p.col] == 0) remainDiffusionCnt--; // 빈 칸이라면 남아있는 확산 개수 줄이기

                if (remainDiffusionCnt == 0) {
                    localTime = p.time; // 선입 선출로 큐에 들어온 time은 증가 함수이므로 break;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int nextR = p.row + DR[i];
                    int nextC = p.col + DC[i];
                    
                    if (!isValid(nextR, nextC)) continue;
                    queue.add(new Point(nextR, nextC, p.time + 1));
                }
            }
            
            if (remainDiffusionCnt != 0) impossibleCnt++; // 만약 남아있는 확산 cnt가 있다면 impossible 증가
            else time = Math.min(localTime, time); // 모두 확산 시킨경우 최소값으로 업데이트
                
            return;
        }

        // 백트래킹
        for (int i = idx; i < virus.size(); i++) {
            active[i] = true;
            combi(i + 1, k + 1);
            active[i] = false;
        }
    }

    int getTime() {
        if (visitedCnt == impossibleCnt) { // 만약 조합의 경우의 수가 모두 불가능한 경우
            time = -1;
        }

        return time;
    }
    
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && visited[row][col] != visitedCnt
            && map[row][col] != 1; 
    }
    
    static class Point {
        int row;
        int col;
        int time;
        
        Point (int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
        
        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}