package dp_2631;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());
        
        int[] map = new int[n];
        for (int row = 0; row < n; row++) map[row] = parseInt(br.readLine());
        int lis = getLISByBinarySearch(map); // 이진 탐색으로 LIS 길이 찾기
        
        System.out.println(n - lis); // 총 길이에서 LIS 개수를 빼면 이동가능한 길이가 나옴
    }
    
    static int getLISByBinarySearch(int[] arr) { // 이진 탐색
        List<Integer> dp = new ArrayList<>(); // dp 저장
        
        for (int num : arr) {
            if (dp.isEmpty() || num > dp.get(dp.size() - 1)) dp.add(num); // 처음이거나 마지막 숫자보다 크면 업데이트
            else {
                int left = 0, right = dp.size() - 1;
                while (left < right) { // 이진 탐색
                    int mid = left + (right - left) / 2;
                    if (dp.get(mid) < num) left = mid + 1;
                    else right = mid;
                }
                dp.set(right, num); // 위치에 값 업데이트
            }
        }
        
        return dp.size();
    }
}
