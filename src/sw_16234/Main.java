package sw_16234;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = parseInt(st.nextToken());
        int L = parseInt(st.nextToken());
        int R = parseInt(st.nextToken());
        
        PeopleMove pm = new PeopleMove(N, L, R);
        
        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++) {
                pm.setMap(row, col, parseInt(st.nextToken()));
            }
        }
        
        pm.run();
        System.out.println(pm.getDays());
    }
}

class PeopleMove {
    
    static final int[] DR = {-1, 0, 1, 0};
    static final int[] DC = {0, -1, 0, 1};
    
    int n; //  n by n 행렬
    int low; // 인구 차이 R 이하의 R
    int high; // 인구 차이 L 이상의 L
    int[][] map;
    int result; // 결과
    int currentPeopleCnt; // dfs  로얻은 현재 인구 수
    List<Point> current; // 현재 dfs에 적용할 때, 인구 이동해야하는 포인트
    boolean[][] visited; // 방문
    
    PeopleMove (int n, int low, int high) {
        map = new int[n][n];
        visited = new boolean[n][n];
        
        this.n = n;
        this.low = low;
        this.high = high;
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    void run() {
        
        while(true) {
            int breakPoint = 0; // 만약 current.size() == 1 이라면 (인구 이동 X) breakPoint++ 하여 n * n 개수와 같은지 판단
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    current = new ArrayList<>(); // dfs에 적용할 current 리스트
                    currentPeopleCnt = 0; // 0 초기화
                    dfs(row, col); // dfs
                    
                    // 국경선이 닫힌 것이므로 이제 인구이동 시작하기 
                    if (current.size() > 1) {
                        int avgPeopleCnt = currentPeopleCnt / current.size();
                        for (Point p : current) map[p.row][p.col] = avgPeopleCnt; // 인구 평균으로 업데이트
                    } else breakPoint++; // 만약 모든 dfs가 하나씩만 나오게 되는 경우
                }
            }

            
            if (breakPoint == n * n) break; // 인구 이동할 수 없으므로 종료
            result++; // 인구 이동 가능하므로 횟수 ++
            for (int row = 0; row < n; row++) Arrays.fill(visited[row], false); //dfs 결과 초기화
        }
    }

    void dfs(int row, int col) {
 
        if (visited[row][col]) return; // 만약 방문한 경우 return
        visited[row][col] = true; // 방문 표시
        
        currentPeopleCnt += map[row][col]; // currentPeopleCnt 전역변수에 ++
        current.add(new Point(row, col)); // current 리스트에 추가
        
        for (int k = 0; k < 4; k++) {
            int nextR = row + DR[k];
            int nextC = col + DC[k];
            if (!isValid(row, col, nextR, nextC)) continue;
            dfs(nextR, nextC); // 재귀
        }
    }
    
    // 현재 행, 열 --- 지금 행, 열 비교 인덱스 유효하고, 인접한 두 좌표가 low <= 차이 <= high 여야 함
    boolean isValid(int preRow, int preCol, int nowRow, int nowCol) {
        return nowRow >= 0 && nowRow < n && nowCol >= 0 && nowCol < n 
            && Math.abs(map[preRow][preCol] - map[nowRow][nowCol]) <= high
            && Math.abs(map[preRow][preCol] - map[nowRow][nowCol]) >= low;
    }
    
    int getDays() {
        return result;
    }
    
    static class Point {
        int row;
        int col;
        
        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}