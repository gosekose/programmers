package pro_68645;

import java.util.*;

class Solution {
    public int[] solution(int n) {
        
        int[] answer = new int[(n * (n + 1)) / 2]; // 등차수열의 합으로 삼각형에 배열 개수 구하기 
        int[][] matrix = new int[n][n];
        
        int x = -1;
        int y = 0; // 좌표 초기화 
        int num = 1; // 매트릭스에 들어갈 숫자
        
        // % 3 을 활용하는 이유 : 아래, 오른쪽, 대각선으로 좌표 이동하기 위하
        // i, j는 좌표가 아니라 이동을 위한 인덱스
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) { 
                
                if (i % 3 == 0) x++; // 아래로 이동해서 매트릭스 채우기 (0, 0), (1, 0)
                else if (i % 3 == 1) y++; // 오른쪽 이동 좌표 채우기 (3, 1), (3, 2)
                else if (i % 3 == 2) { // 대각선으로 올라가며 값 채우기 (2, 2) (1, 1)
                    x--;
                    y--;
                }
                matrix[x][y] = num++;
            }
        }
        
        int t = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) break; 
                answer[t++] = matrix[i][j]; // 배열 순서대로 값 넣기
            }
        }

        return answer;
    }
}