package sw_16236;// 백트래킹

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        
        BabyShark shark = new BabyShark(n);
        
        for (int row = 0; row < n; row++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++)
                shark.setMap(row, col, parseInt(st.nextToken()));
        }
        
        shark.eat();
        System.out.println(shark.getTime());
    }
}

class BabyShark {
    
    static final int[] DR = {-1, 0, 0, 1};
    static final int[] DC = {0, -1, 1, 0};
    
    int n;
    int[][] map;
    int visitedCount;
    int[][] visited;
    int time;
    Shark shark;
    
    BabyShark(int n) {
        this.n = n;
        map = new int[n][n]; // n by n;
        visited = new int[n][n]; // 방문 여부를 int type으로 처리 참조 #1
    }
    
    void setMap(int row, int col, int value) {      
        if (value == 9) shark = new Shark(row, col, 2, 0);
        else if (value != 0) {
            map[row][col] = value;
        }
    }
    
    void eat() {

        while (true) {
            Queue<Point> queue = new ArrayDeque<>(); // queue는 point를 입력하여 가장 가까운 거리를 찾기 위한 용도
            queue.add(new Point(shark.row, shark.col, 0)); // 현재 위치한 상어 위치 설정
            Point fish = null; // 물고기 위치 초기화

            int minTime = Integer.MAX_VALUE; // 최소 시간 최대치로 초기화
            visitedCount++; // 방문한 개수 ++
            visited[shark.row][shark.col] = visitedCount; // 방문처리 (매번 boolean[][]을 생성하거나 false로 초기화 하는 것을 방지 )

            while(!queue.isEmpty()) {
                Point point = queue.poll();

                int row = point.row; 
                int col = point.col;

                if (minTime < point.time) break; // 만약 최소 시간보다 큰 순간부터는 큐의 성질에 의해 time은 현재 타임과 같거나 크게 됨 따라서 종료
                
                if (map[row][col] != 0 && map[row][col] < shark.level) { // 0이 아니면서 상어가 먹을 수 있는 물고기 존재
                    if (fish == null) { // fish에 값이 없다면
                        fish = point;
                        minTime = Math.abs(fish.row - shark.row) + Math.abs(fish.col - shark.col);
                    } else { // fish에 값이 있다면 업데이트 가능 여부 판단
                      if (fish.row > point.row || fish.row == point.row && fish.col > point.col) { // 더 행이 높은 곳에 있거나 (row가 더 작은 것 ), 두 행의 높이가 같다면 더 왼쪽에 가까운  것(col이 더 작은 것)
                          fish = point;
                      }
                    }
                }

                for (int k = 0; k < 4; k++) {
                    int nextR = row + DR[k];
                    int nextC = col + DC[k];

                    if (!isValid(nextR, nextC)) continue; // 유효한 인덱스 X, 물고기가 더 클 때,  visitedCount 같다면 continue
                    queue.add(new Point(nextR, nextC, point.time + 1));
                    visited[nextR][nextC] = visitedCount; // 방문 여부를 visitedCount로 설정함으로써, 방문 여부를 체크하는 것
                }
            }
            
            if (fish == null) break; // 만약 더 이상 물고기가 없다면 종료

            time += fish.time; // 물고기 찾는데 걸린 시간 추가하기
            shark.eatenCnt++; // 상어가 먹은 개수 업데이트
            map[fish.row][fish.col] = 0; // 먹은 물고기 없애기

            shark.row = fish.row; // 물고기 위치로 상어 업데이트
            shark.col = fish.col;
            
            if (shark.eatenCnt == shark.level) { // 만약 먹은 개수와 상어 레벨이 같다면
                shark.level++; // 상어 레벨 업데이트
                shark.eatenCnt = 0; // 먹은 개수 0 초기화
            }
        }

    }
    
    // 유효한 범위 && 물고기가 상어보다 작고 방문 안한 경우 (인트 값이 다른 경우)
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n
            && map[row][col] <= shark.level && visited[row][col] != visitedCount;
    }
    
    int getTime() {
        return time;
    }
    
    static class Shark {
        int row;
        int col;
        int level;
        int eatenCnt;
        
        Shark (int row, int col, int level, int eatenCnt) {
            this.row = row;
            this.col = col;
            this.level = level;
            this.eatenCnt = eatenCnt;
        }
    }
    
    static class Point {
        int row;
        int col;
        int time;

        Point(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }
}