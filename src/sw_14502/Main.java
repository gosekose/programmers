package sw_14502;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        Lab lab = new Lab(n, m);
        
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++)
                lab.setMap(row, col, parseInt(st.nextToken()));
        }
        
        lab.buildWall();
        System.out.println(lab.getSafeRegion());
    }
}

class Lab {
    
    static final int[] DR = {-1, 0, 1, 0};
    static final int[] DC = {0, -1, 0, 1};    
    
    int n; // row의 총 개수
    int m; // col의 총개수
    int minVirusCnt = Integer.MAX_VALUE; // 최소 바이러스 확진
    int virusCnt; // dfs로 실행된 각 조합에 바이러스 확진
    int[][] map; // 맵
    int wallsCnt = 3; // 벽의 개수 (무조건 3개를 세울 수 있으므로 초기화 3)
    
    boolean[][] visited; // 방문 여부
    List<Point> virus = new ArrayList<>(); // 바이러스 저장 리스트
    Combination combi; // 벽이 생성될 수 있는 조합
    
    Lab (int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m];
        visited = new boolean[n][m];
        combi = new Combination();
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
        if (value == 0) combi.setInputs(new Point(row, col)); // 벽을 세울 수 있는 곳이라면 조합에 input 추가
        else if (value == 1) wallsCnt++; // 벽 개수 추가
        else virus.add(new Point(row, col)); // 바이러스 point 추가
    }
    
    void buildWall() {
        List<List<Point>> possibleCombi = combi.getCombination(); // 벽을 세울 수 있는 조합 생성
        
        for (List<Point> combis : possibleCombi) {
            for (Point point : combis) { // 각 조합에 있는 3가지 벽의 위치 하나씩
                map[point.row][point.col] = 1; // 벽 세우기
            }
            
            virusCnt = 0; // 각 조합 당 몇 개의 바이러스가 확진 되는지
            for (Point v : virus) {
                dfs(v.row, v.col); // dfs
            }
            
            minVirusCnt = Math.min(minVirusCnt, virusCnt); // 최소 바이러스 설정

            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++)
                    visited[row][col] = false; // 방문여부 다시 초기화
            }
            
            for (Point point : combis) {
                map[point.row][point.col] = 0; // 다시 벽 해제
            }
        }
    }
    
    void dfs(int row, int col) {
        
        visited[row][col] = true; // 방문 설정
        virusCnt++; // 바이러스 개수 증가
        
        for (int i = 0; i < 4; i++) {
            int nextR = row + DR[i]; // 다음 행
            int nextC = col + DC[i]; // 다음 열
            
            if (!isValid(nextR, nextC)) continue; // 이미 방문 혹은 인덱스 벗어났거나, 확진 가능한 곳이 아니라면
            dfs(nextR, nextC); // dfs
        }
    }
    
    int getSafeRegion() {
        return (n * m) - wallsCnt - minVirusCnt; // 총 안전 지대 = 총 장소 - 벽 - 확진자
    }
    
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m && !visited[row][col] &&
            map[row][col] == 0; // 유효한 인덱스 && 방문 X && 감염 안된 사람들
    }
    
    static class Point {
        int row;
        int col;
        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    
    static class Combination { // 조합 생성
        List<Point> inputs = new ArrayList<>(); // 조합에 사용될 인풋
        List<List<Point>> combi = new ArrayList<>(); // 최종 조합

        Combination () {}

        void setInputs(Point point) {
            inputs.add(point); // 리스트에 추가
        }

        List<List<Point>> getCombination() {
            getCombinationHelper(3, 0, new ArrayList<>()); // 조합 재귀 실행
            return combi;
        }

        void getCombinationHelper(int k, int start, List<Point> current) {
            if (k == 0) {
                combi.add(new ArrayList<>(current)); // 깊은 복사로 combi에 추가
                return;
            }
            
            for (int i = start; i < inputs.size(); i++) {
                current.add(inputs.get(i)); // input에 있는 값 조합에 추가
                getCombinationHelper(k - 1, i + 1, current); // 가능한 개수 줄이고, 다음 번 조합은 i를 증가 시켜서 실행
                current.remove(current.size() - 1); // [1, 2]이 조합에 있는 경우 다음 조합에 영향을 주지 않기 위해 [1]로 설정하여 다음 조합 실행
            }
        }
    }
}

