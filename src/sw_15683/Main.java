package sw_15683;

import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        Cctv cctv = new Cctv(n, m); // 인스턴스 생성
        
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < m; col++)
                cctv.setMap(row, col, parseInt(st.nextToken()));
        }
        
        cctv.build();
        
        System.out.println(cctv.getMinNonSafeRegion());
    }
}

class Cctv {
    
    static final int[] DR = {-1, 0, 1, 0, -1, 0}; // -1, 0, 1, 0 에 추가로 -1, 0 을 넣은 이유 참조 #1
    static final int[] DC = {0, -1, 0, 1, 0, -1};
    
    int n; // row 총 개수
    int m; // col 총 개수
    int safeCnt; // 벽과 cctv 개수를 포함한 개수
    int maxSafeCnt; // 최고 안전 지대 개수
    int[][] map;
    List<Point> cctvs = new ArrayList<>(); // cctv를 저장할 리스트
    
    Cctv (int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n][m];
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
        
        if (value != 0) safeCnt++; // 만약 cctv 혹은 벽이라면 안전지대 개수 추가
        if (value != 0 && value != 6) cctvs.add(new Point(row, col)); // cctv 추가
    }
    
    void build() {
        if (cctvs.isEmpty()) { // cctv가 없을 수 있으므로 safeCnt 바로 maxSafeCnt로 할당하고 종료
            maxSafeCnt = safeCnt;
            return;
        }

        boolean[][] nowVisited = new boolean[n][m]; // 방문 위치 설정
        int nowSafeCnt = safeCnt; // nowSafeCnt로 지역변수 설정 후 재귀
        dfs(0, nowSafeCnt, nowVisited);
    }

    // idx: cctv 리스트를 순회할 인덱스, preSafeCnt: 이전 재귀에서 얻은 안전지대 개수, preVisited: 이전 재귀에서 방문한 곳
    void dfs(int idx, int preSafeCnt, boolean[][] preVisited) {
        if (idx == cctvs.size()) { // 만약 모든 cctv를 전부 탐색했다면
            maxSafeCnt = Math.max(preSafeCnt, maxSafeCnt);
            return; // 최대값 업데이트 후 종료
        }
        Point p = cctvs.get(idx); // 인덱스로 cctv 포인트 가져오기
        int row = p.row;
        int col = p.col;
        int value = map[row][col];

        if (value == 1) { // 1번 시시티비라면
            for (int k = 0; k < 4; k++) { // 상 하 좌 우 한 번만 이동 가능
                int nowSafeCnt = preSafeCnt; // nowSafeCnt로 지역 변수 할당 참조#2
                boolean[][] nowVisited = getNowVisited(preVisited); // preVisted를 깊은 복사한 nowVisited 생성
                nowSafeCnt = viewToDir(row, col, DR[k], DC[k], nowSafeCnt, nowVisited); // while문으로 벽이 나올 때까지 탐색 참조#3
                dfs(idx + 1, nowSafeCnt, nowVisited); // 재귀 실행
            }
        }

        else if (value == 2) {
            for (int k = 0; k < 2; k++) { // 짝 i = 0, i = 2 // i = 1, i = 3
                int nowSafeCnt = preSafeCnt;
                boolean[][] nowVisited = getNowVisited(preVisited);
                for (int d = 0; d < 2; d++) // 2번은 '좌우' 혹은 '상하'가 짝이므로 이중 포문으로 짝이 나올 수 있도록 2d + k 설정
                    nowSafeCnt = viewToDir(row, col, DR[k + 2 * d], DC[k + 2 * d], nowSafeCnt, nowVisited);
                dfs(idx + 1, nowSafeCnt, nowVisited);
            }
        }

        else if (value == 3) {
            for (int k = 0; k < 4; k++) {
                int nowSafeCnt = preSafeCnt;
                boolean[][] nowVisited = getNowVisited(preVisited);
                for (int d = 0; d < 2; d++) // 3번은 'ㄱ' 이 나와야 하므로 k + d로 '상좌', '좌하', '하우', '우상' 설정
                    nowSafeCnt = viewToDir(row, col, DR[k + d], DC[k + d], nowSafeCnt, nowVisited);
                dfs(idx + 1, nowSafeCnt, nowVisited);
            }
        }

        else if (value == 4) {
            for (int k = 0; k < 4; k++) {
                int nowSafeCnt = preSafeCnt;
                boolean[][] nowVisited = getNowVisited(preVisited);
                for (int d = 0; d < 3; d++) // 4번은 'ㅜ'가 나와야 하므로 k + d로  '상하좌', '상하우', '좌우상' '좌우하' 설정
                    nowSafeCnt = viewToDir(row, col, DR[k + d], DC[k + d], nowSafeCnt, nowVisited);
                dfs(idx + 1, nowSafeCnt, nowVisited);
            }
        }

        else {
            int nowSafeCnt = preSafeCnt;
            boolean[][] nowVisited = getNowVisited(preVisited);
            for (int k = 0; k < 4; k++) // 5번은 상하좌우 모두 탐색해야하므로 단일 포문으로 모두 탐색하도록 진행
                nowSafeCnt = viewToDir(row, col, DR[k], DC[k], nowSafeCnt, nowVisited);
            dfs(idx + 1, nowSafeCnt, nowVisited);
        }
    }

    // 유효한 인덱스에 row, col의 map 값이 벽이 아니여야함 (cctv는 통과 가능)
    boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m && map[row][col] != 6;
    }

    int viewToDir(int row, int col, int dr, int dc, int nowSafeCnt, boolean[][] nowVisited) {
        while(true) {
            row += dr; // row에 값 업데이트
            col += dc; // col에 값 업데이트

            if (!isValid(row, col)) break; // 만약 벽이거나 유효한 인덱스가 아니라면 종료
            if (!nowVisited[row][col] && map[row][col] == 0) { // 방문하지 않은 경우에만 참조#4
                nowVisited[row][col] = true;
                nowSafeCnt++;
            }
        }
        return nowSafeCnt;
    }

    boolean[][] getNowVisited(boolean[][] preVisited) { // 깊은 복사 진행
        boolean[][] nowVisited = new boolean[n][m];
        for (int r = 0; r < n; r++) nowVisited[r] = preVisited[r].clone();
        return nowVisited;
    }
    
    int getMinNonSafeRegion() {
        return n * m - maxSafeCnt;
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