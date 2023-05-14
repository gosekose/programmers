package sw_17140;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int r = parseInt(st.nextToken());
        int c = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        
        ArrayCalculation array = new ArrayCalculation(r, c, k);
        
        for (int row = 1; row <= 3; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= 3; col++) {
                array.setMap(row, col, parseInt(st.nextToken()));
            }
        }
        
        array.start();
        System.out.println(array.getTime());
    }
}

class ArrayCalculation {
    int r; // 찾아야 하는 행
    int c; // 찾아야 하는 열
    int k; // 되어야 하는 값
    int time; // k가 되는데까지 걸린 시간
    int rowCnt = 3; // 처음 시작은 3 by 3 이므로 행의 개수
    int colCnt = 3; // 열의 개수
    int[][] map = new int[101][101]; // 총 열과 행의 개수는 100을 넘지 않음 (101 인 이유는 1행, 1열 부터 시작)
    Point[] points = new Point[101];
    
    ArrayCalculation (int r, int c, int k) {
        this.r = r;
        this.c = c;
        this.k = k;
        
        for (int i = 1; i <= 100; i++) points[i] = new Point(i); // 포인트 초기화
    }
    
    void setMap(int row, int col, int value) {
        map[row][col] = value;
    }
    
    void start() {
        while(time < 101) {

            if (map[r][c] == k) return; // 종료 조건

            int nextRowCnt = rowCnt; // 다음 while문에 적용 할 RowCnt
            int nextColCnt = colCnt; // 다음 while문에 적용 할 ColCnt

            if (rowCnt >= colCnt) { // 행이 열보다 같거나 긴 경우
                for (int row = 1; row <= rowCnt; row++) {
                    List<Point> sortList = new ArrayList<>();
                    
                    for (int col = 1; col <= colCnt; col++) {
                        int value = map[row][col]; // map에 저장된 값

                        if (value == 0) continue;
                        Point p = points[value];

                        p.cnt++;
                        if (!p.in) { // 만약 p가 리스트에 없다면
                            p.in = true; // 리스트에 있다는 표시
                            sortList.add(p); // 리스트 추가
                        }
                    }

                    sortList.sort(Comparator.comparing((Point po) -> po.cnt)
                            .thenComparing(po -> po.num));

                    int idx = 1;
                    for (Point p : sortList) {
                        if (idx >= 101) break;
                        map[row][idx] = p.num; // 먼저 1번 인덱스 수
                        map[row][++idx] = p.cnt; // 그 다음 인덱스로 cnt
                        idx++; // idx 증가시키기
                    }
                    
                    for (int col = idx; col < 101; col++) {
                        map[row][col] = 0; // 남아있는 열은 모두 0으로 설정하기
                    }
                    
                    if (row == 1) nextColCnt = idx; // 변경된 col 개수 업데이트
                    else nextColCnt = Math.max(nextColCnt, idx);
                    
                    for (Point p : sortList) {
                        p.cnt = 0; // 초기화 작업
                        p.in = false; // 초기화 작업
                    }
                }
            } 
            
            else {
                for (int col = 1; col <= colCnt; col++) {
                    List<Point> sortList = new ArrayList<>();

                    for (int row = 1; row <= rowCnt; row++) {
                        int value = map[row][col]; // map에 저장된 값
                        if (value == 0) continue;
                        Point p = points[value];

                        p.cnt++;
                        if (!p.in) {
                            p.in = true;
                            sortList.add(p);
                        }
                    }

                    sortList.sort(Comparator.comparing((Point po) -> po.cnt)
                            .thenComparing(po -> po.num));

                    int idx = 1;
                    for (Point p : sortList) {
                        if (idx > 101) break;
                        map[idx][col] = p.num; // 먼저 1번 인덱스 수
                        map[++idx][col] = p.cnt; // 그 다음 인덱스로 cnt
                        idx++; // idx 증가시키기
                    }

                    for (int row = idx; row < 101; row++) {
                        map[row][col] = 0; // 남아있는 열은 모두 0으로 설정하기
                    }

                    if (col == 1) nextRowCnt = idx; // 변경된 col 개수 업데이트
                    else nextRowCnt = Math.max(nextRowCnt, idx);

                    for (Point p : sortList) {
                        p.cnt = 0; // 초기화 작업
                        p.in = false; // 초기화 작업
                    }
                }
            }

            rowCnt = nextRowCnt;
            colCnt = nextColCnt;

            time++;
        }
    }
    
    int getTime() {
        return time <= 100 ? time : -1; // 100 이하라면 그대로, 101이 된다면 -1
    }
    
    static class Point {
        int num; // point 번호 (숫자)
        int cnt; // 숫자가 행 혹은 열에 나온 갯수
        boolean in; // 리스트에 있는지 없는지 판단
        
        Point (int num) {
            this.num = num;
        }
    }
}