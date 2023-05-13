package sw_17837;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        
        Game game = new Game(n, k);
        
        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= n; col++) {
                game.setMap(row, col, parseInt(st.nextToken()));
            }
        }
        
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int row = parseInt(st.nextToken());
            int col = parseInt(st.nextToken());
            int dir = parseInt(st.nextToken());
            game.setHorse(row, col, dir);
        }
        
        game.run();
        System.out.println(game.getTurn());
        
    }
}

class Game {
    
    static final int[] DR = {0, 0, 0, -1, 1}; // 3, 4
    static final int[] DC = {0, 1, -1, 0, 0}; // 1, 2
    
    int n;
    int k;
    int turn;
    int[][] map;
    List<Horse>[][] horseMap;
    List<Horse> horses = new ArrayList<>();
        
    Game (int n, int k) {
        this.n = n;
        this.k = k;
        map = new int[n + 1][n + 1];
        horseMap = new ArrayList[n + 1][n + 1];

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++)
                horseMap[row][col] = new ArrayList<>();
        }
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    void setHorse(int row, int col, int dir) {
        Horse horse = new Horse(row, col, dir);
        horses.add(horse);
        horseMap[row][col].add(horse);
    }
    
    void run() {
        while (turn < 1001) {
            for (Horse h : horses) {
                
                int nextR = h.row + DR[h.dir]; // 이동할 방향에 따라 이동
                int nextC = h.col + DC[h.dir]; // 이동할 방향에 따라 이동

                if (!isValid(nextR, nextC) || map[nextR][nextC] == 2) {
                    if (h.dir == 1) h.dir = 2;
                    else if (h.dir == 2) h.dir = 1;
                    else if (h.dir == 3) h.dir = 4;
                    else if (h.dir == 4) h.dir = 3;

                    nextR = nextR + 2 * DR[h.dir];
                    nextC = nextC + 2 * DC[h.dir];
                    
                    if (!isValid(nextR, nextC) || map[nextR][nextC] == 2) continue;
                }  // 만약 다음에 이동하려는 곳이 또 파랑이라면 넘기기

                // nextR, nextC는 흰 혹은 빨강만 남게 됨
                if (map[nextR][nextC] == 0) setWhite(h, nextR, nextC);
                else if (map[nextR][nextC] == 1) setRed(h, nextR, nextC);

                if (horseMap[nextR][nextC].size() >= 4) {
                    turn++;
                    return;
                }
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    System.out.print(horseMap[i][j].size());
                }
                System.out.println();
            }
            System.out.println();
            turn++;
        }
    }

    boolean isValid(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n; // 유효한 범위
    }
    
    void setWhite(Horse h, int nextR, int nextC) {
        int row = h.row;
        int col = h.col;
        int idx = 0;
        boolean flag = false;
        for(Horse horse : horseMap[row][col]) {
            if (h == horse) flag = true;

            if (flag) {
                horseMap[nextR][nextC].add(horse);
                horse.row = nextR;
                horse.col = nextC;
                idx++;
            }
        }

        for (int i = 0; i < idx; i++) {
            horseMap[row][col].remove(0);
        }

    }
    
    void setRed(Horse h, int nextR, int nextC) {
        int row = h.row;
        int col = h.col;
        int idx = 0;

        List<Horse> localHorses = horseMap[row][col];
        for (int i = localHorses.size() - 1; i >= 0; i--) {
            Horse horse = localHorses.get(i);
            horseMap[nextR][nextC].add(horse);
            horse.row = nextR;
            horse.col = nextC;
            idx = i;
            if (h == horse) break;
        }

        int localSize = localHorses.size() - 1;
        for (int i = localSize; i >= idx; i--) {
            localHorses.remove(i);
        }
    }
    
    int getTurn() {
        return turn <= 1000 ? turn : -1;
    }
    
    static class Horse {
        int row;
        int col;
        int dir;
        
        Horse (int row, int col, int dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }
    }
}