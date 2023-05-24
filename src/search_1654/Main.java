package search_1654;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int k = parseInt(st.nextToken());
        int n = parseInt(st.nextToken());

        int[] arr = new int[k]; // k개의 값을 저장할 배열
        long max = 0; // 최대값 저장 (가장 긴 랜선을 줄여 나가야 가장 큰 길이를 구할 수 있음)

        for (int i = 0; i < k; i++) {
            arr[i] = parseInt(br.readLine());
            if (max < arr[i]) max = arr[i]; // 최대값 업데이트
        }

        max++; //
        long min = 0;
        long mid = 0;

        while (min < max) { // 이분탐색으로 min < max인 조건으로 루프
            mid = (max + min) / 2; // 중간 값 설정하기

            long count = 0; // 카운트 개수 0 초기화

            for (int j : arr) {
                count += (j / mid); // count를 업데이트
                if (count >= n) break; // 개수가 n 보다 크면 종료
            }

            if (count < n) max = mid; // count
            else min = mid + 1; // upper bound로 min 값 증가
        }

        System.out.println(min - 1);
    }
}