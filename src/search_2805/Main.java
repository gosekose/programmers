package search_2805;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    static int[] trees;
    static int m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        trees = new int[n]; // n개의 나무
        
        m = parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int max = 0;
        for (int i = 0; i < n; i++) {
            trees[i] = parseInt(st.nextToken());
            max = Math.max(trees[i], max); // 가장 긴 나무를 선택하여 자를 위치 선정
        }
        
        System.out.println(find(max));
    }
    
    static long find(int max) {
        long value, left, mid; // 이분 탐색을 위한 long 값 20억 이상 될 수 있음
        long right = max; // right 최대값 선정
        value = left = mid = 0L; // value, left 초기화

        while(left < right) {
            mid = (left + right) / 2; // 이분탐색 중간 값 최대 40억

            for (int tree : trees) {
                if (tree > mid) value += tree - mid; // 잘리는 위치보다 큰 경우 더하기
            }
            
            if (value >= m) left = mid + 1; // 목표로 하는 값보다 같거나 크다면 upper bound
            else right = mid; // 아니라면 right
            value = 0; // value 초기화
        }
        
        return right - 1;
    }
}