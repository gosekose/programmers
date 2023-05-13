package sw_17822;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        int t = parseInt(st.nextToken());
        
        Board board = new Board(n, m);
        
        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= m; col++) {
                board.setMap(row, col, parseInt(st.nextToken()));
            }
        }
        
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int x = parseInt(st.nextToken()); // 배수 행
            int dir = parseInt(st.nextToken()); // 방향
            int k = parseInt(st.nextToken()); // 칸 수
            board.rotate(x, dir, k);
        }
        
        System.out.println(board.getSum());
    }
}

class Board {
    int n; // 행의 개수
    int m; // 열의 개수
    int[][] map;
    int[] tmp; // 임시 swap 배열
    
    Board(int n, int m) {
        this.n = n;
        this.m = m;
        map = new int[n + 1][m + 1];
        tmp = new int[m + 1];
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    // dir -> 시계: 0, 반시계: 1
    void rotate(int x, int dir, int k) { // x는 row 배수 형태로
        for (int row = 1; row <= n; row++) {
            if (row % x == 0) { // x의 배수
                swap(map[row], k, dir);
            }
        }

        int leftCol; // 인접한 왼쪽 열
        int rightCol; // 인접한 오른쪽 열
        
        int totalSum = 0; // 총 합
        int totalCnt = 0; // 총 개수
        List<Point> zeros = new ArrayList<>(); // zero가 되는 포인트
        
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= m; col++) {
                int flag = zeros.size(); // zeros.size가 변한다면 인접한 것이 있다는 의미
                int nowValue = map[row][col];

                if (nowValue == 0) continue; // 만약 현재 값이 0인 것은 건너뛰기

                totalSum += nowValue;
                totalCnt++;
                
                if (col == 1) { // 맨 좌측 열은 맨 우측 열 인접하도록 설정하기
                    leftCol = m;
                    rightCol = col + 1; 
                } else if (col == m) {
                    leftCol = m - 1;
                    rightCol = 1;
                } else { // 나머지는 양쪽 열
                    leftCol = col - 1;
                    rightCol = col + 1;
                } 
                
                if (map[row][leftCol] == nowValue) {
                    zeros.add(new Point(row, leftCol));
                }
                
                if (map[row][rightCol] == nowValue) {
                    zeros.add(new Point(row, rightCol));
                }
                
                if (row == 1) { // 맨 윗행은 인접한 행이 바로 아래 행
                    if (nowValue == map[row + 1][col]) zeros.add(new Point(row + 1, col));
                } 
                else if (row == n) { // 맨 아래 행의 인접한 행은 바로 윗행
                    if (nowValue == map[row - 1][col]) zeros.add(new Point(row - 1, col));
                } 
                else {
                    if (nowValue == map[row + 1][col]) zeros.add(new Point(row + 1, col));
                    if (nowValue == map[row - 1][col]) zeros.add(new Point(row - 1, col));
                }

                if (zeros.size() != flag) zeros.add(new Point(row, col)); // 값이 추가되었다는 것을 의미하므로 자신도 추가하기
            }

        }
        
        if (!zeros.isEmpty()) {
            for (Point p : zeros) {
                map[p.row][p.col] = 0; // 인접하여 같은 값도 zero 설정
            }
        } 
        else { // 한 번도 인접한 값이 같은 것이 없음
            if (totalCnt != 0) {
                double avg = (double) totalSum / totalCnt;
                
                for (int row = 1; row <= n; row++) {
                    for (int col = 1; col <= m; col++) {
                        int nowValue = map[row][col];
                        if (nowValue != 0 && avg < nowValue) map[row][col]--; // 평균보다 크다면 --
                        else if (nowValue != 0 && avg > nowValue) map[row][col]++; // 평균보다 작다면 ++
                    }
                }
            }
        }
    }
    
    int getSum() {
        int sum = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= m; col++) sum += map[row][col];
        }
        return sum;
    }

    // 0, 1, 2, 3, 4, 5
    // k만큼 시계 방향 회전 i + k를 하되, 인덱스를 벗어난 경우 m 빼기
    // i = 2, k = 3 => tmp[5] = 2;
    // i = 3, k = 3 => tmp[6 - 5] = 3;
    void swap(int[] arr, int k, int clockWise) { // k 만큼 스왑
        if(clockWise == 0) {    // 시계 방향 회전
            for (int i = 1; i <= m; i++) {
                int next = i + k > m ? i + k - m : i + k;
                tmp[next] = arr[i];
            }
        }

        // 반시계는 왼쪽으로 칸을 옮기되 0보다 작아질 수 있으므로 m을 더하기
        else {    // 반시계 방향 회전
            for (int i = m; i >= 1; i--) {
                int next = i - k > 0 ? i - k : i - k + m;
                tmp[next] = arr[i];
            }
        }

        System.arraycopy(tmp, 1, arr, 1, arr.length - 1);
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