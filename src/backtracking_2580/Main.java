package backtracking_2580;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    static boolean sudokuBreak; // 스도쿠를 찾으면 종료
    static int[][] map; // 값을 저장할 맵
    static boolean[] check = new boolean[10]; // row, col, block에서 값이 없는 숫자를 확인
    static List<Point> zeros = new ArrayList<>(); // zero가 저장된 위치 저장
    static Map<Integer, Set<Integer>> hashMap = new HashMap<>(); // hashMap에 스도쿠 row, col, block에 숫자 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        map = new int[10][10]; // 맵 초기화
        zeros = new ArrayList<>(); // zero 초기화

        for (int row = 1; row <= 9; row++) {
            hashMap.put(row, new HashSet<>());
            hashMap.put(row * -1, new HashSet<>()); // col은 겹치지 않도록 음수로 저장
            hashMap.put(row + 100, new HashSet<>()); // block은 겹치지 않도록 + 100
        }

        for (int row = 1; row <= 9; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= 9; col++) {
                int num = parseInt(st.nextToken());
                map[row][col] = num;
                if (num == 0) zeros.add(new Point(row, col)); // 값이 zero라면 zeros에 추가
            }
        }

        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                int num = map[row][col];
                if (num != 0) { // 값이 0이 아니라면, rowSet, colSet, blockSet에 추가
                    Set<Integer> rowSet = hashMap.get(row);
                    Set<Integer> colSet = hashMap.get(col * -1);

                    rowSet.add(num);
                    colSet.add(num);

                    Set<Integer> blockSet = getBlockSet(row, col);
                    blockSet.add(num);
                }
            }
        }

        for (Point p : zeros) setPossible(p); // zero 위치에서 가능한 숫자 저장

        StringBuilder sb = new StringBuilder();
        if (zeros.size() == 0) { // 만약 zeros가 0이라면 그대로 출력
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++)
                    sb.append(map[i][j]).append(" ");
                sb.append("\n");
            }
            System.out.print(sb);
            return;
        }

        dfs(0); // 재귀

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++)
                sb.append(map[i][j]).append(" ");
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static void dfs(int count) {
        // 검사영역, row, col, 3 by 3
        if (count == zeros.size()) { // 만약 모두 만족했다면 종료
            sudokuBreak = true;
            return;
        }

        Point p = zeros.get(count); // 포인트 가져오기
        for (Integer i : p.possible) { // p 위치에서 가능한 숫자

            if (!isValid(p.row, p.col, i))  continue; // 불가능한 경우라면 패스

            map[p.row][p.col] = i; // 값 설정
            Set<Integer> rowSet = hashMap.get(p.row);
            rowSet.add(i); // rowSet에 추가

            Set<Integer> colSet = hashMap.get(p.col * -1);
            colSet.add(i); // colSet에 추가

            Set<Integer> blockSet = getBlockSet(p.row, p.col);
            blockSet.add(i); // 현재 row, col이 속한 block에 추가

            dfs(count + 1);

            if (sudokuBreak) return;
            map[p.row][p.col] = 0; // 백트래킹
            rowSet.remove(i);
            colSet.remove(i);
            blockSet.remove(i);
        }
        
    }
        
    static boolean isValid(int row, int col, int num) { // value가 있다면 종료
        Set<Integer> rowSet = hashMap.get(row);
        if (rowSet.contains(num)) return false;

        Set<Integer> colSet = hashMap.get(col * -1);
        if (colSet.contains(num)) return false;

        Set<Integer> blockSet = getBlockSet(row, col);
        return !blockSet.contains(num);
    }

    static Set<Integer> getBlockSet(int row, int col) { //block 설정하기
        Set<Integer> blockSet;
        if (row <= 3 && col <= 3) blockSet = hashMap.get(101);
        else if (row <= 6 && col <= 3) blockSet = hashMap.get(102);
        else if (col <= 3) blockSet = hashMap.get(103);
        else if (row <= 3 && col <= 6) blockSet = hashMap.get(104);
        else if (row <= 6 && col <= 6) blockSet = hashMap.get(105);
        else if (col <= 6) blockSet = hashMap.get(106);
        else if (row <= 3) blockSet = hashMap.get(107);
        else if (row <= 6) blockSet = hashMap.get(108);
        else blockSet = hashMap.get(109);
        return blockSet;
    }


    static void setPossible(Point p) { // 가능한 숫자 찾기
        int blockRow, blockCol;
        Set<Integer> possibles = new HashSet<>();
        
        if (p.row <= 3) blockRow = 1;
        else if (p.row <= 6) blockRow = 4;
        else blockRow = 7;
        
        if (p.col <= 3) blockCol = 1;
        else if (p.col <= 6) blockCol = 4;
        else blockCol = 7;

        Arrays.fill(check, false);
        for (int i = blockRow; i < blockRow + 3; i++) { // block에 없는 숫자 찾기
            for (int j = blockCol; j < blockCol + 3; j++) check[map[i][j]] = true;
        }        
        for (int i = 1; i <= 9; i++) if (!check[i]) possibles.add(i);
        
        Arrays.fill(check, false);
        for (int i = 1; i <= 9; i++) check[map[p.row][i]] = true; // row에 없는 숫자 찾기
        for (int i = 1; i <= 9; i++) if (!check[i]) possibles.add(i);
        
        Arrays.fill(check, false);
        for (int i = 1; i <= 9; i++) check[map[i][p.col]] = true; // col에 없는 숫자 찾기
        for (int i = 1; i <= 9; i++) if (!check[i]) possibles.add(i);

        p.possible.addAll(possibles);
    }
    
    static class Point {
        int row;
        int col;
        List<Integer> possible = new ArrayList<>();
        
        Point (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}