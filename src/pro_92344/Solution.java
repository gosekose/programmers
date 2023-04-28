package pro_92344;

class Solution {
    public int solution(int[][] board, int[][] skills) {
        int n = board.length; 
        int m = board[0].length;
        
        int[][] sum = new int[n + 1][m + 1];
        for (int[] skill : skills) {
            int r1 = skill[1], c1 = skill[2];
            int r2 = skill[3], c2 = skill[4];
            
            int degree = skill[5] * (skill[0] == 1 ? -1 : 1); // type == 1 이라면 degree 음수, type == 2라면 degree 양수
            
            sum[r1][c1] += degree;
            sum[r1][c2 + 1] += (degree * -1); // 주어진 인덱스보다 열 인덱스가 1 크면 부호 반대
            sum[r2 + 1][c1] += (degree * -1); // 주어진 인덱스보다 행 인덱스가 1 크면 부호 반대
            sum[r2 + 1][c2 + 1] += degree; // 주어진 인덱스보다 행, 열 인덱스가 각 1씩 크면, 부호 그대로
        }
        
        for (int r = 1; r < n; r++) {
            for (int c = 0; c < m; c++) { // 상하로 계산 주석 #50 (Ry = Ry-1 + Ry)
                sum[r][c] += sum[r - 1][c];
            }
        }
        
        for (int c = 1; c < m; c++) {
            for (int r = 0; r < n; r++) { // 좌우로 계산 주석 #54 (Cx = Cx-1 + Cx)
                sum[r][c] += sum[r][c - 1];
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] + sum[i][j] > 0) answer++;
            }
        }
        return answer;
    }
}

// 누적합
// 행렬 방식으로 아래 방향으로 행 인덱스 증가, 오른쪽 방향으로 열 인덱스 증가

// 만약 (0, 0)을 sum하면 sum 배열은 아래와 같음
// 4 -4
// -4 4

// Ry = Ry-1 + Ry (단, y는 1 이상, R은 행, y = 0이라면 Ry = Ry) 으로 업데이트
// 4 -4
// 0  0

// Cx = Cx-1 + Cx (단, x는 1 이상, C는 컬럼, x = 0이라면 Cx = Cx) 으로 업데이트
// 4 0
// 0 0
