package sw_14890;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = parseInt(st.nextToken());
        int L = parseInt(st.nextToken());
        
        PathMap path = new PathMap(N, L);
        
        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++) {
                path.setMap(row, col, parseInt(st.nextToken()));
            }
        }

        System.out.println(path.calculatePossiblePath());
    }
}

class PathMap {
    int n; // n개
    int len; // 경사로 길이
    int[][] mapR;
    int[][] mapC;
    
    PathMap (int n, int len) {
        this.n = n;
        this.len = len;
        mapR = new int[n][n];
        mapC = new int[n][n];
    }
    
    void setMap(int row, int col, int value) {
        mapR[row][col] = value;
        mapC[col][row] = value;
    }
    
    int calculatePossiblePath() {
        int possiblePath = 0;
        
        for (int row = 0; row < n; row++) {
            if (isPossiblePath(mapR[row])) possiblePath++;
            if (isPossiblePath(mapC[row])) possiblePath++;
        }
        
        return possiblePath;
    }
    
    boolean isPossiblePath(int[] map) {
        int preH = map[0]; // 기준이 되는 높이
        int cnt = 1; // 경사로를 놓을 수 있는 길이 (초기화 1)

        for (int col = 1; col < n; col++) {
            if (preH == map[col]) cnt++; // 높이가 같다면 cnt 증가 (혹시 더 높은 높이가 나타나면 cnt로 판별)

            else if (preH + 1 == map[col]) { // 만약 이전이 더 작다면
                if (cnt < len) return false; // 경사로를 놓을 수 있는 길이보다 짧다면
                preH++; // 기준점 업데이트 (높이 한칸 더 높게)
                cnt = 1; // 길이 초기화 (기준점은 아직 사다리를 안놓았으므로)
            }

            else if (preH - 1 == map[col]){ // 이전 높이가 더 크다면

                int nowCnt = 0;
                int nowH = map[col]; // 현재 높이를 기준으로 len개까지 같은 높이가 나와야 함

                for (int k = col; k < n; k++) { // 다음 행 부터 개수 세기
                    if (nowH != map[k]) break; // 현재 높이와 같지 않은 경우
                    nowCnt++;
                }
                if (nowCnt < len) return false; // 이보다 적은 경우 종료

                col += (len - 1); // col을 포함한 열부터 경사로를 놓으므로
                cnt = 0; // 새로 시작하는 지점은 이미 경사로가 놓여있으므로
                preH = nowH;
            }

            else return false;
        }
        return true;
    }
}