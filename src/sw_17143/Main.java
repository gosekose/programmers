package sw_17143;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int r = parseInt(st.nextToken());
        int c = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
    
        Fishing fishing = new Fishing(r, c);
        
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            fishing.setMap(parseInt(st.nextToken()), parseInt(st.nextToken())
                    ,parseInt(st.nextToken()), parseInt(st.nextToken())
                    ,parseInt(st.nextToken()));
        }
        
        fishing.start();
        
        System.out.println(fishing.getAmount());
    }
}

class Fishing {
    int amount;
    int n; // 행 개수
    int m; // 열 개수 (시작 , 열 , 끝) -> m + 2
     
    Shark[][][] map;
    List<Shark> sharks = new LinkedList<>();
    
    Fishing (int n, int m) {
        this.n = n;
        this.m = m;
        map = new Shark[2][n + 1][m + 2];
    }
    
    // row = 1, col = 1 부터 시작
    void setMap(int row, int col, int speed, int dir, int size) {
        Shark shark = new Shark(row, col, speed, dir, size);
        map[0][row][col] = shark;
        sharks.add(shark);
    }
    
    void start() {
        int king = 0; // 낚시왕 위치 (0번 열)
        while (king < m + 1) { // 낚시 왕이 마지막 인덱스에 도달할 때까지 
            king++; // 낚시왕 한칸 이동
            
            for(int row = 1; row <= n; row++) {
                if (map[0][row][king] != null) { // 만약 왕의 위치에 상어가 있다면
                    Shark shark = map[0][row][king];
                    amount += shark.size;
                    map[0][row][king] = null; // 상어 참조 끊기
                    sharks.remove(shark); // 상어 제거
                    break;
                }
            }
            
            // 상어 이동하기 모두 이동을 마친 후 잡아먹을 수 있음
            for (Shark shark : sharks) move(shark);

            sharks = new LinkedList<>();
            for (int row = 1; row <= n; row++) {
                for (int col = 1; col <= m; col++) {
                    if (map[1][row][col] != null) {
                        map[0][row][col] = map[1][row][col];
                        map[1][row][col] = null;
                        sharks.add(map[0][row][col]);
                    }
                }
            }

        }
    }
    
    void move(Shark shark) {
        int nowRow = shark.row;
        int nowCol = shark.col;

        int nextRow = nowRow;
        int nextCol = nowCol;

        int dir = shark.dir; // 1: 위, 2: 아래, 3: 오른, 4: 왼
        int remain = shark.speed; // 상어가 일초동안 이동해야하는 크기;

        if (dir == 1 || dir == 2) { // 위로 이동이므로 row를 -- 아래로 이동은 row++
            while (remain > 0) { // 남아 있는 양

                if (nextRow == 1 && dir == 1) dir = 2;
                else if (nextRow == n && dir == 2) dir = 1;

                if (dir == 1) nextRow--;
                else nextRow++;
                remain--; // 남은 것 감소시키기
            }
        }

        else if (dir == 3 || dir == 4) {
            while (remain > 0) { // 남아 있는 양

                if (nextCol == 1 && dir == 4) dir = 3;
                else if (nextCol == m && dir == 3) dir = 4;

                if (dir == 3) nextCol++;
                else nextCol--;
                remain--; // 남은 것 감소시키기
            }
        }

        shark.row = nextRow; // 모든 이동 마침 (상어 업데이트)
        shark.col = nextCol;
        shark.dir = dir;
        map[0][nowRow][nowCol] = null; // 기존에 있던 위치 참조 제거하기

        if (map[1][nextRow][nextCol] != null) {
            Shark nowShark = map[1][nextRow][nextCol];
            if (nowShark.size < shark.size) map[1][nextRow][nextCol] = shark;
        } else map[1][nextRow][nextCol] = shark; // 상어 위치 업데이트 하기
    }
    
    int getAmount() {
        return amount;
    }

    static class Shark {
        int row;
        int col;
        int speed;
        int dir;
        int size;
        
        Shark (int row, int col, int speed, int dir, int size) {
            this.row = row;
            this.col = col;
            this.speed = speed;
            this.dir = dir;
            this.size = size;
        }
    }

}

