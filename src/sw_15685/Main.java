package sw_15685;// 최종 모든 4각형 좌표가 true인 개수 찾기

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        
        DragonCurve curve = new DragonCurve();
        
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            curve.setMap(parseInt(st.nextToken()), parseInt(st.nextToken()),
                        parseInt(st.nextToken()), parseInt(st.nextToken()));
        }
        
        curve.build();
        System.out.println(curve.getSquare());
    }
}

class DragonCurve {
    boolean[][] map = new boolean[101][101]; // 0 ~ 100 까지 유효한 좌표로 드래곤 커브가 된 좌표
    List<Start> starts = new ArrayList<>(); // 드래곤 커브 시작점
    void setMap(int x, int y, int dir, int g) {
        starts.add(new Start(x, y, dir, g));
    }
    
    // 0: x++, 1: y--, 2: x--, 3: y++
    void build() {
        for (Start start : starts) {
            List<Point> nowPoints = new ArrayList<>(); // 드래곤 커브로 재귀 형태로 회전할 리스트
            nowPoints.add(new Point(start.x, start.y)); // 시작점 입력으로 추가
            map[start.x][start.y] = true; // 시작점 드래곤 커브에 true
            
            int referX = 0; // 90도 시계방향으로 회전시킬 기준점이 되는 x 좌표
            int referY = 0;
            
            switch (start.dir) {
                case 0:
                    referX = start.x + 1;
                    referY = start.y;
                    break;
                case 1:
                    referX = start.x;
                    referY = start.y - 1;
                    break;
                case 2:
                    referX = start.x - 1;
                    referY = start.y;
                    break;
                case 3:
                    referX = start.x;
                    referY = start.y + 1;
                    break;
            }
            map[referX][referY] = true;
            Point refer = new Point(referX, referY);
            nowPoints.add(refer);
            doCurve(1, start.g, nowPoints); // 재귀 실행
        }
    }
    
    // 특정 점을 기준으로 시계 방향 회전
    // x = a + (b - y)
    // y = b - (a - x)
    void doCurve(int k, int g, List<Point> nowPoints) { // 참조 #1
        if (k == g + 1) return; // 만약 드래곤 커브 세대가 끝이나면 종료

        Point refer = nowPoints.get(nowPoints.size() - 1); // 마지막 입력은 모두 참조점이 됨
        int prePointIdx = nowPoints.size() - 2; // 참조점을 제외하고 회전시킬 인덱스 (계속 리스트에 추가하므로 인트값을 설정 해야 함)
        for (int i = prePointIdx; i >= 0; i--) { // 내림차순 인덱스로 설정 참조 #2
            Point p = nowPoints.get(i);
            int nextX = refer.x + (refer.y - p.y); // 공식 적용
            int nextY = refer.y - (refer.x - p.x);
            map[nextX][nextY] = true;
            nowPoints.add(new Point(nextX, nextY)); // 다음 좌표 리스트에 추가
        }

        doCurve(k + 1, g, nowPoints); // 재귀 실행
    }
    
    int getSquare() {
        int result = 0;
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 100; col++) {
                if (map[row][col] && map[row][col + 1] &&
                   map[row + 1][col] && map[row + 1][col + 1]) // 0 ~ 99까지 순회하며 4개의 좌표가 모두 true라면 정사각형 가능
                    result++;
            }
        }
        return result;
    }
    
    static class Start {
        int x; // x 좌표
        int y; // y 좌표
        int dir; // 방향성
        int g; // 세대
        
        Start (int x, int y, int dir, int g) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.g = g;
        }
    } 
    
    static class Point {
        int x;
        int y;
        
        Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}