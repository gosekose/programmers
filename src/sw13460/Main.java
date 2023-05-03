package sw13460;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        
        Board board = new Board(n, m);
        
        for (int i = 0; i < n; i++) board.setMap(br.readLine(), i);
        board.moveMarble();
        System.out.println(board.getAnswer());
    }
}

class Board {
    
    static final int[] DX = {-1, 0, 1, 0};
    static final int[] DY = {0, -1, 0, 1};
    
    int n, m;
    Marble redMarble, blueMarble;
    char[][] map;
    boolean[][][][] visited; // Red, Blue 구슬의 각 각 x, y의 방문 여부
    int answer = -1;
    
    Board(int n, int m) {
        this.n = n;
        this.m = m;
        map = new char[n][m];
        visited = new boolean[n][m][n][m];
    }
    
    void setMap(String str, int idx) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map[idx][i] = ch;
            
            if (ch == 'R') redMarble = new Marble(idx, i, 0);
            else if (ch == 'B') blueMarble = new Marble(idx, i, 0);
        }
    }
    
    void moveMarble() {

        Queue<Marble> redQ = new ArrayDeque<>();
        Queue<Marble> blueQ = new ArrayDeque<>();
        
        redQ.add(redMarble);
        blueQ.add(blueMarble);
        
        while(!redQ.isEmpty() && !blueQ.isEmpty()) {
            Marble red = redQ.poll();
            Marble blue = blueQ.poll();

            if (red.step > 10) { // bfs 원리로 인해 x 위치에서 -1, +1 등을 수행하더라도 각 움직인 위치는 step + 1
                answer = -1;
                return;
            }
            
            // 파란색이 0에 도착한 경우
            if (map[blue.x][blue.y] == 'O') continue; // 다른 방법이 존재할 수 있으므로 해당 경로로 진행하는 것만 중지
            
            // 파란색이 도착하지 않고 빨간색이 O에 도착한 경우
            if (map[red.x][red.y] == 'O') {
                answer = red.step;
                return;
            }    
            
            for (int i = 0; i < 4; i++) {
                int bx = blue.x; // 반드시 blue부터 실행
                int by = blue.y; // 만약 기울여서 O을 만난 경우 queue에 넣지 말아야 함

                boolean isBlueCheck = false;

                while (true) {
                    bx += DX[i];
                    by += DY[i];
                    
                    if (map[bx][by] == 'O') {
                        isBlueCheck = true;
                        break;
                    }
                    
                    else if (map[bx][by] == '#') {
                        bx -= DX[i];
                        by -= DY[i];
                        break;
                    }
                }

                if (isBlueCheck) continue;
                
                int rx = red.x;
                int ry = red.y;
                
                while(true) {
                    rx += DX[i];
                    ry += DY[i];
                    
                    // 'O'을 만나도 종료하지 않고 break만 하는 이유는 'O'으로 이동 이전에 Blue 구슬에 의해 이동이 막힐 수도 있으므로
                    // 곧바로 종료하지 않고 break 처리 및 방문 처리 후 for 문 이전에 작성한 체킹으로 가능한 경우인지 판단
                    if (map[rx][ry] == 'O') break;
                    else if (map[rx][ry] == '#') {
                        rx -= DX[i];
                        ry -= DY[i];
                        break;
                    }
                }
                
                // 두개의 위치가 같다면 
                // 서로 다른 큐에 의해 구슬의 앞 뒤 관계를 고려하지 않고 이동하였으므로
                // 두 구슬의 우선순위에 따라 배치를 다르게 해야 함
                if (bx == rx && by == ry && map[rx][ry] != 'O') {
                    
                    // 맨해튼 거리 공식을 쓰는 이유 -> x, y 가 한칸씩 움직일 수 있으므로
                    int redDist = Math.abs(red.x - rx) + Math.abs(red.y - ry);
                    int blueDist = Math.abs(blue.x - bx) + Math.abs(blue.y - by);
                    
                    if (redDist > blueDist) {
                        rx -= DX[i];
                        ry -= DY[i];
                    } else {
                        bx -= DX[i];
                        by -= DY[i];
                    }
                }
                
                if (!visited[rx][ry][bx][by]) {
                    visited[rx][ry][bx][by] = true;
                    
                    redQ.add(new Marble(rx, ry, red.step + 1));
                    blueQ.add(new Marble(bx, by, blue.step + 1));
                }
            }
        }
    }
    
    int getAnswer() {
        return this.answer;
    }
    
    class Marble {
        int x;
        int y;
        int step;
        
        Marble(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }
    }
}
