package tp_3273;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(br.readLine());

        TwoSum ts = new TwoSum(n);
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            ts.setMap(i, parseInt(st.nextToken()));
        }
        ts.setSum(parseInt(br.readLine()));
        ts.run();
        System.out.println(ts.getPair());;
    }
}

class TwoSum {

    int n; // n개의 입력
    int[] map; // 입력 배열
    int target; // 목표 합
    int pair; // 순서쌍 개수

    public TwoSum(int n) {
        this.n = n;
        map = new int[n];
    }

    void setMap(int i, int value) {
        map[i] = value;
    }

    void run() {
        Arrays.sort(map); // 오름차순 정렬하여 투포인터가 가능하도록 설정
        if (map.length == 1) return; // n이 하나인 경우 불가

        int left = 0; // 시작점
        int right = n - 1; // 끝 점

        while(left < right) { // 두 좌표가 교차된다면 종료 (left -> right, right -> left는 두 참조 이름만 바뀐 것이기 때문)

            int value = map[left] + map[right];

            if (target > value) left++; // 만약 목표보다 값이 작다면 left를 증가시켜서 합이 커지도록
            else if (target < value) right--; // 목표보다 크다면 right를 줄여서 합이 작아지도록
            else {
                pair++;
                left++; // left를 증가시켜서 무한루프를 막고 시작점을 현재 인덱스 다음으로 넘겨줌
            }
        }
    }

    void setSum(int target) {
        this.target = target;
    }

    int getPair() {
        return pair;
    }
}
